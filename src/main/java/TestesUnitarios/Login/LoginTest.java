package TestesUnitarios.Login;

import Controller.DAL.LerUtilizadores;
import Model.Utilizador;
import Model.UtilizadorAdm;
import Model.UtilizadorFornecedor;
import Model.UtilizadorOperador;
import Utilidades.BaseDados;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class LoginTest {

    private LerUtilizadores lerUtilizadores;

    BaseDados baseDados = new BaseDados();

    String emailadmin = "admin@admin.pt";
    String passwordadmin = "passwordadmin";

    String emailoperador = "teste@operador.pt";
    String passwordoperador = "123";

    String emailfornecedor =  "emailfornecedor";
    String passwordfornecedor = "passwordfornecedor";




    @Before
    public void setUp() {
        // Inicializar o LerUtilizadores sem usar StubUtilizadorRepository
        lerUtilizadores = new LerUtilizadores();
    }



    @Test
    public void testVerificarLoginUtilizadorAdm() throws SQLException {


        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(baseDados,emailadmin, passwordadmin);

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorAdm.class, utilizador.getClass());
    }

    @Test
    public void testVerificarLoginUtilizadorFornecedor() throws SQLException {
        // Adicionar lógica de simulação de dados diretamente no LerUtilizadores


        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(baseDados,emailfornecedor, passwordfornecedor);

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorFornecedor.class, utilizador.getClass());
    }

    @Test
    public void testVerificarLoginOperador() throws SQLException {
        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(baseDados,emailoperador, passwordoperador);

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorOperador.class, utilizador.getClass());
    }


}
