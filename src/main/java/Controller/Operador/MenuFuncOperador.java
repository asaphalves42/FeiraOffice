package Controller.Operador;

import Controller.DAL.LerUtilizadores;
import Model.Utilizador;
import Model.UtilizadorOperador;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuFuncOperador{

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNovoOperador;

    @FXML
    private TableView<Utilizador> tableViewOperador;
    ObservableList<Utilizador> utilizador= FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewOperador.getColumns().clear();
        tableViewOperador.getItems().clear();
        tabelaUtilizadoresOperador();
    }

    public void tabelaUtilizadoresOperador() throws IOException {

        LerUtilizadores lerUtilizadoresOperador = new LerUtilizadores();
        utilizador.addAll(lerUtilizadoresOperador.lerOperadoresDaBaseDados());



            // Defina as colunas da tabela
            TableColumn<Utilizador, Integer> colunaId = new TableColumn<>("ID");
            TableColumn<Utilizador, String> colunaEmail = new TableColumn<>("E-mail");
            TableColumn<Utilizador, String> colunaPassword = new TableColumn<>("Password encriptada");
            TableColumn<Utilizador, String> colunaTipo = new TableColumn<>("Tipo de utilizador");

            // Associe as colunas às propriedades da classe UtilizadorOperador
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colunaPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

            // Adicione as colunas à tabela
            tableViewOperador.getColumns().add(colunaId);
            tableViewOperador.getColumns().add(colunaEmail);
            tableViewOperador.getColumns().add(colunaPassword);
            tableViewOperador.getColumns().add(colunaTipo);

            // Configure a fonte de dados da tabela
            tableViewOperador.setItems(utilizador);

    }

    @FXML
    void clickNovo(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Operador/dialogAdicionarOperador.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADICIONAR OPERADOR!");
        stage.setScene(scene);
        stage.showAndWait();


        DataSingleton data = DataSingleton.getInstance();
        utilizador.add(data.getDataOperador());
    }

    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickEliminar(ActionEvent event) {

    }



}
