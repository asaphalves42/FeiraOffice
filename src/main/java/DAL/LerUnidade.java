package DAL;

import Model.Pais;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe com funções de leitura e acesso de dados referentes as unidades de medida.
 */
public class LerUnidade {

    /**
     * Obtém uma unidade da base de dados com base na descrição.
     *
     * @param baseDados A instância da base de dados.
     * @param UOM A descrição da unidade.
     * @return A unidade obtida da base de dados, ou null se não encontrada.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Unidade obterUnidadePorDescricaoBaseDados(BaseDados baseDados, String UOM) throws IOException {
        Unidade unidade = null;
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            String query = "SELECT * FROM Unidade WHERE Descricao = ?";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setString(1, UOM);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        unidade = criarObjeto(resultado);
                    }
                }

                baseDados.commit(baseDados.getConexao());
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }
        return unidade;
    }

    /**
     * Obtém uma unidade da base de dados com base no ID.
     *
     * @param baseDados A instância da base de dados.
     * @param id O ID da unidade.
     * @return A unidade obtida da base de dados, ou null se não encontrada.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Unidade obterUnidadePorIdBaseDados(BaseDados baseDados, int id) throws IOException {
        Unidade unidade = null;
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            String query = "SELECT * FROM Unidade WHERE id = ?";
            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        unidade = criarObjeto(resultado);
                    }
                }

                baseDados.commit(baseDados.getConexao());
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }
        return unidade;
    }

    private Unidade criarObjeto(ResultSet dados) throws SQLException {
        return new Unidade(
                dados.getInt("Id"),
                dados.getString("Descricao")
        );
    }
}