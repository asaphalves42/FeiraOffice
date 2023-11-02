package Model;

import java.time.LocalDate;

public class Encomenda {
    private int id;
    private String referencia;
    private LocalDate data;
    private Fornecedor idFornecedor;
    private LinhaEncomenda idLinha;
    private Moeda idMoeda;
    private double valorImposto;
    private double valorTotal;

    public Encomenda(int id, String referencia, LocalDate data, Fornecedor idFornecedor, LinhaEncomenda idLinha, Moeda idMoeda, double valorImposto, double valorTotal) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.idFornecedor = idFornecedor;
        this.idLinha = idLinha;
        this.idMoeda = idMoeda;
        this.valorImposto = valorImposto;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public LinhaEncomenda getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(LinhaEncomenda idLinha) {
        this.idLinha = idLinha;
    }

    public Moeda getIdMoeda() {
        return idMoeda;
    }

    public void setIdMoeda(Moeda idMoeda) {
        this.idMoeda = idMoeda;
    }

    public double getValorImposto() {
        return valorImposto;
    }

    public void setValorImposto(double valorImposto) {
        this.valorImposto = valorImposto;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
