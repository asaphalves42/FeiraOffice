package Controller.Fornecedor;

import BL.LerFicheiro;
import Controller.Encomenda.AprovarStock;
import DAL.LerFornecedores;
import BL.LerPDF;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import com.example.lp3_g2_feira_office_2023.OrderConfirmation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Model.Utilizador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

/**
 * Classe que contém as funções de um fornecedor, que inclui fazer o upload de um arquivo XML.
 */
public class MenuFornecedor {

    private Utilizador utilizador;

    @FXML
    private AnchorPane anchorPaneMenuFornecedor;

    @FXML
    private Button btnLogout;


    @FXML
    private Button btnMenuUpload;


    @FXML
    private Button btnHistEncomendas;

    @FXML
    private Label labelCodigoPostal;

    @FXML
    private Label labelID;

    @FXML
    private Label labelLocalidade;

    @FXML
    private Label labelMorada1;

    @FXML
    private Label labelMorada2;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelPais;

    @FXML
    private Label labelNomeMenu;


    /**
     * Inicializa a instância da classe com as informações do utilizador e carrega os dados do fornecedor correspondente.
     * Este método recebe um objeto Utilizador para configurar a instância e, em seguida, carrega os dados do fornecedor associado.
     *
     * @param utilizador O objeto Utilizador contendo as informações necessárias.
     * @throws IOException Se ocorrer um erro durante a leitura dos fornecedores da base de dados.
     */
    public void iniciaData(Utilizador utilizador) throws IOException {
        this.utilizador = utilizador;
        System.out.println(utilizador.getEmail());
        this.carregarFornecedor();
    }
    /**
     * Manipula o evento de clique no botão/menu "Upload" na interface gráfica do fornecedor.
     * Abre a interface para realizar o upload de encomendas, se um utilizador estiver autenticado.
     *
     * @throws IOException Se ocorrer um erro ao carregar a interface de upload de encomendas.
     */
    @FXML
    void clickMenuUpload() throws IOException {
        String resource = null;

        if (utilizador != null) {
            resource = "/lp3/Views/Fornecedor/MenuUploadEncomendas.fxml";
            abrirMenuUpload(resource, utilizador);
        }

    }
    /**
     * Manipula o evento de clique no botão/menu "Histórico de Encomendas" na interface gráfica do fornecedor.
     * Abre a interface para visualizar o histórico de encomendas, se um utilizador estiver autenticado.
     *
     * @throws IOException Se ocorrer um erro ao carregar a interface de histórico de encomendas.
     */
    @FXML
    void clickHistEncomendas() throws IOException {

            String resource = null;

            if (utilizador != null) {
                resource = "/lp3/Views/Fornecedor/VerEncomendasFornecedor.fxml";
                abrirMenuHistorico(resource, utilizador);
            }

    }
    /**
     * Abre a interface gráfica para o menu de upload de encomendas, específico para fornecedores.
     *
     * @param resource   O caminho do recurso FXML que define o layout da interface de upload de encomendas.
     * @param utilizador O utilizador autenticado que está a aceder ao menu de upload.
     * @throws IOException Se ocorrer um erro ao carregar ou manipular a interface gráfica de upload de encomendas.
     */
    private void abrirMenuUpload(String resource, Utilizador utilizador) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        AnchorPane root = loader.load();

        if (utilizador.getTipo() == TipoUtilizador.Fornecedor) {
            MenuUploadEncomenda menuUploadEncomenda = loader.getController();
            menuUploadEncomenda.iniciaData(utilizador);

            // Substitui o conteúdo de anchorPaneMenuAdm com o novo FXML
            anchorPaneMenuFornecedor.getChildren().setAll(root);

        }

    }
    /**
     * Abre a interface gráfica para o menu de histórico de encomendas, específico para fornecedores.
     *
     * @param resource   O caminho do recurso FXML que define o layout da interface do histórico de encomendas.
     * @param utilizador O utilizador autenticado que está a aceder ao menu de histórico.
     * @throws IOException Se ocorrer um erro ao carregar ou manipular a interface gráfica do histórico de encomendas.
     */
    private void abrirMenuHistorico(String resource, Utilizador utilizador) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        AnchorPane root = loader.load();

        if(utilizador.getTipo() == TipoUtilizador.Fornecedor){
            VerEncomendasFornecedor verEncomendasFornecedor = loader.getController();
            verEncomendasFornecedor.iniciaData(utilizador);

            // Substitui o conteúdo de anchorPaneMenuAdm com o novo FXML
            anchorPaneMenuFornecedor.getChildren().setAll(root);
        }
    }


    /**
     * Carrega os dados do fornecedor associado ao utilizador atual e exibe as informações em labels.
     * Este método lê os fornecedores da base de dados, encontra o fornecedor associado ao utilizador atual e exibe suas informações.
     *
     * @throws IOException Se ocorrer um erro durante a leitura dos fornecedores da base de dados.
     */

    public void carregarFornecedor() throws IOException {
        LerFornecedores fornecedor = new LerFornecedores();
        Fornecedor fornecedorLogado = null;
        for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados()) {
            if (this.utilizador.getId() == fornec.getIdUtilizador().getId()) {
                fornecedorLogado = fornec;
            }
        }
        assert fornecedorLogado != null;
        labelID.setText(String.valueOf(fornecedorLogado.getIdExterno()));
        labelNome.setText(fornecedorLogado.getNome());
        labelMorada1.setText(fornecedorLogado.getMorada1());
        labelMorada2.setText(fornecedorLogado.getMorada2());
        labelLocalidade.setText(fornecedorLogado.getLocalidade());
        labelCodigoPostal.setText(fornecedorLogado.getCodigoPostal());
        labelPais.setText(fornecedorLogado.getIdPais().getNome());
        labelNomeMenu.setText(fornecedorLogado.getNome());

    }

    /**
     * Encerra a aplicação ao clicar no botão de logout, terminando o processo.
     * Este método utiliza a chamada do método {@code System.exit(0)} para encerrar a aplicação.
     * Certifique-se de que não há tarefas em segundo plano ou processos importantes a decorrer antes de chamar este método.
     */
    @FXML
    void clickLogout() {
        System.exit(0);

    }
}




