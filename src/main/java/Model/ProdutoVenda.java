package Model;

public class ProdutoVenda {
    /**
     * O identificador único do produto de venda.
     */
    private String UUID;

    /**
     * O produto associado ao produto de venda.
     */
    private Produto produto;
    /**
     * A unidade associada ao produto de venda.
     */
    private Unidade unidade;
    /**
     * O preço de venda do produto.
     */
    private double precoVenda;
    /**
     * Construtor da classe ProdutoVenda.
     *
     * @param produto    O produto associado ao produto de venda.
     * @param unidade    A unidade associada ao produto de venda.
     * @param precoVenda O preço de venda do produto.
     */
    public ProdutoVenda(Produto produto, Unidade unidade, double precoVenda) {
        this.produto = produto;
        this.unidade = unidade;
        this.precoVenda = precoVenda;
    }

    /**
     * Construtor da classe ProdutoVenda.
     *
     * @param uuid       O identificador único do produto de venda.
     * @param precoVenda O preço de venda do produto.
     */

    public ProdutoVenda (String uuid, double precoVenda){
        this.UUID = uuid;
        this.precoVenda = precoVenda;
    }
    /**
     * Construtor da classe ProdutoVenda.
     *
     * @param produto    O produto associado ao produto de venda.
     * @param unidade    A unidade associada ao produto de venda.
     * @param precoVenda O preço de venda do produto.
     * @param UUID       O identificador único do produto de venda.
     */
    public ProdutoVenda(Produto produto, Unidade unidade, double precoVenda, String UUID) {
        this.produto = produto;
        this.unidade = unidade;
        this.precoVenda = precoVenda;
        this.UUID = UUID;
    }

    /**
     * Obtém o identificador único do produto de venda.
     *
     * @return O identificador único do produto de venda.
     */
    public String getUUID() {
        return UUID;
    }

    /**
     * Define o identificador único do produto de venda.
     *
     * @param UUID O identificador único do produto de venda.
     */
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    /**
     * Obtém o produto associado ao produto de venda.
     *
     * @return O produto associado ao produto de venda.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto associado ao produto de venda.
     *
     * @param produto O produto associado ao produto de venda.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Obtém a unidade associada ao produto de venda.
     *
     * @return A unidade associada ao produto de venda.
     */
    public Unidade getUnidade() {
        return unidade;
    }
    /**
     * Define a unidade associada ao produto de venda.
     *
     * @param unidade A unidade associada ao produto de venda.
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
    /**
     * Obtém o preço de venda do produto.
     *
     * @return O preço de venda do produto.
     */
    public double getPrecoVenda() {
        return precoVenda;
    }
    /**
     * Define o preço de venda do produto.
     *
     * @param precoVenda O preço de venda do produto.
     */
    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
