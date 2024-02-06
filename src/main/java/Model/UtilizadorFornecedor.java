package Model;

/**
 * Classe filha do utilizador, referente ao utilizador fornecedor.
 */
public class UtilizadorFornecedor extends Utilizador {
    private Fornecedor fornecedor;  // Adicionando um atributo para armazenar o fornecedor associado
    /**
     * Construtor da classe UtilizadorFornecedor.
     *
     * @param idUtilizador O identificador único do utilizador fornecedor.
     * @param tipoUtilizador O tipo de utilizador (Fornecedor).
     */

    public UtilizadorFornecedor(int idUtilizador, int tipoUtilizador) {
        super (idUtilizador, TipoUtilizador.Fornecedor);
    }
    /**
     * Obtém o fornecedor associado ao utilizador fornecedor.
     *
     * @return O fornecedor associado.
     */
    public Fornecedor getFornecedor() {
        return fornecedor;
    }
    /**
     * Define o fornecedor associado ao utilizador fornecedor.
     *
     * @param fornecedor O fornecedor a ser associado.
     */
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    /**
     * Construtor da classe UtilizadorFornecedor.
     *
     * @param id       O identificador único do utilizador fornecedor.
     * @param email    O email do utilizador fornecedor.
     * @param password A senha do utilizador fornecedor.
     */
    public UtilizadorFornecedor(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Fornecedor);
    }

}
