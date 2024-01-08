package Model;

public class Cliente {
    private String id;
    private String nome;
    private String email;
    private String morada1;
    private String morada2;
    private String codigoPostal;
    private String cidade;
    private String pais;
    private String nif;

    public Cliente(String id, String nome, String email, String morada1, String morada2, String codigoPostal, String cidade, String pais, String nif) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.morada1 = morada1;
        this.morada2 = morada2;
        this.codigoPostal = codigoPostal;
        this.cidade = cidade;
        this.pais = pais;
        this.nif = nif;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada1() {
        return morada1;
    }

    public void setMorada1(String morada1) {
        this.morada1 = morada1;
    }

    public String getMorada2() {
        return morada2;
    }

    public void setMorada2(String morada2) {
        this.morada2 = morada2;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}


