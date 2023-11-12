package Controller.Fornecedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuFornecedor {

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnUpload;

    @FXML
    void clickLogout() throws IOException {
        System.exit(0);

    }

    @FXML
    void clickUpload(ActionEvent event) {

    }

}
