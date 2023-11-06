package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Encriptacao;
import Utilidades.Mensagens;
import Utilidades.ValidarEmail;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LerUtilizadores {
    ArrayList<Utilizador> utilizadores = new ArrayList<>();

    /**
     * Lê os utilizadores da base de dados e os armazena em uma lista.
     * <p>
     * Esta função se conecta a uma base de dados, lê os utilizadores armazenados nela
     * e cria objetos de utilizador correspondentes, com base no valor do campo "id_role".
     * Os objetos de utilizador são adicionados a uma lista chamada "utilizadores".
     *
     * @return true se a leitura for bem-sucedida, false se ocorrer um erro.
     */
    public boolean lerUtilizadoresDaBaseDeDados() throws IOException {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador");

            while (resultado.next()) {
                Utilizador aux;

                // enquanto existirem registros, vou ler 1 a 1
                int idRole = resultado.getInt("id_role");

                if (idRole == 1) {
                    aux = new UtilizadorAdm(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);

                } else if (idRole == 2) {
                    aux = new UtilizadorOperador(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);
                } else if (idRole == 3) {
                    aux = new UtilizadorFornecedor(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);
                }
            }
            basedados.Desligar();
            return true; // A leitura foi bem-sucedida, retorna true.
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return false; // A leitura falhou, retorna false.
        }
    }


    /**
     * Essa Função realiza uma query na base de dados baseado nos paramentros email e password e com base no id_role, retorna-me um tipo de utilizador.
     * @param  email email
     * @param password password
     * @return TipoUtilizador
     * @throws SQLException SQLException
     */

    public TipoUtilizador verificarLoginUtilizador(String email, String password) throws SQLException {
        Encriptacao encript = new Encriptacao();
        ValidarEmail validarEmail = new ValidarEmail();

        // Valida o e-mail usando a classe de validação
        boolean emailValido = validarEmail.isValidEmailAddress(email);

        if (!emailValido) {
            // Se o e-mail for inválido, retorne o tipo padrão.
            return TipoUtilizador.Default;
        }

        // Criptografa a senha usando MD5
        String encryptedPassword = encript.MD5(password);

        BaseDados basedados = new BaseDados();
        basedados.Ligar();
        ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador WHERE username = '" + email + "' AND password = '" + encryptedPassword + "'");

        if (resultado.next()) {
            int idRole = resultado.getInt("id_role");

            if (idRole == 1) {
                return TipoUtilizador.Administrador;
            } else if (idRole == 2) {
                return TipoUtilizador.Operador;
            } else if (idRole == 3) {
                return TipoUtilizador.Fornecedor;
            }
        }

        return TipoUtilizador.Default;
    }

    /**
     * Obtém um utilizador fornecedor a partir da base de dados com base no seu ID e retorna o utilizador encontrado.
     *
     * @param idUtilizador O ID do utilizador fornecedor a ser obtido.
     * @return O utilizador fornecedor correspondente ao ID fornecido, ou null se o utilizador não for encontrado na base de dados ou se ocorrer um erro na leitura.
     * @throws IOException Se ocorrer um erro de E/S durante a leitura.
     */
    public UtilizadorFornecedor obterUtilizadorPorIdFornecedor(int idUtilizador) throws IOException {
        UtilizadorFornecedor util = null;

        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Utilizador WHERE id_util = " + idUtilizador);



            if(resultado.next()) {
                // Cria um objeto UtilizadorFornecedor com os dados do utilizador encontrado.
                util = new UtilizadorFornecedor(
                        resultado.getInt("id_util"),
                        resultado.getString("username"),
                        resultado.getString("password")
                );
            }
            baseDados.Desligar();

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return util;
    }

}
