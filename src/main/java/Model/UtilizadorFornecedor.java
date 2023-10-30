package Model;

public class UtilizadorFornecedor extends Utilizador{

    private String nome;
    private String morada;
    private String nif;
    private String telefone;
    private String pais;

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public UtilizadorFornecedor(int id, String email, String password, String nome, String morada, String nif, String telefone, String pais) {
        super(id, email, password);
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;
        this.telefone = telefone;
        this.pais = pais;
    }

    public UtilizadorFornecedor(int id, String email, String password) {
        super(id, email, password);
    }

    @Override
    public TipoUtilizador getTipo() {
        return TipoUtilizador.Fornecedor;
    }
}
