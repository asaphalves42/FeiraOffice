package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pagamento {
    private int id;
    private String referencia;
    private LocalDate data;
    private ContaCorrente contaCorrente;
    private ArrayList<Encomenda> encomendas;

    public Pagamento(int id, String referencia, LocalDate data, ContaCorrente valor, ArrayList<Encomenda> encomendas) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.contaCorrente = valor;
        this.encomendas = encomendas;
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

    public ContaCorrente getValor() {
        return contaCorrente;
    }

    public void setValor(ContaCorrente valor) {
        this.contaCorrente = valor;
    }

    public ArrayList<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(ArrayList<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }
}
