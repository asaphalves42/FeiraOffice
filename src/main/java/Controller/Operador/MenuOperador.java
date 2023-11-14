package Controller.Operador;

import Model.Utilizador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class MenuOperador {

    private Utilizador utilizador;

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
    private Button btnLogout;

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
    void clickLogout() {
        System.exit(0);
        ;
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

    public void iniciaData(Utilizador utilizador) {
        this.utilizador = utilizador;
        System.out.println(utilizador.getEmail());

        System.out.println(utilizador.getTipo());
    }

}
