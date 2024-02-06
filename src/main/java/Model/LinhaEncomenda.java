package Model;

/**
 * Classe com atributos, getters e setters referentes as linhas da encomenda.
 */
public class LinhaEncomenda {

    /**
     * O identificador da linha de encomenda.
     */
    private int id;
    /**
     * A encomenda à qual a linha pertence.
     */
    private Encomenda idEncomenda;

    /**
     * A sequência da linha dentro da encomenda.
     */
    private int sequencia;
    /**
     * O produto associado à linha.
     */
    private Produto produto;
    /**
     * O preço unitário do produto.
     */
    private double preco;
    /**
     * A quantidade do produto na linha de encomenda.
     */
    private double quantidade;
    /**
     * A unidade de medida do produto na linha de encomenda.
     */
    private Unidade unidade;
    /**
     * O país associado à taxa aplicada na linha de encomenda.
     */
    private Pais taxa;
    /**
     * O total da taxa aplicada na linha de encomenda.
     */
    private double totalTaxa;
    /**
     * O total da incidência aplicada na linha de encomenda.
     */
    private double totalIncidencia;
    /**
     * O total da linha de encomenda (preço * quantidade).
     */
    private double totalLinha;
    /**
     * Construtor para inicializar uma linha de encomenda com todos os detalhes.
     *
     * @param id O ID da linha de encomenda.
     * @param idEncomenda O ID da encomenda à qual a linha pertence.
     * @param sequencia A sequência da linha na encomenda.
     * @param produto O produto da linha de encomenda.
     * @param preco O preço unitário da linha de encomenda.
     * @param quantidade A quantidade da linha de encomenda.
     * @param unidade A unidade de medida da linha de encomenda.
     * @param taxa O país onde a taxa é aplicada.
     * @param totalTaxa O total da taxa da linha de encomenda.
     * @param totalIncidencia O total da incidência da linha de encomenda.
     */
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
    /**
     * Construtor para inicializar uma linha de encomenda com detalhes básicos e calcular o total da linha.
     *
     * @param id O ID da linha de encomenda.
     * @param idEncomenda O ID da encomenda à qual a linha pertence.
     * @param sequencia A sequência da linha na encomenda.
     * @param produto O produto da linha de encomenda.
     * @param preco O preço unitário da linha de encomenda.
     * @param quantidade A quantidade da linha de encomenda.
     * @param taxa O país onde a taxa é aplicada.
     * @param totalTaxa O total da taxa da linha de encomenda.
     * @param totalIncidencia O total da incidência da linha de encomenda.
     * @param totalLinha O total da linha de encomenda.
     */
    public LinhaEncomenda(int id, Encomenda idEncomenda, int sequencia, Produto produto, double preco, double quantidade,Pais taxa,
                          double totalTaxa, double totalIncidencia, double totalLinha) {
        this.id = id;
        this.idEncomenda = idEncomenda;
        this.sequencia = sequencia;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.taxa = taxa;
        this.totalTaxa = totalTaxa;
        this.totalIncidencia = totalIncidencia;
        this.totalLinha = totalLinha;
    }
    /**
     * Construtor para inicializar uma linha de encomenda com um produto encontrado e uma quantidade.
     *
     * @param produtoEncontrado O produto encontrado.
     * @param quantidade A quantidade do produto.
     */
    public LinhaEncomenda(Produto produtoEncontrado, double quantidade) {
        this.produto = produtoEncontrado;
        this.quantidade = quantidade;

    }


    /**
     * Obtém o total da linha de encomenda, incluindo incidência e taxa.
     *
     * @return O total da linha de encomenda.
     */
    public double getTotalLinha() {
        return totalLinha;
    }
    /**
     * Define o total da linha de encomenda.
     *
     * @param totalLinha O total da linha de encomenda a ser definido.
     */
    public void setTotalLinha(double totalLinha) {
        this.totalLinha = totalLinha;
    }

    /**
     * Obtém a encomenda à qual a linha pertence.
     *
     * @return A encomenda à qual a linha pertence.
     */
    public Encomenda getIdEncomenda() {
        return idEncomenda;
    }
    /**
     * Define a encomenda à qual a linha pertence.
     *
     * @param idEncomenda A encomenda à qual a linha pertence a ser definida.
     */
    public void setIdEncomenda(Encomenda idEncomenda) {
        this.idEncomenda = idEncomenda;
    }
    /**
     * Obtém o ID da linha de encomenda.
     *
     * @return O ID da linha de encomenda.
     */
    public int getId() {
        return id;
    }
    /**
     * Obtém o ID do produto como uma string.
     *
     * @return O ID do produto como uma string.
     */
    public String getIdProdutoString(){
        return produto.getId();
    }

    /**
     * Define o ID da linha de encomenda.
     *
     * @param id O ID da linha de encomenda a ser definido.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a sequência da linha na encomenda.
     *
     * @return A sequência da linha na encomenda.
     */
    public int getSequencia() {
        return sequencia;
    }
    /**
     * Define a sequência da linha na encomenda.
     *
     * @param sequencia A sequência da linha na encomenda a ser definida.
     */
    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
    /**
     * Obtém o produto da linha de encomenda.
     *
     * @return O produto da linha de encomenda.
     */
    public Produto getProduto() {
        return produto;
    }
    /**
     * Define o produto da linha de encomenda.
     *
     * @param produto O produto da linha de encomenda a ser definido.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    /**
     * Obtém o preço unitário da linha de encomenda.
     *
     * @return O preço unitário da linha de encomenda.
     */
    public double getPreco() {
        return preco;
    }
    /**
     * Define o preço unitário da linha de encomenda.
     *
     * @param preco O preço unitário da linha de encomenda a ser definido.
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
    /**
     * Obtém a quantidade da linha de encomenda.
     *
     * @return A quantidade da linha de encomenda.
     */
    public double getQuantidade() {
        return quantidade;
    }
    /**
     * Define a quantidade da linha de encomenda.
     *
     * @param quantidade A quantidade da linha de encomenda a ser definida.
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém a unidade de medida da linha de encomenda.
     *
     * @return A unidade de medida da linha de encomenda.
     */
    public Unidade getUnidade() {
        return unidade;
    }
    /**
     * Define a unidade de medida da linha de encomenda.
     *
     * @param unidade A unidade de medida da linha de encomenda a ser definida.
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    /**
     * Obtém o país onde a taxa é aplicada.
     *
     * @return O país onde a taxa é aplicada.
     */
    public Pais getTaxa() {
        return taxa;
    }

    /**
     * Define o país onde a taxa é aplicada.
     *
     * @param taxa O país onde a taxa é aplicada a ser definido.
     */
    public void setTaxa(Pais taxa) {
        this.taxa = taxa;
    }

    /**
     * Obtém o total da linha de encomenda, incluindo incidência e taxa.
     *
     * @return O total da linha de encomenda.
     */
    public double getTotal() {
        return totalIncidencia+totalTaxa;
    }
    /**
     * Obtém o total da incidência da linha de encomenda.
     *
     * @return O total da incidência da linha de encomenda.
     */
    public double getTotalIncidencia() {
        return totalIncidencia;
    }
    /**
     * Obtém o total da taxa da linha de encomenda.
     *
     * @return O total da taxa da linha de encomenda.
     */
    public double getTotalTaxa() {
        return totalTaxa;
    }

}