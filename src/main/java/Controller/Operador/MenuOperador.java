package Controller.Operador;

import Model.Utilizador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Classe com funções de acesso restrito ao operador, aprovar uma encomenda, acesso às faturas, fornecedores e estatísticas.
 */
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
    /**
     * Manipula o evento de clique no botão "Aprovar".
     * Carrega a interface gráfica para aprovação de stock e substitui o conteúdo do painel principal.
     */
    @FXML
    void clickAprovar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Encomenda/menuAprovarStock.fxml"));
            AnchorPane root = loader.load();

            anchorPaneOperador.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Manipula o evento de clique no botão "Fatura".
     * Carrega a interface gráfica para consulta de conta corrente e substitui o conteúdo do painel principal.
     */
    @FXML
    void clickFatura() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Admin/menuContaCorrente.fxml"));
            AnchorPane root = loader.load();

            anchorPaneOperador.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * Manipula o evento de clique no botão "Fornecedor".
     * Carrega a interface gráfica com as funcionalidades disponíveis para o fornecedor e substitui o conteúdo do painel principal.
     */
    @FXML
    void clickFornecedor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Fornecedor/menuFuncoesFornecedor.fxml"));
            AnchorPane root = loader.load();

            anchorPaneOperador.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Manipula o evento de clique no botão "Logout".
     * Encerra a aplicação ao clicar no botão "Logout".
     */
    @FXML
    void clickLogout() {
        System.exit(0);
        ;
    }
    /**
     * Manipula o evento de clique no botão "Produto".
     * Carrega a interface gráfica para gestão de produtos e substitui o conteúdo do painel principal.
     */
    @FXML
    void clickProduto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Produtos/menuProdutos.fxml"));
            AnchorPane root = loader.load();

            anchorPaneOperador.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Inicializa os dados da interface com o utilizador logado.
     *
     * @param utilizador O utilizador que efetuou o login.
     */
    public void iniciaData(Utilizador utilizador) {
        this.utilizador = utilizador;
        System.out.println(utilizador.getEmail());

        System.out.println(utilizador.getTipo());
    }

}
