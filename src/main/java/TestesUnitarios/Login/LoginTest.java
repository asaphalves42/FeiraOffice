package TestesUnitarios.Login;

import Controller.DAL.LerUtilizadores;
import Model.Utilizador;
import Model.UtilizadorAdm;
import Model.UtilizadorFornecedor;
import Model.UtilizadorOperador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class LoginTest {

    private LerUtilizadores lerUtilizadores;

    @Before
    public void setUp() {
        // Inicializar o LerUtilizadores sem usar StubUtilizadorRepository
        lerUtilizadores = new LerUtilizadores();
    }



    @Test
    public void testVerificarLoginUtilizadorAdm() throws SQLException {
        


        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador("email", "123");

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorAdm.class, utilizador.getClass());
    }

    @Test
    public void testVerificarLoginUtilizadorFornecedor() throws SQLException {


        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador("admin@admin.pt", "123");

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorAdm.class, utilizador.getClass());
    }

    @Test
    public void testVerificarLoginOperador() throws SQLException {


        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador("teste@operador.pt", "123");

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorOperador.class, utilizador.getClass());
    }



}
