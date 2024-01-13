package DAL;


import Model.*;
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

import static Utilidades.API.createProduct;
import static Utilidades.BaseDados.getConexao;

public class LerProdutos {

    public ObservableList<Produto> lerProdutosFornecedores() throws IOException {

        ObservableList<Produto> produtosFornecedores = new ObservableList<>();

        try (Connection conn = getConexao()) {

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

        try (Connection conn = getConexao()) {

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
        try (Connection conn = getConexao()) {
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

    public boolean gerarProdutoParaVenda(String idProduto, int idUnidade, double precoUnitario) throws IOException {
        Connection conn = null;
        try {
            conn = getConexao();
            BaseDados.iniciarTransacao(conn);

            // Verificar se o produto já existe na tabela Produto_Venda
            boolean produtoExiste = produtoVendaExiste(idProduto, idUnidade, conn);

            if (!produtoExiste) {
                // Verificar o maior preço unitário na tabela Produto_Fornecedor
                double maiorPrecoUnitario = obterMaiorPrecoUnitario(idProduto, conn);

                // Calcular o preço de venda (usando o maior preço unitário se existir)
                double calcularPrecoVenda = (maiorPrecoUnitario > 0) ? maiorPrecoUnitario : precoUnitario;
                calcularPrecoVenda *= 1.6; // Aumento de 60%

                String query = """
                        INSERT INTO Produto_Venda (id_produto, id_unidade, preco_venda)
                        VALUES (?, ?, ?)
                        """;

                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, idProduto);
                    ps.setInt(2, idUnidade);
                    ps.setDouble(3, calcularPrecoVenda);

                    ps.executeUpdate();
                    BaseDados.commit(conn);
                    return true;
                }
            }

        } catch (Exception e) {
            assert conn != null;
            BaseDados.rollback(conn);
            e.printStackTrace();
        } finally {
            BaseDados.Desligar();
        }
        return false;

    }
    private boolean produtoVendaExiste(String idProduto, int idUnidade, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) AS total FROM Produto_Venda WHERE id_produto = ? AND id_unidade = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idProduto);
            ps.setInt(2, idUnidade);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("total") > 0;
            }
        }
    }

    private double obterMaiorPrecoUnitario(String idProduto, Connection conn) throws SQLException {
        String query = """
            SELECT MAX(preco_unitario)
            FROM Produto_Fornecedor
            WHERE id_produto = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idProduto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }

        return 0; // Retorna 0 se não houver maior preço unitário registrado
    }

    public List<ProdutoVenda> obterProdutosVendaDaBaseDadosPorIdProduto(String id) {
        List<ProdutoVenda> produtosVenda = new ArrayList<>();

        try (Connection conn = getConexao()) {
            String query = """
                    SELECT Produto_Venda.id_produto as id_produto,
                    		Unidade.Id as id_unidade,
                    		Unidade.Descricao as descricao_unidade,
                    		Produto_Venda.preco_venda as preco_venda
                    		FROM Produto_Venda
                    		INNER JOIN Unidade ON Unidade.Id = Produto_Venda.id_unidade
                    WHERE id_produto = ?
              
                    """;
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        double precoVenda = rs.getDouble("preco_venda");
                        // Obter informações do Produto
                        Produto produto = criarObjetoProdutoVenda(rs);

                        // Obter informações da Unidade
                        Unidade unidade = criarObjetoUnidade(rs);

                        // Criar o objeto ProdutoVenda
                        ProdutoVenda produtoVenda = new ProdutoVenda(produto, unidade, precoVenda);
                        produtosVenda.add(produtoVenda);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Lide com a exceção conforme necessário
        }

        return produtosVenda;
    }

    private Unidade criarObjetoUnidade(ResultSet dados) throws SQLException {
        return new Unidade(
                dados.getInt("id_unidade"),
                dados.getString("descricao_unidade")
        );
    }

    private Produto criarObjetoProdutoVenda(ResultSet dados) throws SQLException {
        return new Produto(
                dados.getString("id_produto")
        );
    }


    public Map<String, Object> obterInfoProdutoVenda(String idProduto) {
        Map<String, Object> infoProdutoVenda = new HashMap<>();

        try (Connection conn = getConexao()) {
            String query = """
                SELECT pv.preco_venda, p.descricao
                FROM Produto_Venda pv
                INNER JOIN Produto p ON pv.id_produto = p.id
                WHERE pv.id_produto = ?
                """;
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, idProduto);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double precoVenda = rs.getDouble("preco_venda");
                        String descricao = rs.getString("descricao");

                        infoProdutoVenda.put("precoVenda", precoVenda);
                        infoProdutoVenda.put("descricao", descricao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Lide com a exceção conforme necessário
        }

        return infoProdutoVenda;
    }

    public Map<String, Object> obterInformacoesDoStock(String idProduto, int idUnidade) {
        Map<String, Object> informacoesStock = new HashMap<>();

        try (Connection conn = getConexao()) {
            String query = """
                SELECT s.quantidade, u.descricao as descricao_unidade
                FROM Stock s
                INNER JOIN Unidade u ON u.Id = s.id_unidade
                WHERE s.id_produto = ? AND s.id_unidade = ?
                """;
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, idProduto);
                ps.setInt(2, idUnidade);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double quantidadeStock = rs.getDouble("quantidade");
                        String descricaoUnidade = rs.getString("descricao_unidade");

                        informacoesStock.put("quantidadeStock", quantidadeStock);
                        informacoesStock.put("descricaoUnidade", descricaoUnidade);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Lide com a exceção conforme necessário
        }

        return informacoesStock;
    }



    public boolean enviarProdutosParaAPI(ProdutoVenda produto, String descricao, String unidade, double quantidade, double precoVenda){
        try {
            // Construa os dados do produto para enviar
            String data = construirDadosDoProduto(produto, descricao, unidade, quantidade, precoVenda);

            // Chame o método createProduct para criar o produto na API
            String respostaAPI = createProduct(data);

            System.out.println("Resposta da API para produto " + produto.getProduto().getId()+ ": " + respostaAPI);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // ou lide com a exceção conforme necessário
            return false;
        }

    }

    private String construirDadosDoProduto(ProdutoVenda produto, String descricao, String unidade, double quantidade, double precoVenda) {
        // Construir dados do produto em formato JSON
        return "{"
                + "\"Code\": \"" + produto.getProduto().getId() + "\","
                + "\"Description\": \"" + descricao + "\","
                + "\"PVP\": " + precoVenda + ","
                + "\"Stock\": " + quantidade + ","
                + "\"Unit\": \"" + unidade + "\","
                + "\"Active\": true"
                + "}";
    }


}
