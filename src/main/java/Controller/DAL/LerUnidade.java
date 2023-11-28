package Controller.DAL;

import Model.Pais;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerUnidade {

    public Unidade obterUnidadePorDescricaoBaseDados(BaseDados baseDados, String UOM) throws IOException {
        Unidade unidade = null;
        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Unidade WHERE Descricao = '" + UOM + "'");

            if (resultado.next()) {
                unidade = criarObjeto(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return unidade;

    }

    public Unidade obterUnidadePorIdBaseDados(BaseDados baseDados, int id) throws IOException {
        Unidade unidade = null;
        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Unidade WHERE id = " + id);

            if (resultado.next()) {
                unidade = criarObjeto(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return unidade;

    }



    private Unidade criarObjeto(ResultSet dados) throws IOException, SQLException {
        return new Unidade(
                dados.getInt("Id"),
                dados.getString("Descricao")
        );
    }
}