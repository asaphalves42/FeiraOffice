package DAL;

import Model.Pais;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
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
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Unidade WHERE Descricao = '" + UOM + "'");

            if (resultado.next()) {
                unidade = criarObjeto(resultado);
            }
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
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
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Unidade WHERE id = " + id);

            if (resultado.next()) {
                unidade = criarObjeto(resultado);
            }
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        } finally {
            baseDados.Desligar();
        }
        return unidade;

    }

    /**
     * Cria um objeto Unidade a partir dos dados do ResultSet.
     *
     * @param dados O ResultSet contendo os dados da unidade.
     * @return Um objeto Unidade criado a partir dos dados do ResultSet.
     * @throws IOException Se ocorrer um erro durante a leitura.
     * @throws SQLException Se ocorrer um erro ao acessar os dados no ResultSet.
     */
    private Unidade criarObjeto(ResultSet dados) throws IOException, SQLException {
        return new Unidade(
                dados.getInt("Id"),
                dados.getString("Descricao")
        );
    }
}