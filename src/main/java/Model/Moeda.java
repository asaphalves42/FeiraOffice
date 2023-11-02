package Model;

public class Moeda {
    private int id;
    private String nome;
    private double cambio;

    public Moeda(int id, String nome, double cambio) {
        this.id = id;
        this.nome = nome;
        this.cambio = cambio;
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

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }
}
