package Model;

public class Fornecedor {

    private String id;
    private UtilizadorFornecedor idUtil;
    private String nome;
    private String morada;
    private String nif;
    private String telefone;
    private Pais pais;

    public Fornecedor(String id, UtilizadorFornecedor idUtil, String nome, String morada, String nif, String telefone, Pais pais) {
        this.id = id;
        this.idUtil = idUtil;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;
        this.telefone = telefone;
        this.pais = pais;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UtilizadorFornecedor getIdUtil() {
        return idUtil;
    }

    public void setIdUtil(UtilizadorFornecedor idUtil) {
        this.idUtil = idUtil;
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
