package Controller.Produtos;

import Controller.DAL.LerProdutos;
import Model.Produto;
import Model.Produto;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class MenuProdutos {
    BaseDados baseDados = new BaseDados();

    @FXML
    private TableView<Produto> tableViewProdutos;

    ObservableList<Produto> produtos = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewProdutos.getColumns().clear();
        tableViewProdutos.getItems().clear();
        tabelaProdutos();
    }

    public void tabelaProdutos() throws IOException {

        LerProdutos lerProdutos = new LerProdutos();
        produtos.addAll(lerProdutos.lerProdutosBaseDados(baseDados));

        if (!produtos.isEmpty()) {
            if (tableViewProdutos.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<Produto, String> colunaIdFornecedor = new TableColumn<>("ID Fornecedor");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descriçao");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("ID Unidade");
                TableColumn<Produto, String> colunaIdExterno = new TableColumn<>("ID Externo");


                TableColumn<Produto, Integer> colunaEstado = new TableColumn<>("Estado");
               

                // Associe as colunas às propriedades da classe Produto
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaIdUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
                colunaIdExterno.setCellValueFactory(new PropertyValueFactory<>("idExterno"));
                colunaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));


                // Adicione as colunas à tabela
                tableViewProdutos.getColumns().add(colunaId);
                tableViewProdutos.getColumns().add(colunaIdFornecedor);
                tableViewProdutos.getColumns().add(colunaDescricao);
                tableViewProdutos.getColumns().add(colunaIdUnidade);
                tableViewProdutos.getColumns().add(colunaIdExterno);
                tableViewProdutos.getColumns().add(colunaEstado);

                // Configure a fonte de dados da tabela
                tableViewProdutos.setItems(produtos);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }




}
