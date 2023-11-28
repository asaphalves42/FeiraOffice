package Controller.Fornecedor;

import Controller.DAL.LerFicheiro;
import Controller.DAL.LerFornecedores;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import com.example.lp3_g2_feira_office_2023.OrderConfirmation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class MenuFornecedor {

    private Utilizador utilizador;
    private File arquivoSelecionado;

    BaseDados baseDados = new BaseDados();

    @FXML
    private Button btnEscolherFicheiro;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnUpload;

    @FXML
    private Label labelCodigoPostal;

    @FXML
    private Label labelFicheiroEscolhido;

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
        for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados(baseDados)){
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
        labelNomeMenu.setText(fornecedorLogado.getNome());

    }


    @FXML
    void clickLogout() {
        System.exit(0);

    }

    @FXML
    void clickEscolherFicheiro() {
        try {
            FileChooser novoFicheiro = new FileChooser();
            novoFicheiro.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documento", "*.xml"));
            arquivoSelecionado = novoFicheiro.showOpenDialog(new Stage());
            labelFicheiroEscolhido.setText(arquivoSelecionado.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void clickUpload() throws IOException {

        LerFornecedores fornecedor = new LerFornecedores();
        Fornecedor fornecedorLogado = null;
        for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados(baseDados)){
            if(this.utilizador.getId() == fornec.getIdUtilizador().getId()){
                fornecedorLogado = fornec;
            }
        }

        //Validação do id do fornecedor
        if (arquivoSelecionado != null) {
            LerFicheiro lerFicheiro = new LerFicheiro();
            OrderConfirmation orderConfirmation = lerFicheiro.orderConfirmation(arquivoSelecionado, utilizador);

            if (orderConfirmation == null){
                return;
            }

            // Obter o ID do fornecedor logado
            assert fornecedorLogado != null;
            String idFornecedor = fornecedorLogado.getIdExterno();

            // Obter o ID do fornecedor do arquivo
            String idFornecedorFicheiro = orderConfirmation.getOrderConfirmationHeader().getSupplierParty().getPartyIdentifier();

            // Validar se o ID é o mesmo do fornecedor
            if (!idFornecedor.equals(idFornecedorFicheiro)) {
                Mensagens.Erro("Erro!", "ID do fornecedor não coincide com o do ficheiro. Não foi possível fazer upload da encomenda");
            }

        } else {
            Mensagens.Erro("Erro!", "Erro ao ler o ficheiro!");
        }

    }
}



