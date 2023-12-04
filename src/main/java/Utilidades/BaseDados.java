package Utilidades;

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
    /**
     * Executa uma consulta preparada (PreparedStatement) e retorna a chave gerada (se houver).
     *
     * @param query A consulta preparada a ser executada.
     * @return A chave gerada pela consulta, ou -1 se nenhuma chave foi gerada.
     */
    public int ExecutarPreparementStatement(String query) {
        try {
            // Se já foi invocado o ligar e a ligação está válida, então envia o comando da query
            if (connection != null && !connection.isClosed()) {
                try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    int linhasAfetadas = statement.executeUpdate();

                    if (linhasAfetadas > 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar a consulta SQL", e);
        }
        // Retorna -1 para indicar que nenhuma chave foi gerada
        return -1;
    }

}

