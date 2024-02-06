package Model;

/**
 * Classe com atributos, getters e setters referentes a conta corrente.
 */
public class ContaCorrente {
    /**
     * O ID da conta corrente.
     */
    private int id;

    /**
     * O fornecedor associado à conta corrente.
     */
    private Fornecedor idFornecedor;

    /**
     * O saldo da conta corrente.
     */
    private double saldo;
    /**
     * Constrói um novo objeto ContaCorrente com os detalhes especificados.
     *
     * @param id           O ID da conta corrente.
     * @param idFornecedor O fornecedor associado à conta corrente.
     * @param saldo        O saldo da conta corrente.
     */
    public ContaCorrente(int id, Fornecedor idFornecedor, double saldo) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.saldo = saldo;
    }
    /**
     * Obtém o ID da conta corrente.
     *
     * @return O ID da conta corrente.
     */

    public int getId() {
        return id;
    }
    /**
     * Define o ID da conta corrente.
     *
     * @param id O ID da conta corrente a definir.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o fornecedor associado à conta corrente.
     *
     * @return O fornecedor associado à conta corrente.
     */
    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    /**
     * Define o fornecedor associado à conta corrente.
     *
     * @param idFornecedor O fornecedor associado à conta corrente a definir.
     */
    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
    /**
     * Obtém o saldo da conta corrente.
     *
     * @return O saldo da conta corrente.
     */
    public double getSaldo() {
        return saldo;
    }
    /**
     * Define o saldo da conta corrente.
     *
     * @param saldo O saldo da conta corrente a definir.
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
