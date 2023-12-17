package Controller.Administrador;

import BL.LerSepa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    @FXML
    void clickConfirmar() throws Exception {
/*
LerSepa.gerarSEPATransferencia(
              "FAC 01/20231",
              LocalDate.now(),
              200.00,
              "Empresa origem",
              "Morada empresa origem",
              "Localidade",
              "4500-001",
              "PT",
              "PT500000000000000",
              "ACTVPTPL",
              "Empresa destino",
              "Rua das flores",
              "4500-321",
              "PT500000000000000",
              "ACTVPTPL",
              "C:\\a\\SEPA.xml"
      );
 */



        



    }
    @FXML
    void clickCancelar(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
