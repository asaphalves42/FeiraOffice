package TestesUnitarios.Fornecedores;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerUtilizadores;
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

public class LerFornecedoresTest {

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
    @Test
    public void testAdicionarFornecedorBaseDeDados() throws IOException, SQLException {

        String   password = "123";
        String encryptedPassword = encript.MD5(password);

        // Criar instância do LerFornecedores usando a conexão da base de dados
        LerFornecedores lerFornecedores = new LerFornecedores();

        // Criar test data
        Pais pais = new Pais(1, "Brasil");
        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, "fornecedor222@teste.pt", encryptedPassword);
        Fornecedor fornecedor = new Fornecedor(1, "NomeFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);

        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador);

        // Verifica se a inserção foi bem-sucedida
        assertEquals(fornecedor, fornecedorInserido);
    }
    @Test
    public void testAdicionarFornecedorBaseDeDados_Falha() throws IOException, SQLException {
        // Configurando o comportamento da BaseDados para retornar false se a query contiver "falhar"
        doReturn(false).when(baseDados).Executar(anyString());

        // Criar instância do LerFornecedores usando a conexão da base de dados
        LerFornecedores lerFornecedores = new LerFornecedores();

        // Criar test data
        Pais pais = new Pais(1, "Brasil");
        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, "fornecedor@teste.pt", "123");
        Fornecedor fornecedor = new Fornecedor(1, "TesteFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);

        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador);

        // Verifica se a inserção falhou
        assertEquals(fornecedor, fornecedorInserido);
    }

    @Test
    public void testAdicionarEExcluirFornecedorBaseDeDados() throws IOException, SQLException {
        // Criar test data
        LerFornecedores lerFornecedores = new LerFornecedores();
        Pais pais = new Pais(1, "Brasil");
        UtilizadorFornecedor utilizador = new UtilizadorFornecedor(1, "fornecedor222@teste.pt", "123");
        Fornecedor fornecedor = new Fornecedor(1, "NomeFornecedor", "Externo123", "Rua Principal", "Rua Secundária", "Feira", "3885-261", pais, utilizador);
        LerUtilizadores lerUtilizadores = new LerUtilizadores();


        // Tentativa de adicionar um fornecedor ao banco de dados
        Fornecedor fornecedorInserido = lerFornecedores.adicionarFornecedorBaseDeDados(fornecedor, pais, utilizador);

        // Verifica se a inserção foi bem-sucedida
        assertEquals(fornecedor, fornecedorInserido);

        // Agora, tentamos excluir o fornecedor
        boolean exclusaoBemSucedida = lerFornecedores.removerFornecedorDaBaseDeDados(fornecedorInserido.getId());
       boolean exclusaoutilizadorfornecedor= lerUtilizadores.removerUtilizador(utilizador);
        // Verifica se a exclusão foi bem-sucedida
        assertTrue(exclusaoBemSucedida);
        assertTrue(exclusaoutilizadorfornecedor);
    }
}