package Model;

public class Stock {
    private String idProduto;
    private int idUnidade;
    private int quantidade;

    // Construtor
    public Stock(String idProduto, int idUnidade, int quantidade) {
        this.idProduto = idProduto;
        this.idUnidade = idUnidade;
        this.quantidade = quantidade;
    }

    // Métodos getters e setters
    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
