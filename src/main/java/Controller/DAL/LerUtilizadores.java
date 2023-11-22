package Controller.DAL;

import Model.*;
import Utilidades.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Model.TipoUtilizador.Operador;


public class LerUtilizadores {
    /**
     * Lê os utilizadores da base de dados e os armazena em uma lista.
     * <p>
     * Esta função se conecta a uma base de dados, lê os utilizadores armazenados nela
     * e cria objetos de utilizador correspondentes, com base no valor do campo "id_role".
     * Os objetos de utilizador são adicionados a uma lista chamada "utilizadores".
     *
     * @return true se a leitura for bem-sucedida, false se ocorrer um erro.
     */
    public ObservableList<Utilizador> lerUtilizadoresDaBaseDeDados() throws IOException {

        ObservableList<Utilizador> utilizador= FXCollections.observableArrayList();

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
            basedados.Desligar();
            return utilizador; // A leitura retorna o utilizador
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou
        }
    }

    public ObservableList<Utilizador> lerOperadoresDaBaseDados() throws IOException {

        ObservableList<Utilizador> utilizadores = FXCollections.observableArrayList();

        try {
            BaseDados baseDados = new BaseDados();
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
        for (Utilizador util : lerUtilizadoresDaBaseDeDados()) {
            return !util.getEmail().equals(userName);
        }
        return false;
    }



    /**
     * Essa Função realiza uma query na base de dados baseado nos paramentros endereço eletrónico e senha e com base no id_role, retorna-me um tipo de utilizador.
     * @param  email email
     * @param password password
     * @return TipoUtilizador
     * @throws SQLException SQLException
     */
    public Utilizador verificarLoginUtilizador(String email, String password) throws SQLException {
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

        BaseDados basedados = new BaseDados();
        basedados.Ligar();
        ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador WHERE username = '" + email + "' AND password = '" + encryptedPassword + "'");

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
    public UtilizadorFornecedor obterUtilizadorPorIdFornecedor(int idUtilizador) throws IOException {
        UtilizadorFornecedor util = null; // Inicializa a variável de retorno como nula.

        try {

            BaseDados baseDados = new BaseDados();
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

    public boolean removerOperadorDaBaseDeDados(int utilizadorID) throws SQLException {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            String query = "DELETE FROM Utilizador WHERE id_role =2 ";
            boolean linhasAfetadas = baseDados.Executar(query);

            baseDados.Desligar();

            if (linhasAfetadas) {
                return true; // Retorna true se alguma linha foi afetada (remoção bem-sucedida)
            }

        } catch (Exception e) {
            try {
                Mensagens.Erro("Erro na remoção!", "Erro na remoção da base de dados!");
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
    public boolean adicionarOperadorBaseDados(String username, String password) throws IOException {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            String query = "INSERT INTO Utilizador (username, password, id_role) VALUES ('" + username + "', '" + password + "', 2)";

            baseDados.Executar(query);

            baseDados.Desligar();

            return true;

        }catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
        }
        return false;
    }

    /**
     * Função que recebe um fornecedor, e deleta o mesmo a partir do ID fornecido.
     *
     * @param fornecedor fornecedor obtido a partir do utilizador
     * @return true se a query for bem sucedida
     * @throws IOException se acontecer uma exceção de IO
     */
    public boolean removerUtilizador(UtilizadorFornecedor fornecedor) throws IOException {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            String query = "DELETE FROM Utilizador WHERE id_util = " + fornecedor.getId();

            baseDados.Executar(query);

            baseDados.Desligar();

            return true;

        }catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
        }
        return false;
    }
    private ObservableList<Utilizador> utilizadoresSimulados;
    private Utilizador utilizadorSimulado;

    public void setUtilizadoresSimulados(ObservableList<Utilizador> utilizadores) {
        this.utilizadoresSimulados = utilizadores;
    }

    public void setUtilizadorSimulado(Utilizador utilizador) {
        this.utilizadorSimulado = utilizador;
    }


    }



