package BL;

import Utilidades.Mensagens;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe que lê os dados de um ficheiro XML e cria um PDF com os mesmos.
 */
public class LerPDF {

    /**
     * Lê um arquivo XML e gera um arquivo PDF para conferir uma encomenda.
     *
     * @param selectedFile O arquivo XML a ser lido e processado.
     * @throws IOException Se ocorrer um erro durante a leitura ou gravação do arquivo.
     */
    public void lerFicheiroXML(File selectedFile) throws IOException {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Document doc = (Document) db.parse(selectedFile);
            //doc.getDocumentElement().normalize();

            // Parse o arquivo XML para obter o objeto org.w3c.dom.Document
            org.w3c.dom.Document doc = db.parse(selectedFile);

            // Caminho do arquivo PDF em branco que será criado
            String caminhoArquivo = "conferirEncomenda.pdf";

            try (FileOutputStream fileOutputStream = new FileOutputStream(caminhoArquivo)) {
                PdfWriter writer = new PdfWriter(fileOutputStream);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                // Abre o documento para edição
                document.add(new Paragraph("Confira sua encomenda antes de enviar!"));

                //referencia
                String referencia = doc.getElementsByTagName("OrderConfirmationReference").item(0).getTextContent();
                document.add(new Paragraph("Referência: " + referencia));

                //data
                Node issuedDateNode = doc.getElementsByTagName("OrderConfirmationIssuedDate").item(0);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String Data = ((Element) issuedDateNode).getElementsByTagName("Year").item(0).getTextContent() + "-" +
                        ((Element) issuedDateNode).getElementsByTagName("Month").item(0).getTextContent() + "-" +
                        ((Element) issuedDateNode).getElementsByTagName("Day").item(0).getTextContent();
                LocalDate date = LocalDate.parse(Data, formatter);
                document.add(new Paragraph("Data: " + Data));

                //fornecedor
                Node supplierPartyNode = doc.getElementsByTagName("SupplierParty").item(0);
                String codigoFornecedor = ((Element) supplierPartyNode).getElementsByTagName("PartyIdentifier").item(0).getTextContent();
                String nomeFornecedor = ((Element) supplierPartyNode).getElementsByTagName("Name").item(0).getTextContent();
                document.add(new Paragraph("Fornecedor: " + codigoFornecedor));
                document.add(new Paragraph("Nome: " + nomeFornecedor));

                //moeda
                Node moedaNode = doc.getElementsByTagName("CurrencyValue").item(0);
                String codigoMoeda = ((Element) moedaNode).getAttribute("CurrencyType");
                document.add(new Paragraph("Moeda: " + codigoMoeda));

                //linhas
                document.add(new Paragraph("Itens:"));
                document.add(new Paragraph("______________________________________________________________\n"));

                NodeList lineItemNodes = doc.getElementsByTagName("OrderConfirmationLineItem");
                for (int i = 0; i < lineItemNodes.getLength(); i++) {
                    Element lineItemNode = (Element) lineItemNodes.item(i);

                    int sequencia = Integer.parseInt(lineItemNode.getElementsByTagName("OrderConfirmationLineItemNumber").item(0).getTextContent());
                    document.add(new Paragraph("Item número: " + sequencia + "\n"));

                    Node productNode = lineItemNode.getElementsByTagName("Product").item(0);
                    String produtoDescricao = ((Element) productNode).getElementsByTagName("ProductDescription").item(0).getTextContent();
                    document.add(new Paragraph("Descrição do produto: " + produtoDescricao));

                    String codigoArtigoInterno = "";
                    String codigoArtigoFornecedor = "";
                    NodeList productIdentifierNodes = ((Element) productNode).getElementsByTagName("ProductIdentifier");
                    for (int j = 0; j < productIdentifierNodes.getLength(); j++) {
                        Element productIdentifierElement = (Element) productIdentifierNodes.item(j);
                        String agency = productIdentifierElement.getAttribute("Agency");
                        if ("Buyer".equals(agency)) {
                            codigoArtigoInterno = productIdentifierElement.getTextContent();
                        }
                        if ("Supplier".equals(agency)) {
                            codigoArtigoFornecedor = productIdentifierElement.getTextContent();
                        }
                    }
                    document.add(new Paragraph("Código interno do produto: " + codigoArtigoInterno));
                    document.add(new Paragraph("Código no fornecedor: " + codigoArtigoFornecedor));

                    Node priceNode = lineItemNode.getElementsByTagName("PricePerUnit").item(0);
                    double preco = Double.parseDouble(((Element) priceNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                    document.add(new Paragraph("Preço unitário: " + preco));

                    Node quantidadeNode = lineItemNode.getElementsByTagName("Quantity").item(0);
                    Node ValorQuantidadeNode = ((Element) quantidadeNode).getElementsByTagName("Value").item(0);

                    double quantidade = Double.parseDouble(ValorQuantidadeNode.getTextContent());
                    String unidade = ((Element) ValorQuantidadeNode).getAttribute("UOM");
                    document.add(new Paragraph("Quantidade: " + quantidade));
                    document.add(new Paragraph("Unidade: " + unidade));

                    Node valorTotalNode = lineItemNode.getElementsByTagName("LineBaseAmount").item(0);
                    double total = Double.parseDouble(((Element) valorTotalNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                    document.add(new Paragraph("Valor total do produto: " + total + "\n"));

                    //taxas
                    Node monetaryAdjustmentNode = ((Element) lineItemNode).getElementsByTagName("MonetaryAdjustment").item(0);
                    NodeList taxAdjusmentNodes = ((Element) monetaryAdjustmentNode).getElementsByTagName("TaxAdjustment");
                    document.add(new Paragraph("Taxas e valores ........."));

                    for (int j = 0; j < taxAdjusmentNodes.getLength(); j++) {
                        Element taxAdjusmentElement = (Element) taxAdjusmentNodes.item(j);

                        String tipoTaxa = taxAdjusmentElement.getAttribute("TaxType");
                        document.add(new Paragraph("Tipo de taxa: " + tipoTaxa + " (Conferir se taxa é a mesma do país!)"));

                        String paisTaxa = taxAdjusmentElement.getElementsByTagName("TaxLocation").item(0).getTextContent();
                        document.add(new Paragraph("País: " + paisTaxa));

                        double percentagemTaxa = Double.parseDouble(taxAdjusmentElement.getElementsByTagName("TaxPercent").item(0).getTextContent());
                        document.add(new Paragraph("Percentagem: " + percentagemTaxa));

                        Node taxAmountNode = taxAdjusmentElement.getElementsByTagName("TaxAmount").item(0);
                        double valorTaxa = Double.parseDouble(((Element) taxAmountNode).getElementsByTagName("CurrencyValue")
                                .item(0).getTextContent());
                        document.add(new Paragraph("Total das taxas: " + valorTaxa));

                    }

                    document.add(new Paragraph("______________________________________________________________\n"));

                }

                document.close();
                // Abre o programa padrão associado ao arquivo PDF
                File file = new File(caminhoArquivo);
                Desktop.getDesktop().open(file);

            } catch (IOException e) {
                Mensagens.Erro("Erro!", "Erro ao mostrar arquivo selecionado!");
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao mostrar arquivo selecionado!");
        }
    }
}
