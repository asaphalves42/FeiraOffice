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
        // quando clicar fechar a janela anterior e abrir o login outra vez

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FEIRA & OFFICE!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickUpload(ActionEvent event) {

    }

}
