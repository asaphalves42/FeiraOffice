package Model;

import Utilidades.Mensagens;

import java.io.IOException;

public enum Estado {
    Aprovado(2),
    Recusado(3),
    Pendente(1),
    Default(0);

    private int value;

    public int getValue() {
        return value;
    }

    Estado(int i) {
        this.value = i;
    }

    public static Estado valueOfId(int id) throws IOException {
        for (Estado estado : values()) {
            if (estado.value == id) {
                return estado;
            }
        }
        Mensagens.Erro("Inválido!", "Estado inválido!");
        return null;
    }
}
