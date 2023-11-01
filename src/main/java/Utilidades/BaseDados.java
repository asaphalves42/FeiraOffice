package Utilidades;

import java.sql.*;

public class BaseDados {
    // credenciais de acesso ao SQL SERVER
    String url = "jdbc:sqlserver://CTESPBD.DEI.ISEP.IPP.PT;databasename=2023_LP3_G2_FEIRA;encrypt=true;trustServerCertificate=true;";
    String username = "2023_LP3_G2_FEIRA";
    String password = "LP32023g2*123";
    Connection connection; // a ligação ao SQL

    //função de estabelecer ligação ao SQL

    public boolean Ligar() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // função de terminar a ligação ao SQL
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

    // função de executar uma query no SQL
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

