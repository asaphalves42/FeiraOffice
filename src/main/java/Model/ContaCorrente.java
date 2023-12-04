package Model;

/**
 * Classe com atributos, getters e setters referentes a conta corrente.
 */
public class ContaCorrente {

    private int id;
    private Fornecedor idFornecedor;
    private double saldo;

    public ContaCorrente(int id, Fornecedor idFornecedor, double saldo) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
