package Controller.Administrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuAdm {

    @FXML
    private AnchorPane anchorPaneMenuAdm;

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnEstatisticas;

    @FXML
    private Button btnFatura;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnOperador;

    @FXML
    private Button btnProdutos;

    @FXML
    void clickAprovar() {

    }

    @FXML
    void clickEstatisticas() {

    }

    @FXML
    void clickFatura() {

    }

    @FXML
    void clickFornecedor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Fornecedor/menuFuncoesFornecedor.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clickOperador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Operador/menuFuncOperador.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clickProdutos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Produtos/menuProdutos.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
