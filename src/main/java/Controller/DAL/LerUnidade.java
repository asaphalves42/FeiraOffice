package Controller.DAL;

import Model.Pais;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerUnidade {

    public Unidade obterUnidadePorDescricaoBaseDados(String UOM) throws IOException {
        Unidade unidade = null;
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Unidade WHERE Descricao = '" + UOM + "'");

            if (resultado.next()) {
                unidade = criarObjeto(resultado);
            }
            basedados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return unidade;

    }

    public Unidade obterUnidadePorIdBaseDados(int id) throws IOException {
        Unidade unidade = null;
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Unidade WHERE Descricao = " + id);

            if (resultado.next()) {
                unidade = criarObjeto(resultado);
            }
            basedados.Desligar();
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