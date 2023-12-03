package Controller.DAL;

import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerStock {

    public int obterQuantidadePorIdProduto(BaseDados baseDados, String idProduto) throws IOException {
        int quantidade = 0;

        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT Quantidade FROM Stock WHERE Id_Produto = '" + idProduto + "'");

            if (resultado.next()) {
                quantidade = resultado.getInt("Quantidade");
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura do Stock!", "Erro na leitura da tabela Stock na base de dados!");
        }

        return quantidade;
    }
}
