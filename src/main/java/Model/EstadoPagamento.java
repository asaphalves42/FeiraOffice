package Model;

import Utilidades.Mensagens;

import java.io.IOException;

/**
 * Enumeração que representa os diferentes estados de pagamento.
 */
public enum EstadoPagamento {
    NaoPago(1, "Não pago"),// Estado de pagamento não pago
    Pago(2, "Pago"),// Estado de pagamento pago
    NaoAplicavel(3, "Não aplicavel");// Estado de pagamento não aplicável

    private final String descricao;// Descrição do estado de pagamento


    private int value;// Valor associado ao estado de pagamento
    /**
     * Obtém o valor associado ao estado de pagamento.
     *
     * @return O valor associado ao estado de pagamento
     */
    public int getValue() {
        return value;
    }
    /**
     * Construtor para criar um novo estado de pagamento com um valor associado e uma descrição.
     *
     * @param i         O valor associado ao estado de pagamento
     * @param descricao A descrição do estado de pagamento
     */
    EstadoPagamento(int i, String descricao) {
        this.value = i;
        this.descricao = descricao;
    }
    /**
     * Obtém a descrição do estado de pagamento.
     *
     * @return A descrição do estado de pagamento
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Obtém a representação em string do estado de pagamento.
     *
     * @return A descrição do estado de pagamento
     */
    @Override
    public String toString() {
        return descricao;
    }
    /**
     * Obtém o estado de pagamento correspondente a um determinado valor.
     *
     * @param id O valor associado ao estado de pagamento a ser obtido
     * @return O estado de pagamento correspondente ao valor fornecido
     * @throws IOException Se o valor fornecido não corresponder a nenhum estado de pagamento válido
     */
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
