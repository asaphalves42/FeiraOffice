package Controller.Produtos;

import DAL.LerProdutos;
import DAL.LerUnidade;
import Model.Produto;
import Model.ProdutoVenda;
import Model.Stock;
import Model.Unidade;
import Utilidades.API;
import Utilidades.Mensagens;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static Utilidades.API.createProduct;
import static Utilidades.API.getProduct;

public class MenuProdutos {

    LerProdutos lerProdutos = new LerProdutos();

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableView<Stock> tableViewStock;

    @FXML
    private Button btnAprovar;

    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    ObservableList<Stock> produtosEmStock = FXCollections.observableArrayList();
    /**
     * Inicializa a classe.
     * Preenche a tabela de produtos em stock chamando o método {@link #tabelastock()}.
     * Adiciona um ouvinte de seleção ao {@code tableViewStock} para atualizar a tabela de produtos
     * sempre que uma nova linha é selecionada.
     *
     * @throws IOException Se ocorrer um erro durante a inicialização ou leitura dos produtos em estoque.
     */
    public void initialize() throws IOException {
        tabelastock();

        tableViewStock.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    tableViewProdutos.getColumns().clear(); // Clear existing columns
                    tabelaProdutos(newSelection.getIdProduto());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    /**
     * Preenche a tabela de produtos em estoque.
     * Limpa a tabela existente e adiciona as colunas e dados da lista de produtos em estoque.
     * As colunas incluem ID do Produto, Descrição, Unidade, Quantidade, UUID e Preço de Venda.
     *
     * @throws IOException Se ocorrer um erro durante a leitura dos produtos em estoque.
     */
    public void tabelastock() throws IOException {

        produtosEmStock.addAll(lerProdutos.lerStock());


        if (!produtosEmStock.isEmpty()) {
            if (tableViewStock.getColumns().isEmpty()) {
                TableColumn<Stock, String> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Stock, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<Stock, String> colunaUnidade = new TableColumn<>("Unidade");
                TableColumn<Stock, Integer> colunaQuantidade = new TableColumn<>("Quantidade");
                TableColumn<Stock, String> colunaUUID = new TableColumn<>("UUID");
                TableColumn<Stock, String> colunaPrecoVenda = new TableColumn<>("Preço de venda");

                colunaId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdProduto().getId()));
                colunaDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdProduto().getDescricao()));
                colunaUnidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUnidade().getDescricao()));
                colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
                colunaUUID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUuidVenda().getUUID()));

                colunaPrecoVenda.setCellValueFactory(cellData -> {
                    if (cellData.getValue().getUuidVenda().getUUID() != null) {
                        try {
                            return new SimpleStringProperty(lerProdutos.obterPVP(cellData.getValue().getIdProduto().getId()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        // Se a coluna UUID for nula, mostra o PVP da base de dados e adiciona um asterisco
                        String pvp = String.valueOf(cellData.getValue().getUuidVenda().getPrecoVenda());
                        return new SimpleStringProperty(pvp + "*");
                    }
                });

                tableViewStock.getColumns().addAll(colunaId, colunaDescricao, colunaUnidade, colunaQuantidade, colunaUUID, colunaPrecoVenda);
            }

            tableViewStock.setItems(produtosEmStock);

        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }

    }

    /**
     * Preenche a tabela de produtos com base no stock fornecido.
     * Limpa a tabela existente e adiciona as colunas e dados da lista de produtos associados ao estoque.
     *
     * @param stock O estoque para o qual deseja-se exibir os produtos.
     * @throws IOException Se ocorrer um erro durante a leitura dos produtos do estoque.
     */
    public void tabelaProdutos(Produto stock) throws IOException {

        if (stock!=null) {

            tableViewProdutos.getItems().clear();
            produtos.addAll(lerProdutos.lerProdutosFornecedores(stock.getId()));

            if (!produtos.isEmpty()) {
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<Produto, String> colunaNomeFornecedor = new TableColumn<>("Nome do Fornecedor");
                TableColumn<Produto, String> colunaIdFornecedor = new TableColumn<>("Id fornecedor");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("Unidade");
                TableColumn<Produto, Double> colunaPrecoUnitario = new TableColumn<>("Preço Unitário");
                TableColumn<Produto, String> colunaIdExterno = new TableColumn<>("Id no fornecedor");

                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaNomeFornecedor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFornecedor().getIdExterno()));
                colunaIdFornecedor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFornecedor().getNome()));
                colunaIdUnidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidade().getDescricao()));
                colunaPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
                colunaIdExterno.setCellValueFactory(new PropertyValueFactory<>("idExterno"));

                tableViewProdutos.getColumns().addAll(colunaId, colunaDescricao, colunaNomeFornecedor,
                        colunaIdFornecedor, colunaIdUnidade, colunaPrecoUnitario, colunaIdExterno);

                tableViewProdutos.setItems(produtos);


            } else {
                Mensagens.Erro("Erro!", "Erro ao ler tabela");
            }
        }

    }

    /**
     * Função para aprovar um produto manualmente; envia os produtos para a API.
     * @throws IOException em caso de erro de leitura ou gravação.
     * @throws SQLException em caso de erro de acesso à base de dados.
     */
    @FXML
    void clickAprovar() throws IOException, SQLException {

        Stock produtoSelecionado = tableViewStock.getSelectionModel().getSelectedItem();

        if(produtoSelecionado!=null){

            String dadosProduto = lerProdutos.construirDadosDoProdutoAprovar(produtoSelecionado);

            String respostaApi = createProduct(dadosProduto);

            String produtoCriado = lerProdutos.getUUIDFromResponse(respostaApi);

            ProdutoVenda produto = produtoSelecionado.getUuidVenda();
            produto.setUUID(produtoCriado);
            String idProduto = produtoSelecionado.getIdProduto().getId();

            boolean produtoNaBd = lerProdutos.criarProdutoNaBaseDadosAprovar(produto, idProduto);

            if (produtoNaBd){
                Mensagens.Informacao("Sucesso","Produto aprovado com sucesso!");
            } else {
                Mensagens.Erro("Erro","Erro ao aprovar produto!");
            }
        } else {
            Mensagens.Erro("Erro","Selecione um produto!");
        }

    }
}
