package Model;

public class LinhaTaxa {
    private int id;
    private TaxaPais taxaPais;

    public LinhaTaxa(int id, TaxaPais taxaPais) {
        this.id = id;
        this.taxaPais = taxaPais;

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

}
