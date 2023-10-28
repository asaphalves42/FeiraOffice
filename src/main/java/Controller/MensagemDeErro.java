package Controller;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class MensagemDeErro extends Parent {

    @FXML
    public Label mensagem;

    public void SetMensagem(String mensagem) {
        this.mensagem.setText(mensagem);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}

