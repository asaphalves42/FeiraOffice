package Model;

public class LinhaTaxa {
    private int id;
    private TaxaPais taxaPais;
    private Pais pais;
    private double valor;

    public LinhaTaxa(int id, TaxaPais taxaPais, Pais pais, double valor) {
        this.id = id;
        this.taxaPais = taxaPais;
        this.pais = pais;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaxaPais getTaxaPais() {
        return taxaPais;
    }

    public void setTaxaPais(TaxaPais taxaPais) {
        this.taxaPais = taxaPais;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
