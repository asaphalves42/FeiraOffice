package Model;

public class UtilizadorFornecedor extends Utilizador{
    public UtilizadorFornecedor(int id, String email, String password) {
        super(id, email, password);
    }

    @Override
    public TipoUtilizador getTipo() {
        return TipoUtilizador.Fornecedor;
    }
}
