package Controller.Operador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuOperador {

    @FXML
    private AnchorPane anchorPaneOperador;

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnFatura;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnProduto;

    @FXML
    void clickAprovar(ActionEvent event) {

    }

    @FXML
    void clickFatura(ActionEvent event) {

    }

    @FXML
    void clickFornecedor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Fornecedor/menuFuncoesFornecedor.fxml"));
            AnchorPane root = loader.load();

            anchorPaneOperador.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clickProduto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Produtos/menuProdutos.fxml"));
            AnchorPane root = loader.load();

            anchorPaneOperador.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
