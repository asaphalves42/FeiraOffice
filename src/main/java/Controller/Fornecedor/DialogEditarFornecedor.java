package Controller.Fornecedor;

import DAL.LerFornecedores;
import DAL.LerPaises;
import Controller.Email.ControllerEmail;
import Model.Fornecedor;
import Model.Pais;
import Model.UtilizadorFornecedor;
import Utilidades.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe que recebe os dados de um novo fornecedor, e edita os mesmos alterando assim na base de dados.
 */
public class DialogEditarFornecedor {

    @FXML
    private ComboBox<Pais> comboBoxPais; // Corrigido para usar o tipo apropriado
    private final DataSingleton dadosCompartilhados = DataSingleton.getInstance();
    LerPaises lerPaises = new LerPaises();
    BaseDados baseDados = new BaseDados();
    @FXML
    private TextField textoCodigoPostal;

    @FXML
    private TextField textoEmail;

    @FXML
    private TextField textoIdExterno;

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

    private Fornecedor fornecedorEmEdicao;


    public void initialize() throws IOException {

        ObservableList<Pais> listaDePaises = lerPaises.getListaDePaises(baseDados);
        comboBoxPais.setItems(listaDePaises);
    }

    /**
     * Define os campos de entrada com os detalhes do fornecedor selecionado para edição.
     *
     * @param fornecedor O fornecedor selecionado para edição.
     */
    @FXML
    public void setFornecedorSelecionado(Fornecedor fornecedor) {
        this.fornecedorEmEdicao = fornecedor;

        textoNome.setText(fornecedor.getNome());
        textoIdExterno.setText(fornecedor.getIdExterno());

        UtilizadorFornecedor utilizador = fornecedor.getIdUtilizador();
        if (utilizador != null) {
            UtilizadorFornecedor utilizadorEdicao = this.fornecedorEmEdicao.getIdUtilizador();

            utilizadorEdicao.setId(utilizador.getId());
            textoEmail.setText(utilizador.getEmail());
        }

        textoPassword.setText("");
        textoMorada1.setText(fornecedor.getMorada1());
        textoMorada2.setText(fornecedor.getMorada2());
        textoLocalidade.setText(fornecedor.getLocalidade());
        textoCodigoPostal.setText(fornecedor.getCodigoPostal());

    }

    /**
     * Manipula o evento de clique no botão "Cancelar".
     * Fecha a janela atual ao qual o botão pertence.
     *
     * @param event O evento de clique do botão.
     */
    @FXML
    void clickCancelar(ActionEvent event) {
        fecharJanela(event);
    }

    @FXML
    void clickComboPais(ActionEvent event) {
        // Lógica para manipular a seleção do país, se necessário
    }

    /**
     * Manipula o evento de clique no botão "Confirmar".
     * Coleta os dados dos campos de entrada, valida-os e, se válidos, atualiza o fornecedor na base de dados.
     *
     * @param event O evento de clique do botão.
     * @throws IOException Se ocorrer um erro durante a edição do fornecedor.
     */
    @FXML
    void clickConfirmar(ActionEvent event) throws IOException {
        try {

            String nome = textoNome.getText();
            String idExterno = textoIdExterno.getText();
            String email = textoEmail.getText();
            String password = textoPassword.getText();
            String morada1 = textoMorada1.getText();
            String morada2 = textoMorada2.getText();
            String localidade = textoLocalidade.getText();
            String codigoPostal = textoCodigoPostal.getText();
            Pais pais = comboBoxPais.getSelectionModel().getSelectedItem();


            // Verificar campos obrigatórios
            if (camposObrigatoriosVazios(nome, email, idExterno, password, morada1, localidade, codigoPostal)) {
                Mensagens.Erro("Campos obrigatórios!", "Preencha todos os campos obrigatórios.");
                return;
            }

            // Validar formato do e-mail
            ValidarEmail validarEmail = new ValidarEmail();
            if (!validarEmail.isValidEmailAddress(email)) {
                Mensagens.Erro("E-mail inválido", "Insira um endereço de e-mail válido.");
                return;
            }

            // Criar um objeto Utilizador com email e senha
            Encriptacao encriptacao = new Encriptacao();
            String senhaEncriptada = encriptacao.MD5(password);
            UtilizadorFornecedor utilizador = new UtilizadorFornecedor(fornecedorEmEdicao.getIdUtilizador().getId(), email, senhaEncriptada);

            // Criar um objeto Fornecedor
            Fornecedor fornecedor = new Fornecedor(fornecedorEmEdicao.getId(), nome, idExterno, morada1, morada2, localidade, codigoPostal, pais, utilizador);

            // Chamar a DAL para editar o fornecedor
            LerFornecedores editarFornecedor = new LerFornecedores();
            Fornecedor fornecedorEditado = editarFornecedor.atualizarFornecedorNaBaseDeDados(baseDados, fornecedor, pais, utilizador);

            dadosCompartilhados.setDataFornecedor(fornecedor);

            if (fornecedorEditado == null) {
                Mensagens.Erro("Erro", "Erro ao editar fornecedor!");
            } else {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                Mensagens.Informacao("Fornecedor editado", "As informações do fornecedor foram editadas com sucesso!");
               // ControllerEmail controllerEmail = new ControllerEmail();
                //controllerEmail.enviarEmail(email, password);
            }

        } catch (IOException e) {
            Mensagens.Erro("Erro!", "Erro na edição de fornecedor!");
        }
    }

    /**
     * Função para obter todos os campos obrigatórios para edição do fornecedor.
     * @param campos obtém os campos
     * @return
     */
    private boolean camposObrigatoriosVazios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true; // Pelo menos um campo obrigatório está vazio
            }
        }
        return false; // Todos os campos obrigatórios estão preenchidos
    }


    /**
     * Fecha a janela atual associada ao evento fornecido.
     *
     * @param event O evento que desencadeou o fechamento da janela.
     */
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}