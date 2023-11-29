package TestesUnitarios.Operadores;


import Model.UtilizadorOperador;

import Model.Utilizador;

import java.io.IOException;

public interface InterfaceLerOperadores {
    UtilizadorOperador adicionarOperadorBaseDeDados( UtilizadorOperador utilizador) throws IOException;
}

