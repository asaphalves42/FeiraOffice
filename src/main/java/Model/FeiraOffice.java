package Model;

public class FeiraOffice {
    private int id;
    private String nome;
    private String morada;
    private String localidade;
    private String codPostal;
    private Pais pais;
    private String iban;
    private String bic;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
