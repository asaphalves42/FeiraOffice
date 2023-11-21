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

    private int estado;

    public Encomenda(int id, String referencia, LocalDate data, Fornecedor fornecedor, Pais pais, ArrayList<LinhaEncomenda> linhas, int estado) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.fornecedor = fornecedor;
        this.pais = pais;
        this.linhas = linhas;
        this.estado = estado;
        this.valorImposto = valorImposto;
        this.valorTotal = valorTotal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public double getTotal() {
        double valor = 0;
        for (LinhaEncomenda linha : this.linhas) {
            valor += linha.getTotal();
        }
        return valor;
    }

    public double getTotalTaxa() {
        double valor = 0;
        for (LinhaEncomenda linha : this.linhas) {
            valor += linha.getTotalTaxa();
        }
        return valor;
    }

    public double getTotalIncidencia() {
        double valor = 0;
        for (LinhaEncomenda linha : this.linhas) {
            valor += linha.getTotalIncidencia();
        }
        return valor;
    }
}