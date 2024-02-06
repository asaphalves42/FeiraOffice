package Model;

/**
 * Classe com atributos, getters e setters referentes ao utilizador.
 */
public abstract class Utilizador {
    /**
     * Construtor da classe Utilizador.
     *
     * @param id    O identificador único do utilizador.
     * @param tipo  O tipo de utilizador.
     */
    public Utilizador (int id, TipoUtilizador tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    /**
     * Construtor da classe Utilizador.
     *
     * @param id       O identificador único do utilizador.
     * @param email    O email do utilizador.
     * @param password A password do utilizador.
     * @param tipo     O tipo de utilizador.
     */

    public Utilizador(int id, String email, String password, TipoUtilizador tipo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }
    /**
     * O email do utilizador.
     */
    private String email;
    /**
     * A password do utilizador.
     */
    private String password;
    /**
     * O identificador único do utilizador.
     */
    private int id;
    /**
     * O tipo de utilizador.
     */
    private TipoUtilizador tipo;


    /**
     * Obtém o identificador único do utilizador.
     *
     * @return O identificador único do utilizador.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único do utilizador.
     *
     * @param id O identificador único do utilizador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o email do utilizador.
     *
     * @return O email do utilizador.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Obtém a password do utilizador.
     *
     * @return A password do utilizador.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define o email do utilizador.
     *
     * @param email O email do utilizador.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Define a password do utilizador.
     *
     * @param password A password do utilizador.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Obtém o tipo de utilizador.
     *
     * @return O tipo de utilizador.
     */
    public TipoUtilizador getTipo(){
        return this.tipo;
    }
}
