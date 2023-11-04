package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Model.Fornecedor;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controller.DAL.LerFornecedores.fornecedores;

public class MenuFuncoesFornecedor{

    @FXML
    private SplitPane anchorPaneFuncoesFornc;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNovoFornecedor;

    @FXML
    private TableView<Fornecedor> tableViewFornecedores;


    public void initialize() throws IOException {
     tabelaFornecedores();
    }

    public void tabelaFornecedores() throws IOException {


        LerFornecedores lerFornecedores = new LerFornecedores();
        if (lerFornecedores.lerFornecedoresDaBaseDeDados()) {

            tableViewFornecedores.getItems().clear();// Limpa os itens da tabela
            tableViewFornecedores.setItems(null);

            if(tableViewFornecedores.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<Fornecedor, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<Fornecedor, String> colunaNome = new TableColumn<>("Nome");
                TableColumn<Fornecedor, String> colunaMorada1 = new TableColumn<>("Morada 1");
                TableColumn<Fornecedor, String> colunaMorada2 = new TableColumn<>("Morada 2");
                TableColumn<Fornecedor, String> colunaLocalidade = new TableColumn<>("Localidade");
                TableColumn<Fornecedor, String> colunaCodPostal = new TableColumn<>("Código postal");
                TableColumn<Fornecedor, Integer> colunaIdPais = new TableColumn<>("País");
                TableColumn<Fornecedor, Integer> colunaIdUtilizador = new TableColumn<>(" Tipo de utilizador");

                // Associe as colunas às propriedades da classe Fornecedor
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                colunaMorada1.setCellValueFactory(new PropertyValueFactory<>("morada1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("morada2"));
                colunaLocalidade.setCellValueFactory(new PropertyValueFactory<>("localidade"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
                colunaIdPais.setCellValueFactory(new PropertyValueFactory<>("idPaisString"));
                colunaIdUtilizador.setCellValueFactory(new PropertyValueFactory<>("idUtilizadorString"));

                // Adicione as colunas à tabela
                tableViewFornecedores.getColumns().add(colunaId);
                tableViewFornecedores.getColumns().add(colunaNome);
                tableViewFornecedores.getColumns().add(colunaMorada1);
                tableViewFornecedores.getColumns().add(colunaMorada2);
                tableViewFornecedores.getColumns().add(colunaLocalidade);
                tableViewFornecedores.getColumns().add(colunaCodPostal);
                tableViewFornecedores.getColumns().add(colunaIdPais);
                tableViewFornecedores.getColumns().add(colunaIdUtilizador);

                // Configure a fonte de dados da tabela
                tableViewFornecedores.setItems(fornecedores);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }

    @FXML
    void clickEditar() {

    }

    @FXML
    void clickEliminar() {

    }

    @FXML
    void clickNovo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Fornecedor/dialogAdicionarFornecedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADICIONAR FORNECEDOR!");
        stage.setScene(scene);
        stage.showAndWait();
    }


}
