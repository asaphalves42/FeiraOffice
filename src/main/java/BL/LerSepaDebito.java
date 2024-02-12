package BL;

import Utilidades.Mensagens;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por gerar ficheiros SEPA de débito direto
 */
public class LerSepaDebito {
    /**
     * Gera um arquivo XML SEPA para débito direto.
     *
     * @param referencia           Referência associada à transação.
     * @param dataTransferencia    Data da transferência.
     * @param valorTotal           Valor total da transação.
     * @param empresaNome          Nome da empresa debitante.
     * @param empresaMorada        Morada da empresa debitante.
     * @param empresaLocalidade    Localidade da empresa debitante.
     * @param empresaCPostal       Código postal da empresa debitante.
     * @param empresaPais          País da empresa debitante (código de 2 letras).
     * @param empresaIBAN          IBAN da empresa debitante.
     * @param empresaBIC           BIC da empresa debitante.
     * @param clienteNome          Nome do cliente debitado.
     * @param clienteMorada        Morada do cliente debitado.
     * @param clienteCPostal       Código postal do cliente debitado.
     * @param clientePais          País do cliente debitado.
     * @param clienteLocalidade    Localidade do cliente debitado.
     * @param clienteIBAN          IBAN do cliente debitado.
     * @param clienteBIC           BIC do cliente debitado.
     * @param destinoFicheiro      Caminho de destino para o arquivo XML SEPA gerado.
     * @return                     Retorna verdadeiro se o arquivo foi gerado com sucesso, falso caso contrário.
     * @throws Exception           Lança uma exceção se ocorrerem erros durante o processo de geração.
     */
    public static Boolean gerarSEPADebito(

            String referencia,
            LocalDate dataTransferencia,
            double valorTotal,

            //Criar dados para a Feira e Office
            String empresaNome,
            String empresaMorada,
            String empresaLocalidade,
            String empresaCPostal,
            String empresaPais,
            String empresaIBAN,
            String empresaBIC,


            //Baseado no fornecedor
            String clienteNome,
            String clienteMorada,
            String clienteCPostal,
            String clientePais,
            String clienteLocalidade,
            String clienteIBAN,
            String clienteBIC,
            String destinoFicheiro

    ) throws Exception
    {
        boolean resultado = false;

        //validações
        if ("".equals(referencia)){
            Mensagens.Erro("Erro!", "Referência inválida");
            throw new Exception("Referência inválida");
        }

        if (valorTotal <= 0){
            Mensagens.Erro("Erro!", "Valor inválido");
            throw new Exception("Valor inválido");
        }

        if ("".equals(empresaNome) || "".equals(empresaMorada) || "".equals(empresaLocalidade) || "".equals(empresaCPostal)){
            Mensagens.Erro("Erro!", "Dados da empresa incompletos");
            throw new Exception("Dados da empresa incompletos");
        }

        if (empresaPais.length() != 2){
            Mensagens.Erro("Erro!", "País da empresa errado");
            throw new Exception("País da empresa errado");
        }

        if ("".equals(empresaIBAN) || "".equals(empresaBIC)){
            Mensagens.Erro("Erro!", "Dados bancários empresa errados");
            throw new Exception("Dados bancários empresa errados");
        }

        if ("".equals(clienteNome) || "".equals(clienteMorada) || "".equals(clienteCPostal) || "".equals(clienteLocalidade) || "".equals(clientePais)){
            Mensagens.Erro("Erro!", "Dados do cliente incompletos");
            throw new Exception("Dados do cliente incompletos");
        }

        if ("".equals(clienteIBAN) || "".equals(clienteBIC)) {
            Mensagens.Erro("Erro!", "Dados bancários do cliente errados");
            throw new Exception("Dados bancários do cliente errados");
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElementNS("urn:iso:std:iso:20022:tech:xsd:pain.008.001.02", "Document");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            doc.appendChild(rootElement);

            Element cstmrCdtTrfInitn = doc.createElement("CstmrDrctDbtInitn");
            rootElement.appendChild(cstmrCdtTrfInitn);

            Element grpHdr = doc.createElement("GrpHdr");
            cstmrCdtTrfInitn.appendChild(grpHdr);

            criarElemento(doc, grpHdr, "MsgId", referencia);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            criarElemento(doc, grpHdr, "CreDtTm", formattedDateTime);
            criarElemento(doc, grpHdr, "NbOfTxs", "1");
            DecimalFormat decimalFormat = new DecimalFormat("########0.00");
            String valorFormatado = decimalFormat.format(valorTotal);
            criarElemento(doc, grpHdr, "CtrlSum", valorFormatado.replace(",", "."));

            Element initgPty = doc.createElement("InitgPty");
            grpHdr.appendChild(initgPty);

            criarElemento(doc, initgPty, "Nm", clienteNome);

            Element pstlAdr = doc.createElement("PstlAdr");
            initgPty.appendChild(pstlAdr);

            criarElemento(doc, pstlAdr, "StrtNm", clienteMorada);
            criarElemento(doc, pstlAdr, "PstCd", clienteCPostal);
            criarElemento(doc, pstlAdr, "TwnNm", clienteLocalidade);
            criarElemento(doc, pstlAdr, "Ctry", clientePais);


            Element pmtInf = doc.createElement("PmtInf");
            cstmrCdtTrfInitn.appendChild(pmtInf);

            criarElemento(doc, pmtInf, "PmtInfId", referencia);
            criarElemento(doc, pmtInf, "PmtMtd", "DD");
            criarElemento(doc, pmtInf, "BtchBookg", "false");

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateTime = dataTransferencia.format(formatter);
            criarElemento(doc, pmtInf, "ReqdColltnDt", formattedDateTime);

            Element dbtr = doc.createElement("Cdtr");
            pmtInf.appendChild(dbtr);

            criarElemento(doc, dbtr, "Nm", clienteNome);

            Element pstlAdrDbtr = doc.createElement("PstlAdr");
            dbtr.appendChild(pstlAdrDbtr);

            criarElemento(doc, pstlAdrDbtr, "StrtNm", clienteMorada);
            criarElemento(doc, pstlAdrDbtr, "PstCd", clienteCPostal);
            criarElemento(doc, pstlAdrDbtr, "TwnNm", clienteLocalidade);
            criarElemento(doc, pstlAdrDbtr, "Ctry", clientePais);

            Element cdtrAcct = doc.createElement("CdtrAcct");
            pmtInf.appendChild(cdtrAcct);

            Element idDbtrAcct = doc.createElement("Id");
            cdtrAcct.appendChild(idDbtrAcct);

            criarElemento(doc, idDbtrAcct, "IBAN", clienteIBAN);

            Element dbtrAgt = doc.createElement("CdtrAgt");
            pmtInf.appendChild(dbtrAgt);

            Element finInstnId = doc.createElement("FinInstnId");
            dbtrAgt.appendChild(finInstnId);

            criarElemento(doc, finInstnId, "BIC", clienteBIC);

            Element drctDbtTxInf = doc.createElement("DrctDbtTxInf");
            pmtInf.appendChild(drctDbtTxInf);

            Element pmtId = doc.createElement("PmtId");
            drctDbtTxInf.appendChild(pmtId);

            criarElemento(doc, pmtId, "InstrId", referencia);
            criarElemento(doc, pmtId, "EndToEndId", referencia);

            Element pmtTpInf = doc.createElement("PmTpInf");
            drctDbtTxInf.appendChild(pmtTpInf);

            Element svclv1 = doc.createElement("Svclv1");
            pmtTpInf.appendChild(svclv1);

            criarElemento(doc, svclv1, "Prty", "VERPA-1");

            criarElemento(doc,pmtTpInf,"SeqTp","RCUR");

            Element instAmt = criarElemento(doc, drctDbtTxInf, "InstdAmt", valorFormatado.replace(",","."));
            instAmt.setAttribute("Ccy", "EUR");

            criarElemento(doc,drctDbtTxInf,"ChrgBr", "SHAR");

            Element drctDbtTx = doc.createElement("DrctDbtTx");
            drctDbtTxInf.appendChild(drctDbtTx);

            Element mndtRltd = doc.createElement("MndtRltdInf");
            drctDbtTx.appendChild(mndtRltd);

            criarElemento(doc,mndtRltd,"MndtId", referencia);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateTime = dataTransferencia.format(formatter);
            criarElemento(doc, mndtRltd,"DtOfSgntr",formattedDateTime);
            criarElemento(doc, mndtRltd,"FnlColltnDt",formattedDateTime);
            criarElemento(doc, mndtRltd,"Frqcy","YEAR");

            Element cdtrAgt = doc.createElement("DbtrAgt");
            drctDbtTxInf.appendChild(cdtrAgt);

            Element finInstnIdCdtrAgt = doc.createElement("FinInstnId");
            cdtrAgt.appendChild(finInstnIdCdtrAgt);

            criarElemento(doc, finInstnIdCdtrAgt, "BIC", empresaBIC);

            Element cdtr = doc.createElement("Dbtr");
            drctDbtTxInf.appendChild(cdtr);

            criarElemento(doc, cdtr, "Nm", empresaNome);

            Element pstlAdrCdtr = doc.createElement("PstlAdr");
            cdtr.appendChild(pstlAdrCdtr);

            criarElemento(doc, pstlAdrCdtr, "StrtNm", empresaMorada);
            criarElemento(doc, pstlAdrCdtr, "BldgNb", empresaMorada);
            criarElemento(doc, pstlAdrCdtr, "PstCd", empresaCPostal);
            criarElemento(doc, pstlAdrCdtr, "TwnNm", empresaLocalidade);
            criarElemento(doc, pstlAdrCdtr, "Ctry", empresaPais);

            Element dbtrAcct = doc.createElement("DbtrAcct");
            drctDbtTxInf.appendChild(dbtrAcct);

            Element idCdtrAcct = doc.createElement("Id");
            dbtrAcct.appendChild(idCdtrAcct);

            criarElemento(doc, idCdtrAcct, "IBAN", empresaIBAN);

            Element purp = doc.createElement("Purp");
            drctDbtTxInf.appendChild(purp);
            criarElemento(doc, purp,"Cd","SUPP");

            Element rmtInf = doc.createElement("RmtInf");
            drctDbtTxInf.appendChild(rmtInf);
            criarElemento(doc,rmtInf,"Ustrd","Feira & Office Order");


            //gravar o ficheiro
            String caminhoSchema = "src/main/lp3/Views/xsdSchemas/schemaDebito.xsd";
            File file = new File(destinoFicheiro);
            if(validateXmlAgainstXsd(file,caminhoSchema)){

                javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
                javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
                javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(file);
                transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
            }

            resultado = true;
        } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
            System.out.println(e.getMessage());;
        }
        return resultado;
    }


    /**
     * Valida um arquivo XML em relação a um esquema XSD.
     *
     * @param xmlFilePath Caminho do arquivo XML a ser validado.
     * @param xsdFilePath Caminho do arquivo XSD que representa o esquema de validação.
     * @return Retorna verdadeiro se o XML for válido em relação ao XSD, falso caso contrário.
     * @throws IOException Se ocorrer um erro de leitura nos arquivos XML ou XSD.
     */
    private static boolean validateXmlAgainstXsd(File xmlFilePath, String xsdFilePath) throws IOException {
        try {
            // Carregar esquema XSD
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));

            // Criar validador
            Validator validator = schema.newValidator();

            // Validar o XML
            validator.validate(new StreamSource(new File(String.valueOf(xmlFilePath))));

            return true; // XML é válido em relação ao XSD
        } catch (SAXException | IOException e) {
            Mensagens.Erro("Erro!","Erro na construção do XML!");
            return false; // XML não é válido em relação ao XSD
        }
    }
    /**
     * Cria um novo elemento XML com o nome especificado, adiciona-o como filho do elemento pai fornecido
     * e define seu valor interno.
     *
     * @param doc           Documento XML no qual o elemento será criado.
     * @param pai           Elemento pai ao qual o novo elemento será adicionado.
     * @param nomeElemento  Nome do novo elemento a ser criado.
     * @param valor         Valor interno a ser atribuído ao novo elemento.
     * @return              Retorna o elemento recém-criado.
     */
    private static Element criarElemento(Document doc, Element pai, String nomeElemento, String valor) {
        Element elemento = doc.createElement(nomeElemento);
        elemento.appendChild(doc.createTextNode(valor));
        pai.appendChild(elemento);
        return elemento;
    }
}
