package BL;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import Utilidades.Mensagens;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Classe responsável por gerar ficheiro SEPA de transferência bancária
 */
public class LerSepaTransferencia {

    /**
     * Gera um arquivo XML SEPA para transferência de fundos.
     *
     * @param referencia         Referência associada à transação (gerar um random de String).
     * @param dataTransferencia  Data da transferência.
     * @param valor              Valor a ser transferido.
     * @param empresaNome        Nome da empresa remetente.
     * @param empresaMorada      Morada da empresa remetente.
     * @param empresaLocalidade  Localidade da empresa remetente.
     * @param empresaCPostal     Código postal da empresa remetente.
     * @param empresaPais        País da empresa remetente (código de 2 letras).
     * @param empresaIBAN        IBAN da empresa remetente.
     * @param empresaBIC         BIC da empresa remetente.
     * @param clienteNome        Nome do cliente destinatário.
     * @param clienteMorada      Morada do cliente destinatário.
     * @param clienteCPostal     Código postal do cliente destinatário.
     * @param clienteIBAN        IBAN do cliente destinatário.
     * @param clienteBIC         BIC do cliente destinatário.
     * @param destinoFicheiro    Caminho de destino para o arquivo XML SEPA gerado.
     * @return                   Retorna verdadeiro se o arquivo foi gerado com sucesso, falso caso contrário.
     * @throws Exception         Lança uma exceção se ocorrerem erros durante o processo de geração.
     */
    public static Boolean gerarSEPATransferencia(

            String referencia, //Gerar um random de String
            LocalDate dataTransferencia,
            double valor,

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

        if (valor <= 0){
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

        if ("".equals(clienteNome) || "".equals(clienteMorada) || "".equals(clienteCPostal)){
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

            Element rootElement = doc.createElementNS("urn:iso:std:iso:20022:tech:xsd:pain.001.001.03", "Document");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            doc.appendChild(rootElement);

            Element cstmrCdtTrfInitn = doc.createElement("CstmrCdtTrfInitn");
            rootElement.appendChild(cstmrCdtTrfInitn);

            Element grpHdr = doc.createElement("GrpHdr");
            cstmrCdtTrfInitn.appendChild(grpHdr);

            criarElemento(doc, grpHdr, "MsgId", referencia);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            criarElemento(doc, grpHdr, "CreDtTm", formattedDateTime);
            criarElemento(doc, grpHdr, "NbOfTxs", "1");
            DecimalFormat decimalFormat = new DecimalFormat("########0.00");
            String valorFormatado = decimalFormat.format(valor);
            criarElemento(doc, grpHdr, "CtrlSum", valorFormatado.replace(",", "."));

            Element initgPty = doc.createElement("InitgPty");
            grpHdr.appendChild(initgPty);

            criarElemento(doc, initgPty, "Nm", empresaNome);

            Element pstlAdr = doc.createElement("PstlAdr");
            initgPty.appendChild(pstlAdr);

            criarElemento(doc, pstlAdr, "StrtNm", empresaMorada);
            criarElemento(doc, pstlAdr, "PstCd", empresaCPostal);
            criarElemento(doc, pstlAdr, "TwnNm", empresaLocalidade);
            criarElemento(doc, pstlAdr, "Ctry", empresaPais);


            Element pmtInf = doc.createElement("PmtInf");
            cstmrCdtTrfInitn.appendChild(pmtInf);

            criarElemento(doc, pmtInf, "PmtInfId", referencia);
            criarElemento(doc, pmtInf, "PmtMtd", "TRF");
            criarElemento(doc, pmtInf, "BtchBookg", "false");

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateTime = dataTransferencia.format(formatter);
            criarElemento(doc, pmtInf, "ReqdExctnDt", formattedDateTime);

            Element dbtr = doc.createElement("Dbtr");
            pmtInf.appendChild(dbtr);

            criarElemento(doc, dbtr, "Nm", empresaNome);

            Element pstlAdrDbtr = doc.createElement("PstlAdr");
            dbtr.appendChild(pstlAdrDbtr);

            criarElemento(doc, pstlAdrDbtr, "StrtNm", empresaMorada);
            criarElemento(doc, pstlAdrDbtr, "PstCd", empresaCPostal);
            criarElemento(doc, pstlAdrDbtr, "TwnNm", empresaLocalidade);
            criarElemento(doc, pstlAdrDbtr, "Ctry", empresaPais);

            Element dbtrAcct = doc.createElement("DbtrAcct");
            pmtInf.appendChild(dbtrAcct);

            Element idDbtrAcct = doc.createElement("Id");
            dbtrAcct.appendChild(idDbtrAcct);

            criarElemento(doc, idDbtrAcct, "IBAN", empresaIBAN);

            Element dbtrAgt = doc.createElement("DbtrAgt");
            pmtInf.appendChild(dbtrAgt);

            Element finInstnId = doc.createElement("FinInstnId");
            dbtrAgt.appendChild(finInstnId);

            criarElemento(doc, finInstnId, "BIC", empresaBIC);

            Element cdtTrfTxInf = doc.createElement("CdtTrfTxInf");
            pmtInf.appendChild(cdtTrfTxInf);

            Element pmtId = doc.createElement("PmtId");
            cdtTrfTxInf.appendChild(pmtId);

            criarElemento(doc, pmtId, "InstrId", referencia);
            criarElemento(doc, pmtId, "EndToEndId", referencia);

            Element amt = doc.createElement("Amt");
            cdtTrfTxInf.appendChild(amt);
            Element instAmt = criarElemento(doc, amt, "InstdAmt", valorFormatado.replace(",","."));
            instAmt.setAttribute("Ccy", "EUR");

            Element cdtrAgt = doc.createElement("CdtrAgt");
            cdtTrfTxInf.appendChild(cdtrAgt);

            Element finInstnIdCdtrAgt = doc.createElement("FinInstnId");
            cdtrAgt.appendChild(finInstnIdCdtrAgt);

            criarElemento(doc, finInstnIdCdtrAgt, "BIC", clienteBIC);

            Element cdtr = doc.createElement("Cdtr");
            cdtTrfTxInf.appendChild(cdtr);

            criarElemento(doc, cdtr, "Nm", clienteNome);

            Element pstlAdrCdtr = doc.createElement("PstlAdr");
            cdtr.appendChild(pstlAdrCdtr);

            criarElemento(doc, pstlAdrCdtr, "AdrLine", clienteMorada);
            criarElemento(doc, pstlAdrCdtr, "AdrLine", clienteCPostal);

            Element cdtrAcct = doc.createElement("CdtrAcct");
            cdtTrfTxInf.appendChild(cdtrAcct);

            Element idCdtrAcct = doc.createElement("Id");
            cdtrAcct.appendChild(idCdtrAcct);

            criarElemento(doc, idCdtrAcct, "IBAN", clienteIBAN);

            Element rmtInf = doc.createElement("RmtInf");
            cdtTrfTxInf.appendChild(rmtInf);

            //informação adicional

            /*Element strd = doc.createElement("Strd");
            rmtInf.appendChild(strd);

            Element rfrdDocInf = doc.createElement("RfrdDocInf");
            strd.appendChild(rfrdDocInf);

            criarElemento(doc, rfrdDocInf, "Nb", "4562");
            criarElemento(doc, rfrdDocInf, "RltdDt", "2009-09-08");*/

            //gravar o ficheiro
            String caminhoSchema = "src/main/lp3/Views/xsdSchemas/schemaTransferencia.xsd";
            File file = new File(destinoFicheiro);
            //if(validateXmlAgainstXsd(file,caminhoSchema)){

                javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
                javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
                javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(file);
                transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
            //}

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
