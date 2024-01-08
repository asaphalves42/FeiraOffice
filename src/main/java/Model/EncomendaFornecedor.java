package Model;

import java.time.LocalDate;

public class EncomendaFornecedor {

    private int id;
    private String referencia;
    private LocalDate data;
    private String nomeFornecedor;
    private String emailUtilizador;
    private int idutilizador;
    private double valorTotal;



    public EncomendaFornecedor(int id, String referencia, LocalDate data, String nomeFornecedor, double valorTotal, String emailUtilizador,int idutilizador) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.nomeFornecedor = nomeFornecedor;
        this.valorTotal = valorTotal;
        this.emailUtilizador = emailUtilizador;
        this.idutilizador = idutilizador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.lang.String getReferencia() {
        return referencia;
    }

    public void setReferencia(java.lang.String referencia) {
        this.referencia = referencia;
    }

    public java.time.LocalDate getData() {
        return data;
    }

    public void setData(java.time.LocalDate data) {
        this.data = data;
    }

    public java.lang.String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(java.lang.String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public java.lang.String getEmailUtilizador() {
        return emailUtilizador;
    }

    public void setEmailUtilizador(java.lang.String emailUtilizador) {
        this.emailUtilizador = emailUtilizador;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


    public void setIdutilizador(int idutilizador) {
        this.idutilizador = idutilizador;
    }

    public int getIdutilizador() {
        return idutilizador;
    }
}
