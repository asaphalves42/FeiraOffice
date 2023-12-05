package Model;

/**
 * Classe filha do utilizador, referente ao utilizador operador..
 */
public class UtilizadorOperador extends Utilizador {
    public UtilizadorOperador(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Operador);
    }

    public TipoUtilizador getTipo() {
        return TipoUtilizador.Operador;
    }

}



