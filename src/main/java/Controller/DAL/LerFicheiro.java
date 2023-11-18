package Controller.DAL;


import Controller.MensagensDeErro.MensagemDeErro;
import Model.*;
import Utilidades.Mensagens;
import com.example.lp3_g2_feira_office_2023.OrderConfirmation;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static eu.hansolo.tilesfx.tools.SunMoonCalculator.getDate;

public class LerFicheiro {

        private final File arquivoXml = new File("C:\\a\\XML-Sample.xml");
        private final JAXBContext jaxbContext;

        public LerFicheiro() throws JAXBException {
            // Criar o contexto JAXB para a classe gerada
            this.jaxbContext = JAXBContext.newInstance(OrderConfirmation.class);
        }

        public OrderConfirmation orderConfirmation() throws JAXBException {
            try {
                // Criar um Unmarshaller
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                OrderConfirmation orderConfirmation = (OrderConfirmation) unmarshaller.unmarshal(arquivoXml);

                //Referencia
                String orderConfirmationReference = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationReference();
                System.out.println("OrderConfirmationReference: " + orderConfirmationReference);

                //Data
                OrderConfirmation.OrderConfirmationHeader.OrderConfirmationIssuedDate.Date date = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationIssuedDate().getDate();

                // Acessando Year, Month, Day
                BigInteger year = date.getYear();
                BigInteger month = date.getMonth();
                BigInteger day = date.getDay();

                System.out.println("Year: " + year + ", Month: " + month + ", Day: " + day);

                //Fornecedor (SupplierParty)
                OrderConfirmation.OrderConfirmationHeader.SupplierParty supplierParty = orderConfirmation.getOrderConfirmationHeader().getSupplierParty();

                // Acessando o PartyIdentifier
                String partyIdentifier = supplierParty.getPartyIdentifier();
                System.out.println("PartyIdentifier: " + partyIdentifier);

                // Acessando o NameAddress
                OrderConfirmation.OrderConfirmationHeader.SupplierParty.NameAddress nameAddress = supplierParty.getNameAddress();

                // Acessando os atributos de NameAddress
                String name = nameAddress.getName();
                String address1 = nameAddress.getAddress1();
                String address2 = nameAddress.getAddress2(); // Pode ser nulo
                String city = nameAddress.getCity();
                String postalCode = nameAddress.getPostalCode();

                // Acessando o Country
                OrderConfirmation.OrderConfirmationHeader.SupplierParty.NameAddress.Country country = nameAddress.getCountry();
                String isoCountryCode = String.valueOf(country.getISOCountryCode());


                System.out.println("Name: " + name);
                System.out.println("Address1: " + address1);
                System.out.println("Address2: " + address2);
                System.out.println("City: " + city);
                System.out.println("PostalCode: " + postalCode);
                System.out.println("ISOCountryCode: " + isoCountryCode);

                List<OrderConfirmation.OrderConfirmationLineItem> lineItems = orderConfirmation.getOrderConfirmationLineItem();

                // Iterate through line items to get ProductIdentifier
                for (OrderConfirmation.OrderConfirmationLineItem lineItem : lineItems) {
                    OrderConfirmation.OrderConfirmationLineItem.Product.ProductIdentifier productIdentifier =
                            (OrderConfirmation.OrderConfirmationLineItem.Product.ProductIdentifier) lineItem.getOrderConfirmationLineItemNumberOrProductOrPriceDetails();

                    System.out.println(productIdentifier);


                    // Encontrando a primeira instância de InformationalQuantity usando stream
                    OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity informationalQuantity =
                            lineItems.stream()
                                    .flatMap(item -> item.getOrderConfirmationLineItemNumberOrProductOrPriceDetails().stream())
                                    .filter(obj -> obj instanceof OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity)
                                    .map(obj -> (OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity) obj)
                                    .findFirst()
                                    .orElse(null);

                    System.out.println("Informação da quantidade:" + informationalQuantity);

                }









                        
/*

//iterar sobre as linhas
                for(int i = 0; i < lineItems.size(); i++){
                    OrderConfirmation.OrderConfirmationLineItem lineItem = lineItems.get(i);
                    List<Object> lineItemsDetails = lineItem.getOrderConfirmationLineItemNumberOrProductOrPriceDetails();

                    for(Object detail : lineItemsDetails){
                        if(detail instanceof OrderConfirmation.OrderConfirmationLineItem.Product product) {
                            List<Object> productDetails = product.getProductIdentifierOrProductDescription();

                            for(Object productDetail : productDetails){
                               if(productDetail instanceof OrderConfirmation.OrderConfirmationLineItem.Product.ProductIdentifier identifier){
                                   if("Buyer".equals(identifier.getAgency())){
                                       String productId = identifier.getValue();
                                       System.out.printf("Id do produto" + productId);
                                   } else if("Supplier".equals(identifier.getAgency())){
                                       String buyerId = identifier.getValue();
                                       System.out.println("id do comprador" + buyerId);
                                   }
                               } else if(productDetail instanceof String descriptionOfProduct){
                                   System.out.println("Desrição" + descriptionOfProduct);
                               }

                            }



                        }
                    }

                }
 */







                return orderConfirmation;
            } catch (JAXBException e) {
                // Lidar com exceções JAXB, se necessário
                throw e;
            }
        }










    }


















