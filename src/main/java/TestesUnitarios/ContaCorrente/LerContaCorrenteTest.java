package TestesUnitarios.ContaCorrente;

import static org.junit.Assert.*;

import DAL.LerContaCorrente;
import Model.ContaCorrente;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de teste para a classe LerContaCorrente.
 */
public class LerContaCorrenteTest {



    private LerContaCorrente leitorContaCorrente;

    /**
     * Configuração inicial para os testes.
     */
    @Before
    public void setUp() {
        leitorContaCorrente = new LerContaCorrente();
    }

    /**
     * Testa a leitura de uma conta corrente existente.
     */
    @Test
    public void testLerContaCorrenteExistente() {
        // Configurar os dados de teste para uma conta existente.
        int idContaExistente = 65;

        try {
            ContaCorrente contaCorrente = leitorContaCorrente.lerContaCorrente(idContaExistente);

            // Verifica se a conta corrente não é nula e possui o ID correto.
            assertNotNull("A conta corrente não deve ser nula", contaCorrente);
            assertEquals(idContaExistente,contaCorrente.getId());

        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testLerContaCorrenteInexistente() {
        // Configurar os dados de teste para uma conta inexistente.
        int idContaInexistente = 999;

        try {
            ContaCorrente contaCorrente = leitorContaCorrente.lerContaCorrente(idContaInexistente);

// Verifica se a conta corrente é nula para contas inexistentes.
            assertNull("A conta corrente deve ser nula para contas inexistentes", contaCorrente);

        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }


}
