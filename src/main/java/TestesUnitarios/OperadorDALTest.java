package TestesUnitarios;

import Utilidades.Mensagens;
import org.junit.Test;


import java.io.IOException;

import static Utilidades.Mensagens.*;
import static Utilidades.Mensagens.Erro;
import static org.junit.Assert.*;

public class OperadorDALTest {

    public interface BaseDados {
        void Executar(String query) throws IOException;
    }

    public static class OperadorTest {

        private BaseDados baseDados;

        public OperadorTest(BaseDados baseDados) {
            this.baseDados = baseDados;
        }

        public boolean adicionarOperadorBaseDados(String email, String password) throws IOException {
            try {
                String query = "INSERT INTO Utilizador (email, password, id_role) VALUES ('" + email + "', '" + password + "', 2)";
                baseDados.Executar(query);
                return true;
            } catch (Exception e) {
                Mensagens.Erro("Erro na base de dados","Erro na adição na base de dados");
                return false;
            }
        }
    }

    public static class BaseDadosImpl implements BaseDados {
        @Override
        public void Executar(String query) throws IOException {
            // Implementação simulada da execução da query
            System.out.println("Executando query: " + query);
        }
    }

    @Test
    public void adicionarOperadorBaseDados_WithValidInput_ShouldReturnTrue() throws IOException {
        // Configuração
        BaseDadosMock baseDadosMock = new BaseDadosMock();
        OperadorTest utilizadorDAL = new OperadorTest(baseDadosMock);

        // Ação
        boolean result = utilizadorDAL.adicionarOperadorBaseDados("testEmail", "testPassword");

        // Verificação
        assertTrue(result); // Verifica se o método retornou true
        assertEquals("INSERT INTO Utilizador (email, password, id_role) VALUES ('testEmail', 'testPassword', 2)", baseDadosMock.getExecutedQuery());
    }
/*
    @Test
    public void adicionarOperadorBaseDados_WithException_ShouldReturnFalse() throws IOException {
        // Configuração
        BaseDadosExceptionMock baseDadosExceptionMock = new BaseDadosExceptionMock();
        OperadorTest utilizadorDAL = new OperadorTest(baseDadosExceptionMock);

        // Ação
        boolean result = utilizadorDAL.adicionarOperadorBaseDados("testEmail", "testPassword");

        // Verificação
        assertFalse(result); // Verifica se o método retornou false
    }
*/
    // Mocks para simular o comportamento da BaseDados

    private static class BaseDadosMock implements BaseDados {
        private String executedQuery;

        @Override
        public void Executar(String query) throws IOException {
            executedQuery = query;
            // Simula a execução de uma query
        }

        public String getExecutedQuery() {
            return executedQuery;
        }
    }

    private static class BaseDadosExceptionMock implements BaseDados {
        @Override
        public void Executar(String query) throws IOException {
            throw new IOException("Simular uma exceção ao executar a query");
        }
    }
}