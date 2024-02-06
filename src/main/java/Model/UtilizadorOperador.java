package Model;

/**
 * Classe filha de Utilizador que representa um operador do sistema.
 */
public class UtilizadorOperador extends Utilizador {

    /**
     * Construtor da classe UtilizadorOperador.
     *
     * @param id       O identificador do operador.
     * @param email    O email do operador.
     * @param password A senha do operador.
     */
    public UtilizadorOperador(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Operador);
    }

    /**
     * Obtém o tipo de utilizador, que é Operador.
     *
     * @return O tipo de utilizador Operador.
     */
    public TipoUtilizador getTipo() {
        return TipoUtilizador.Operador;
    }
}
