package Model;

/**
 * Classe com atributos, getters e setters referentes ao stock.
 */
public class Stock {
    private Produto idProduto;
    private Unidade idUnidade;
    private int quantidade;

    public Stock(Produto idProduto, Unidade idUnidade, int quantidade) {
        this.idProduto = idProduto;
        this.idUnidade = idUnidade;
        this.quantidade = quantidade;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }

    public Unidade getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Unidade idUnidade) {
        this.idUnidade = idUnidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
