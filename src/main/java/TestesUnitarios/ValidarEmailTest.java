package TestesUnitarios;

import Utilidades.ValidarEmail;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ValidarEmailTest {

    @Test
    public void testEnderecoDeEmailValido() {
        ValidarEmail validador = new ValidarEmail();

        assertTrue(validador.isValidEmailAddress("utilizador@gmail.com"));
        assertTrue(validador.isValidEmailAddress("utilizador@hotmail.com"));
        assertTrue(validador.isValidEmailAddress("nome123@gmail.com"));
    }

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

