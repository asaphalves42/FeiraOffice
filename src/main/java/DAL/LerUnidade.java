package DAL;

import Model.Pais;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Utilidades.BaseDados.getConexao;

/**
 * Classe com funções de leitura e acesso de dados referentes as unidades de medida.
 */
public class LerUnidade {

    /**
     * Obtém uma unidade da base de dados com base na descrição.
     *
     * @param UOM A descrição da unidade.
     * @return A unidade obtida da base de dados, ou null se não encontrada.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Unidade obterUnidadePorDescricaoBaseDados(String UOM) throws IOException {
        Unidade unidade = null;
        try {
            Connection conn = getConexao();

            String query = "SELECT * FROM Unidade WHERE Descricao = ?";
            try (PreparedStatement preparedStatement =conn.prepareStatement(query)) {
                preparedStatement.setString(1, UOM);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        unidade = criarObjeto(resultado);
                    }
                }
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        } finally {
            BaseDados.Desligar();
        }
        return unidade;
    }

    /**
     * Obtém uma unidade da base de dados com base no ID.
     *
     * @param id O ID da unidade.
     * @return A unidade obtida da base de dados, ou null se não encontrada.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public static Unidade obterUnidadePorIdBaseDados(int id) throws IOException {
        Unidade unidade = null;
        try {
            Connection conn = getConexao();

            String query = "SELECT * FROM Unidade WHERE id = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    if (resultado.next()) {
                        unidade = criarObjeto(resultado);
                    }
                }

                BaseDados.commit(BaseDados.getConexao());
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        } finally {
            BaseDados.Desligar();
        }
        return unidade;
    }

    /**
     * Cria um objeto da classe Unidade a partir dos dados fornecidos por um ResultSet.
     *
     * @param dados Um ResultSet contendo os dados para criar o objeto Unidade.
     * @return Um objeto Unidade criado com base nos dados do ResultSet.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private static Unidade criarObjeto(ResultSet dados) throws SQLException {
        return new Unidade(
                dados.getInt("Id"),
                dados.getString("Descricao")
        );
    }
}