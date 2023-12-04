package Utilidades;

/**
 * Classe com método que encripta as passwords em MD5.
 */
public class Encriptacao {
    //Função retirada da internet, https://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash-in-java

    /**
     * Gera um hash MD5 a partir de uma ‘string’ de entrada.
     *
     * @param md5 A string a ser convertida em um hash MD5.
     * @return O hash MD5 da ‘string’ de entrada, ou null se ocorrer uma exceção.
     */
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {
        }
        return null;
    }
}
