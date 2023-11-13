package Utilidades;

import javafx.stage.FileChooser;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    // Método para escolher um arquivo XML usando JFileChooser
    public static File chooseXMLFile() throws IOException {
        // Cria um objeto JFileChooser
        FileChooser fileChooser = new FileChooser();

        // Configura um filtro para exibir apenas arquivos XML com a extensão .xml
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Arquivos XML", "*.xml");
        fileChooser.getExtensionFilters().add(filter);

        // Exibe a caixa de diálogo para escolher um arquivo e aguarda a decisão do usuário
        File selectedFile = fileChooser.showOpenDialog(null);



        // Verifica se o usuário clicou em "Abrir" (ou equivalente)
            if (selectedFile.getName().contains(".xml")) {
                System.out.println("Arquivo XML selecionado: " + selectedFile.getAbsolutePath());
                return selectedFile;

            } else {
                Mensagens.Informacao("Arquivo XML","Não selecionou um arquivo XML!");
                selectedFile = null;
            }

        return null;
    }
}
