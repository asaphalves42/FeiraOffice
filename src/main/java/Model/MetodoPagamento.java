package Model;

import Utilidades.Mensagens;

import java.io.IOException;

public enum MetodoPagamento {
    DebitoDireto(1, "Débito direto"),
    Transferencia(2, "Transferência bancária");

    private final String descricao;

    private int value;

    public int getValue() {
        return value;
    }

    MetodoPagamento(int i, String descricao) {
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

    public static MetodoPagamento valueOfId(int id) throws IOException {
        for (MetodoPagamento metodo : values()) {
            if (metodo.value == id) {
                return metodo;
            }
        }
        Mensagens.Erro("Inválido!", "Método inválido!");
        return null;
    }
}

