package Model;

/**
 * Classe com atributos, getters e setters referentes as linhas da encomenda.
 */
public class LinhaEncomenda {

    private int id;
    private Encomenda idEncomenda;
    private int sequencia;
    private Produto produto;
    private double preco;
    private double quantidade;
    private Unidade unidade;
    private Pais taxa;
    private double totalTaxa;
    private double totalIncidencia;
    private double totalLinha;

    public LinhaEncomenda(int id, Encomenda idEncomenda, int sequencia, Produto produto, double preco, double quantidade, Unidade unidade, Pais taxa,
                          double totalTaxa, double totalIncidencia) {
        this.id = id;
        this.idEncomenda = idEncomenda;
        this.sequencia = sequencia;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.taxa = taxa;
        this.totalTaxa = totalTaxa;
        this.totalIncidencia = totalIncidencia;
        this.totalLinha = totalLinha;
    }

    public LinhaEncomenda(Produto produtoEncontrado, double quantidade) {
        this.produto = produtoEncontrado;
        this.quantidade = quantidade;

    }


    public double getTotalLinha() {
        return totalLinha;
    }

    public void setTotalLinha(double totalLinha) {
        this.totalLinha = totalLinha;
    }

    public Encomenda getIdEncomenda() {
        return idEncomenda;
    }

    public void setIdEncomenda(Encomenda idEncomenda) {
        this.idEncomenda = idEncomenda;
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

    public Pais getTaxa() {
        return taxa;
    }

    public void setTaxa(Pais taxa) {
        this.taxa = taxa;
    }


    public double getTotal() {
        return totalIncidencia+totalTaxa;
    }

    public double getTotalIncidencia() {
        return totalIncidencia;
    }

    public double getTotalTaxa() {
        return totalTaxa;
    }

}