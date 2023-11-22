package Model;

public class UtilizadorAdm extends Utilizador {

    public UtilizadorAdm(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Administrador);
    }



}