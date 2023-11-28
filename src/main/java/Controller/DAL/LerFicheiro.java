package Controller.DAL;


import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import com.example.lp3_g2_feira_office_2023.OrderConfirmation;


import com.example.lp3_g2_feira_office_2023.UOM;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


import java.io.File;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class LerFicheiro {

    private final JAXBContext jaxbContext;
    private Utilizador utilizador;

    BaseDados baseDados = new BaseDados();
    LerUnidade lerUnidade = new LerUnidade();
    LerPaises lerPaises = new LerPaises();
    LerEncomenda lerEncomenda = new LerEncomenda();
    LerPaises pais = new LerPaises();
    LerFornecedores fornecedor = new LerFornecedores();

    public void iniciaData(Utilizador utilizador) throws IOException {
        this.utilizador = utilizador;

    }

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
    public OrderConfirmation orderConfirmation(File arquivoXml, Utilizador utilizador) throws IOException {
        OrderConfirmation orderConfirmation = null;
        iniciaData(utilizador);
        Encomenda encomenda = null;
        try {

            // Criar um Unmarshaller
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            orderConfirmation = (OrderConfirmation) unmarshaller.unmarshal(arquivoXml);

            // Acesso à referência da confirmação de pedido
            String orderConfirmationReference = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationReference();
            System.out.println("OrderConfirmationReference: " + orderConfirmationReference);

            //verificar se encomenda ja foi inserida
            for (Encomenda enc : lerEncomenda.lerEncomendaDaBaseDeDados(baseDados)) {
                if (enc.getReferencia().equals(orderConfirmationReference)) {

                    // Encomenda já inserida, exibir mensagem de erro e retornar
                    Mensagens.Erro("Duplicado!", "Encomenda já foi integrada!");
                    return null;
                }
            }

            // Acesso à data de emissão da confirmação de pedido
            OrderConfirmation.OrderConfirmationHeader.OrderConfirmationIssuedDate.Date date = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationIssuedDate().getDate();

            // Acesso ao ano, mês e dia da data
            BigInteger year = date.getYear();
            BigInteger month = date.getMonth();
            BigInteger day = date.getDay();

            System.out.println("Dia: " + day + "Mês: " + month + "Ano: " + year );

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


            Fornecedor fornecedorLogado = null;
            for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados()){
                if(this.utilizador.getId() == fornec.getIdUtilizador().getId()){
                    fornecedorLogado = fornec;
                }
            }
            assert fornecedorLogado != null;

            // Verifica se o fornecedor não é nulo e se o IdExterno é igual
            if (fornecedorLogado.getIdExterno().equals(orderConfirmation.getOrderConfirmationHeader().getSupplierParty().getPartyIdentifier())) {

                //referencia
                String referencia = orderConfirmation.getOrderConfirmationHeader().getOrderConfirmationReference();

                //Data
                // Converta os valores para inteiros
                int yearValue = year.intValue();
                int monthValue = month.intValue();
                int dayValue = day.intValue();

                // Crie um objeto LocalDate
                LocalDate data = LocalDate.of(yearValue, monthValue, dayValue);

                //País
                Pais lerPais = pais.obterPaisPorISO(baseDados, orderConfirmation.getOrderConfirmationHeader().getSupplierParty().getNameAddress().getCountry().getISOCountryCode().value());

                // Define os valores do fornecedor antes de usá-lo
                fornecedorLogado.setIdExterno(orderConfirmation.getOrderConfirmationHeader().getSupplierParty().getPartyIdentifier());

                encomenda = new Encomenda(0,
                        referencia,
                        data,
                        fornecedorLogado,
                        lerPais,
                        new ArrayList<>(),
                        0
                );

                // Iteração pelos itens da confirmação de pedido
                for (OrderConfirmation.OrderConfirmationLineItem lineItem : lineItems) {

                    int sequencia = 0;
                    Produto produto = new Produto();
                    double precoLinha = 0, quantidadeLinha = 0;
                    Unidade unidade = null;
                    Pais paisLinha = null;
                    double totalTaxa = 0, totalIncidencia = 0, totalLinha =0;

                    // Acesso aos detalhes do produto na linha do pedido
                    List<Object> productDetails = lineItem.getOrderConfirmationLineItemNumberOrProductOrPriceDetails();
                    System.out.println("Item número: " + lineItem.getOrderConfirmationLineItemNumberOrProductOrPriceDetails().get(0));

                    // Converte o primeiro elemento para inteiro e atribui a sequencia
                    sequencia = Integer.parseInt(productDetails.get(0).toString());


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
                                        produto.setId(value);
                                    } else {
                                        System.out.println("Supplier: " + value);
                                        produto.setIdExterno(value);
                                        produto.setFornecedor(encomenda.getFornecedor());
                                    }

                                } else if (identifierOrDescription instanceof String) {

                                    // Acesso à descrição do produto
                                    String description = (String) identifierOrDescription;

                                    // Impressão da descrição do produto
                                    System.out.println("Descrição: " + description);
                                    produto.setDescricao(description);
                                }
                            }

                        }

                        // Blocos adicionais para outros tipos de produtos (PriceDetails, MonetaryAdjustment, Quantity, InformationalQuantity, LineBaseAmount)
                        if (product instanceof OrderConfirmation.OrderConfirmationLineItem.PriceDetails) {

                            // Conversão para o tipo específico de detalhes de preço (PriceDetails)
                            OrderConfirmation.OrderConfirmationLineItem.PriceDetails priceDetailsObj =
                                    (OrderConfirmation.OrderConfirmationLineItem.PriceDetails) product;

                            BigDecimal precoUnitario = priceDetailsObj.getPricePerUnit().getCurrencyValue().getValue();

                            // Impressão do preço por unidade e tipo de preço
                            System.out.println("Preço por unidade: " + precoUnitario);

                            BigInteger quantidade = priceDetailsObj.getPricePerUnit().getValue().getValue();
                            UOM tipo = priceDetailsObj.getPricePerUnit().getValue().getUOM();

                            System.out.println("Tipo: " + tipo + ", Quantidade: " + quantidade);
                            precoLinha = Double.parseDouble(precoUnitario.toString()) / Double.parseDouble(quantidade.toString());

                        }

                        if (product instanceof OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment) {

                            // Conversão para o tipo específico de ajuste monetário (MonetaryAdjustment)
                            OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment monetaryAdjustmentObj =
                                    (OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment) product;


                            BigInteger linha = ((OrderConfirmation.OrderConfirmationLineItem.MonetaryAdjustment) product).getMonetaryAdjustmentLine();

                            // Impressão do número da linha de ajuste monetário
                            System.out.println("Ajuste de taxas, linha: " + linha);

                            BigDecimal valorTotal = monetaryAdjustmentObj.getMonetaryAdjustmentStartAmount().getCurrencyValue().getValue();
                            String tipoDeMoeda = monetaryAdjustmentObj.getMonetaryAdjustmentStartAmount().getCurrencyValue().getCurrencyType().value();

                            System.out.println("Valor total: " + valorTotal + ", Em: " + tipoDeMoeda);
                            totalIncidencia = Double.parseDouble(valorTotal.toString());

                            BigDecimal taxaDeJuros = monetaryAdjustmentObj.getTaxAdjustment().getTaxPercent();

                            System.out.println("Taxa de juros: " + taxaDeJuros + "%");

                            BigDecimal totalJuros = monetaryAdjustmentObj.getTaxAdjustment().getTaxAmount().getCurrencyValue().getValue();
                            totalTaxa = Double.parseDouble(totalJuros.toString());

                            String tipoMoeda = monetaryAdjustmentObj.getTaxAdjustment().getTaxType();
                            String paisTaxa = monetaryAdjustmentObj.getTaxAdjustment().getTaxLocation();

                            if(lerPaises.obterPaisPorISO(baseDados, paisTaxa) == null){
                                Mensagens.Erro("País", "País (" + paisTaxa + ") existente na linha número " + sequencia + " não é válida.");
                                return null;
                            }
                            paisLinha = lerPaises.obterPaisPorISO(baseDados, paisTaxa);

                            System.out.println("Total de juros: " + totalJuros
                                    + ", Em: " + tipoMoeda);
                            System.out.println("País: " + paisTaxa);

                        }

                        if (product instanceof OrderConfirmation.OrderConfirmationLineItem.Quantity) {

                            // Conversão para o tipo específico de quantidade (Quantity)
                            OrderConfirmation.OrderConfirmationLineItem.Quantity quantity =
                                    (OrderConfirmation.OrderConfirmationLineItem.Quantity) product;

                            BigDecimal quantidade = quantity.getValue().getValue();
                            String tipoQuantidade = quantity.getValue().getUOM().value();

                            if(lerUnidade.obterUnidadePorDescricaoBaseDados(tipoQuantidade) == null){
                                Mensagens.Erro("Unidade", "Unidade (" + tipoQuantidade + ") existente na linha número " + sequencia + " não é válida.");
                                return null;
                            }

                            produto.setUnidade(lerUnidade.obterUnidadePorDescricaoBaseDados(tipoQuantidade));

                            System.out.println("Quantidade: " + quantidade + ", Tipo: " + tipoQuantidade);
                            quantidadeLinha = Double.parseDouble(quantidade.toString());

                        }

                        if (product instanceof OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity) {

                            // Conversão para o tipo específico de quantidade informativa (InformationalQuantity)
                            OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity informationalQuantity =
                                    (OrderConfirmation.OrderConfirmationLineItem.InformationalQuantity) product;

                            BigDecimal quantidadeKilo = informationalQuantity.getValue().getValue();
                            BigDecimal tipoSheet = informationalQuantity.getValue().getValue();


                            // Impressão da quantidade informativa (NetWeight ou Sheet)
                            if (informationalQuantity.getQuantityType().equals("NetWeight")) {
                                System.out.println("Kilogram: " + quantidadeKilo);
                            } else {
                                System.out.println("Sheet: " + tipoSheet);
                            }

                        }

                        if (product instanceof OrderConfirmation.OrderConfirmationLineItem.LineBaseAmount) {

                            // Conversão para o tipo específico de montante base da linha (LineBaseAmount)
                            OrderConfirmation.OrderConfirmationLineItem.LineBaseAmount lineBaseAmount =
                                    (OrderConfirmation.OrderConfirmationLineItem.LineBaseAmount) product;

                            BigDecimal totalProduto = lineBaseAmount.getCurrencyValue().getValue();

                            System.out.println("Total do produto (LineBaseAmount): " + totalProduto + "€");

                        }

                    }
                    encomenda.getLinhas().add(new LinhaEncomenda(0, encomenda, sequencia, produto, precoLinha, quantidadeLinha, produto.getUnidade(), paisLinha, totalTaxa, totalIncidencia));

                }


                int sucesso =  lerEncomenda.adicionarEncomendaBaseDeDados(baseDados,encomenda);

                if(sucesso == 0) {
                    Mensagens.Erro("Erro!", "Não foi possível adicionar a encomenda!");
                }else{
                    Mensagens.Informacao("Sucesso!","Encomenda enviada com sucesso, aguarda aprovação!");
                }

                System.out.println("-------------------------------------------------------");

            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao ler arquixo XML!");
        }

        return orderConfirmation;

    }
}























