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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class MenuProdutos {

    BaseDados baseDados = new BaseDados();
    LerStock lerStock = new LerStock();
    LerProdutos lerProdutos = new LerProdutos();
    LerFornecedores lerFornecedor = new LerFornecedores();

    @FXML
    private TableView<FornecedorProdutoData> tableViewProdutos;

    @FXML
    private TableView<FornecedorProdutoData> tableView2;

    ObservableList<FornecedorProdutoData> produtos = FXCollections.observableArrayList();
    ObservableList<FornecedorProdutoData> produtos2 = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewProdutos.getColumns().clear();
        tableViewProdutos.getItems().clear();
        tabelaProdutos();
        tableView2.getColumns().clear();
        tableView2.getItems().clear();

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
}
