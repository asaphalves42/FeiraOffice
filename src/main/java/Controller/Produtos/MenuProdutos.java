package Controller.Produtos;

import DAL.LerFornecedores;
import DAL.LerProdutos;
import DAL.LerStock;
import DAL.LerUnidade;
import Model.FornecedorProdutoData;
import Model.Produto;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuProdutos {

    LerProdutos lerProdutos = new LerProdutos();

    @FXML
    private TableView<FornecedorProdutoData> tableViewProdutos;

    @FXML
    private TableView<Map<String, Object>> tableView2;

    @FXML
    private Button btnAprovar;

    ObservableList<FornecedorProdutoData> produtos = FXCollections.observableArrayList();
    ObservableList<Map<String, Object>> stock = FXCollections.observableArrayList();



    public void initialize() throws IOException {
        tableViewProdutos.getColumns().clear();
        tableViewProdutos.getItems().clear();
        tabelaProdutos();
        tableView2.getColumns().clear();
        tableView2.getItems().clear();
        tabelastock();
    }

    public void tabelaProdutos() throws IOException {
        produtos.addAll(lerProdutos.lerProdutosFornecedores());

        if (!produtos.isEmpty()) {
            if (tableViewProdutos.getColumns().isEmpty()) {
                TableColumn<FornecedorProdutoData, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<FornecedorProdutoData, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<FornecedorProdutoData, String> colunaNomeFornecedor = new TableColumn<>("Nome do Fornecedor");
                TableColumn<FornecedorProdutoData, String> colunaIdUnidade = new TableColumn<>("Unidade");
                TableColumn<FornecedorProdutoData, Double> colunaPrecoUnitario = new TableColumn<>("Preço Unitário");

                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
                colunaIdUnidade.setCellValueFactory(cellData -> {
                    int id = cellData.getValue().getIdUnidade(); // Supondo que você tenha um método getIdUnidade() na classe FornecedorProdutoData
                    try {

                        Unidade unidade = LerUnidade.obterUnidadePorIdBaseDados(id);
                        return new SimpleStringProperty(unidade.getDescricao()); // Supondo que Unidade tenha um método getDescricao()
                    } catch (IOException e) {
                        e.printStackTrace(); // Trate a exceção de acordo com a sua lógica de erro
                        return new SimpleStringProperty("Erro ao obter unidade");
                    }
                });

                colunaPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));

                tableViewProdutos.getColumns().addAll(colunaId, colunaDescricao, colunaNomeFornecedor, colunaIdUnidade, colunaPrecoUnitario);
            }

            tableViewProdutos.setItems(produtos);
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }
    public void tabelastock() throws IOException {
        List<Map<String, Object>> produtosList = LerProdutos.lerProdutos();

        if (!produtosList.isEmpty()) {
            if (tableView2.getColumns().isEmpty()) {
                TableColumn<Map<String, Object>, String> colunaProdutoId = new TableColumn<>("ID Produto");
                TableColumn<Map<String, Object>, String> colunaIdProdutoFornec = new TableColumn<>("ID Produto no Fornecedor");
                TableColumn<Map<String, Object>, String> colunaProdutoDescricao = new TableColumn<>("Descricao");
                TableColumn<Map<String, Object>, String> colunaUnidade = new TableColumn<>("Unidade");
                TableColumn<Map<String, Object>, Double> colunaStock = new TableColumn<>("Stock");

                colunaProdutoId.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("produtoid")));
                colunaIdProdutoFornec.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("produtoidfornec")));
                colunaProdutoDescricao.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("produtodescricao")));
                colunaUnidade.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().get("unidade")));
                colunaStock.setCellValueFactory(cellData -> new SimpleDoubleProperty((double) cellData.getValue().get("stock")).asObject());

                tableView2.getColumns().addAll(colunaProdutoId,colunaIdProdutoFornec, colunaProdutoDescricao, colunaUnidade, colunaStock);
            }

            tableView2.getItems().addAll(produtosList);
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }
    @FXML
    void clickAprovar() {

    }



}
