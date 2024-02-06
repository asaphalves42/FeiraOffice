package Model;

/**
 * Classe com atributos, getters e setters referentes a país.
 */
public class Pais {
    private int id; // O identificador único do país
    private String nome; // O nome do país
    private String ISO; // O código ISO do país
    private double taxa; // A taxa do país
    private String moeda; // A moeda do país

    /**
     * Construtor da classe Pais.
     *
     * @param id   O identificador único do país.
     * @param nome O nome do país.
     */
    public Pais(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    /**
     * Construtor da classe Pais.
     *
     * @param id   O identificador único do país.
     * @param nome O nome do país.
     * @param ISO  O código ISO do país.
     */
    public Pais(int id, String nome,String ISO) {
        this.id = id;
        this.nome = nome;
        this.ISO = ISO;
    }
    /**
     * Construtor da classe Pais.
     *
     * @param id    O identificador único do país.
     * @param nome  O nome do país.
     * @param ISO   O código ISO do país.
     * @param taxa  A taxa do país.
     * @param moeda A moeda do país.
     */
    public Pais(int id, String nome, String ISO, double taxa, String moeda) {
        this.id = id;
        this.nome = nome;
        this.ISO = ISO;
        this.taxa = taxa;
        this.moeda = moeda;
    }
    /**
     * Construtor padrão da classe Pais.
     */
    public Pais() {

    }
    /**
     * Construtor da classe Pais.
     *
     * @param nomePais   O nome do país.
     * @param moedaPais  A moeda do país.
     */
    public Pais(String nomePais, String moedaPais) {
        this.nome = nomePais;
        this.moeda = moedaPais;
    }
    /**
     * Obtém o código ISO do país.
     *
     * @return O código ISO do país.
     */
    public String getISO() {
        return ISO;
    }
    /**
     * Define o código ISO do país.
     *
     * @param ISO O código ISO do país.
     */
    public void setISO(String ISO) {
        this.ISO = ISO;
    }
    /**
     * Obtém a taxa do país.
     *
     * @return A taxa do país.
     */
    public double getTaxa() {
        return taxa;
    }

    /**
     * Define a taxa do país.
     *
     * @param taxa A taxa do país.
     */
    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    /**
     * Obtém a moeda do país.
     *
     * @return A moeda do país.
     */
    public String getMoeda() {
        return moeda;
    }

    /**
     * Define a moeda do país.
     *
     * @param moeda A moeda do país.
     */
    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    /**
     * Obtém o identificador único do país.
     *
     * @return O identificador único do país.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único do país.
     *
     * @param id O identificador único do país.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do país.
     *
     * @return O nome do país.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do país.
     *
     * @param nome O nome do país.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;

    }
}