package Controller.Fornecedor;

import Controller.DAL.LerPaises;
import Model.Fornecedor;
import Model.Pais;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogEditarFornecedor implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ComboBox<?> comboBoxPais;

    @FXML
    private Label labelCodigoPostal;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelId;

    @FXML
    private Label labelLocalidade;

    @FXML
    private Label labelMorada1;

    @FXML
    private Label labelMorada2;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelPassword;

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

    private TableView<Fornecedor> tableViewFornecedores;

    private Fornecedor fornecedorSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LerPaises lerPaises = new LerPaises();
        try {
            ObservableList<Pais> listaDePaises = lerPaises.getListaDePaises();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clickEditar(ActionEvent event) {
        Fornecedor fornecedorSelecionado = tableViewFornecedores.getSelectionModel().getSelectedItem();
        textoNome.setEditable(true);
        textoIdExterno.setEditable(true);
        textoMorada1.setEditable(true);
        textoMorada2.setEditable(true);
        textoLocalidade.setEditable(true);
        textoCodigoPostal.setEditable(true);
    }

    @FXML
    void clickSalvar(ActionEvent event) {
        fornecedorSelecionado.setNome(textoNome.getText());
        fornecedorSelecionado.setIdExterno(textoIdExterno.getText());
        fornecedorSelecionado.setMorada1(textoMorada1.getText());
        fornecedorSelecionado.setMorada2(textoMorada2.getText());
        fornecedorSelecionado.setLocalidade(textoLocalidade.getText());
        fornecedorSelecionado.setCodigoPostal(textoCodigoPostal.getText());

        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clickCancelar(ActionEvent event) {
        // Close the edit window without saving changes
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
