package Controller.Login;

import Controller.Administrador.MenuAdm;
import Controller.DAL.LerUtilizadores;
import Controller.Fornecedor.MenuFornecedor;
import Controller.Operador.MenuOperador;
import Model.TipoUtilizador;
import Model.Utilizador;
import Utilidades.LogUser;
import Utilidades.Mensagens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    void clickLogin() throws IOException, SQLException {
        Utilizador utilizador = getUtilizador();
        abrirMenuCorrespondente(utilizador);
    }

    private void abrirMenuCorrespondente(Utilizador utilizador) throws IOException {
        String resource = null;
        String title = null;

        if(utilizador == null) {
            Mensagens.Erro("Erro", "Credenciais inválidas. Ocorreu um erro ao realizar login!");
        }

        else if (utilizador.getTipo() == TipoUtilizador.Administrador) {
            resource = "/lp3/Views/Admin/menuAdm.fxml";
            title = "MENU ADMINISTRADOR!";

            abrirMenu(resource, title, utilizador);
            fecharJanelaAtual();

        } else if (utilizador.getTipo() == TipoUtilizador.Operador) {
            resource = "/lp3/Views/Operador/menuOperador.fxml";
            title = "MENU OPERADOR!";

            abrirMenu(resource, title, utilizador);
            fecharJanelaAtual();

        } else if (utilizador.getTipo() == TipoUtilizador.Fornecedor) {
            resource = "/lp3/Views/Fornecedor/menuFornecedor.fxml";
            title = "MENU FORNECEDOR!";

            abrirMenu(resource, title, utilizador);
            fecharJanelaAtual();
        } else {
            Mensagens.Erro("Erro", "Credenciais inválidas. Ocorreu um erro ao realizar login!");
        }

    }

    private void abrirMenu(String resource, String title, Utilizador utilizador) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());

        if (utilizador.getTipo() == TipoUtilizador.Administrador) {
            MenuAdm menuAdm = fxmlLoader.getController();
            menuAdm.iniciaData(utilizador);
        } else if (utilizador.getTipo() == TipoUtilizador.Operador) {
            MenuOperador menuOperador = fxmlLoader.getController();
            menuOperador.iniciaData(utilizador);
        } else if (utilizador.getTipo() == TipoUtilizador.Fornecedor) {
            MenuFornecedor menuFornecedor = fxmlLoader.getController();
            menuFornecedor.iniciaData(utilizador);
        }

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void fecharJanelaAtual() {
        Stage currentStage = (Stage) btnLogin.getScene().getWindow();
        currentStage.close();
    }

    private Utilizador getUtilizador() throws SQLException {
        // Instância da classe para ler os utilizadores.
        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        // Recupera o nome de usuário e senha inseridos dos elementos da interface gráfica.
        String username = labelEmail.getText(); // Nome de utilizador
        String password = labelPassword.getText(); // Senha

        // Verifica as credenciais de 'login' e determina o tipo de utilizador.
        return lerUtilizadores.verificarLoginUtilizador(username, password);
    }


    public void clickEnter(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode().toString().equals("ENTER")){
            clickLogin();
        }
    }
}






















