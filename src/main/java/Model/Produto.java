package Model;

public class Produto {
    private int id;
    private Fornecedor idFornecedor;
    private String descricao;

    public Produto(int id, Fornecedor idFornecedor, String descricao) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.descricao = descricao;
    }

    public Produto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
