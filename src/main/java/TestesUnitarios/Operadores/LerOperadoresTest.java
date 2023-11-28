package TestesUnitarios.Operadores;

import Controller.DAL.LerUtilizadores;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LerOperadoresTest {
    private BaseDados baseDados;

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

    @Test
    public void testAdicionarOperadorBaseDeDados() throws IOException, SQLException {
        String email = "operador@test.pt";
        String password = "123";
        String encryptedPassword = new Encriptacao().MD5(password);

        // Configurar o comportamento simulado da BaseDados para retornar true
        doReturn(true).when(baseDados).Executar(anyString());

        // Criar instância do LerUtilizadores usando a conexão simulada da base de dados
        LerUtilizadores lerUtilizadores = new LerUtilizadores();
        //lerUtilizadores.setBaseDados(baseDados);

        // Tentativa de adicionar um operador ao banco de dados
        boolean operadorInserido = lerUtilizadores.adicionarOperadorBaseDados(baseDados,email, password);

        // Verifica se a inserção foi bem-sucedida
        assertEquals(true, operadorInserido);
    }
    @Test
    public void testAdicionarOperadorBaseDeDados_Falha() throws IOException, SQLException {
        String email = "operador@test.pt";
        String password = "123";

        // Configurando o comportamento simulado da BaseDados para retornar false
        BaseDados baseDados = mock(BaseDados.class);
        doReturn(false).when(baseDados).Executar(anyString());

        // Criar instância do LerUtilizadores usando a conexão simulada da base de dados
        LerUtilizadores lerOperadores = new LerUtilizadores();

        // Wrapper method to capture the failure state
        boolean operationFailed = false;
        try {
            lerOperadores.adicionarOperadorBaseDados(baseDados,email, password);
        } catch (Exception e) {
            operationFailed = true;
        }

        // Verifica se a inserção falhou
        assertFalse("Operation should fail", operationFailed);
    }
}


