package Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Encomenda {
    private int id;

    private String referencia;

    private LocalDate data;

    private Fornecedor fornecedor;
    private ArrayList<LinhaEncomenda> linhas;

    private Pais pais;

    
    private double valorImposto;
    private double valorTotal;

    public Encomenda(int id, String referencia, LocalDate data, Fornecedor fornecedor, Pais pais, double valorImposto, double valorTotal) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.fornecedor = fornecedor;
        this.pais = pais;
        this.valorImposto = valorImposto;
        this.valorTotal = valorTotal;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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

    public void setLinhas(ArrayList<LinhaEncomenda> linhas) {
        this.linhas = linhas;
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
