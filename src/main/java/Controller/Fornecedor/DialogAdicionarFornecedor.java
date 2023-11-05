package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerPaises;
import Controller.DAL.LerUtilizadores;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DialogAdicionarFornecedor {

    private DataSingleton dadosCompartilhados =  DataSingleton.getInstance();

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Pais> comboBoxPais;

    @FXML
    private TextField textoCodigoPostal;

    @FXML
    private TextField textoEmail;

    @FXML
    private TextField textoLocalidade;

    @FXML
    private TextField textoMorada1;

    @FXML
    private TextField textoMorada2;

    @FXML
    private TextField textoNome;

    @FXML
    private PasswordField textoPassword;

    public void initialize() throws IOException {
        clickComboPais();
    }
    @FXML
    void clickAdicionar() throws IOException {

        try {
            String nome = textoNome.getText();
            String email = textoEmail.getText(); //validar email para o formato correto
            String password = textoPassword.getText();
            String morada1 = textoMorada1.getText();
            String morada2 = textoMorada2.getText();
            String localidade = textoLocalidade.getText();
            String codigoPostal = textoCodigoPostal.getText();
            Pais pais = comboBoxPais.getSelectionModel().getSelectedItem();

            // Verificar se algum campo obrigatório está vazio
            if (nome.isEmpty() || email.isEmpty() || password.isEmpty() || morada1.isEmpty() || morada2.isEmpty() || localidade.isEmpty() || codigoPostal.isEmpty() || pais == null) {
                // Exibir uma mensagem de erro ao utilizador
                Mensagens.Erro("Campos obrigatórios!", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }
            // Criar um objeto Utilizador com email e password
            UtilizadorFornecedor utilizador = new UtilizadorFornecedor();
            utilizador.setEmail(email);
            utilizador.setPassword(password);

            //Criar um objeto fornecedor com o atributos
            Fornecedor fornecedor = new Fornecedor(0, nome, morada1, morada2,localidade,codigoPostal, pais, utilizador);

            // Chamar a DAL para adicionar o utilizador à tabela "Utilizador"
            LerUtilizadores adicionarUtilizador = new LerUtilizadores();
            if (adicionarUtilizador.adicionarUtilizadorOperadorBaseDados(email, password)) {

                //chamar a DAL para adicionar o fornecedor
                LerFornecedores adicionarFornecedor = new LerFornecedores();
                adicionarFornecedor.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador);

            }

            // Limpar os campos de entrada após a adição bem-sucedida
            textoNome.clear();
            textoEmail.clear();
            textoPassword.clear();
            textoMorada1.clear();
            textoMorada2.clear();
            textoLocalidade.clear();
            textoCodigoPostal.clear();
            comboBoxPais.getSelectionModel().clearSelection();

            Mensagens.Informacao("Novo fornecedor", "Novo fornecedor inseridos com sucesso!");
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro na adição de fornecedor!");
        }
    }

    @FXML
    void clickCancelar(ActionEvent event) {

    }

    @FXML
    void clickComboPais() throws IOException {
        LerPaises lerPaises = new LerPaises();

        ObservableList<Pais> listaDePaises = lerPaises.getListaDePaises();
        comboBoxPais.setItems(listaDePaises);

    }


}
