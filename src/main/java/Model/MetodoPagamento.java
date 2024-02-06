package Model;

import Utilidades.Mensagens;

import java.io.IOException;

public enum MetodoPagamento {
    /**
     * Opção de pagamento: Débito Direto.
     */
    DebitoDireto(1, "Débito direto"),
    /**
     * Opção de pagamento: Transferência Bancária.
     */
    Transferencia(2, "Transferência bancária");
    /**
     * A descrição do método de pagamento.
     */
    private final String descricao;


    /**
     * O valor numérico associado ao método de pagamento.
     */
    private int value;
    /**
     * Obtém o valor numérico do método de pagamento.
     *
     * @return O valor numérico do método de pagamento.
     */
    public int getValue() {
        return value;
    }
    /**
     * Enumeração que representa os métodos de pagamento disponíveis.
     */
    MetodoPagamento(int i, String descricao) {
        this.value = i;
        this.descricao = descricao;
    }
    /**
     * Obtém a descrição do método de pagamento.
     *
     * @return A descrição do método de pagamento.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Obtém a representação em string do método de pagamento.
     *
     * @return A descrição do método de pagamento.
     */
    @Override
    public String toString() {
        return descricao;
    }
    /**
     * Obtém o enum MetodoPagamento associado a um determinado valor numérico.
     *
     * @param id O valor numérico a ser procurado.
     * @return O enum MetodoPagamento correspondente ao valor numérico especificado.
     * @throws IOException se nenhum enum correspondente for encontrado.
     */
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

