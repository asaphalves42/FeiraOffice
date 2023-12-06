package BL;

import Utilidades.Mensagens;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe que lê os dados de um ficheiro XML e cria um ficheiro .txt com os mesmos.
 */
public class LerTXT {

    /**
     * Lê um arquivo XML e gera um arquivo de texto para conferir uma encomenda.
     *
     * @param selectedFile O arquivo XML a ser lido e processado.
     * @throws IOException Se ocorrer um erro durante a leitura ou gravação do arquivo.
     */
    public void lerFicheiroXML(File selectedFile) throws IOException {

        FileOutputStream fileOutputStream = null;
        PrintStream printStream = null;

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(selectedFile);
            doc.getDocumentElement().normalize();

            // Crie um arquivo para a saída
            File outputFile = new File("VerificaEncomenda.txt");

            fileOutputStream = new FileOutputStream(outputFile);
            printStream = new PrintStream(fileOutputStream);

            System.setOut(printStream);

            System.out.println("Confira sua encomenda antes de enviar!");
            System.out.println("-----------------------------------------------------------------\n");

            //referencia
            String referencia = doc.getElementsByTagName("OrderConfirmationReference").item(0).getTextContent();
            System.out.println("Referência: " + referencia);

            //data
            Node issuedDateNode = doc.getElementsByTagName("OrderConfirmationIssuedDate").item(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String Data = ((Element) issuedDateNode).getElementsByTagName("Year").item(0).getTextContent() + "-" +
                    ((Element) issuedDateNode).getElementsByTagName("Month").item(0).getTextContent() + "-" +
                    ((Element) issuedDateNode).getElementsByTagName("Day").item(0).getTextContent();
            LocalDate date = LocalDate.parse(Data, formatter);
            System.out.println("Data: " + Data);

            //fornecedor
            Node supplierPartyNode = doc.getElementsByTagName("SupplierParty").item(0);
            String codigoFornecedor = ((Element) supplierPartyNode).getElementsByTagName("PartyIdentifier").item(0).getTextContent();
            String nomeFornecedor = ((Element) supplierPartyNode).getElementsByTagName("Name").item(0).getTextContent();
            System.out.println("Fornecedor: " + codigoFornecedor);
            System.out.println("Nome: " + nomeFornecedor);


            //moeda
            Node moedaNode = doc.getElementsByTagName("CurrencyValue").item(0);
            String codigoMoeda = ((Element) moedaNode).getAttribute("CurrencyType");
            System.out.println("Moeda: " + codigoMoeda);

            //linhas
            System.out.println("A escrever linhas:");
            System.out.println("----------------------------------------------------------------------------------\n");

            NodeList lineItemNodes = doc.getElementsByTagName("OrderConfirmationLineItem");
            for (int i = 0; i < lineItemNodes.getLength(); i++) {
                Element lineItemNode = (Element) lineItemNodes.item(i);

                int sequencia = Integer.parseInt(lineItemNode.getElementsByTagName("OrderConfirmationLineItemNumber").item(0).getTextContent());
                System.out.println("Sequência linha: " + sequencia + "\n");

                Node productNode = lineItemNode.getElementsByTagName("Product").item(0);
                String produtoDescricao = ((Element) productNode).getElementsByTagName("ProductDescription").item(0).getTextContent();
                System.out.println("Descrição do produto: " + produtoDescricao);

                String codigoArtigoInterno = "";
                String codigoArtigoFornecedor = "";
                NodeList productIdentifierNodes = ((Element) productNode).getElementsByTagName("ProductIdentifier");
                for (int j = 0; j < productIdentifierNodes.getLength(); j++) {
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
                System.out.println("Código interno do produto: " + codigoArtigoInterno);
                System.out.println("Código no fornecedor: " + codigoArtigoFornecedor);


                Node priceNode = lineItemNode.getElementsByTagName("PricePerUnit").item(0);
                double preco = Double.parseDouble(((Element) priceNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                System.out.println("Preço unitário: " + preco);


                Node quantidadeNode = lineItemNode.getElementsByTagName("Quantity").item(0);
                Node ValorQuantidadeNode = ((Element) quantidadeNode).getElementsByTagName("Value").item(0);

                double quantidade = Double.parseDouble(ValorQuantidadeNode.getTextContent());
                String unidade = ((Element) ValorQuantidadeNode).getAttribute("UOM");
                System.out.println("Quantidade: " + quantidade);
                System.out.println("Unidade: " + unidade);

                Node valorTotalNode = lineItemNode.getElementsByTagName("LineBaseAmount").item(0);
                double Total = Double.parseDouble(((Element) valorTotalNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                System.out.println("Valor total do produto: " + Total + "\n");

                //taxas
                Node monetaryAdjustmentNode = ((Element) lineItemNode).getElementsByTagName("MonetaryAdjustment").item(0);
                NodeList taxAdjusmentNodes = ((Element) monetaryAdjustmentNode).getElementsByTagName("TaxAdjustment");
                System.out.println("Taxas e valores .........");
                for (int j = 0; j < taxAdjusmentNodes.getLength(); j++) {
                    //preparar as taxas
                    Element taxAdjusmentElement = (Element) taxAdjusmentNodes.item(j);

                    String tipoTaxa = taxAdjusmentElement.getAttribute("TaxType");
                    System.out.println("Tipo de taxa: " + tipoTaxa + " (Conferir se taxa é a mesma do país!)");

                    String PaisTaxa = taxAdjusmentElement.getElementsByTagName("TaxLocation").item(0).getTextContent();
                    System.out.println("País: " + PaisTaxa);

                    double percentagemTaxa = Double.parseDouble(taxAdjusmentElement.getElementsByTagName("TaxPercent").item(0).getTextContent());
                    System.out.println("Percentagem: " + percentagemTaxa);

                    Node taxAmountNode = taxAdjusmentElement.getElementsByTagName("TaxAmount").item(0);
                    double valorTaxa = Double.parseDouble(((Element) taxAmountNode).getElementsByTagName("CurrencyValue")
                            .item(0).getTextContent());
                    System.out.println("Total das taxas: " + valorTaxa);


                }

                System.out.println("______________________________________________________________\n");

            }

            // Abre o programa padrão associado ao arquivo PDF
            //Runtime.getRuntime().exec("cmd /c start \"\" \"" + outputFile.getAbsolutePath() + "\"");
            Runtime.getRuntime().exec("notepad.exe " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao mostrar arquivo selecionado!");
        } finally {
            // Fechar Streams após o processamento
            if (printStream != null) {
                printStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}
