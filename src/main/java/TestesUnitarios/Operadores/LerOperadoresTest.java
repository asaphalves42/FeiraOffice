package TestesUnitarios.Operadores;

import Controller.DAL.LerUtilizadores;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Classe de testes unitários referente aos operadores.
 */
public class LerOperadoresTest {
    private BaseDados baseDados;
    Encriptacao encript = new Encriptacao();
    String email = "operador222@teste.pt";
    String password = "123";
    String encryptedPassword = encript.MD5(password);

    @Before
    public void setUp() {
        // Inicializar a instância da BaseDados
        baseDados = mock(BaseDados.class);
        // Ligar ao banco de dados antes de cada teste
        doReturn(true).when(baseDados).Ligar();
    }

    @After
    public void tearDown() {
        // Desligar a conexão após cada teste
        doReturn(true).when(baseDados).Desligar();
    }
    /**
     * Testa o método de adição de fornecedor à base de dados.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarOperBaseDeDados() throws IOException, SQLException {
        // Criar instância do LerUtilizadores usando a conexão da base de dados
        doReturn(true).when(baseDados).Executar(anyString());
        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        // Criar test data
        //Utilizador utilizador = new UtilizadorOperador(2, email, encryptedPassword);
        UtilizadorOperador operador = new UtilizadorOperador(2, email, encryptedPassword);

        // Tentativa de adicionar um operador ao banco de dados
        Utilizador operadorInserido = lerUtilizadores.adicionarOperadorBaseDados(baseDados, email, encryptedPassword, operador);
        // Verifica se a inserção foi bem-sucedida
        assertEquals(operador, operadorInserido);
    }
    /**
     * Testa o método de adição de fornecedor à base de dados, simulando um falha na execução da SQL.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarOperBaseDeDados_Falha() throws IOException, SQLException {
        // Configurando o comportamento da BaseDados para retornar false se a query contiver "falhar"
        doReturn(false).when(baseDados).Executar(anyString());

        // Criar instância do LerUtilizadores usando a conexão da base de dados
        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        // Criar test data

        //UtilizadorOperador utilizador = new UtilizadorOperador(2, email, encryptedPassword);
        UtilizadorOperador operador = new UtilizadorOperador(2, email, encryptedPassword);


        // Tentativa de adicionar um operador ao banco de dados
        Utilizador operadorInserido = lerUtilizadores.adicionarOperadorBaseDados(baseDados, email, password, operador);

        // Verifier se a inserção falhou
        assertEquals(operador, operadorInserido);
    }
    /**
     * Testa os métodos de adição e exclusão de fornecedor à base de dados.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarEExcluirOperBaseDeDados() throws IOException, SQLException {
        doReturn(true).when(baseDados).Executar(anyString());
        // Criar test data
        LerUtilizadores lerUtilizadores = new LerUtilizadores();
        UtilizadorOperador utilizador = new UtilizadorOperador(2, email, encryptedPassword);
        UtilizadorOperador operador = new UtilizadorOperador(2, email, encryptedPassword);


        // Tentativa de adicionar um fornecedor ao banco de dados
        Utilizador operadorInserido = lerUtilizadores.adicionarOperadorBaseDados(baseDados,email,password,operador );

        // Verifica se a inserção foi bem-sucedida
        assertEquals(operador, operadorInserido);

        // Agora, tentamos excluir o operador

        boolean exclusaoutilizadoroperador = lerUtilizadores.removerOperadorDaBaseDeDados(baseDados, utilizador.getId());
        // Verifica se a exclusão foi bem-sucedida
        //boolean (exclusaoBemSucedida) = false;
        assertTrue(exclusaoutilizadoroperador);
    }
}


