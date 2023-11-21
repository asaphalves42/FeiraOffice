package Model;

public class Pais {
    private int id;
    private String nome;
    private String ISO;
    private double taxa;
    private String moeda;

    public Pais(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Pais(int id, String nome, String ISO, double taxa, String moeda) {
        this.id = id;
        this.nome = nome;
        this.ISO = ISO;
        this.taxa = taxa;
        this.moeda = moeda;
    }

    public Pais() {

    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;

    }
}