package Model;

/**
 * Classe filha do utilizador, referente ao utilizador administrador.
 */
public class UtilizadorAdm extends Utilizador {
    /**
     * Construtor da classe UtilizadorAdm.
     *
     * @param id       O identificador único do utilizador.
     * @param email    O email do utilizador.
     * @param password A password do utilizador.
     */
    public UtilizadorAdm(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Administrador);
    }



}