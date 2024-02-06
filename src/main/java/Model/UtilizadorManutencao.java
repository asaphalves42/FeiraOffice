package Model;

/**
 * Classe que representa um utilizador de manutenção do sistema.
 */
public class UtilizadorManutencao {
    private static String email; // O email do utilizador de manutenção

    /**
     * Obtém o email do utilizador de manutenção.
     *
     * @return O email do utilizador de manutenção.
     */
    public static String getEmail() {
        return email;
    }

    /**
     * Define o email do utilizador de manutenção.
     *
     * @param novoEmail O novo email a ser definido para o utilizador de manutenção.
     */
    public static void setEmail(String novoEmail) {
        email = novoEmail;
    }
}
