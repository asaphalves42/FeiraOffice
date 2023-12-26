package Model;

public class FornecedorProdutoData {
    private int id;
    private String descricao;
    private String nomeFornecedor;
    private int idUnidade;
    private double precoUnitario;

    public FornecedorProdutoData(int id, String descricao, String nomeFornecedor, int idUnidade, double precoUnitario) {
        this.id = id;
        this.descricao = descricao;
        this.nomeFornecedor = nomeFornecedor;
        this.idUnidade = idUnidade;
        this.precoUnitario = precoUnitario;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }



}