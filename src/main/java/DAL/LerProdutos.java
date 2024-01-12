package DAL;


import Model.Fornecedor;
import Model.Produto;
import Model.Stock;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import eu.hansolo.toolbox.observables.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LerProdutos {

    public ObservableList<Produto> lerProdutosFornecedores() throws IOException {

        ObservableList<Produto> produtosFornecedores = new ObservableList<>();

        try (Connection conn = BaseDados.getConexao()) {

            String query = """
                    SELECT
                    
                        Produto.Id as id_produto,
                        Produto.Descricao as descricao_produto,
                    	Produto.Id_Unidade as Id_Unidade,
                        Unidade.Descricao as descricao_unidade,
                        Fornecedor.Id_Externo as id_fornecedor,
                        Fornecedor.Nome as nome_fornecedor,
                        Produto_Fornecedor.preco_unitario as preco_unitario,
                        Produto_Fornecedor.id_externo as id_externo
                                        
                    FROM Produto_Fornecedor
                    INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Produto_Fornecedor.id_fornecedor
                    INNER JOIN Produto ON Produto.Id = Produto_Fornecedor.id_produto
                    INNER JOIN Unidade ON Unidade.Id = Produto.Id_Unidade
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Produto produto = criarObjetoProduto(resultSet);
                        produtosFornecedores.add(produto);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException e) {
            Mensagens.Erro("Erro!","Erro ao ler produtos disponíveis!");
        } finally {
            BaseDados.Desligar();
        }
        return produtosFornecedores;
    }

    private Produto criarObjetoProduto(ResultSet dados) throws SQLException {
        Fornecedor fornecedor = new Fornecedor(
                dados.getString("id_fornecedor"),
                dados.getString("nome_fornecedor")
        );

        Unidade unidade = new Unidade(
                dados.getInt("id_Unidade"),
                dados.getString("descricao_unidade")
        );
        return new Produto(
                dados.getString("id_produto"),
                dados.getString("descricao_produto"),
                unidade,
                fornecedor,
                dados.getDouble("preco_unitario"),
                dados.getString("id_externo"));
    }


    public ObservableList<Stock> lerStock() throws IOException {
        ObservableList<Stock> stockProdutos = new ObservableList<>();

        try (Connection conn = BaseDados.getConexao()) {

            String query = """
                    SELECT Produto.Id as id_produto,
                    	Produto.Descricao as descricao_produto,
                    	Unidade.Id as id_unidade,
                    	Unidade.Descricao as descricao_unidade,
                    	Stock.Quantidade as quantidade
                    FROM Stock
                    	INNER JOIN Produto ON Produto.Id = Stock.Id_Produto
                    	INNER JOIN Unidade ON Unidade.Id = Produto.Id_Unidade
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Stock stock = criarObjetoStock(resultSet);
                        stockProdutos.add(stock);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException | IOException e) {
            Mensagens.Erro("Erro!","Erro ao ler stock de produtos!");
        } finally {
            BaseDados.Desligar();
        }
        return stockProdutos;
    }

    private Stock criarObjetoStock(ResultSet dados) throws SQLException {
        Produto produto = new Produto(
                dados.getString("id_produto"),
                dados.getString("descricao_produto"));

        Unidade unidade = new Unidade(
                dados.getInt("id_unidade"),
                dados.getString("descricao_unidade"));

        return new Stock(
                produto,
                unidade,
                dados.getInt("quantidade"));
    }

    public void aprovarProduto(String idProduto) throws SQLException, IOException {
        String query = "UPDATE Produto SET estado = 2 WHERE id = ?";
        try (Connection conn = BaseDados.getConexao()) {
            BaseDados.iniciarTransacao(conn);
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, idProduto);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
            BaseDados.commit(conn);
        }
    }


}
