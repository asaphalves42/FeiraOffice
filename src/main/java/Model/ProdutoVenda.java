package Model;

public class ProdutoVenda {
    private Produto produto;
    private Unidade unidade;
    private double precoVenda;

    public ProdutoVenda(Produto produto, Unidade unidade, double precoVenda) {
        this.produto = produto;
        this.unidade = unidade;
        this.precoVenda = precoVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
