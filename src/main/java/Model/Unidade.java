package Model;

/**
 * Classe que representa uma unidade de medida.
 */
public class Unidade {
    /**
     * O identificador único da unidade.
     */
    private int id;

    /**
     * A descrição da unidade.
     */
    private String descricao;

    /**
     * Construtor da classe Unidade.
     *
     * @param id         O identificador único da unidade.
     * @param descricao  A descrição da unidade.
     */
    public Unidade(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    /**
     * Obtém o identificador único da unidade.
     *
     * @return O identificador único da unidade.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único da unidade.
     *
     * @param id O identificador único da unidade.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a descrição da unidade.
     *
     * @return A descrição da unidade.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da unidade.
     *
     * @param descricao A descrição da unidade.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
