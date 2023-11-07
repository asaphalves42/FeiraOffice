package TestesUnitarios;

import Utilidades.ValidarEmail;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Esta classe contém testes unitários para validar endereços de e-mail usando a classe ValidarEmail.
 */
public class ValidarEmailTest {

    /**
     * Testa se os endereços de e-mail fornecidos são válidos.
     */
    @Test
    public void testEnderecoDeEmailValido() {
        ValidarEmail validador = new ValidarEmail();

        assertTrue(validador.isValidEmailAddress("utilizador@gmail.com"));
        assertTrue(validador.isValidEmailAddress("utilizador@hotmail.com"));
        assertTrue(validador.isValidEmailAddress("nome123@gmail.com"));
    }

    /**
     * Testa se os endereços de e-mail fornecidos são inválidos.
     */
    @Test
    public void testEnderecoDeEmailInvalido() {
        ValidarEmail validador = new ValidarEmail();

        assertFalse(validador.isValidEmailAddress("email-invalido"));
        assertFalse(validador.isValidEmailAddress("utilizador@.com"));
        assertFalse(validador.isValidEmailAddress("utilizador@.123"));
        assertFalse(validador.isValidEmailAddress("utilizador@."));
        assertFalse(validador.isValidEmailAddress("@dominio.com"));
        assertFalse(validador.isValidEmailAddress("utilizador@@."));
    }
}
