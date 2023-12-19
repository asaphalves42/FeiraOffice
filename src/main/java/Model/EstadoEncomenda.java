package Model;

import Utilidades.Mensagens;

import java.io.IOException;

public enum EstadoEncomenda {
    Aprovado(2),
    Recusado(3),
    Pendente(1),
    Default(0);

    private int value;

    public int getValue() {
        return value;
    }

    EstadoEncomenda(int i) {
        this.value = i;
    }

    public static EstadoEncomenda valueOfId(int id) throws IOException {
        for (EstadoEncomenda estado : values()) {
            if (estado.value == id) {
                return estado;
            }
        }
        Mensagens.Erro("Inválido!", "Estado inválido!");
        return null;
    }


}
