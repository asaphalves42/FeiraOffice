package Controller.Produtos;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerProdutos;

import Controller.DAL.LerStock;

import Model.Produto;
import Model.Produto;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class MenuProdutos {
    BaseDados baseDados = new BaseDados();
    LerStock lerStock = new LerStock();

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableView<Produto> tableView2;



    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    ObservableList<Produto> produtos2 = FXCollections.observableArrayList();



    public void initialize() throws IOException {

        tableViewProdutos.getColumns().clear();
        tableViewProdutos.getItems().clear();

        tabelaProdutos();
        tableView2.getColumns().clear();
        tableView2.getItems().clear();
        tabela2();

    }

    public void tabelaProdutos() throws IOException {

        LerProdutos lerProdutos = new LerProdutos();


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

    public void tabela2() throws IOException {
        LerProdutos lerProdutos = new LerProdutos();
        LerFornecedores lerFornecedor = new LerFornecedores();

        produtos2.addAll(lerProdutos.lerProdutosBaseDados(baseDados));

        if (!produtos2.isEmpty()) {
            if (tableView2.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<Produto, String> colunaIdFornecedor = new TableColumn<>("Id no Fornecedor");
                TableColumn<Produto, String> colunaNomeFornecedor = new TableColumn<>("Fornecedor");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("Unidade");

                // Associe as colunas às propriedades da classe Produto
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("idFornecedorAsString"));
                colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>(""));






                // Adicione as colunas à tabela
                tableView2.getColumns().addAll(colunaId, colunaDescricao, colunaIdFornecedor,colunaNomeFornecedor, colunaIdUnidade);

                // Configure a fonte de dados da tabela
                tableView2.setItems(produtos2);

                // Configurar a cellValueFactory para a coluna de Unidade na tabela2
                colunaIdUnidade.setCellValueFactory(cellData -> {
                    // Obtém a linha selecionada na tabela1
                    Produto produtoTabela1 = cellData.getValue();
                    // Usar cellData.getValue() em vez de tableViewProdutos.getSelectionModel().getSelectedItem()
                    if (produtoTabela1 != null) {
                        // Retorna a Unidade da linha correspondente na tabela1
                        return new SimpleStringProperty(produtoTabela1.getDescricaoUnidade());
                    } else {
                        return new SimpleStringProperty("");
                    }
                });
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }




}
