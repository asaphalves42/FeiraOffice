package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LerFornecedores {
    public static ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();

    public boolean lerFornecedoresDaBaseDeDados() throws IOException {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Fornecedor");


            while (resultado.next()) {

                int idPais = resultado.getInt("Id_Pais");
                int idUtilizador = resultado.getInt("Id_Utilizador");

                Pais pais = obterPaisPorId(idPais);
                UtilizadorFornecedor utilizador = obterUtilizadorPorId(idUtilizador);

                Fornecedor aux = new Fornecedor(
                        resultado.getInt("id"),
                        resultado.getString("Nome"),
                        resultado.getString("Morada1"),
                        resultado.getString("Morada2"),
                        resultado.getString("Localidade"),
                        resultado.getString("CodigoPostal"),
                        pais,
                        utilizador
                );
                fornecedores.add(aux);

            }
            basedados.Desligar();
            return true; // A leitura foi bem-sucedida, retorna true.
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return false; // A leitura falhou, retorna false.
        }
    }

    private Pais obterPaisPorId(int id) throws IOException {
        Pais pais = null;
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Pais WHERE id = " + id);

            if (resultado.next()) {
                pais = new Pais(
                        resultado.getInt("Id"),
                        resultado.getString("Nome")
                );
            }
            basedados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return pais;
    }

    private UtilizadorFornecedor obterUtilizadorPorId(int idUtilizador) throws IOException {
        UtilizadorFornecedor util = null;

        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Utilizador WHERE id_util = " + idUtilizador);

            if(resultado.next()) {
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

