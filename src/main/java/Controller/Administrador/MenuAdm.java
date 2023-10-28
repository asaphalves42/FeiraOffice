package Controller.Administrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuAdm {

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnEstatisticas;

    @FXML
    private Button btnFatura;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnOperador;

    @FXML
    private Button btnProdutos;

    @FXML
    void clickAprovar(ActionEvent event) {

    }

    @FXML
    void clickEstatisticas(ActionEvent event) {

    }

    @FXML
    void clickFatura(ActionEvent event) {

    }

    @FXML
    void clickFornecedor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/menuFuncoesFornecedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu principal!");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void clickOperador(ActionEvent event) {

    }

    @FXML
    void clickProdutos(ActionEvent event) {

    }

}
