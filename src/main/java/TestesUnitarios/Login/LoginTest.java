package TestesUnitarios.Login;

import DAL.LerUtilizadores;
import Model.Utilizador;
import Model.UtilizadorAdm;
import Model.UtilizadorFornecedor;
import Model.UtilizadorOperador;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Testes unitários para a funcionalidade de login.
 */
public class LoginTest {

    private LerUtilizadores lerUtilizadores;
    private BaseDados baseDados = new BaseDados();

    private String emailadmin = "admin@admin.pt";
    private String passwordadmin = "123";

    private String emailoperador = "teste@operador.pt";
    private String passwordoperador = "123";

    private String emailfornecedor = "f2@f2.pt";
    private String passwordfornecedor = "123";

    private Encriptacao encript = new Encriptacao();

    /**
     * Configuração inicial para os testes.
     */
    @Before
    public void setUp() {
        // Inicializar o LerUtilizadores
        lerUtilizadores = new LerUtilizadores();
    }

    /**
     * Testa a verificação de login para um Utilizador Administrador.
     *
     * @throws SQLException Se ocorrer um erro ao executar o teste.
     */
    @Test
    public void testVerificarLoginUtilizadorAdm() throws SQLException {
        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(emailadmin, passwordadmin);

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorAdm.class, utilizador.getClass());
    }

    /**
     * Testa a verificação de login para um Utilizador Fornecedor.
     *
     * @throws SQLException Se ocorrer um erro ao executar o teste.
     */
    @Test
    public void testVerificarLoginUtilizadorFornecedor() throws SQLException {
        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(emailfornecedor, passwordfornecedor);

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorFornecedor.class, utilizador.getClass());
    }

    /**
     * Testa a verificação de login para um Utilizador Operador.
     *
     * @throws SQLException Se ocorrer um erro ao executar o teste.
     */
    @Test
    public void testVerificarLoginOperador() throws SQLException {
        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(emailoperador, passwordoperador);

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorOperador.class, utilizador.getClass());
    }
}
