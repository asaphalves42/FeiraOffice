package Controller.Operador;

import Model.UtilizadorOperador;
import Utilidades.BaseDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

import static Model.TipoUtilizador.Operador;

public class MenuFuncOperador {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNovoOperador;

    @FXML
    private TableView<?> tableViewOperador;

    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickEliminar(ActionEvent event) {

    }
    @FXML
    void clickNovo(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Operador/dialogAdicionarOperador.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADICIONAR OPERADOR!");
        stage.setScene(scene);
        stage.showAndWait();

    }


}
