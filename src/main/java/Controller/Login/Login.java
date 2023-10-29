package Controller.Login;

import Controller.DAL.LerUtilizadores;
import Model.TipoUtilizador;
import Utilidades.Mensagens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Login {


    @FXML
    private Button btnLogin;

    @FXML
    private TextField labelEmail;

    @FXML
    private PasswordField labelPassword;


    @FXML
    void clickLogin(ActionEvent event) throws IOException, SQLException {

        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        String username = labelEmail.getText();
        String password = labelPassword.getText();

        if (lerUtilizadores.lerUtilizadoresDaBaseDeDados()) {
            TipoUtilizador tipo = lerUtilizadores.verificarLoginUtilizador(username, password);
            if (tipo == TipoUtilizador.Administrador) {
                // Código para menu do administrador
                abrirMenu("/lp3/Views/Admin/menuAdm.fxml", "MENU ADMINISTRADOR!");
            } else if (tipo == TipoUtilizador.Operador) {
                // Código para menu do operador
                abrirMenu("/lp3/Views/Operador/menuOperador.fxml", "MENU OPERADOR!");
            } else if (tipo == TipoUtilizador.Fornecedor) {
                // Código para menu do fornecedor
                abrirMenu("/lp3/Views/Fornecedor/menuFornecedor.fxml", "MENU FORNECEDOR!");
            } else {
                Mensagens.Erro("Erro", "Credenciais inválidas. Ocorreu um erro ao realizar login!");
            }

        } else {
            Mensagens.Erro("Erro", "Ocorreu um erro ao realizar login!");
        }
    }
    
    private void abrirMenu(String resource, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


}






















