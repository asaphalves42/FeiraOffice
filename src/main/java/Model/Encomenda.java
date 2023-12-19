package Model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Atributos da encomenda, construtores, getters e setters.
 */
public class Encomenda {
    private int id;

    private String referencia;

    private LocalDate data;

    private Fornecedor fornecedor;
    private ArrayList<LinhaEncomenda> linhas;

    private Pais pais;

    private double valorIncidencia;

    private double valorImposto;
    private double valorTotal;

    private EstadoEncomenda estado;
    private EstadoPagamento estadoPagamento;

    public Encomenda(int id){
        this.id = id;
    }

    public Encomenda(int id, String referencia, LocalDate data, Fornecedor fornecedor, Pais pais, ArrayList<LinhaEncomenda> linhas, EstadoEncomenda estado,
                     EstadoPagamento estadoPagamento) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.fornecedor = fornecedor;
        this.pais = pais;
        this.linhas = linhas;
        this.estado = estado;
        this.estadoPagamento = estadoPagamento;
        this.valorImposto = valorImposto;
        this.valorTotal = valorTotal;
        this.valorIncidencia = valorIncidencia;

    }

    public Encomenda(int id, String referencia, LocalDate data, Fornecedor fornecedor, Pais pais,double totalTaxa, double totalIncidencia, double total, EstadoEncomenda estado) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.fornecedor = fornecedor;
        this.pais = pais;
        this.valorImposto = totalTaxa;
        this.valorIncidencia = totalIncidencia;
        this.valorTotal = total;
        this.estado = estado;

    }

    public Encomenda(int idEncomenda, Fornecedor fornecedor,  String referencia, LocalDate dataEncomenda, double total, EstadoEncomenda estado,
                     EstadoPagamento estadoPagamento) {
        this.id = idEncomenda;
        this.fornecedor = fornecedor;
        this.referencia = referencia;
        this.data = dataEncomenda;
        this.valorTotal = total;
        this.estado = estado;
        this.estadoPagamento = estadoPagamento;
    }

    public EstadoPagamento getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public double getValorIncidencia() {
        return valorIncidencia;
    }

    public void setValorIncidencia(double valorIncidencia) {
        this.valorIncidencia = valorIncidencia;
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

    public EstadoEncomenda getEstado() {
        return estado;
    }

    public void setEstado(EstadoEncomenda estado) {
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

    /**
     * Calcula o valor total da encomenda somando os totais de todas as linhas.
     *
     * @return O valor total da encomenda.
     */
    public double getTotal() {
        double valor = 0;
        for (LinhaEncomenda linha : this.linhas) {
            valor += linha.getTotal();
        }
        return valor;
    }
    /**
     * Calcula o valor total da taxa da encomenda somando as taxas de todas as linhas.
     *
     * @return O valor total da taxa da encomenda.
     */
    public double getTotalTaxa() {
        double valor = 0;
        for (LinhaEncomenda linha : this.linhas) {
            valor += linha.getTotalTaxa();
        }
        return valor;
    }
    /**
     * Calcula o valor total de incidência da encomenda somando os totais de incidência de todas as linhas.
     *
     * @return O valor total de incidência da encomenda.
     */
    public double getTotalIncidencia() {
        double valor = 0;
            for (LinhaEncomenda linha : this.linhas) {
                valor += linha.getTotalIncidencia();
            }
        return valor;
    }
}