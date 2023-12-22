package DAL;


import Model.FornecedorProdutoData;
import Utilidades.BaseDados;
import eu.hansolo.toolbox.observables.ObservableList;
import javafx.beans.Observable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Utilidades.BaseDados.getConexao;

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
                System.out.println(query);
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

}
