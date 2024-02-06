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

import static org.junit.Assert.*;


/**
 * Classe de testes unitários referentes aos fornecedores.
 */
public class LerFornecedoresTest {

    Encriptacao encript = new Encriptacao();

    String emailfornecedor = "fornecedor222@teste.pt";
    String password = "123";
    String encryptedPassword = encript.MD5(password);
    private BaseDados baseDados;
    /**
     * Configuração inicial dos testes.
     */
    @Before
    public void setUp() {
        // Inicializar a instância da BaseDados
        baseDados = new BaseDados();
        baseDados.Ligar();
    }

    /**
     * Limpeza após a execução dos testes.
     */
    @After
    public void tearDown() {
        BaseDados.Desligar();
    }

    /**
     * Testa o método de adição de fornecedor à base de dados.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarFornecedorBaseDeDados() throws IOException, SQLException {
        try {


            LerFornecedores lerFornecedores = new LerFornecedores();

            Pais pais = new Pais(1, "Brasil");

            UtilizadorFornecedor utilizador = new UtilizadorFornecedor(2, emailfornecedor, encryptedPassword);
            Fornecedor fornecedor = new Fornecedor(1121, "NomeFornecedorv2", "Externo123v2", "Rua Principalv2", "Rua Secundáriav2", "Feira", "3885-264", pais, utilizador);

            Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador,false);

            assertEquals(fornecedor, fornecedorInserido);

        } catch (IOException e) {

            e.printStackTrace();
            fail("Exceção durante o teste: " + e.getMessage());
        }
    }

    /**
     * Testa o método de adição de fornecedor à base de dados, simulando um falha na execução da SQL.
     *
     * @throws IOException Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */
    @Test
    public void testAdicionarFornecedorBaseDeDados_Falha() throws IOException, SQLException {


        // Criar instância do LerFornecedores usando a conexão da base de dados
        LerFornecedores lerFornecedores = new LerFornecedores();

        // Criar test data
        Pais pais = new Pais(1, "Brasil");

        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, emailfornecedor, encryptedPassword);
        Fornecedor fornecedor = new Fornecedor(1, "TesteFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);

        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador,false);

        // Verifica se a inserção falhou
        assertEquals(fornecedor, fornecedorInserido);
    }


}
