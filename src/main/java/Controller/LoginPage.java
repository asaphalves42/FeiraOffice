package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginPage {

    @FXML
    private Button btnEntrar;

    @FXML
    void clickLogin(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/menuInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu principal!");
        stage.setScene(scene);
        stage.show();
    }
}






