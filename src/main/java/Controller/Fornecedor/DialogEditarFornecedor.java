package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerPaises;
import Controller.DAL.LerUtilizadores;
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

public class DialogEditarFornecedor {

    @FXML
    private ComboBox<Pais> comboBoxPais; // Corrigido para usar o tipo apropriado
    private final DataSingleton dadosCompartilhados =  DataSingleton.getInstance();
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

    // Removido a inicialização do fornecedor aqui
    private Fornecedor fornecedorEmEdicao;


    public void initialize() throws IOException {

        ObservableList<Pais> listaDePaises = lerPaises.getListaDePaises(baseDados);
        comboBoxPais.setItems(listaDePaises);
    }

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



    @FXML
    void clickCancelar(ActionEvent event) {
        fecharJanela(event);
    }

    @FXML
    void clickComboPais(ActionEvent event) {
        // Lógica para manipular a seleção do país, se necessário
    }

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
            LerUtilizadores  lerUtilizadores = new LerUtilizadores();
            ValidarEmail validarEmail = new ValidarEmail();
            if (!email.equals(fornecedorEmEdicao.getIdUtilizador().getEmail())) {

                if (!validarEmail.isValidEmailAddress(email) || !lerUtilizadores.verificarUserName(email)) {
                    Mensagens.Erro("E-mail inválido", "Por favor, insira um endereço de e-mail válido e que não esteja em uso.");
                    return;
                }
            }

            // Criar um objeto Utilizador com email e senha
            Encriptacao encriptacao = new Encriptacao();
            String senhaEncriptada = encriptacao.MD5(password);
            UtilizadorFornecedor utilizador = new UtilizadorFornecedor(fornecedorEmEdicao.getIdUtilizador().getId(), email, senhaEncriptada);

            // Criar um objeto Fornecedor
            Fornecedor fornecedor = new Fornecedor(fornecedorEmEdicao.getId(), nome, idExterno, morada1, morada2, localidade, codigoPostal, pais, utilizador);

            // Chamar a DAL para editar o fornecedor
            LerFornecedores editarFornecedor = new LerFornecedores();
            Fornecedor fornecedorEditado = editarFornecedor.atualizarFornecedorNaBaseDeDados(baseDados,fornecedor, pais, utilizador);

            if (fornecedorEditado == null) {
                Mensagens.Erro("Erro", "Erro ao editar fornecedor!");
            } else {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                Mensagens.Informacao("Fornecedor editado", "As informações do fornecedor foram editadas com sucesso!");
            }

        } catch (IOException e) {
            Mensagens.Erro("Erro!", "Erro na edição de fornecedor!");
        }
    }

    private boolean camposObrigatoriosVazios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true; // Pelo menos um campo obrigatório está vazio
            }
        }
        return false; // Todos os campos obrigatórios estão preenchidos
    }




    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}