package TestesUnitarios.Operadores;

import DAL.LerUtilizadores;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;


/**
 * Classe de testes unitários referente aos operadores.
 */
public class LerOperadoresTest {
    private BaseDados baseDados;
    Encriptacao encript = new Encriptacao();
    String email = "operador222@teste.pt";
    String password = "123";
    String encryptedPassword = encript.MD5(password);

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
     * Testa o método de adição de Operador à base de dados.
     *
     * @throws IOException  Em caso de erro de leitura/escrita.
     * @throws SQLException Em caso de erro na execução da SQL.
     */


        @Test
        public void testAdicionarOperadorBaseDados() throws IOException {


            UtilizadorOperador operador = new UtilizadorOperador(2, email, encryptedPassword);
            operador.setEmail(email);
            operador.setPassword(encryptedPassword);
            operador.setId(2);


            LerUtilizadores lerUtilizadores = new LerUtilizadores();
            Utilizador resultado = lerUtilizadores.adicionarOperadorBaseDados(email, encryptedPassword, operador);
            assertNotNull(resultado);


            assertEquals(operador, resultado);

        }

    /**
     * Limpeza após a execução dos testes.
     */
    @After
    public void tearDown() {
        BaseDados.Desligar();
        if (email != null) {
            try {
                LerUtilizadores lerUtilizadores = new LerUtilizadores();
                lerUtilizadores.removerOperadorPorEmail(email);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    }

