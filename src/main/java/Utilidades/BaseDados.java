package Utilidades;

import java.io.IOException;
import java.sql.*;

/**
 * Métodos de conexão com a base de Dados SQL
 */
public class BaseDados {
    // credenciais de acesso ao SQL SERVER
    String url = "jdbc:sqlserver://CTESPBD.DEI.ISEP.IPP.PT;databasename=2023_LP3_G2_FEIRA;encrypt=true;trustServerCertificate=true;";
    String username = "2023_LP3_G2_FEIRA";
    String password = "LP32023g2*123";
    Connection connection; // a ligação ao SQL

    /**
     * Retorna uma instância da classe {@code Connection} que representa a conexão com o banco de dados.
     *
     * @return Uma instância da classe {@code Connection} que representa a conexão com o banco de dados.
     */
    public Connection getConexao() {
        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace(); // Tratamento de exceção adequado ao seu código
        }

        return conexao;
    }

    /**
     * Inicia uma transação na conexão.
     *
     * @param conexao A conexão com o banco de dados.
     */
    public void iniciarTransacao(Connection conexao) throws IOException {
        try {
            conexao.setAutoCommit(false);
        } catch (SQLException e) {
            Mensagens.Erro("Erro!","Erro ao iniciar transação!");
        }
    }

    /**
     * Realiza o commit da transação na conexão.
     *
     * @param conexao A conexão com o banco de dados.
     */
    public void commit(Connection conexao) throws IOException {
        try {
            conexao.commit();
            conexao.setAutoCommit(false);
        } catch (SQLException e) {
            Mensagens.Erro("Erro!","Erro ao realizar commit!");
            rollback(conexao); // Em caso de falha, realiza rollback
        }
    }

    /**
     * Realiza o rollback da transação na conexão.
     *
     * @param conexao A conexão com o banco de dados.
     */
    public void rollback(Connection conexao) throws IOException {
        try {
            conexao.rollback();
            conexao.setAutoCommit(true);
        } catch (SQLException e) {
            Mensagens.Erro("Erro!","Erro ao realizar rollback!" + e.getMessage());
        }
    }



    /**
     * Estabelece uma ligação com o SQL Server.
     *
     * @return true se a ligação for bem-sucedida, false caso contrário.
     */

    public boolean Ligar() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Termina a ligação com o SQL Server.
     *
     * @return true se a desligação for bem-sucedida, false caso contrário.
     */
    public boolean Desligar() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Executa uma consulta de seleção SQL e retorna o resultado como um conjunto de resultados (ResultSet).
     *
     * @param query A consulta SQL a ser executada.
     * @return O conjunto de resultados (ResultSet) da consulta.
     */

    public ResultSet Selecao(String query) {
        try {
            //se já foi invocado o ligar e a ligação está valida então envia o comando da query
            if (connection != null && !connection.isClosed()) {
                Statement script = connection.createStatement();
                return script.executeQuery(query); //executa o script e aguarda tabela retorno (ResultSet)
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    /**
     * Executa uma consulta SQL que não retorna um conjunto de resultados (por exemplo, INSERT, UPDATE, DELETE).
     *
     * @param query A consulta SQL a ser executada.
     * @return true se a consulta for bem-sucedida, false caso contrário.
     */
    public boolean Executar(String query) {
        try {
            //se já foi invocado o ligar e a ligação está valida então envia o comando da query
            if (connection != null && !connection.isClosed()) {
                Statement script = connection.createStatement();
                script.execute(query); //executa o script e aguarda true ou false (boolean)
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}

