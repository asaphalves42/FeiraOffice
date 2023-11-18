package Controller.DAL;


import Utilidades.Mensagens;
import com.example.lp3_g2_feira_office_2023.OrderConfirmation;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


import java.io.File;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.List;



public class LerFicheiro {

    File arquivoXml = new File("C:\\a\\XML-Sample.xml");
    private final JAXBContext jaxbContext;

    public LerFicheiro() {

        try {
            // Create the JAXB context for the generated class
            this.jaxbContext = JAXBContext.newInstance(OrderConfirmation.class);
        } catch (JAXBException e) {
            // Handle JAXB initialization exception
            throw new RuntimeException("Error initializing JAXB context", e);
        }
    }

    // Função para processar um arquivo XML e extrair informações de uma confirmação de pedido (OrderConfirmation)
    public OrderConfirmation orderConfirmation() throws IOException {
        OrderConfirmation orderConfirmation = null;
        try {

            // Criar um Unmarshaller
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            orderConfirmation = (OrderConfirmation) unmarshaller.unmarshal(arquivoXml);



            // Acesso à referência da confirmação de pedido
            String orderConfirmationReference = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationReference();
            System.out.println("OrderConfirmationReference: " + orderConfirmationReference);

            // Acesso à data de emissão da confirmação de pedido
            OrderConfirmation.OrderConfirmationHeader.OrderConfirmationIssuedDate.Date date = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationIssuedDate().getDate();

            // Acesso ao ano, mês e dia da data
            BigInteger year = date.getYear();
            BigInteger month = date.getMonth();
            BigInteger day = date.getDay();

            // Acesso às informações do fornecedor (SupplierParty)
            OrderConfirmation.OrderConfirmationHeader.SupplierParty supplierParty = orderConfirmation.getOrderConfirmationHeader().getSupplierParty();

            // Acesso ao identificador do fornecedor
            String partyIdentifier = supplierParty.getPartyIdentifier();

            // Acesso ao endereço do fornecedor
            OrderConfirmation.OrderConfirmationHeader.SupplierParty.NameAddress nameAddress = supplierParty.getNameAddress();

            //Atribuo as variáveis
            String name = nameAddress.getName();
            String address1 = nameAddress.getAddress1();
            String address2 = nameAddress.getAddress2();
            String city = nameAddress.getCity();
            String postalCode = nameAddress.getPostalCode();

            // Acesso ao país do fornecedor
            OrderConfirmation.OrderConfirmationHeader.SupplierParty.NameAddress.Country country = nameAddress.getCountry();
            String isoCountryCode = String.valueOf(country.getISOCountryCode());

            // Impressão das informações do fornecedor
            System.out.println("PartyIdentifier: " + partyIdentifier);
            System.out.println("Name: " + name);
            System.out.println("Address1: " + address1);
            System.out.println("Address2: " + address2);
            System.out.println("City: " + city);
            System.out.println("PostalCode: " + postalCode);
            System.out.println("ISOCountryCode: " + isoCountryCode);

            System.out.println("-------------------------------------------------");
            System.out.println("Produtos:");

            List<OrderConfirmation.OrderConfirmationLineItem> lineItems = orderConfirmation.getOrderConfirmationLineItem();

            // Iteração pelos itens da confirmação de pedido
            for (OrderConfirmation.OrderConfirmationLineItem lineItem : lineItems) {

                // Acesso aos detalhes do produto na linha do pedido
                List<Object> productDetails = lineItem.getOrderConfirmationLineItemNumberOrProductOrPriceDetails();
                System.out.println("Item número: " + lineItem.getOrderConfirmationLineItemNumberOrProductOrPriceDetails().get(0));

                // Iteração pelos detalhes do produto
                for (Object product : productDetails) {

                    // Verificação do tipo de produto (Product)
                    if (product instanceof OrderConfirmation.OrderConfirmationLineItem.Product) {

                        // Conversão para o tipo específico de produto (Product)
                        OrderConfirmation.OrderConfirmationLineItem.Product productObj =
                                (OrderConfirmation.OrderConfirmationLineItem.Product) product;


                        // Acesso à lista de identificadores ou descrições do produto
                        List<Object> productIdentifierOrProductDescription = productObj.getProductIdentifierOrProductDescription();

                        // Iteração pela lista para acessar identificadores e descrições
                        for (Object identifierOrDescription : productIdentifierOrProductDescription) {

                            // Verificação se é um identificador de produto (ProductIdentifier)
                            if (identifierOrDescription instanceof OrderConfirmation.OrderConfirmationLineItem.Product.ProductIdentifier) {

                                // Conversão para o tipo específico de identificador de produto
                                OrderConfirmation.OrderConfirmationLineItem.Product.ProductIdentifier identifier =
                                        (OrderConfirmation.OrderConfirmationLineItem.Product.ProductIdentifier) identifierOrDescription;

                                // Acesso às propriedades do identificador de produto
                                String value = identifier.getValue();

                                // Impressão dos dados do identificador de produto
                                if (identifier.getAgency().equals("Buyer")) {
                                    System.out.println("Buyer: " + value);
                                } else {
                                    System.out.println("Supplier: " + value);
                                }

                            } else if (identifierOrDescription instanceof String) {

                                // Acesso à descrição do produto
                                String description = (String) identifierOrDescription;

                                // Impressão da descrição do produto
                                System.out.println("Descrição: " + description);
                            }
                        }

                    }
                    // Blocos adicionais para outros tipos de produtos (PriceDetails, MonetaryAdjustment, Quantity, InformationalQuantity, LineBaseAmount)
                    if (product instanceof OrderConfirmation.OrderConfirmationLineItem.PriceDetails) {

                        // Conversão para o tipo específico de detalhes de preço (PriceDetails)
                        OrderConfirmation.OrderConfirmationLineItem.PriceDetails priceDetailsObj =
                                (OrderConfirmation.OrderConfirmationLineItem.PriceDetails) product;

                        // Impressão do preço por unidade e tipo de preço
                        System.out.println("Preço por unidade: " + priceDetailsObj.getPricePerUnit().getCurrencyValue().getValue());

                        System.out.println("Tipo: " + priceDetailsObj.getPricePerUnit().getValue().getUOM() + ", Quantidade: " + priceDetailsObj.getPricePerUnit().getValue().getValue());

                    }

                    if (product instanceof OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment) {

                        // Conversão para o tipo específico de ajuste monetário (MonetaryAdjustment)
                        OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment monetaryAdjustmentObj =
                                (OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment) product;

                        // Impressão do número da linha de ajuste monetário
                        System.out.println("Ajuste de taxas, linha: " + ((OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment) product).getMonetaryAdjustmentLine());

                        System.out.println("Valor total: " + monetaryAdjustmentObj.getMonetaryAdjustmentStartAmount().getCurrencyValue().getValue() + ", Em: " + monetaryAdjustmentObj.getMonetaryAdjustmentStartAmount().getCurrencyValue().getCurrencyType());

                        BigDecimal taxaDeJuros = monetaryAdjustmentObj.getTaxAdjustment().getTaxPercent();

                        System.out.println("Taxa de juros: " + taxaDeJuros + "%");

                        System.out.println("Total de juros: " + monetaryAdjustmentObj.getTaxAdjustment().getTaxAmount().getCurrencyValue().getValue()
                                + ", Em: " + monetaryAdjustmentObj.getTaxAdjustment().getTaxAmount().getCurrencyValue().getCurrencyType());
                        System.out.println("País: " + monetaryAdjustmentObj.getTaxAdjustment().getTaxLocation());

                    }

                    if (product instanceof OrderConfirmation.OrderConfirmationLineItem.Quantity) {

                        // Conversão para o tipo específico de quantidade (Quantity)
                        OrderConfirmation.OrderConfirmationLineItem.Quantity quantity =
                                (OrderConfirmation.OrderConfirmationLineItem.Quantity) product;

                        System.out.println("Quantidade: " + quantity.getValue().getValue() + ", Tipo: " + quantity.getValue().getUOM().value());

                    }

                    if (product instanceof OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity) {

                        // Conversão para o tipo específico de quantidade informativa (InformationalQuantity)
                        OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity informationalQuantity =
                                (OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity) product;

                        // Impressão da quantidade informativa (NetWeight ou Sheet)
                        if (informationalQuantity.getQuantityType().equals("NetWeight")) {
                            System.out.println("Kilogram: " + informationalQuantity.getValue().getValue());
                        } else {
                            System.out.println("Sheet: " + informationalQuantity.getValue().getValue());
                        }

                    }

                    if (product instanceof OrderConfirmation.OrderConfirmationLineItem.LineBaseAmount) {

                        // Conversão para o tipo específico de montante base da linha (LineBaseAmount)
                        OrderConfirmation.OrderConfirmationLineItem.LineBaseAmount lineBaseAmount =
                                (OrderConfirmation.OrderConfirmationLineItem.LineBaseAmount) product;

                        System.out.println("Total do produto (LineBaseAmount): " + lineBaseAmount.getCurrencyValue().getValue() + "€");

                    }

                }
                System.out.println("-------------------------------------------------------");
            }

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao ler arquixo XML!");
        }

        return orderConfirmation;

    }
}























