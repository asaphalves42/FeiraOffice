package Model;

import Utilidades.Mensagens;

import java.io.IOException;
/**
 * Enumeração que representa os estados possíveis de uma encomenda.
 */
public enum EstadoEncomenda {
    Aprovado(2),// Estado de encomenda aprovada
    Recusado(3),// Estado de encomenda recusada
    Pendente(1),// Estado de encomenda pendente
    Default(0);// Estado de encomenda padrão

    private int value;// Valor associado ao estado
    /**
     * Obtém o valor associado ao estado.
     *
     * @return O valor associado ao estado
     */
    public int getValue() {
        return value;
    }
    /**
     * Construtor para criar um novo estado de encomenda com um valor associado.
     *
     * @param i O valor associado ao estado
     */
    EstadoEncomenda(int i) {
        this.value = i;
    }
    /**
     * Obtém o estado de encomenda correspondente a um determinado valor.
     *
     * @param id O valor associado ao estado a ser obtido
     * @return O estado de encomenda correspondente ao valor fornecido
     * @throws IOException Se o valor fornecido não corresponder a nenhum estado válido
     */
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
