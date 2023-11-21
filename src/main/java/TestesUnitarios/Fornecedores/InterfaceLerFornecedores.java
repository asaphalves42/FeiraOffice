package TestesUnitarios.Fornecedores;

import Model.Fornecedor;
import Model.Pais;
import Model.UtilizadorFornecedor;

import java.io.IOException;

public interface InterfaceLerFornecedores {
    Fornecedor adicionarFornecedorBaseDeDados(Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException;
}
