package Model;

public class LinhaTaxa {
    private int id;
    private TaxaPais idTaxaPais;
    private Pais idPais;
    private double valor;

    public LinhaTaxa(int id, TaxaPais idTaxaPais, Pais idPais, double valor) {
        this.id = id;
        this.idTaxaPais = idTaxaPais;
        this.idPais = idPais;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaxaPais getIdTaxaPais() {
        return idTaxaPais;
    }

    public void setIdTaxaPais(TaxaPais idTaxaPais) {
        this.idTaxaPais = idTaxaPais;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
