package Controller.Fornecedor;

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

        try {
            File selectedFile = FileUtils.chooseXMLFile();

            // creating a constructor of file class and
            // parsing an XML file
            //File file = new File("C:\\a\\XML-Sample.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(selectedFile);
            doc.getDocumentElement().normalize();


            //referencia
            String referencia = doc.getElementsByTagName("OrderConfirmationReference").item(0).getTextContent();
            System.out.println("Referencia documento - " + referencia);

            //data
            Node issuedDateNode = doc.getElementsByTagName("OrderConfirmationIssuedDate").item(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String Data = ((Element)issuedDateNode).getElementsByTagName("Year").item(0).getTextContent() +"-"+
                    ((Element)issuedDateNode).getElementsByTagName("Month").item(0).getTextContent() + "-"+
                    ((Element)issuedDateNode).getElementsByTagName("Day").item(0).getTextContent();
            LocalDate date = LocalDate.parse(Data, formatter);
            System.out.println("Data documento - " + Data);

            //fornecedor
            Node supplierPartyNode = doc.getElementsByTagName("SupplierParty").item(0);
            String codigoFornecedor = ((Element)supplierPartyNode).getElementsByTagName("PartyIdentifier").item(0).getTextContent();
            System.out.println("Fornecedor documento - " + codigoFornecedor);
            Fornecedor objetoFornecedor = null;
            // com base no codigo do fornecedor, obtemos da base de dados o fornecedor, e passamos para a encomenda o objecto
            // se não existir, então nao pode importar

            //moeda
            Node moedaNode = doc.getElementsByTagName("CurrencyValue").item(0);
            String codigoMoeda = ((Element) moedaNode).getAttribute("CurrencyType");
            System.out.println("Moeda  - " + codigoMoeda);
            Moeda objetoMoeda = null;
            // com base no codigo do moeda, obtemos da base de dados o fornecedor, e passamos para a encomenda o objecto
            // se não existir, então nao pode importar



            //linhas
            System.out.println("A escrever linhas");
            NodeList lineItemNodes = doc.getElementsByTagName("OrderConfirmationLineItem");
            for (int i = 0; i < lineItemNodes.getLength(); i++) {
                Element lineItemNode = (Element) lineItemNodes.item(i);

                int sequencia = Integer.parseInt(lineItemNode.getElementsByTagName("OrderConfirmationLineItemNumber").item(0).getTextContent());
                System.out.println("sequencia linha - " + sequencia);

                Node productNode = lineItemNode.getElementsByTagName("Product").item(0);
                String produtoDescricao = ((Element) productNode).getElementsByTagName("ProductDescription").item(0).getTextContent();
                System.out.println("produto  descricao - " + produtoDescricao);

                String codigoArtigoInterno="";
                String codigoArtigoFornecedor="";
                NodeList productIdentifierNodes = ((Element) productNode).getElementsByTagName("ProductIdentifier");
                for (int j = 0; j < productIdentifierNodes.getLength(); j++)
                {
                    Element productIdentifierElement = (Element) productIdentifierNodes.item(j);
                    // Verificar se o atributo "ProductIdentifierType" é "PartNumber"
                    String agency = productIdentifierElement.getAttribute("Agency");
                    if ("Buyer".equals(agency)) {
                        // Obter o valor do elemento "ProductIdentifier"
                        codigoArtigoInterno = productIdentifierElement.getTextContent();
                    }
                    if ("Supplier".equals(agency)) {
                        // Obter o valor do elemento "ProductIdentifier"
                        codigoArtigoFornecedor = productIdentifierElement.getTextContent();
                    }
                }
                System.out.println("Produto codigo interno - " + codigoArtigoInterno);
                System.out.println("Produto codigo fornecedor - " + codigoArtigoFornecedor);


                Node priceNode = lineItemNode.getElementsByTagName("PricePerUnit").item(0);
                double preco = Double.parseDouble(((Element) priceNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                System.out.println("produto preço - " + preco);


                Node quantidadeNode = lineItemNode.getElementsByTagName("Quantity").item(0);
                Node ValorQuantidadeNode = ((Element) quantidadeNode).getElementsByTagName("Value").item(0);

                double quantidade = Double.parseDouble(ValorQuantidadeNode.getTextContent());
                String unidade = ((Element)ValorQuantidadeNode).getAttribute("UOM");
                System.out.println("produto quantidade - " + quantidade);
                System.out.println("produto unidade - " + unidade);

                Node valorTotalNode = lineItemNode.getElementsByTagName("LineBaseAmount").item(0);
                double Total = Double.parseDouble(((Element)valorTotalNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                System.out.println("Produto valor total - " + Total);

                //taxas
                Node monetaryAdjustmentNode = ((Element) lineItemNode).getElementsByTagName("MonetaryAdjustment").item(0);
                NodeList taxAdjusmentNodes = ((Element)monetaryAdjustmentNode).getElementsByTagName("TaxAdjustment");
                System.out.println("produto taxas .....");
                for (int j = 0; j < taxAdjusmentNodes.getLength(); j++)
                {
                    //preparar as taxas
                    Element taxAdjusmentElement = (Element) taxAdjusmentNodes.item(j);

                    String tipoTaxa = taxAdjusmentElement.getAttribute("TaxType");
                    System.out.println("produto taxas tipo - " + tipoTaxa);

                    String PaisTaxa = taxAdjusmentElement.getElementsByTagName("TaxLocation").item(0).getTextContent();
                    System.out.println("produto taxas pais - " + PaisTaxa);

                    double percentagemTaxa =  Double.parseDouble(taxAdjusmentElement.getElementsByTagName("TaxPercent").item(0).getTextContent());
                    System.out.println("produto taxas percentagem - " + percentagemTaxa);

                    Node taxAmountNode = taxAdjusmentElement.getElementsByTagName("TaxAmount").item(0);
                    double valorTaxa = Double.parseDouble(((Element)taxAmountNode).getElementsByTagName("CurrencyValue")
                            .item(0).getTextContent());
                    System.out.println("produto taxas valor - " + valorTaxa);

                    //obter taxa pais com base no codigo "tipo taxa"
                    //criar classe LinhaTaxa para depois associar ao objecto LinhaEncomenda
                }


                System.out.println("_______________");

            }
            // criar encomenda
            double valorImposto = 0; //somar das linhas;
            double valorLinhas = 0; //somar das linhas
            Encomenda fatura = new Encomenda(0, referencia, date, objetoFornecedor, objetoMoeda, valorImposto, valorLinhas);



        }

        // This exception block catches all the exception
        // raised.
        // For example if we try to access a element by a
        // TagName that is not there in the XML etc.
        catch (Exception e) {
            System.out.println(e);
        }

    }


}
