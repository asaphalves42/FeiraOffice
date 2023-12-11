package Model;

/**
 * Classe com atributos, getters e setters referentes ao utilizador.
 */
public abstract class Utilizador {

    public Utilizador (int id, TipoUtilizador tipo) {
        this.id = id;
        this.tipo = tipo;
    }


    public Utilizador(int id, String email, String password, TipoUtilizador tipo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }
    private String email;
    private String password;
    private int id;
    private TipoUtilizador tipo;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public TipoUtilizador getTipo(){
        return this.tipo;
    }
}
