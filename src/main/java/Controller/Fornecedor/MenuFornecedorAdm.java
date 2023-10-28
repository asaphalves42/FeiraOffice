package Controller.Fornecedor;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuFornecedorAdm {
    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEstatisticas;

    @FXML
    private Button btnFatura;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnNovoFornecedor;

    @FXML
    private Button btnOperador;

    @FXML
    private Button btnProdutos;

    @FXML
    private TableView<?> tableView;

    @FXML
    void clickAprovar(ActionEvent event) {

    }

    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickEliminar(ActionEvent event) {

    }

    @FXML
    void clickEstatisticas(ActionEvent event) {

    }

    @FXML
    void clickFatura(ActionEvent event) {

    }

    @FXML
    void clickFornecedor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/menuFuncoesFornecedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu principal!");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void clickNovo(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/dialogAdicionarFornecedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADICIONAR FORNECEDOR!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickOperador(ActionEvent event) {

    }

    @FXML
    void clickProdutos(ActionEvent event) {

    }

}
