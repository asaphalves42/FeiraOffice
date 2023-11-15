package Controller.Fornecedor;

import Controller.DAL.LerFicheiro;
import Controller.DAL.LerFornecedores;
import Model.*;
import Utilidades.DataSingleton;
import Utilidades.FileUtils;
import Utilidades.LogUser;
import Utilidades.Mensagens;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MenuFornecedor {

    private Utilizador utilizador;

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
    private Button btnLogout;

    @FXML
    private Button btnUpload;

    /**
     * Inicializa a instância da classe com as informações do utilizador e carrega os dados do fornecedor correspondente.
     * Este método recebe um objeto Utilizador para configurar a instância e, em seguida, carrega os dados do fornecedor associado.
     *
     * @param utilizador O objeto Utilizador contendo as informações necessárias.
     * @throws IOException Se ocorrer um erro durante a leitura dos fornecedores da base de dados.
     */
    public void iniciaData(Utilizador utilizador) throws IOException {
        this.utilizador = utilizador;
        this.carregarFornecedor();
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
        for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados()){
            if(this.utilizador.getId() == fornec.getIdUtilizador().getId()){
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

    }


    @FXML
    void clickLogout() {
        System.exit(0);

    }


    @FXML
    void clickUpload() {
        LerFicheiro lerFicheiroXML = new LerFicheiro();
        lerFicheiroXML.lerFicheiroXML();
        

    }


}
