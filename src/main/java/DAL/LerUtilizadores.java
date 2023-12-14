package DAL;

import Model.*;
import Utilidades.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe com funções de leitura e acesso de dados referentes aos utilizadores.
 */
public class LerUtilizadores {
    BaseDados baseDados = new BaseDados();
    /**
     * Lê os utilizadores da base de dados e os armazena em uma lista.
     * <p>
     * Esta função se conecta a uma base de dados, lê os utilizadores armazenados nela
     * e cria objetos de utilizador correspondentes, com base no valor do campo "id_role".
     * Os objetos de utilizador são adicionados a uma lista chamada "utilizadores".
     *
     * @return true se a leitura for bem-sucedida, false se ocorrer um erro.
     */
    public ObservableList<Utilizador> lerUtilizadoresDaBaseDeDados(BaseDados baseDados) throws IOException {
        ObservableList<Utilizador> utilizadores = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());
            String query = "SELECT * FROM Utilizador";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);
                 ResultSet resultado = preparedStatement.executeQuery()) {

                while (resultado.next()) {
                    Utilizador aux;

                    int idRole = resultado.getInt("id_role");
                    int idUtil = resultado.getInt("id_util");
                    String username = resultado.getString("username");
                    String password = resultado.getString("password");

                    aux = switch (idRole) {
                        case 1 -> new UtilizadorAdm(idUtil, username, password);
                        case 2 -> new UtilizadorOperador(idUtil, username, password);
                        case 3 -> new UtilizadorFornecedor(idUtil, username, password);
                        default -> throw new IllegalStateException("Role inesperado: " + idRole);
                    };

                    utilizadores.add(aux);
                }
                baseDados.commit(baseDados.getConexao());
            }
            return utilizadores; // A leitura retorna a lista de utilizadores
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            baseDados.rollback(baseDados.getConexao());
            return null; // A leitura falhou
        } finally {
            baseDados.Desligar();
        }
    }


    /**
     * Lê os operadores da base de dados e retorna uma lista observável de Utilizadores.
     *
     * @param baseDados A instância da classe BaseDados para realizar a operação.
     * @return Uma ObservableList de Utilizador contendo os operadores lidos da base de dados.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Utilizador> lerOperadoresDaBaseDados(BaseDados baseDados) throws IOException {
        ObservableList<Utilizador> utilizadores = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();
            String query = "SELECT * FROM Utilizador WHERE id_role = 2";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);
                 ResultSet resultado = preparedStatement.executeQuery()) {

                while (resultado.next()) {
                    Utilizador aux = new UtilizadorOperador(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);
                }
            }

            return utilizadores; // A leitura retorna a lista de utilizadores
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou
        } finally {
            baseDados.Desligar();
        }
    }


    /**
     * Função que verifica se username ja existe na base de dados
     * @param userName 'username' ou endereço eletrónico recebido do utilizador
     * @return false se existir um igual
     * @throws IOException caso ocorra uma execção
     */
    public boolean verificarUserName(String userName) throws IOException {
        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        // Obtém a lista de utilizadores da base de dados
        ObservableList<Utilizador> utilizadores = lerUtilizadores.lerUtilizadoresDaBaseDeDados(baseDados);

        // Verifica se o email já existe na base de dados
        for (Utilizador util : utilizadores) {
            if (util.getEmail().equals(userName)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Essa Função realiza uma query na base de dados baseado nos paramentros endereço eletrónico e senha e com base no id_role, retorna-me um tipo de utilizador.
     * @param  email email
     * @param password password
     * @return TipoUtilizador
     * @throws SQLException SQLException
     */
    public Utilizador verificarLoginUtilizador(BaseDados baseDados, String email, String password) throws SQLException {
        Encriptacao encript = new Encriptacao();
        ValidarEmail validarEmail = new ValidarEmail();

        // Valida o e-mail usando a classe de validação
        boolean emailValido = validarEmail.isValidEmailAddress(email);

        if (!emailValido) {
            // Se o e-mail for inválido, retorne o tipo padrão.
            return null;
        }

        // Criptografa a senha usando MD5
        String encryptedPassword = encript.MD5(password);

        Utilizador utilizador = null;

        try {
            baseDados.Ligar();
            String query = "SELECT * FROM Utilizador WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, encryptedPassword);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        int idRole = resultado.getInt("id_role");

                        if (idRole == 1) {
                            utilizador = new UtilizadorAdm(
                                    resultado.getInt("id_util"),
                                    resultado.getString("username"),
                                    resultado.getString("password")
                            );
                        } else if (idRole == 2) {
                            utilizador = new UtilizadorOperador(
                                    resultado.getInt("id_util"),
                                    resultado.getString("username"),
                                    resultado.getString("password")
                            );
                        } else if (idRole == 3) {
                            utilizador = new UtilizadorFornecedor(
                                    resultado.getInt("id_util"),
                                    resultado.getString("username"),
                                    resultado.getString("password")
                            );
                        }
                    }
                }
            }
        } finally {
            baseDados.Desligar();
        }

        return utilizador;
    }


    /**
     * Obtém um utilizador fornecedor a partir da base de dados com base no seu ID e retorna o utilizador encontrado.
     *
     * @param idUtilizador O ID do utilizador fornecedor a ser obtido.
     * @return O utilizador fornecedor correspondente ao ID fornecido, ou null se o utilizador não for encontrado na base de dados ou se ocorrer um erro na leitura.
     * @throws IOException Se ocorrer um erro de E/S durante a leitura.
     */
    public UtilizadorFornecedor obterUtilizadorPorIdFornecedor(BaseDados baseDados, int idUtilizador) throws IOException {
        UtilizadorFornecedor util = null; // Inicializa a variável de retorno como nula.

        try {
            baseDados.Ligar();

            // Utilizando PreparedStatement para evitar injeção de SQL
            String query = "SELECT * FROM Utilizador WHERE id_util = ?";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, idUtilizador);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        // Se um registro for encontrado, cria um objeto UtilizadorFornecedor com os dados do registro.
                        util = new UtilizadorFornecedor(
                                resultado.getInt("id_util"),
                                resultado.getString("username"),
                                resultado.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            // Em caso de erro SQL, exibe uma mensagem de erro.
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        } finally {
            baseDados.Desligar();
        }

        return util; // Retorna o utilizador fornecedor encontrado ou null em caso de erro ou se não for encontrado.
    }



    /**
     * Remove um operador da base de dados com base no ID do utilizador.
     *
     * @param baseDados    A instância da classe BaseDados para realizar a operação.
     * @param utilizadorID O ID do utilizador a ser removido.
     * @return True se a remoção foi bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a execução da operação SQL.
     */
    public boolean removerOperadorDaBaseDeDados(BaseDados baseDados, int utilizadorID) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Utilizando PreparedStatement para evitar injeção de SQL
            String query = "DELETE FROM Utilizador WHERE id_util = ?";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, utilizadorID);

                int linhasAfetadas = preparedStatement.executeUpdate();

                if (linhasAfetadas > 0) {
                    baseDados.commit(baseDados.getConexao());
                    return true; // Retorna true se alguma linha foi afetada (remoção bem-sucedida)
                }
            }

        } catch (SQLException e) {
            try {
                Mensagens.Erro("Erro na remoção!", "Erro na remoção da base de dados!");
            } finally {
                baseDados.rollback(baseDados.getConexao());
                baseDados.Desligar();
            }
            return false; // Retorna false se alguma linha não foi afetada (remoção falhou)
        } finally {
            baseDados.Desligar();
        }

        return false;
    }




    /**
     * Adiciona um operador à base de dados com o nome de utilizador e senha fornecidos.
     *
     * @param username O nome de utilizador do operador a ser adicionado.
     * @param password A senha do operador a ser adicionado.
     * @return true se a adição for bem-sucedida, false em caso de erro.
     * @throws IOException se ocorrer um erro de entrada/saída durante a execução.
     */
    public Utilizador adicionarOperadorBaseDados(BaseDados baseDados, String username, String password, Utilizador utilizador) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Utilizando PreparedStatement para evitar injeção de SQL
            String query = "INSERT INTO Utilizador (username, password, id_role) VALUES (?, ?, 2)";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                preparedStatement.executeUpdate();

                baseDados.commit(baseDados.getConexao());
                return utilizador;
            }

        } catch (SQLException e) {
            try {
                Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
            } finally {
                baseDados.rollback(baseDados.getConexao());
                baseDados.Desligar();
            }
            return null;
        } finally {
            baseDados.Desligar();
        }
    }


    /**
     * Função que recebe um fornecedor, e deleta o mesmo a partir do ID fornecido.
     *
     * @param fornecedor fornecedor obtido a partir do utilizador
     * @return true se a query for bem sucedida
     * @throws IOException se acontecer uma exceção de IO
     */
    public boolean removerUtilizador(BaseDados baseDados, UtilizadorFornecedor fornecedor) throws IOException {
        try {

            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            String query = "DELETE FROM Utilizador WHERE id_util = ?";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, fornecedor.getId());

                int linhasAfetadas = preparedStatement.executeUpdate();

                if (linhasAfetadas > 0) {
                    baseDados.commit(baseDados.getConexao());
                    return true; // Remoção bem-sucedida
                }
            }

        }catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na remoção na base de dados ou utilizador tem encomendas!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }
        return false;
    }

    /**
     * Atualiza as informações de um operador na base de dados.
     *
     * @param baseDados             A instância da classe BaseDados para realizar a operação.
     * @param id                    O ID do operador a ser atualizado.
     * @param novoEmail             O novo email a ser atribuído ao operador.
     * @param encryptedNovaPassword A nova senha criptografada a ser atribuída ao operador.
     * @return True se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizarOperadorBaseDados(BaseDados baseDados, int id, String novoEmail, String encryptedNovaPassword) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Construir a query UPDATE com PreparedStatement
            String query = "UPDATE Utilizador SET username = ?, password = ? WHERE id_util = ? AND id_role = 2";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setString(1, novoEmail);
                preparedStatement.setString(2, encryptedNovaPassword);
                preparedStatement.setInt(3, id);

                // Executar a query de atualização
                int linhasAfetadas = preparedStatement.executeUpdate();

                if (linhasAfetadas > 0) {
                    baseDados.commit(baseDados.getConexao());
                    return true; // Atualização bem-sucedida
                }
            }
        } catch (SQLException e) {
            try {
                Mensagens.Erro("Erro na atualização!", "Erro na atualização da base de dados!");
            } finally {
                baseDados.rollback(baseDados.getConexao());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            baseDados.Desligar();
        }
        return false; // Atualização falhou
    }

}



