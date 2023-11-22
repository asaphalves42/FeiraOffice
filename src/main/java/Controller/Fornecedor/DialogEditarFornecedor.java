package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Model.Fornecedor;
import Model.Pais;
import Model.UtilizadorFornecedor;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DialogEditarFornecedor {



    @FXML
    private ComboBox<?> comboBoxPais;




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
    public void setFornecedorSelecionado(Fornecedor fornecedor) {
        textoNome.setText(fornecedor.getNome());
        textoIdExterno.setText(fornecedor.getIdExterno());

        UtilizadorFornecedor utilizador = fornecedor.getIdUtilizador();
        if (utilizador != null) {
            textoEmail.setText(utilizador.getEmail());
        }

        // Se desejar exibir a senha, você pode removê-lo ou definir um texto diferente
        textoPassword.setText("");

        textoMorada1.setText(fornecedor.getMorada1());
        textoMorada2.setText(fornecedor.getMorada2());
        textoLocalidade.setText(fornecedor.getLocalidade());
        textoCodigoPostal.setText(fornecedor.getCodigoPostal());


    }
    @FXML
    void clickCancelar(ActionEvent event) {

    }

    @FXML
    void clickComboPais(ActionEvent event) {

    }

    Fornecedor fornecedor = new Fornecedor();

    @FXML
    void clickConfirnar(ActionEvent event) throws IOException {
        // Obter os novos valores dos campos
        String novoNome = textoNome.getText();
        String novoIdExterno = textoIdExterno.getText();
        String novoEmail = textoEmail.getText();
        String novaMorada1 = textoMorada1.getText();
        String novaMorada2 = textoMorada2.getText();
        String novaLocalidade = textoLocalidade.getText();
        String novoCodigoPostal = textoCodigoPostal.getText();
        Pais novoPais = (Pais) comboBoxPais.getValue();




        fornecedor.setNome(novoNome);
        fornecedor.setIdExterno(novoIdExterno);

        if (fornecedor.getIdUtilizador() != null) {
            fornecedor.getIdUtilizador().setEmail(novoEmail);
        }

        fornecedor.setMorada1(novaMorada1);
        fornecedor.setMorada2(novaMorada2);
        fornecedor.setLocalidade(novaLocalidade);
        fornecedor.setCodigoPostal(novoCodigoPostal);
        fornecedor.setIdPais(novoPais);

        atualizarFornecedorNaBaseDeDados(fornecedor);


        fecharJanela(event);


        Mensagens.Informacao("Edição Concluída", "O fornecedor foi editado com sucesso!");
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
                    //"Id_Pais = '" + novoPais.getId() + "' " +
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
