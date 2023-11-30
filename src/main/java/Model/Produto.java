package Model;

public class Produto {
    private String id;
    private Fornecedor fornecedor;
    private String idExterno;
    private String descricao;
    private Unidade unidade;

    private int estado;
    public Produto(){}

    public Produto(String id, Fornecedor fornecedor, String idExterno, String descricao, Unidade unidade,int estado) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.idExterno = idExterno;
        this.descricao = descricao;
        this.unidade = unidade;
        this.estado = estado;
    }

    public Produto(String idProduto, String descricaoProduto, Unidade unidade) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
        this.unidade = unidade;
    }


    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }


    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}