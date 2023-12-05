package Utilidades;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe que gera alertas de mensagens editáveis.
 */
public class Mensagens {

    /**
     * Exibe um diálogo de alerta de erro com o título e texto especificados.
     *
     * @param titulo O título a ser exibido no diálogo de erro.
     * @param texto O texto a ser exibido no corpo do diálogo de erro.
     * @throws IOException Se ocorrer uma exceção de E/S durante a exibição do alerta.
     */
    public static void Erro(String titulo, String texto) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }


    /**
     * Exibe um diálogo de alerta de informação com o título e texto especificados.
     *
     * @param titulo O título a ser exibido no diálogo de informação.
     * @param texto O texto a ser exibido no corpo do diálogo de informação.
     * @throws IOException Se ocorrer uma exceção de E/S durante a exibição do alerta.
     */
    public static void Informacao(String titulo, String texto) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}