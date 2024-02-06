package Model;

/**
 * Classe com atributos, getters e setters referentes ao produto.
 */
public class Produto {
    /**
     * O identificador único do produto.
     */
    private String id;

    /**
     * O fornecedor do produto.
     */
    private Fornecedor fornecedor;

    /**
     * O identificador externo do produto.
     */
    private String idExterno;

    /**
     * A descrição do produto.
     */
    private String descricao;

    /**
     * A unidade do produto.
     */
    private Unidade unidade;

    /**
     * O estado do produto.
     */
    private int estado;

    /**
     * A descrição da unidade do produto.
     */
    private String descricaoUnidade;

    /**
     * O preço unitário do produto.
     */
    private double precoUnitario;

    /**
     * Construtor vazio da classe Produto.
     */
    public Produto() {
    }
    /**
     * Construtor da classe Produto.
     *
     * @param id     O identificador único do produto.
     */
    public Produto(String id){
        this.id = id;
    }
    /**
     * Construtor da classe Produto.
     *
     * @param id            O identificador único do produto.
     * @param fornecedor    O fornecedor do produto.
     * @param idExterno     O identificador externo do produto.
     * @param descricao     A descrição do produto.
     * @param unidade       A unidade do produto.
     * @param estado        O estado do produto.
     */
    public Produto(String id, Fornecedor fornecedor, String idExterno, String descricao, Unidade unidade, int estado) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.idExterno = idExterno;
        this.descricao = descricao;
        this.unidade = unidade;
        this.estado = estado;
        this.descricaoUnidade = unidade != null ? unidade.getDescricao() : "";
    }
    /**
     * Construtor da classe Produto.
     *
     * @param idProduto     O identificador único do produto.
     * @param descricaoProduto A descrição do produto.
     * @param unidade       A unidade do produto.
     */
    public Produto(String idProduto, String descricaoProduto, Unidade unidade) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
        this.unidade = unidade;
        this.descricaoUnidade = unidade != null ? unidade.getDescricao() : "";
    }
    /**
     * Construtor da classe Produto.
     *
     * @param idProduto     O identificador único do produto.
     * @param descricaoProduto A descrição do produto.
     * @param unidade       A unidade do produto.
     * @param fornecedor    O fornecedor do produto.
     * @param precoUnitario O preço unitário do produto.
     * @param idExterno     O identificador externo do produto.
     */
    public Produto(String idProduto, String descricaoProduto, Unidade unidade, Fornecedor fornecedor, double precoUnitario, String idExterno) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
        this.unidade = unidade;
        this.fornecedor = fornecedor;
        this.precoUnitario = precoUnitario;
        this.idExterno = idExterno;
    }
    /**
     * Construtor da classe Produto.
     *
     * @param idProduto     O identificador único do produto.
     * @param descricaoProduto A descrição do produto.
     */
    public Produto(String idProduto, String descricaoProduto) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
    }

    /**
     * Obtém o preço unitário do produto.
     *
     * @return O preço unitário do produto.
     */
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    /**
     * Define o preço unitário do produto.
     *
     * @param precoUnitario O preço unitário do produto.
     */
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    /**
     * Obtém o identificador externo do fornecedor do produto como uma string.
     *
     * @return O identificador externo do fornecedor do produto como uma string.
     */
    public String getIdFornecedorAsString() {
        return fornecedor != null ? fornecedor.getIdExterno() : "";
    }
    /**
     * Obtém o nome do fornecedor do produto.
     *
     * @return O nome do fornecedor do produto.
     */
    public String getNomeFornecedor() {
        return fornecedor != null ? fornecedor.getNome() : "";
    }
    /**
     * Obtém o estado do produto.
     *
     * @return O estado do produto.
     */
    public int getEstado() {
        return estado;
    }
    /**
     * Define o estado do produto.
     *
     * @param estado O estado do produto.
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    /**
     * Obtém a unidade do produto.
     *
     * @return A unidade do produto.
     */
    public Unidade getUnidade() {
        return unidade;
    }
    /**
     * Define a unidade do produto.
     *
     * @param unidade A unidade do produto.
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
        this.descricaoUnidade = unidade != null ? unidade.getDescricao() : "";
    }
    /**
     * Obtém o fornecedor do produto.
     *
     * @return O fornecedor do produto.
     */
    public Fornecedor getFornecedor() {
        return fornecedor;
    }
    /**
     * Define o fornecedor do produto.
     *
     * @param fornecedor O fornecedor do produto.
     */
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    /**
     * Obtém o identificador único do produto.
     *
     * @return O identificador único do produto.
     */
    public String getId() {
        return id;
    }
    /**
     * Define o identificador único do produto.
     *
     * @param id O identificador único do produto.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Obtém o identificador externo do produto.
     *
     * @return O identificador externo do produto.
     */
    public String getIdExterno() {
        return idExterno;
    }
    /**
     * Define o identificador externo do produto.
     *
     * @param idExterno O identificador externo do produto.
     */
    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }
    /**
     * Obtém a descrição do produto.
     *
     * @return A descrição do produto.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Define a descrição do produto.
     *
     * @param descricao A descrição do produto.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém o identificador externo do fornecedor do produto como uma string.
     *
     * @return O identificador externo do fornecedor do produto como uma string.
     */
    public Fornecedor getIdFornecedor() {
        return fornecedor.getIdUtilizador().getFornecedor();
    }
    /**
     * Obtém a descrição da unidade do produto.
     *
     * @return A descrição da unidade do produto.
     */
    public String getDescricaoUnidade() {
        return descricaoUnidade;
    }
}
