package Controller.Administrador;

import BL.LerSepa;
import DAL.LerContaCorrente;
import DAL.LerPagamento;
import Model.ContaCorrente;
import Model.FeiraOffice;
import Model.Fornecedor;
import Utilidades.BaseDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


public class PagamentoSepa {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Label labelBic;

    @FXML
    private Label labelBicForn;

    @FXML
    private Label labelCodPostal;

    @FXML
    private Label labelContaForn;

    @FXML
    private Label labelIban;

    @FXML
    private Label labelIbanForn;

    @FXML
    private Label labelIdForn;

    @FXML
    private Label labelLocalidade;

    @FXML
    private Label labelMorada;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelNomeForn;

    @FXML
    private Label labelPais;

    @FXML
    private Label labelValor;
    ContaCorrente dadosConta;
    FeiraOffice dadosFeira;

    public void getDados(ContaCorrente contaCorrente) {
        if(contaCorrente!=null){
            this.dadosConta = contaCorrente;
        }

    }
    LerContaCorrente lerContaCorrente = new LerContaCorrente();
    LerPagamento lerPagamento = new LerPagamento();
    BaseDados baseDados = new BaseDados();

    public void initialize() throws SQLException, IOException {
        carregarLabelsFeira();
        carregarLabelsFornecedor();
    }

    public void carregarLabelsFeira() throws SQLException, IOException {
        dadosFeira = lerPagamento.lerDadosDaEmpresa(baseDados);

        if(dadosFeira!=null){
            labelNome.setText(dadosFeira.getNome());
            labelMorada.setText(dadosFeira.getMorada());
            labelLocalidade.setText(dadosFeira.getLocalidade());
            labelCodPostal.setText(dadosFeira.getCodPostal());
            labelPais.setText(dadosFeira.getPais().getNome());
            labelIban.setText(dadosFeira.getIban());
            labelBic.setText(dadosFeira.getBic());

        }

    }

    public void carregarLabelsFornecedor() throws SQLException, IOException {
        dadosConta = lerContaCorrente.lerContaCorrente(baseDados, dadosConta.getId());
        labelValor.setText(String.valueOf(dadosConta.getSaldo()));

        if (dadosConta != null && dadosConta.getIdFornecedor() != null) {
            Fornecedor fornecedorAssociado = dadosConta.getIdFornecedor();
            labelIdForn.setText(fornecedorAssociado.getIdExterno());
            labelNomeForn.setText(fornecedorAssociado.getNome());
            labelIbanForn.setText(fornecedorAssociado.getIban());
            labelContaForn.setText(fornecedorAssociado.getConta());
            labelBicForn.setText(fornecedorAssociado.getBic());

        }
    }

    @FXML
    void clickConfirmar() throws Exception {


    }

    @FXML
    void clickCancelar(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
