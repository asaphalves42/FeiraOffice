package Model;

public class Fornecedor {
    private int id;
    private String nome;
    private String morada1;
    private String morada2;
    private String localidade;
    private String codigoPostal;
    private String nif;
    private String telefone;
    private Pais idPais;
    private Fornecedor idFornecedor;

    public Fornecedor(int id, String nome, String morada1, String morada2, String localidade, String codigoPostal, String nif, String telefone, Pais idPais, Fornecedor idFornecedor) {
        this.id = id;
        this.nome = nome;
        this.morada1 = morada1;
        this.morada2 = morada2;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.nif = nif;
        this.telefone = telefone;
        this.idPais = idPais;
        this.idFornecedor = idFornecedor;
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

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
