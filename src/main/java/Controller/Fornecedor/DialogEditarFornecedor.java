package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerPaises;
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
        LerPaises lerPaises = new LerPaises();
        ObservableList<Pais> listaDePaises = lerPaises.getListaDePaises();
        comboBoxPais.setItems(listaDePaises);
    }

    @FXML
    public void setFornecedorSelecionado(Fornecedor fornecedor) {
        this.fornecedorEmEdicao = fornecedor;

        textoNome.setText(fornecedor.getNome());
        textoIdExterno.setText(fornecedor.getIdExterno());

        UtilizadorFornecedor utilizador = fornecedor.getIdUtilizador();
        if (utilizador != null) {
            textoEmail.setText(utilizador.getEmail());
        }

        textoPassword.setText(""); // Se desejar exibir a senha, ajuste conforme necessário
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
    void clickConfirnar(ActionEvent event) throws IOException {
        try {
            String nome = textoNome.getText();
            String idExterno = textoIdExterno.getText();
            String email = textoEmail.getText(); // validar email para o formato correto
            String password = textoPassword.getText();
            String morada1 = textoMorada1.getText();
            String morada2 = textoMorada2.getText();
            String localidade = textoLocalidade.getText();
            String codigoPostal = textoCodigoPostal.getText();
            Pais pais = comboBoxPais.getSelectionModel().getSelectedItem();


            // Verificar se algum campo obrigatório está vazio
            if (nome.isEmpty() || email.isEmpty() || idExterno.isEmpty() || password.isEmpty() || morada1.isEmpty() || localidade.isEmpty() || codigoPostal.isEmpty() || pais == null) {
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

            // Obtenha o fornecedor existente da instância compartilhada ou crie um novo se for nulo
            Fornecedor fornecedorExistente = dadosCompartilhados.getDataFornecedor();
            if (fornecedorExistente == null) {
                fornecedorExistente = new Fornecedor(); // ou o construtor apropriado
            }

            // Restante do código permanece inalterado

            //chamar a DAL para editar o fornecedor
            LerFornecedores editarFornecedor = new LerFornecedores();
            Fornecedor fornecedorEditado = editarFornecedor.atualizarFornecedorNaBaseDeDados(fornecedorExistente);

            if (fornecedorEditado == null) {
                Mensagens.Erro("Erro", "Erro ao editar fornecedor!");
            }

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Mensagens.Informacao("Fornecedor editado", "As informações do fornecedor foram editadas com sucesso!");

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro na edição de fornecedor!");
            e.printStackTrace();
        }
    }


    private void atualizarFornecedorNaBaseDeDados(Fornecedor fornecedor) throws IOException {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            String query = "UPDATE Fornecedor SET " +
                    "Nome = '" + fornecedor.getNome() + "', " +
                    "Id_Externo = '" + fornecedor.getIdExterno() + "', " +
                    "Morada1 = '" + fornecedor.getMorada1() + "', " +
                    "Morada2 = '" + fornecedor.getMorada2() + "', " +
                    "Localidade = '" + fornecedor.getLocalidade() + "', " +
                    "CodigoPostal = '" + fornecedor.getCodigoPostal() + "', " +
                    "Id_Pais = '" + fornecedor.getIdPais().getId() + "' " +
                    "WHERE id = " + fornecedor.getId();

            baseDados.Executar(query);
            baseDados.Desligar();
        } catch (Exception e) {
            e.printStackTrace();
            Mensagens.Erro("Erro na base de dados!", "Erro na atualização na base de dados!");
        }
    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}