package Model;

public class UtilizadorOperador extends Utilizador {
    public UtilizadorOperador(int id, String email, String password) {
        super(id, email, password);
    }

    public TipoUtilizador getTipo() {
        return TipoUtilizador.Operador;
    }
}



