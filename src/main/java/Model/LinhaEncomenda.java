package Model;

public class LinhaEncomenda {

    private int id;
    private int sequencia;
    private Produto produto;
    private double preco;
    private double quantidade;
    private Unidade unidade;
    private LinhaTaxa taxa;
    private InformacaoQuantidade idInfoQuantidade;
    private double total;

    public LinhaEncomenda(int id, int sequencia, Produto produto, double preco, double quantidade, Unidade unidade, LinhaTaxa taxa, InformacaoQuantidade idInfoQuantidade, double total) {
        this.id = id;
        this.sequencia = sequencia;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.taxa = taxa;
        this.idInfoQuantidade = idInfoQuantidade;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public LinhaTaxa getTaxa() {
        return taxa;
    }

    public void setTaxa(LinhaTaxa taxa) {
        this.taxa = taxa;
    }

    public InformacaoQuantidade getIdInfoQuantidade() {
        return idInfoQuantidade;
    }

    public void setIdInfoQuantidade(InformacaoQuantidade idInfoQuantidade) {
        this.idInfoQuantidade = idInfoQuantidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
