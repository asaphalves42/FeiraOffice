package Model;

/**
 * Enumeração que define os tipos de utilizadores no sistema.
 */
public enum TipoUtilizador {
    /**
     * Representa o tipo de utilizador "Operador".
     */
    Operador(2),

    /**
     * Representa o tipo de utilizador "Administrador".
     */
    Administrador(1),

    /**
     * Representa o tipo de utilizador "Fornecedor".
     */
    Fornecedor(3),

    /**
     * Representa o tipo de utilizador padrão.
     */
    Default(0);

    /**
     * O valor associado ao tipo de utilizador.
     */
    private int value;

    /**
     * Obtém o valor associado ao tipo de utilizador.
     *
     * @return O valor associado ao tipo de utilizador.
     */
    public int getValue() {
        return value;
    }

    /**
     * Construtor da enumeração TipoUtilizador.
     *
     * @param i O valor associado ao tipo de utilizador.
     */
    TipoUtilizador(int i) {
        this.value = i;
    }
}
