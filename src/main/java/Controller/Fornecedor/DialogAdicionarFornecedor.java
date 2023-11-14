package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerPaises;
import Controller.DAL.LerUtilizadores;
import Model.*;
import Utilidades.*;
import Utilidades.Encriptacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class DialogAdicionarFornecedor {

    private final DataSingleton dadosCompartilhados =  DataSingleton.getInstance();

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Pais> comboBoxPais;

    @FXML
    private TextField textoCodigoPostal;
    @FXML
    private TextField textoIdExterno;
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
        LerPaises lerPaises = new LerPaises();
        ObservableList<Pais> listaDePaises = lerPaises.getListaDePaises();
        comboBoxPais.setItems(listaDePaises);
    }

    /**
     * Manipula o evento de clique no botão "Adicionar" para adicionar um novo fornecedor à base de dados.
     * Recebe os 'inputs' do utilizador e cria um fornecedor e um novo utilizador.
     *
     *
     * @param event O evento de clique que acionou a ação.
     * @throws IOException Se ocorrer um erro de E/S durante o processo de adição.
     */
    @FXML
    void clickAdicionar(ActionEvent event) throws IOException {

        try {
            String nome = textoNome.getText();
            String idExterno = textoIdExterno.getText();
            String email = textoEmail.getText(); //validar email para o formato correto
            String password = textoPassword.getText();
            String morada1 = textoMorada1.getText();
            String morada2 = textoMorada2.getText();
            String localidade = textoLocalidade.getText();
            String codigoPostal = textoCodigoPostal.getText();
            Pais pais = comboBoxPais.getSelectionModel().getSelectedItem();

            // Verificar se algum campo obrigatório está vazio
            if (nome.isEmpty() || email.isEmpty() || password.isEmpty() || morada1.isEmpty() || localidade.isEmpty() || codigoPostal.isEmpty() || pais == null) {
                // Exibir uma mensagem de erro ao utilizador
                Mensagens.Erro("Campos obrigatórios!", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }

            ValidarEmail validarEmail = new ValidarEmail();
            // Validar o formato do e-mail
            if (!validarEmail.isValidEmailAddress(email)) {
                Mensagens.Erro("E-mail inválido", "Por favor, insira um endereço de e-mail válido.");
                return;
            }

            // Encripte a senha usando o método MD5
            Encriptacao encriptacao = new Encriptacao();
            String senhaEncriptada = encriptacao.MD5(password);

            // Criar um objeto Utilizador com email e password
            UtilizadorFornecedor utilizador = new UtilizadorFornecedor();
            utilizador.setEmail(email);
            utilizador.setPassword(senhaEncriptada);

            //Criar um objeto fornecedor com o atributos
            Fornecedor fornecedor = new Fornecedor(0,
                    nome,
                    idExterno,
                    morada1,
                    morada2,
                    localidade,
                    codigoPostal,
                    pais,
                    utilizador);

            //chamar a DAL para adicionar o fornecedor
            LerFornecedores adicionarFornecedor = new LerFornecedores();
            Fornecedor fornecedorInserido = adicionarFornecedor.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador);

            dadosCompartilhados.setDataFornecedor(fornecedor);

            if (fornecedorInserido == null) {
                Mensagens.Erro("Erro", "Erro ao adicionar fornecedor!");
            }

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Mensagens.Informacao("Novo fornecedor", "Novo fornecedor inserido com sucesso!");


        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro na adição de fornecedor!");
        }
    }

    @FXML
    void clickCancelar(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void clickComboPais(ActionEvent event) {
        comboBoxPais.getSelectionModel().getSelectedItem();

    }


}
