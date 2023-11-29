package Controller.Operador;

import Controller.DAL.LerUtilizadores;
import Model.UtilizadorOperador;
import Utilidades.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogAdicionarOperador {

    BaseDados baseDados = new BaseDados();

    private final DataSingleton dadosCompartilhados =DataSingleton.getInstance();

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField textoEmail;

    @FXML
    private PasswordField textoPassword;

    /**
     * Manipula o evento de clique no botão "Adicionar". Este método é responsável por validar o formato do email,
     * criptografar a senha, criar um objeto de utilizador, adicionar o operador à base de dados e fechar a janela atual.
     *
     * @param event O evento de clique que acionou a ação.
     * @throws IOException se ocorrer um erro de entrada/saída durante a execução.
     */
    @FXML
    void clickAdicionar(ActionEvent event) throws IOException {
        try {
            String email = textoEmail.getText(); //validar email para o formato correto
            String password = textoPassword.getText();

            Encriptacao encript = new Encriptacao();
            ValidarEmail validarEmail = new ValidarEmail();
            String encryptedPassword = encript.MD5(password);

            // Verificar se algum campo obrigatório está vazio
            if (email.isEmpty() || password.isEmpty()){

                // Exibir uma mensagem de erro ao utilizador
                Mensagens.Erro("Campos obrigatórios!", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }

            LerUtilizadores lerUtilizadores = new LerUtilizadores();
            if (!validarEmail.isValidEmailAddress(email) || !lerUtilizadores.verificarUserName(email)) {
                Mensagens.Erro("E-mail inválido", "Por favor, insira um endereço de e-mail válido e que não esteja em uso.");
                return;
            }

            // Criar um objeto Utilizador com email e password
            UtilizadorOperador utilizador = new UtilizadorOperador(0,email,encryptedPassword);
            utilizador.setEmail(email);
            utilizador.setPassword(password);


            // Chamar a DAL para adicionar o utilizador à tabela "Utilizador"
            LerUtilizadores adicionarOperador = new LerUtilizadores();
            if(adicionarOperador.adicionarOperadorBaseDados(baseDados, email, encryptedPassword)){
                Mensagens.Informacao("Novo operador!", "Novo operador inserido com sucesso!");
            }
            dadosCompartilhados.setDataOperador(utilizador);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

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
