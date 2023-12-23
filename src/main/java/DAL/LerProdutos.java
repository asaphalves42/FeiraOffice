package DAL;


import Model.FornecedorProdutoData;
import Utilidades.BaseDados;
import eu.hansolo.toolbox.observables.ObservableList;
import javafx.collections.FXCollections;

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

    public ObservableList<FornecedorProdutoData> lerProdutosFornecedores() throws IOException {
        ObservableList<FornecedorProdutoData> produtosFornecedores = new ObservableList<>();

        try (Connection conn = BaseDados.getConexao()) {
            BaseDados.iniciarTransacao(conn);

            String query = """
                    SELECT produto.id as id , produto.descricao as descricao , fornecedor.nome AS nomeFornecedor, produto.Id_Unidade as Id_Unidade, 
                    FornecedorProdutos.preco_unitario as preco_unitario
                    FROM produto
                    INNER JOIN FornecedorProdutos ON produto.id = FornecedorProdutos.id_produto
                    INNER JOIN Fornecedor ON FornecedorProdutos.id_fornecedor = Fornecedor.id_externo
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        FornecedorProdutoData produtoFornecedor = new FornecedorProdutoData(
                                resultSet.getInt("id"),
                                resultSet.getString("descricao"),
                                resultSet.getString("nomeFornecedor"),
                                resultSet.getInt("Id_Unidade"),
                                resultSet.getDouble("preco_unitario")
                        );
                        produtosFornecedores.add(produtoFornecedor);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.Desligar();
        }

        return produtosFornecedores;
    }

    public static List<Map<String, Object>> lerProdutos() throws IOException {
        List<Map<String, Object>> produtosList = new ArrayList<>();

        try (Connection conn = BaseDados.getConexao()) {
            BaseDados.iniciarTransacao(conn);

            String query = """
            SELECT
                produto.id_fornecedor as produtoidfornec,
                produto.Id as produtoid,
                produto.descricao as produtodescricao,
                unidade.Descricao AS unidade,
                Stock.Quantidade as stock
            FROM produto
            INNER JOIN unidade ON produto.id_unidade = unidade.Id
            INNER JOIN fornecedor ON produto.id_fornecedor = fornecedor.id_externo
            INNER JOIN Stock ON Produto.Id = Stock.Id_Produto
            """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> produtoFornecedor = new HashMap<>();
                        produtoFornecedor.put("produtoidfornec", resultSet.getString("produtoidfornec"));
                        produtoFornecedor.put("produtoid", resultSet.getString("produtoid"));
                        produtoFornecedor.put("produtodescricao", resultSet.getString("produtodescricao"));
                        produtoFornecedor.put("unidade", resultSet.getString("unidade"));
                        produtoFornecedor.put("stock", resultSet.getDouble("stock"));

                        produtosList.add(produtoFornecedor);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.Desligar();
        }

        return produtosList;
    }

}
