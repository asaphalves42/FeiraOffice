package Model;

import java.time.LocalDate;

public class EncomendaFornecedor {

    private int id;// Identificador da encomenda
    private String referencia;// Referência da encomenda
    private LocalDate data;// Data da encomenda
    private String nomeFornecedor;// Nome do fornecedor
    private String emailUtilizador;// Email do utilizador que fez a encomenda
    private int idutilizador;// Identificador do utilizador
    private double valorTotal;// Valor total da encomenda

    /**
     * Construtor para criar uma nova instância de EncomendaFornecedor.
     *
     * @param id               Identificador da encomenda
     * @param referencia       Referência da encomenda
     * @param data             Data da encomenda
     * @param nomeFornecedor   Nome do fornecedor
     * @param valorTotal       Valor total da encomenda
     * @param emailUtilizador  Email do utilizador que fez a encomenda
     * @param idutilizador     Identificador do utilizador
     */

    public EncomendaFornecedor(int id, String referencia, LocalDate data, String nomeFornecedor, double valorTotal, String emailUtilizador,int idutilizador) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.nomeFornecedor = nomeFornecedor;
        this.valorTotal = valorTotal;
        this.emailUtilizador = emailUtilizador;
        this.idutilizador = idutilizador;
    }

    /**
     * Obtém o identificador da encomenda.
     *
     * @return O identificador da encomenda
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador da encomenda.
     *
     * @param id O identificador da encomenda
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém a referência da encomenda.
     *
     * @return A referência da encomenda
     */
    public java.lang.String getReferencia() {
        return referencia;
    }
    /**
     * Define a referência da encomenda.
     *
     * @param referencia A referência da encomenda
     */
    public void setReferencia(java.lang.String referencia) {
        this.referencia = referencia;
    }
    /**
     * Obtém a data da encomenda.
     *
     * @return A data da encomenda
     */
    public java.time.LocalDate getData() {
        return data;
    }
    /**
     * Define a data da encomenda.
     *
     * @param data A data da encomenda
     */
    public void setData(java.time.LocalDate data) {
        this.data = data;
    }
    /**
     * Obtém o nome do fornecedor.
     *
     * @return O nome do fornecedor
     */
    public java.lang.String getNomeFornecedor() {
        return nomeFornecedor;
    }
    /**
     * Define o nome do fornecedor.
     *
     * @param nomeFornecedor O nome do fornecedor
     */
    public void setNomeFornecedor(java.lang.String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
    /**
     * Obtém o email do utilizador que fez a encomenda.
     *
     * @return O email do utilizador que fez a encomenda
     */
    public java.lang.String getEmailUtilizador() {
        return emailUtilizador;
    }
    /**
     * Define o email do utilizador que fez a encomenda.
     *
     * @param emailUtilizador O email do utilizador que fez a encomenda
     */
    public void setEmailUtilizador(java.lang.String emailUtilizador) {
        this.emailUtilizador = emailUtilizador;
    }
    /**
     * Obtém o valor total da encomenda.
     *
     * @return O valor total da encomenda
     */
    public double getValorTotal() {
        return valorTotal;
    }
    /**
     * Define o valor total da encomenda.
     *
     * @param valorTotal O valor total da encomenda
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    /**
     * Define o identificador do utilizador.
     *
     * @param idutilizador O identificador do utilizador
     */

    public void setIdutilizador(int idutilizador) {
        this.idutilizador = idutilizador;
    }
    /**
     * Obtém o identificador do utilizador.
     *
     * @return O identificador do utilizador
     */
    public int getIdutilizador() {
        return idutilizador;
    }
}
