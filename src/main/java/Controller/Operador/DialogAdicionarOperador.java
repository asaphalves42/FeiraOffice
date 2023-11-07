package Controller.Operador;

import Controller.DAL.LerUtilizadores;
import Model.UtilizadorOperador;
import Utilidades.DataSingleton;
import Utilidades.Encriptacao;
import Utilidades.Mensagens;
import Utilidades.ValidarEmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogAdicionarOperador {

    private final DataSingleton dadosCompartilhados =DataSingleton.getInstance();

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField textoEmail;

    @FXML
    private PasswordField textoPassword;


    @FXML
    void clickAdicionar(ActionEvent event) throws IOException {
        try {
            String email = textoEmail.getText(); //validar email para o formato correto
            String password = textoPassword.getText();

            Encriptacao encript = new Encriptacao();
            ValidarEmail validarEmail = new ValidarEmail();
            String encryptedPassword = encript.MD5(password);
            // Validar o formato do e-mail
            if (!validarEmail.isValidEmailAddress(email)) {
                Mensagens.Erro("E-mail inválido", "Por favor, insira um endereço de e-mail válido.");
                return;
            }

            // Criar um objeto Utilizador com email e password
            UtilizadorOperador utilizador = new UtilizadorOperador(0,email,encryptedPassword);
            utilizador.setEmail(email);
            utilizador.setPassword(password);


            // Chamar a DAL para adicionar o utilizador à tabela "Utilizador"
            LerUtilizadores adicionarOperador = new LerUtilizadores();
            if(adicionarOperador.adicionarOperadorBaseDados(email, encryptedPassword)){
                Mensagens.Informacao("Novo operador", "Novo operador inseridos com sucesso!");
            }
            dadosCompartilhados.setDataOperador(utilizador);

            // Limpar os campos de entrada após a adição bem-sucedida
            textoEmail.clear();
            textoPassword.clear();

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro na adição de operador!");
        }
    }

    @FXML
    void clickCancelar(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
