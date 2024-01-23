package Model;

/**
 * Classe com atributos, getters e setters referentes ao stock.
 */
public class Stock {
    private Produto idProduto;
    private Unidade idUnidade;
    private int quantidade;

    private ProdutoVenda uuidVenda;

    public Stock(Produto idProduto, Unidade idUnidade, int quantidade) {
        this.idProduto = idProduto;
        this.idUnidade = idUnidade;
        this.quantidade = quantidade;
    }

    public Stock(Produto idProduto, Unidade idUnidade, int quantidade, ProdutoVenda uuid) {
        this.idProduto = idProduto;
        this.idUnidade = idUnidade;
        this.quantidade = quantidade;
        this.uuidVenda = uuid;
    }

    public ProdutoVenda getUuidVenda() {
        return uuidVenda;
    }

    public void setUuidVenda(ProdutoVenda uuidVenda) {
        this.uuidVenda = uuidVenda;
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
