package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LerUtilizadores {
    ArrayList<Utilizador> utilizadores = new ArrayList<>();

    /**
     * Lê os utilizadores da base de dados e os armazena em uma lista.
     *
     * Esta função se conecta a uma base de dados, lê os utilizadores armazenados nela
     * e cria objetos de utilizador correspondentes, com base no valor do campo "id_role".
     * Os objetos de utilizador são adicionados a uma lista chamada "utilizadores".
     *
     * @return true se a leitura for bem-sucedida, false se ocorrer um erro.
     */
    public boolean lerUtilizadoresDaBaseDeDados() {
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
            e.printStackTrace();
            return false; // A leitura falhou, retorna false.
        }
    }


    /**
     * Essa Função realiza uma query na base de dados baseado nos paramentros email e password e com base no id_role, me retorna um tipo de utilizador.
     * @param  email email
     * @param password password
     * @return TipoUtilizador
     * @throws SQLException SQLException
     */

    public TipoUtilizador verificarLoginUtilizador(String email, String password) throws SQLException {

        BaseDados basedados = new BaseDados();
        basedados.Ligar();
        ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador WHERE username = '" + email + "' AND password = '" + password + "'");

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

}
