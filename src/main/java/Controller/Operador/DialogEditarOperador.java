package Controller.Operador;

import Controller.DAL.LerUtilizadores;
import Model.Utilizador;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import Utilidades.Mensagens;
import Utilidades.ValidarEmail;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogEditarOperador {

    BaseDados baseDados = new BaseDados();

    @FXML
    private TextField txtNovoEmail;

    @FXML
    private Button btnCancelar;

    @FXML
    private PasswordField txtNovaPassword;


    @FXML
    private Button btnGuardar;

    private Utilizador operador;


    public void setOperador(Utilizador operador) {
        this.operador = operador;
        txtNovoEmail.setText(operador.getEmail());

    }

    @FXML
    public void clickGuardar() throws IOException {
        try {
            String novoEmail = txtNovoEmail.getText();
            String novaPassword = txtNovaPassword.getText();

            Encriptacao encript = new Encriptacao();
            ValidarEmail validarEmail = new ValidarEmail();
            String encryptedNovaPassword = encript.MD5(novaPassword);

            // Verificar se algum campo obrigatório está vazio
            if (novoEmail.isEmpty() || novaPassword.isEmpty()) {
                // Exibir uma mensagem de erro ao utilizador
                Mensagens.Erro("Campos obrigatórios!", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }


            LerUtilizadores lerUtilizadores = new LerUtilizadores();

            // Validar o formato do e-mail e verificar se já existe na base de dados
            if (!validarEmail.isValidEmailAddress(novoEmail) || !lerUtilizadores.verificarUserName(novoEmail)) {
                Mensagens.Erro("E-mail inválido", "Por favor, insira um endereço de e-mail válido e que não esteja em uso.");
                return;
            }

            // Atualizar o objeto Utilizador com os novos valores
            operador.setEmail(novoEmail);
            operador.setPassword(novaPassword);  // Pode ser necessário atualizar a lógica conforme necessário

            // Chamar a DAL para atualizar o utilizador na tabela "Utilizador"

            if (lerUtilizadores.atualizarOperadorBaseDados(baseDados, operador.getId(), novoEmail, encryptedNovaPassword)) {
                Mensagens.Informacao("Operador atualizado!", "Operador atualizado com sucesso!");
            }

            // Fechar a janela de edição
            Stage stage = (Stage) btnGuardar.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro na edição de operador!");
        }
    }

    public void clickCancelar(javafx.event.ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}

