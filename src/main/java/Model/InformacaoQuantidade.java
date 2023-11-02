package Model;

public class InformacaoQuantidade {
    private int id;
    private Unidade idUnidade;
    private double valor;

    public InformacaoQuantidade(int id, Unidade idUnidade, double valor) {
        this.id = id;
        this.idUnidade = idUnidade;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Unidade getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Unidade idUnidade) {
        this.idUnidade = idUnidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
