package Model;

public class Produto {
    private int id;
    private String idExterno;
    private String descricao;

    public Produto(int id, String idExterno, String descricao) {
        this.id = id;
        this.idExterno = idExterno;
        this.descricao = descricao;
    }
    public Produto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}