package Controller.Fornecedor;

import DAL.LerFornecedores;
import DAL.LerPaises;
import DAL.LerUtilizadores;
import Controller.Email.ControllerEmail;
import Model.*;
import Utilidades.*;
import Utilidades.Encriptacao;
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


/**
 * Classe que recebe os dados de um novo fornecedor, e adiciona os mesmos na base de dados.
 */
public class DialogAdicionarFornecedor {

    private final DataSingleton dadosCompartilhados =  DataSingleton.getInstance();

    BaseDados baseDados = new BaseDados();
    LerPaises lerPaises = new LerPaises();
    LerFornecedores adicionarFornecedor = new LerFornecedores();

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Pais> comboBoxPais;

    @FXML
    private TextField labelBic;

    @FXML
    private TextField labelConta;

    @FXML
    private TextField labelIban;


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

    /**
     * Inicia os dados assim que classe é chamada.
     * @throws IOException lança uma exception caso um erro aconteça.
     */
    public void initialize() throws IOException {
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
            String conta = labelConta.getText();
            String bic = labelBic.getText();
            String iban = labelIban.getText();
            Pais pais = comboBoxPais.getSelectionModel().getSelectedItem();

            // Verificar se algum campo obrigatório está vazio
            if (nome.isEmpty() || email.isEmpty() || idExterno.isEmpty() ||password.isEmpty() || morada1.isEmpty() || localidade.isEmpty()
                    || codigoPostal.isEmpty() || conta.isEmpty() || bic.isEmpty() ||iban.isEmpty() || pais == null) {
                // Exibir uma mensagem de erro ao utilizador
                Mensagens.Erro("Campos obrigatórios!", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }

            ValidarEmail validarEmail = new ValidarEmail();
            LerUtilizadores lerUtilizadores = new LerUtilizadores();

            // Validar o formato do e-mail e verificar se já existe na base de dados
            if (!validarEmail.isValidEmailAddress(email) || !lerUtilizadores.verificarUserName(email)) {
                Mensagens.Erro("E-mail inválido", "Por favor, insira um endereço de e-mail válido e que não esteja em uso.");
                return;
            }

            // Encripte a senha usando o método MD5
            Encriptacao encriptacao = new Encriptacao();
            String senhaEncriptada = encriptacao.MD5(password);

            // Criar um objeto Utilizador com email e password
            UtilizadorFornecedor utilizador = new UtilizadorFornecedor(0, email, senhaEncriptada);

            //Criar um objeto fornecedor com o atributos
            Fornecedor fornecedor = new Fornecedor(0,
                    nome,
                    idExterno,
                    morada1,
                    morada2,
                    localidade,
                    codigoPostal,
                    pais,
                    utilizador,
                    bic,
                    conta,
                    iban
                    );

            //chamar a DAL para adicionar o fornecedor
            Fornecedor fornecedorInserido = adicionarFornecedor.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador);

            dadosCompartilhados.setDataFornecedor(fornecedor);

            if (fornecedorInserido == null) {
                Mensagens.Erro("Erro", "Erro ao adicionar fornecedor!");
            }

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Mensagens.Informacao("Novo fornecedor", "Novo fornecedor inserido com sucesso!");
            //ControllerEmail controllerEmail = new ControllerEmail();
            //controllerEmail.enviarEmail(email, password);



        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro na adição de fornecedor!");
        }
    }

    /**
     * Manipula o evento de clique no botão "Cancelar".
     * Fecha a janela atual ao qual o botão pertence.
     *
     * @param event O evento de clique do botão.
     */
    @FXML
    void clickCancelar(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    /**
     * Manipula o evento de seleção no ComboBox de países.
     * Obtém e não utiliza o item selecionado no ComboBox de países.
     *
     * @param event O evento de seleção do ComboBox.
     */
    @FXML
    void clickComboPais(ActionEvent event) {
        comboBoxPais.getSelectionModel().getSelectedItem();

    }


}
