package Controller.Produtos;

import DAL.LerFornecedores;
import DAL.LerProdutos;

import DAL.LerStock;

import Model.Fornecedor;
import Model.Produto;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

/**
 * Classe que mostra os produtos disponíveis e o stock da empresa.
 */
public class MenuProdutos {
    BaseDados baseDados = new BaseDados();
    LerStock lerStock = new LerStock();
    LerProdutos lerProdutos = new LerProdutos();
    LerFornecedores lerFornecedor = new LerFornecedores();

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableView<Produto> tableView2;

    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    ObservableList<Produto> produtos2 = FXCollections.observableArrayList();

    /**
     * Inicializa o controller e configura as tabelas de produtos.
     *
     * @throws IOException Se houver um erro ao ler os dados.
     */

    public void initialize() throws IOException {

        tableViewProdutos.getColumns().clear();
        tableViewProdutos.getItems().clear();

        tabelaProdutos();
        tableView2.getColumns().clear();
        tableView2.getItems().clear();
        tabela2();

    }
    /**
     * Configura a tabela de produtos na interface do utilizador.
     *
     * @throws IOException Se houver um erro ao ler os dados.
     */
    public void tabelaProdutos() throws IOException {


        produtos.addAll(lerProdutos.lerProdutosBaseDados(baseDados));


        if (!produtos.isEmpty()) {
            if (tableViewProdutos.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descriçao");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("Unidade");
                TableColumn<Produto, Integer> colunaQuantidade = new TableColumn<>("Quantidade");


                // Associe as colunas às propriedades da classe Produto
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

                colunaIdUnidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricaoUnidade()));
                colunaQuantidade.setCellValueFactory(cellData -> {
                    int quantidade = 0;
                    try {
                        quantidade = lerStock.obterQuantidadePorIdProduto(baseDados, cellData.getValue().getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return new SimpleIntegerProperty(quantidade).asObject();
                });


                // Adicione as colunas à tabela
                tableViewProdutos.getColumns().add(colunaId);

                tableViewProdutos.getColumns().add(colunaDescricao);
                tableViewProdutos.getColumns().add(colunaIdUnidade);
                tableViewProdutos.getColumns().add(colunaQuantidade);


                // Configure a fonte de dados da tabela
                tableViewProdutos.setItems(produtos);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }
    /**
     * Configura a segunda tabela de produtos na interface do utilizador, incluindo informações de fornecedores.
     *
     * @throws IOException Se houver um erro ao ler os dados.
     */
    public void tabela2() throws IOException {
        produtos2.addAll(lerProdutos.lerProdutosBaseDados(baseDados));

        if (!produtos2.isEmpty()) {
            if (tableView2.getColumns().isEmpty()) {
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<Produto, String> colunaIdFornecedor = new TableColumn<>("Id no Fornecedor");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("Unidade");

                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("idFornecedorAsString"));

                tableView2.getColumns().addAll(colunaId, colunaDescricao, colunaIdFornecedor, colunaIdUnidade);
                tableView2.setItems(produtos2);

                colunaIdUnidade.setCellValueFactory(cellData -> {
                    Produto produtoTabela1 = cellData.getValue();
                    if (produtoTabela1 != null) {
                        return new SimpleStringProperty(produtoTabela1.getDescricaoUnidade());
                    } else {
                        return new SimpleStringProperty("");
                    }
                });

                // Configura a seleção da tabela2 para seleção única
                tableView2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

                // Adiciona um ouvinte para detectar mudanças na seleção da tabela2
                tableView2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produto>() {
                    @Override
                    public void changed(ObservableValue<? extends Produto> observable, Produto oldValue, Produto newValue) {
                        try {
                            exibirFornecedoresParaProdutoSelecionado();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }
    private void exibirFornecedoresParaProdutoSelecionado() throws IOException {
        Produto produtoSelecionado = tableView2.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            try {
                int idProduto = Integer.parseInt(produtoSelecionado.getId());
                List<Fornecedor> fornecedores = lerFornecedor.obterFornecedoresPorProduto(baseDados, idProduto);

                if (fornecedores != null) {
                    exibirDialogoFornecedores(fornecedores);
                } else {
                    Mensagens.Erro("Erro!", "Erro ao obter fornecedores do produto.");
                }
            } catch (IOException e) {
                Mensagens.Erro("Erro!", "Erro ao ler dados da base de dados.");
                e.printStackTrace();
            }
        }
    }

    private void exibirDialogoFornecedores(List<Fornecedor> fornecedores) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fornecedores para o Produto");
        alert.setHeaderText("Fornecedores associados ao produto selecionado");

        if (fornecedores != null && !fornecedores.isEmpty()) {
            TextArea textArea = new TextArea();
            textArea.setEditable(false);

            // Adiciona informações dos fornecedores ao TextArea
            for (Fornecedor fornecedor : fornecedores) {
                textArea.appendText("ID: " + fornecedor.getId() + "\n");
                textArea.appendText("Nome: " + fornecedor.getNome() + "\n");
                textArea.appendText("Endereço: " + fornecedor.getMorada1() + "\n\n");
            }

            alert.getDialogPane().setContent(textArea);
        } else {
            alert.setContentText("Não há fornecedores associados a este produto.");
        }

        alert.showAndWait();
    }
}
