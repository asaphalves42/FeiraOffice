package TestesUnitarios.ContaCorrente;

import static org.junit.Assert.*;

import DAL.LerContaCorrente;
import Model.ContaCorrente;
import org.junit.Before;
import org.junit.Test;

public class LerContaCorrenteTest {



    private LerContaCorrente leitorContaCorrente;

    @Before
    public void setUp() {
        leitorContaCorrente = new LerContaCorrente();
    }

    @Test
    public void testLerContaCorrenteExistente() {
        // Configurar os dados de teste para uma conta existente.
        int idContaExistente = 58;

        try {
            ContaCorrente contaCorrente = leitorContaCorrente.lerContaCorrente(idContaExistente);

            // Verifica se a conta corrente existe.
            assertNotNull("A conta corrente não deve ser nula", contaCorrente);
            // Adicione mais verificações conforme necessário.

        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testLerContaCorrenteInexistente() {

        int idContaInexistente = 999;

        try {
            ContaCorrente contaCorrente = leitorContaCorrente.lerContaCorrente(idContaInexistente);


            assertNull("A conta corrente deve ser nula para contas inexistentes", contaCorrente);

        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }


}
