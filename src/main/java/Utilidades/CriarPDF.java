package Utilidades;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
public class CriarPDF {
    public void criaPDF(){
        try {
            // Criação de um novo documento PDF
            Document document = new Document(PageSize.A4);

            // Caminho do arquivo PDF em branco que será criado
            String caminhoArquivo = "C:\\caminho\\para\\seu\\arquivo.pdf";

            // Criação do arquivo PDF em branco
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));

            // Abre o documento para edição
            document.open();

            // Adiciona um parágrafo em branco
            document.add(new Paragraph(" "));

            // Fecha o documento
            document.close();

            System.out.println("Arquivo PDF em branco criado com sucesso!");

        } catch (DocumentException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

}

