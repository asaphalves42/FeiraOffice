package Controller.Administrador;

import Model.Utilizador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Menu com as opções do administrador
 */
public class MenuAdm {

    private Utilizador utilizador;

    @FXML
    private AnchorPane anchorPaneMenuAdm;
    @FXML
    private Button btnClientes;

    @FXML
    private Button btnAprovar;
    @FXML
    private Button btnLogout;

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

    /**
     * Carrega e exibe o menu para aprovação de estoque quando o botão "Aprovar" é clicado.
     */
    @FXML
    void clickAprovar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Encomenda/menuAprovarStock.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método de espaço reservado para lidar com o clique no botão "Estatísticas".
     */
    @FXML
    void clickEstatisticas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Admin/menuEstatisticas.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Carrega e exibe o menu de extrato de conta quando o botão "Fatura" é clicado.
     */
    @FXML
    void clickFatura() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Admin/menuContaCorrente.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Carrega e exibe o menu de funções para o fornecedor quando o botão "Fornecedor" é clicado.
     */
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

    /**
     * Carrega e exibe o menu de funções para o operador quando o botão "Operador" é clicado.
     */
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

    /**
     * Carrega e exibe o menu de produtos quando o botão "Produtos" é clicado.
     */
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



    /**
     * Encerra a aplicação quando o botão "Logout" é clicado.
     */
    @FXML
    void clickLogout() {
        System.exit(0);

    }

    /**
     * Inicializa a instância da classe com as informações do utilizador.
     * Este construtor recebe um objeto Utilizador para configurar a instância.
     *
     * @param utilizador O objeto Utilizador contendo as informações necessárias.
     */
    public void iniciaData(Utilizador utilizador) {
        this.utilizador = utilizador;
        System.out.println(utilizador.getEmail());

        System.out.println(utilizador.getTipo());
    }

    @FXML
    void clickClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp3/Views/Clientes/menuClientes.fxml"));
            AnchorPane root = loader.load();

            anchorPaneMenuAdm.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

