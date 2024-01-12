package Model;

/**
 * Classe com atributos, getters e setters referentes ao produto.
 */
public class Produto {
    private String id;
    private Fornecedor fornecedor;
    private String idExterno;
    private String descricao;
    private Unidade unidade;
    private int estado;
    private String descricaoUnidade;
    private double precoUnitario;

    public Produto() {
    }

    public Produto(String id, Fornecedor fornecedor, String idExterno, String descricao, Unidade unidade, int estado) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.idExterno = idExterno;
        this.descricao = descricao;
        this.unidade = unidade;
        this.estado = estado;
        this.descricaoUnidade = unidade != null ? unidade.getDescricao() : "";
    }

    public Produto(String idProduto, String descricaoProduto, Unidade unidade) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
        this.unidade = unidade;
        this.descricaoUnidade = unidade != null ? unidade.getDescricao() : "";
    }

    public Produto(String idProduto, String descricaoProduto, Unidade unidade, Fornecedor fornecedor, double precoUnitario, String idExterno) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
        this.unidade = unidade;
        this.fornecedor = fornecedor;
        this.precoUnitario = precoUnitario;
        this.idExterno = idExterno;
    }

    public Produto(String idProduto, String descricaoProduto) {
        this.id = idProduto;
        this.descricao = descricaoProduto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getIdFornecedorAsString() {
        return fornecedor != null ? fornecedor.getIdExterno() : "";
    }

    public String getNomeFornecedor() {
        return fornecedor != null ? fornecedor.getNome() : "";
    }
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
        this.descricaoUnidade = unidade != null ? unidade.getDescricao() : "";
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fornecedor getIdFornecedor() {
        return fornecedor.getIdUtilizador().getFornecedor();
    }

    public String getDescricaoUnidade() {
        return descricaoUnidade;
    }
}
