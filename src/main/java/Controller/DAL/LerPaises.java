package Controller.DAL;

import Model.Pais;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe com funções de leitura e acesso de dados referentes aos países.
 */
public class LerPaises {

    /**
     * Obtém uma lista de países a partir da base de dados e retorna uma ObservableList contendo os países.
     *
     * @return Uma ObservableList contendo a lista de países lidos da base de dados, ou uma lista vazia se ocorrer um erro na leitura.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Pais> getListaDePaises(BaseDados baseDados) throws IOException {
        ObservableList<Pais> listaDePaises = FXCollections.observableArrayList();
        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Pais");

            while (resultado.next()) {
                Pais pais = new Pais(
                        resultado.getInt("Id"),
                        resultado.getString("Nome")
                );

                listaDePaises.add(pais);
            }
            baseDados.Desligar();

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return listaDePaises;
    }

    /**
     * Obtém um país a partir da base de dados com base no seu ID e retorna o país encontrado.
     *
     * @param id O ID do país a ser obtido.
     * @return O país correspondente ao ID fornecido, ou null se o país não for encontrado na base de dados ou se ocorrer um erro na leitura.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Pais obterPaisPorId(BaseDados baseDados, int id) throws IOException {
        Pais pais = null;
        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Pais WHERE id = " + id);

            if (resultado.next()) {
                pais = criarObjeto(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return pais;
    }

    /**
     * Obtém informações sobre um país com base no código ISO na base de dados.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param ISO O código ISO do país a ser procurado.
     * @return Um objeto Pais contendo as informações do país encontrado ou null se não for encontrado.
     * @throws IOException Se ocorrer um erro durante a leitura da base de dados.
     */
    public Pais obterPaisPorISO(BaseDados baseDados, String ISO) throws IOException {
        Pais pais = null;
        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Pais WHERE ISO = '" + ISO + "'");

            if (resultado.next()) {
                pais = criarObjeto(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return pais;
    }

    /**
     * Cria um objeto Pais a partir dos dados de um ResultSet representando um país.
     *
     * @param dados Resultado da consulta que contém os dados do país.
     * @return Um objeto Pais com as informações obtidas do ResultSet.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Pais criarObjeto(ResultSet dados) throws SQLException {
        return new Pais(
                dados.getInt("id"),
                dados.getString("Nome"),
                dados.getString("ISO"),
                dados.getDouble("Taxa"),
                dados.getString("Moeda")
        );
    }

}
