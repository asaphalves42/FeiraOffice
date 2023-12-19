package BL;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Utilidades.Mensagens;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LerSepa {

    //Criar classe com os dados da feira e office, uma tabela para ler esses dados e uma tabela com o id da encomenda e a referencia de pagamento quando pagar
    //Criar uma tabela na bd de pagamento com todos esses dados

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
            File file = new File(destinoFicheiro);
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(file);
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

            resultado = true;
        } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
            System.out.println(e.getMessage());;
        }
        return resultado;
    }

    private static Element criarElemento(Document doc, Element pai, String nomeElemento, String valor) {
        Element elemento = doc.createElement(nomeElemento);
        elemento.appendChild(doc.createTextNode(valor));
        pai.appendChild(elemento);
        return elemento;
    }
}
