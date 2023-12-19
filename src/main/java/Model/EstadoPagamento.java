package Model;

import Utilidades.Mensagens;

import java.io.IOException;

public enum EstadoPagamento {
    NaoPago(1, "Não pago"),
    Pago(2, "Pago"),
    NaoAplicavel(3, "Não aplicavel");

    private final String descricao;

    private int value;

    public int getValue() {
        return value;
    }

    EstadoPagamento(int i, String descricao) {
        this.value = i;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static EstadoPagamento valueOfId(int id) throws IOException {
        for (EstadoPagamento estado : values()) {
            if (estado.value == id) {
                return estado;
            }
        }
        Mensagens.Erro("Inválido!", "Estado inválido!");
        return null;
    }


}
