package Model;

public class LinhaEncomenda {

    private int id;
    private int sequencia;
    private Produto idProduto;
    private double preco;
    private int quantidade;
    private Unidade idUnidade;
    private LinhaTaxa idTaxa;
    private InformacaoQuantidade idInfoQuantidade;
    private double total;

    public LinhaEncomenda(int id, int sequencia, Produto idProduto, double preco, int quantidade, Unidade idUnidade, LinhaTaxa idTaxa, InformacaoQuantidade idInfoQuantidade, double total) {
        this.id = id;
        this.sequencia = sequencia;
        this.idProduto = idProduto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.idUnidade = idUnidade;
        this.idTaxa = idTaxa;
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

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Unidade getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Unidade idUnidade) {
        this.idUnidade = idUnidade;
    }

    public LinhaTaxa getIdTaxa() {
        return idTaxa;
    }

    public void setIdTaxa(LinhaTaxa idTaxa) {
        this.idTaxa = idTaxa;
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
