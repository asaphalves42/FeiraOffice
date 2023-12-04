package Model;

/**
 * Classe filha do utilizador, referente ao utilizador administrador.
 */
public class UtilizadorAdm extends Utilizador {

    public UtilizadorAdm(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Administrador);
    }



}