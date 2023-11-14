package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Model.*;
import Utilidades.DataSingleton;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MenuFuncoesFornecedor {

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
    ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewFornecedores.getColumns().clear();
        tableViewFornecedores.getItems().clear();
        tabelaFornecedores();
    }

    /**
     * Preenche a tabela de fornecedores com dados lidos da base de dados e define as colunas da tabela, caso ainda não tenham sido definidas.
     *
     * @throws IOException Se ocorrer um erro durante a leitura da base de dados.
     */
    public void tabelaFornecedores() throws IOException {

        LerFornecedores lerFornecedores = new LerFornecedores();
        fornecedores.addAll(lerFornecedores.lerFornecedoresDaBaseDeDados());

        if (!fornecedores.isEmpty()) {
            if (tableViewFornecedores.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<Fornecedor, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<Fornecedor, String> colunaNome = new TableColumn<>("Nome");
                TableColumn<Fornecedor, String> colunaMorada1 = new TableColumn<>("Morada 1");
                TableColumn<Fornecedor, String> colunaMorada2 = new TableColumn<>("Morada 2");
                TableColumn<Fornecedor, String> colunaLocalidade = new TableColumn<>("Localidade");
                TableColumn<Fornecedor, String> colunaCodPostal = new TableColumn<>("Código postal");
                TableColumn<Fornecedor, Integer> colunaIdPais = new TableColumn<>("País");
                TableColumn<Fornecedor, Integer> colunaIdUtilizador = new TableColumn<>(" Tipo de utilizador");
                TableColumn<Fornecedor, String> colunaIdExterno = new TableColumn<>(" ID Externo");

                // Associe as colunas às propriedades da classe Fornecedor
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                colunaMorada1.setCellValueFactory(new PropertyValueFactory<>("morada1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("morada2"));
                colunaLocalidade.setCellValueFactory(new PropertyValueFactory<>("localidade"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
                colunaIdPais.setCellValueFactory(new PropertyValueFactory<>("idPaisString"));
                colunaIdUtilizador.setCellValueFactory(new PropertyValueFactory<>("idUtilizadorString"));
                colunaIdExterno.setCellValueFactory(new PropertyValueFactory<>("idExterno"));

                // Adicione as colunas à tabela
                tableViewFornecedores.getColumns().add(colunaId);
                tableViewFornecedores.getColumns().add(colunaNome);
                tableViewFornecedores.getColumns().add(colunaMorada1);
                tableViewFornecedores.getColumns().add(colunaMorada2);
                tableViewFornecedores.getColumns().add(colunaLocalidade);
                tableViewFornecedores.getColumns().add(colunaCodPostal);
                tableViewFornecedores.getColumns().add(colunaIdPais);
                tableViewFornecedores.getColumns().add(colunaIdUtilizador);
                tableViewFornecedores.getColumns().add(colunaIdExterno);

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

        DataSingleton data = DataSingleton.getInstance();
        fornecedores.add(data.getDataFornecedor());

    }
}
