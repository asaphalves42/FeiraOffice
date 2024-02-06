package Model;

/**
 * Classe com atributos, getters e setters referentes ao stock.
 */
public class Stock {
    /**
     * O produto associado ao stock.
     */
    private Produto idProduto;

    /**
     * A unidade associada ao stock.
     */
    private Unidade idUnidade;

    /**
     * A quantidade de produtos em stock.
     */
    private int quantidade;

    /**
     * O UUID de venda associado ao stock, se aplicável.
     */
    private ProdutoVenda uuidVenda;
    /**
     * Construtor da classe Stock.
     *
     * @param idProduto O produto associado ao stock.
     * @param idUnidade A unidade associada ao stock.
     * @param quantidade A quantidade de produtos em stock.
     */
    public Stock(Produto idProduto, Unidade idUnidade, int quantidade) {
        this.idProduto = idProduto;
        this.idUnidade = idUnidade;
        this.quantidade = quantidade;
    }
    /**
     * Construtor da classe Stock.
     *
     * @param idProduto O produto associado ao stock.
     * @param idUnidade A unidade associada ao stock.
     * @param quantidade A quantidade de produtos em stock.
     * @param uuid O UUID de venda associado ao stock.
     */
    public Stock(Produto idProduto, Unidade idUnidade, int quantidade, ProdutoVenda uuid) {
        this.idProduto = idProduto;
        this.idUnidade = idUnidade;
        this.quantidade = quantidade;
        this.uuidVenda = uuid;
    }
    /**
     * Obtém o UUID de venda associado ao stock.
     *
     * @return O UUID de venda associado ao stock.
     */
    public ProdutoVenda getUuidVenda() {
        return uuidVenda;
    }
    /**
     * Define o UUID de venda associado ao stock.
     *
     * @param uuidVenda O UUID de venda associado ao stock.
     */
    public void setUuidVenda(ProdutoVenda uuidVenda) {
        this.uuidVenda = uuidVenda;
    }
    /**
     * Obtém o produto associado ao stock.
     *
     * @return O produto associado ao stock.
     */
    public Produto getIdProduto() {
        return idProduto;
    }
    /**
     * Define o produto associado ao stock.
     *
     * @param idProduto O produto associado ao stock.
     */
    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }
    /**
     * Obtém a unidade associada ao stock.
     *
     * @return A unidade associada ao stock.
     */
    public Unidade getIdUnidade() {
        return idUnidade;
    }
    /**
     * Define a unidade associada ao stock.
     *
     * @param idUnidade A unidade associada ao stock.
     */
    public void setIdUnidade(Unidade idUnidade) {
        this.idUnidade = idUnidade;
    }
    /**
     * Obtém a quantidade de produtos em stock.
     *
     * @return A quantidade de produtos em stock.
     */
    public int getQuantidade() {
        return quantidade;
    }
    /**
     * Define a quantidade de produtos em stock.
     *
     * @param quantidade A quantidade de produtos em stock.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
