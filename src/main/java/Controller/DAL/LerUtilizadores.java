package Controller.DAL;

import Model.*;
import Utilidades.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


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

        ObservableList<Utilizador> utilizador= FXCollections.observableArrayList();

        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Utilizador");

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
                    utilizador.add(aux);

                } else if (idRole == 2) {
                    aux = new UtilizadorOperador(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizador.add(aux);
                } else if (idRole == 3) {
                    aux = new UtilizadorFornecedor(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizador.add(aux);
                }
            }
            baseDados.Desligar();
            return utilizador; // A leitura retorna o utilizador
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou
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
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Utilizador WHERE id_role = 2");

            while (resultado.next()) {
                Utilizador aux;

                // Obtém o ID do role do resultado
                int idRole = resultado.getInt("id_role");

                // Verifica se o ID do role é 2 (indicando um operador)
                if (idRole == 2) {
                    aux = new UtilizadorOperador(
                            resultado.getInt("id_util"),
                            resultado.getString("username"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);
                }
            }

            baseDados.Desligar();
            return utilizadores; // A leitura retorna a lista de utilizadores
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou
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

        baseDados.Ligar();
        ResultSet resultado = baseDados.Selecao("SELECT * FROM Utilizador WHERE username = '" + email + "' AND password = '" + encryptedPassword + "'");

        Utilizador utilizador = null;

        if (resultado.next()) {
            int idRole = resultado.getInt("id_role");

            if(idRole == 1){
                utilizador = new UtilizadorAdm(
                        resultado.getInt("id_util"),
                        resultado.getString("username"),
                        resultado.getString("password")

                );
            } else if (idRole == 2){
                utilizador = new UtilizadorOperador(
                        resultado.getInt("id_util"),
                        resultado.getString("username"),
                        resultado.getString("password")
                );return utilizador;
            } else if (idRole == 3) {
                utilizador = new UtilizadorFornecedor(
                        resultado.getInt("id_util"),
                        resultado.getString("username"),
                        resultado.getString("password")
                );
            }

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

            // Executa uma consulta SQL para selecionar um registro da tabela Utilizador com base no ID fornecido.
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Utilizador WHERE id_util = " + idUtilizador);

            if (resultado.next()) {
                // Se um registro for encontrado, cria um objeto UtilizadorFornecedor com os dados do registro.
                util = new UtilizadorFornecedor(
                        resultado.getInt("id_util"),
                        resultado.getString("username"),
                        resultado.getString("password")
                );
            }

            // Desconecta-se da base de dados.
            baseDados.Desligar();

        } catch (SQLException e) {
            // Em caso de erro SQL, exibe uma mensagem de erro.
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
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
    public boolean removerOperadorDaBaseDeDados(BaseDados baseDados, int utilizadorID) throws SQLException {
        try {
            baseDados.Ligar();

            String query = "DELETE FROM Utilizador WHERE id_util = '" + utilizadorID + "'";
            boolean linhasAfetadas = baseDados.Executar(query);

            baseDados.Desligar();

            if (linhasAfetadas) {
                return true; // Retorna true se alguma linha foi afetada (remoção bem-sucedida)
            }

        } catch (Exception e) {
            try {
                Mensagens.Erro("Erro na remoção!", "Erro na remoção da base de dados! ");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false; // Retorna false se alguma linha não foi afetada (remoção falhou)
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

            String query = "INSERT INTO Utilizador (username, password, id_role) VALUES ('" + username + "', '" + password + "', 2)";

            baseDados.Executar(query);

            baseDados.Desligar();


            return utilizador;

        }catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
        }
        return null;
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

            String query = "DELETE FROM Utilizador WHERE id_util = " + fornecedor.getId();

            baseDados.Executar(query);

            baseDados.Desligar();

            return true;

        }catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na remoção na base de dados ou utilizador tem encomendas!");
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
    public boolean atualizarOperadorBaseDados(BaseDados baseDados, int id, String novoEmail, String encryptedNovaPassword) {
        try {

            baseDados.Ligar();

            // Construir a query UPDATE
            String query = "UPDATE Utilizador SET username = '" + novoEmail + "', password = '" + encryptedNovaPassword + "' WHERE id_util = " + id + " AND id_role = 2";

            // Executar a query de atualização

            boolean linhasAfetadasInsert = baseDados.Executar(query);

            // Desconectar
            baseDados.Desligar();
            return linhasAfetadasInsert;
        } catch (Exception e) {
            try {
                Mensagens.Erro("Erro na atualização!", "Erro na atualização da base de dados!");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false; // Retorna false se a atualização falhou
        }
    }

    }



