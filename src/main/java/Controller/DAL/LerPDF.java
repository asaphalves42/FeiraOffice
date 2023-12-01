package Controller.DAL;

import Utilidades.Mensagens;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LerPDF {

    public void lerFicheiroXML(File selectedFile) throws IOException {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(selectedFile);
            doc.getDocumentElement().normalize();

            // Caminho do arquivo PDF em branco que será criado
            String caminhoArquivo = "conferirEncomenda.pdf";

            try (FileOutputStream fileOutputStream = new FileOutputStream(caminhoArquivo)) {
                com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
                PdfWriter.getInstance(document, fileOutputStream);

                // Abre o documento para edição
                document.open();
                document.add(new Paragraph("Confira sua encomenda antes de enviar!"));
                // System.out.println("Confira sua encomenda antes de enviar!");
                //System.out.println("-----------------------------------------------------------------\n");

                //referencia
                String referencia = doc.getElementsByTagName("OrderConfirmationReference").item(0).getTextContent();
                //System.out.println("Referência: " + referencia);
                document.add(new Paragraph("Referência: " + referencia));

                //data
                Node issuedDateNode = doc.getElementsByTagName("OrderConfirmationIssuedDate").item(0);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String Data = ((Element) issuedDateNode).getElementsByTagName("Year").item(0).getTextContent() + "-" +
                        ((Element) issuedDateNode).getElementsByTagName("Month").item(0).getTextContent() + "-" +
                        ((Element) issuedDateNode).getElementsByTagName("Day").item(0).getTextContent();
                LocalDate date = LocalDate.parse(Data, formatter);
                //System.out.println("Data: " + Data);
                document.add(new Paragraph("Data: " + Data));


                //fornecedor
                Node supplierPartyNode = doc.getElementsByTagName("SupplierParty").item(0);
                String codigoFornecedor = ((Element) supplierPartyNode).getElementsByTagName("PartyIdentifier").item(0).getTextContent();
                String nomeFornecedor = ((Element) supplierPartyNode).getElementsByTagName("Name").item(0).getTextContent();

                //System.out.println("Fornecedor: " + codigoFornecedor);
                document.add(new Paragraph("Fornecedor: " + codigoFornecedor));

                //System.out.println("Nome: " + nomeFornecedor);
                document.add(new Paragraph("Nome: " + nomeFornecedor));


                //moeda
                Node moedaNode = doc.getElementsByTagName("CurrencyValue").item(0);
                String codigoMoeda = ((Element) moedaNode).getAttribute("CurrencyType");
                //System.out.println("Moeda: " + codigoMoeda);
                document.add(new Paragraph("Moeda: " + codigoMoeda));


                //linhas
                //System.out.println("A escrever linhas:");
                //System.out.println("----------------------------------------------------------------------------------\n");
                document.add(new Paragraph("Itens:"));
                document.add(new Paragraph("----------------------------------------------------------------------------------\n"));


                NodeList lineItemNodes = doc.getElementsByTagName("OrderConfirmationLineItem");
                for (int i = 0; i < lineItemNodes.getLength(); i++) {
                    Element lineItemNode = (Element) lineItemNodes.item(i);

                    int sequencia = Integer.parseInt(lineItemNode.getElementsByTagName("OrderConfirmationLineItemNumber").item(0).getTextContent());
                    //System.out.println("Sequência linha: " + sequencia + "\n");
                    document.add(new Paragraph("Item número: " + sequencia + "\n"));

                    Node productNode = lineItemNode.getElementsByTagName("Product").item(0);
                    String produtoDescricao = ((Element) productNode).getElementsByTagName("ProductDescription").item(0).getTextContent();
                    //System.out.println("Descrição do produto: " + produtoDescricao);
                    document.add(new Paragraph("Descrição do produto: " + produtoDescricao));

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
                    //System.out.println("Código interno do produto: " + codigoArtigoInterno);
                    document.add(new Paragraph("Código interno do produto: " + codigoArtigoInterno));

                    //System.out.println("Código no fornecedor: " + codigoArtigoFornecedor);
                    document.add(new Paragraph("Código no fornecedor: " + codigoArtigoFornecedor));


                    Node priceNode = lineItemNode.getElementsByTagName("PricePerUnit").item(0);
                    double preco = Double.parseDouble(((Element) priceNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                    //System.out.println("Preço unitário: " + preco);
                    document.add(new Paragraph("Preço unitário: " + preco));


                    Node quantidadeNode = lineItemNode.getElementsByTagName("Quantity").item(0);
                    Node ValorQuantidadeNode = ((Element) quantidadeNode).getElementsByTagName("Value").item(0);

                    double quantidade = Double.parseDouble(ValorQuantidadeNode.getTextContent());
                    String unidade = ((Element) ValorQuantidadeNode).getAttribute("UOM");
                    //System.out.println("Quantidade: " + quantidade);
                    document.add(new Paragraph("Quantidade: " + quantidade));

                    //System.out.println("Unidade: " + unidade);
                    document.add(new Paragraph("Unidade: " + unidade));

                    Node valorTotalNode = lineItemNode.getElementsByTagName("LineBaseAmount").item(0);
                    double total = Double.parseDouble(((Element) valorTotalNode).getElementsByTagName("CurrencyValue").item(0).getTextContent());
                    //System.out.println("Valor total do produto: " + Total + "\n");
                    document.add(new Paragraph("Valor total do produto: " + total + "\n"));

                    //taxas
                    Node monetaryAdjustmentNode = ((Element) lineItemNode).getElementsByTagName("MonetaryAdjustment").item(0);
                    NodeList taxAdjusmentNodes = ((Element) monetaryAdjustmentNode).getElementsByTagName("TaxAdjustment");
                    //System.out.println("Taxas e valores .........");
                    document.add(new Paragraph("Taxas e valores ........."));

                    for (int j = 0; j < taxAdjusmentNodes.getLength(); j++) {
                        //preparar as taxas
                        Element taxAdjusmentElement = (Element) taxAdjusmentNodes.item(j);

                        String tipoTaxa = taxAdjusmentElement.getAttribute("TaxType");
                        //System.out.println("Tipo de taxa: " + tipoTaxa + " (Conferir se taxa é a mesma do país!)");
                        document.add(new Paragraph("Tipo de taxa: " + tipoTaxa + " (Conferir se taxa é a mesma do país!)"));

                        String paisTaxa = taxAdjusmentElement.getElementsByTagName("TaxLocation").item(0).getTextContent();
                        //System.out.println("País: " + PaisTaxa);
                        document.add(new Paragraph("País: " + paisTaxa));

                        double percentagemTaxa = Double.parseDouble(taxAdjusmentElement.getElementsByTagName("TaxPercent").item(0).getTextContent());
                        //System.out.println("Percentagem: " + percentagemTaxa);
                        document.add(new Paragraph("Percentagem: " + percentagemTaxa));


                        Node taxAmountNode = taxAdjusmentElement.getElementsByTagName("TaxAmount").item(0);
                        double valorTaxa = Double.parseDouble(((Element) taxAmountNode).getElementsByTagName("CurrencyValue")
                                .item(0).getTextContent());
                        //System.out.println("Total das taxas: " + valorTaxa);
                        document.add(new Paragraph("Total das taxas: " + valorTaxa));


                    }

                    //System.out.println("______________________________________________________________\n");
                    document.add(new Paragraph("______________________________________________________________\n"));

                }

                // Abre o programa padrão associado ao arquivo PDF
                File file = new File(caminhoArquivo);
                Desktop.getDesktop().open(file);


            } catch (IOException | DocumentException e) {
                Mensagens.Erro("Erro!", "Erro ao mostrar arquivo selecionado!");
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao mostrar arquivo selecionado!");
        }
    }


}