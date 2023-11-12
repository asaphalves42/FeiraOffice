package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Encomenda {
    private int id;
    private String referencia;
    private LocalDate data;
    private Fornecedor fornecedor;
    private ArrayList<LinhaEncomenda> linhas;
    private Moeda moeda;
    private double valorImposto;
    private double valorTotal;

    public Encomenda(int id, String referencia, LocalDate data, Fornecedor fornecedor, Moeda moeda, double valorImposto, double valorTotal) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.fornecedor = fornecedor;
        this.moeda = moeda;
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor Fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ArrayList<LinhaEncomenda> getLinhas() {
        return linhas;
    }

    public void setLinha(LinhaEncomenda linha) {
        this.linhas.add(linha);
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
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
