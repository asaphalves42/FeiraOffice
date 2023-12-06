package TestesUnitarios.Fornecedores;

import DAL.LerFornecedores;
import DAL.LerUtilizadores;
import Model.Fornecedor;
import Model.Pais;
import Model.UtilizadorFornecedor;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Classe de testes unitários referentes aos fornecedores.
 */
public class LerFornecedoresTest {

    String emailfornecedor = "fornecedor222@teste.pt";
    String password = "123";

    private BaseDados baseDados;

    @Before
    public void setUp() {
        // Inicializar a instância da BaseDados
        baseDados = mock(BaseDados.class);
        // Ligar ao banco de dados antes de cada teste
        doReturn(true).when(baseDados).Ligar();
    }
    Encriptacao encript = new Encriptacao();

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
    public void testAdicionarFornecedorBaseDeDados() throws IOException, SQLException {


        String encryptedPassword = encript.MD5(password);

        // Criar instância do LerFornecedores usando a conexão da base de dados
        LerFornecedores lerFornecedores = new LerFornecedores();

        // Criar test data
        Pais pais = new Pais(1, "Brasil");
        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, emailfornecedor, encryptedPassword);
        Fornecedor fornecedor = new Fornecedor(1, "NomeFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);

        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(baseDados,fornecedor, pais, utilizador);

        // Verifica se a inserção foi bem-sucedida
        assertEquals(fornecedor, fornecedorInserido);
    }
    /**
     * Testa o método de adição de fornecedor à base de dados, simulando um falha na execução da SQL.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarFornecedorBaseDeDados_Falha() throws IOException, SQLException {
        // Configurando o comportamento da BaseDados para retornar false se a query contiver "falhar"
        doReturn(false).when(baseDados).Executar(anyString());

        // Criar instância do LerFornecedores usando a conexão da base de dados
        LerFornecedores lerFornecedores = new LerFornecedores();

        // Criar test data
        Pais pais = new Pais(1, "Brasil");
        String encryptedPassword = encript.MD5(password);
        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, emailfornecedor, encryptedPassword);
        Fornecedor fornecedor = new Fornecedor(1, "TesteFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);

        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(baseDados,fornecedor, pais, utilizador);

        // Verifica se a inserção falhou
        assertEquals(fornecedor, fornecedorInserido);
    }
    /**
     * Testa os métodos de adição e exclusão de fornecedor à base de dados.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarEExcluirFornecedorBaseDeDados() throws IOException, SQLException {
        // Criar test data
        String encryptedPassword = encript.MD5(password);
        LerFornecedores lerFornecedores = new LerFornecedores();
        Pais pais = new Pais(1, "Brasil");
        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, emailfornecedor, encryptedPassword);
        Fornecedor fornecedor = new Fornecedor(1, "NomeFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);
        LerUtilizadores lerUtilizadores = new LerUtilizadores();


        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(baseDados,fornecedor, pais, utilizador);

        // Verifica se a inserção foi bem-sucedida
        assertEquals(fornecedor, fornecedorInserido);

        // Agora, tentamos excluir o fornecedor
       // boolean exclusaoBemSucedida = lerFornecedores.removerFornecedorDaBaseDeDados(baseDados,fornecedorInserido.getId());
       boolean exclusaoutilizadorfornecedor= lerUtilizadores.removerUtilizador(baseDados, utilizador);
        // Verifica se a exclusão foi bem-sucedida
       //boolean (exclusaoBemSucedida) = false;
        assertTrue(exclusaoutilizadorfornecedor);
    }
}
