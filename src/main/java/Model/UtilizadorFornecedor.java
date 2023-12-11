package Model;

/**
 * Classe filha do utilizador, referente ao utilizador fornecedor.
 */
public class UtilizadorFornecedor extends Utilizador {
    private Fornecedor fornecedor;  // Adicionando um atributo para armazenar o fornecedor associado


    public UtilizadorFornecedor(int idUtilizador, int tipoUtilizador) {
        super (idUtilizador, TipoUtilizador.Fornecedor);
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public UtilizadorFornecedor(int id, String email, String password) {
        super(id, email, password, TipoUtilizador.Fornecedor);
    }

}
