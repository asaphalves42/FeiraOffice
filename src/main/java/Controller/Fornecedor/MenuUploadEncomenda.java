package Controller.Fornecedor;

import BL.LerFicheiro;
import BL.LerPDF;
import DAL.LerFornecedores;
import Model.Fornecedor;
import Model.Utilizador;
import Utilidades.Mensagens;
import com.example.lp3_g2_feira_office_2023.OrderConfirmation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuUploadEncomenda {
    private Utilizador utilizador;
    private File arquivoSelecionado;

    @FXML
    private SplitPane anchorPaneFuncoesFornc;

    @FXML
    private Button btnClickUpload;

    @FXML
    private Button btnEscolherFicheiro;

    @FXML
    private Label labelFicheiroEscolhido;
    public void iniciaData(Utilizador utilizador) throws IOException {
        this.utilizador = utilizador;
        System.out.println(utilizador.getTipo());
        System.out.println(utilizador.getEmail());

    }

    /**
     * Manipula o evento de clique no botão para escolher um arquivo. Abre um seletor de arquivo
     * para escolher um documento XML, exibe o caminho do arquivo selecionado e lê o conteúdo do arquivo XML.
     */
    @FXML
    void clickEscolherFicheiro() {
        try {
            FileChooser novoFicheiro = new FileChooser();
            novoFicheiro.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documento", "*.xml"));
            arquivoSelecionado = novoFicheiro.showOpenDialog(new Stage());
            labelFicheiroEscolhido.setText(arquivoSelecionado.getAbsolutePath());

            //LerTXT verificaFicheiroDAL = new LerTXT();
            LerPDF verificaFicheiroDAL = new LerPDF();
            verificaFicheiroDAL.lerFicheiroXML(arquivoSelecionado);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Manipula o evento de clique no botão de upload. Lê o arquivo XML escolhido, valida o ID do fornecedor,
     * e faz upload da encomenda se o ID do fornecedor no arquivo coincidir com o ID do fornecedor logado.
     *
     * @throws IOException Se ocorrer um erro durante o upload da encomenda.
     */
    @FXML
    void clickUpload() throws IOException {
        LerFornecedores fornecedor = new LerFornecedores();
        Fornecedor fornecedorLogado = null;
        for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados()){
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
