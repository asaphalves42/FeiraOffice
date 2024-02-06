package Model;

public class FeiraOffice {
    /**
     * O ID da entidade FeiraOffice.
     */
    private int id;
    private String nome;

    /**
     * A morada da entidade FeiraOffice.
     */
    private String morada;

    /**
     * A localidade da entidade FeiraOffice.
     */
    private String localidade;

    /**
     * O código postal da entidade FeiraOffice.
     */
    private String codPostal;

    /**
     * O país da entidade FeiraOffice.
     */
    private Pais pais;

    /**
     * O IBAN da entidade FeiraOffice.
     */
    private String iban;

    /**
     * O BIC da entidade FeiraOffice.
     */
    private String bic;

    /**
     * Constrói um novo objeto FeiraOffice.
     */
    public FeiraOffice() {

    }
    /**
     * Constrói um novo objeto FeiraOffice com os detalhes especificados.
     *
     * @param id         O ID da entidade FeiraOffice.
     * @param nome       O nome da entidade FeiraOffice.
     * @param morada     A morada da entidade FeiraOffice.
     * @param localidade A localidade da entidade FeiraOffice.
     * @param codPostal  O código postal da entidade FeiraOffice.
     * @param pais       O país da entidade FeiraOffice.
     * @param iban       O IBAN da entidade FeiraOffice.
     * @param bic        O BIC da entidade FeiraOffice.
     */
    public FeiraOffice(int id, String nome, String morada, String localidade, String codPostal, Pais pais, String iban, String bic) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.localidade = localidade;
        this.codPostal = codPostal;
        this.pais = pais;
        this.iban = iban;
        this.bic = bic;
    }
    /**
     * Retorna o ID da entidade FeiraOffice.
     *
     * @return O ID da entidade FeiraOffice.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da entidade FeiraOffice.
     *
     * @param id O ID da entidade FeiraOffice.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome da entidade FeiraOffice.
     *
     * @return O nome da entidade FeiraOffice.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da entidade FeiraOffice.
     *
     * @param nome O nome da entidade FeiraOffice.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a morada da entidade FeiraOffice.
     *
     * @return A morada da entidade FeiraOffice.
     */
    public String getMorada() {
        return morada;
    }

    /**
     * Define a morada da entidade FeiraOffice.
     *
     * @param morada A morada da entidade FeiraOffice.
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }

    /**
     * Retorna a localidade da entidade FeiraOffice.
     *
     * @return A localidade da entidade FeiraOffice.
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Define a localidade da entidade FeiraOffice.
     *
     * @param localidade A localidade da entidade FeiraOffice.
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Retorna o código postal da entidade FeiraOffice.
     *
     * @return O código postal da entidade FeiraOffice.
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     * Define o código postal da entidade FeiraOffice.
     *
     * @param codPostal O código postal da entidade FeiraOffice.
     */
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    /**
     * Retorna o país da entidade FeiraOffice.
     *
     * @return O país da entidade FeiraOffice.
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * Define o país da entidade FeiraOffice.
     *
     * @param pais O país da entidade FeiraOffice.
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * Retorna o IBAN da entidade FeiraOffice.
     *
     * @return O IBAN da entidade FeiraOffice.
     */
    public String getIban() {
        return iban;
    }

    /**
     * Define o IBAN da entidade FeiraOffice.
     *
     * @param iban O IBAN da entidade FeiraOffice.
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Retorna o BIC da entidade FeiraOffice.
     *
     * @return O BIC da entidade FeiraOffice.
     */
    public String getBic() {
        return bic;
    }

    /**
     * Define o BIC da entidade FeiraOffice.
     *
     * @param bic O BIC da entidade FeiraOffice.
     */
    public void setBic(String bic) {
        this.bic = bic;
    }
}
