package Model;

public class UtilizadorFornecedor extends Utilizador {
    private Fornecedor fornecedor;  // Adicionando um atributo para armazenar o fornecedor associado

    // Construtores, métodos e outros atributos já existentes na sua classe

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
