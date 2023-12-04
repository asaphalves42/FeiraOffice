package Controller.DAL;

import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A classe LerStock fornece métodos para ler informações relacionadas ao stock na base de dados.
 */
public class LerStock {

    /**
     * Obtém a quantidade de um produto no stock com base no ID do produto.
     *
     * @param baseDados   A instância da classe BaseDados para conexão com a base de dados.
     * @param idProduto   O ID do produto para o qual a quantidade no stock será obtida.
     * @return A quantidade do produto no estoque. Se ocorrer um erro, retorna 0.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a leitura do stock.
     */
    public int obterQuantidadePorIdProduto(BaseDados baseDados, String idProduto) throws IOException {
        int quantidade = 0;

        try {
            // Conecta à base de dados
            baseDados.Ligar();

            // Executa a consulta SQL para obter a quantidade do produto no stock
            ResultSet resultado = baseDados.Selecao("SELECT Quantidade FROM Stock WHERE Id_Produto = '" + idProduto + "'");

            // Verifica se há um resultado e obtém a quantidade
            if (resultado.next()) {
                quantidade = resultado.getInt("Quantidade");
            }

            // Desconecta da base de dados
            baseDados.Desligar();
        } catch (SQLException e) {
            // Em caso de erro, exibe uma mensagem de erro
            Mensagens.Erro("Erro na leitura do Stock!", "Erro na leitura da tabela Stock na base de dados!");
        }

        // Retorna a quantidade do produto no estoque
        return quantidade;
    }
}
