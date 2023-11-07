package Controller.Operador;

import Controller.DAL.LerUtilizadores;
import Model.UtilizadorOperador;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuFuncOperador {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNovoOperador;

    @FXML
    private TableView<UtilizadorOperador> tableViewOperador;
    ObservableList<UtilizadorOperador> operadores = FXCollections.observableArrayList();

    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickEliminar(ActionEvent event) {

    }

    public void tabelaUtilizadoresOperador() throws IOException {
        LerUtilizadores lerUtilizadoresOperador = new LerUtilizadores();
     //   operadores.addAll(lerUtilizadoresOperador.lerUtilizadoresDaBaseDeDados());

        if (!operadores.isEmpty()) {
            if (tableViewOperador.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<UtilizadorOperador, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<UtilizadorOperador, String> colunaEmail = new TableColumn<>("Email");
                TableColumn<UtilizadorOperador, String> colunaPassword = new TableColumn<>("Password");

                // Associe as colunas às propriedades da classe UtilizadorOperador
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                colunaPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

                // Adicione as colunas à tabela
                tableViewOperador.getColumns().add(colunaId);
                tableViewOperador.getColumns().add(colunaEmail);
                tableViewOperador.getColumns().add(colunaPassword);

                // Configure a fonte de dados da tabela
                tableViewOperador.setItems(operadores);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
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
        operadores.add(data.getDataOperador());
    }


}
