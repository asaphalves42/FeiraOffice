package Utilidades;

/**
 * Classe que valida os emails para o formato correto.
 */
public class ValidarEmail {

    /**
     * Verifica se um endereço de e-mail fornecido está num formato válido de acordo com uma expressão regular.
     *
     * @param email O endereço de e-mail a ser verificado.
     * @return true se o endereço de e-mail estiver em um formato válido, caso contrário, false.
     */

    //Função de validação retirada da net https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method

    public boolean isValidEmailAddress(String email) {
        // Define o padrão de expressão regular para validar endereços de e-mail.
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0,9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        // Compila o padrão de expressão regular em um objeto Pattern.
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);

        // Cria um objeto Matcher para verificar se o email corresponde ao padrão.
        java.util.regex.Matcher m = p.matcher(email);

        // Retorna true se o email corresponde ao padrão, caso contrário, retorna false.
        return m.matches();
    }

}
