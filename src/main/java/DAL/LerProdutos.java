package DAL;


import Model.*;
import Model.API.OrderLine;
import Model.API.ProdutoAPI;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import com.google.gson.*;
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

import static Utilidades.API.*;
import static Utilidades.BaseDados.getConexao;
import static Utilidades.BaseDados.iniciarTransacao;

public class LerProdutos {

    public ObservableList<Produto> lerProdutosFornecedores(String id) throws IOException {

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
                                        
                    WHERE Produto.Id = ?
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Produto produto = criarObjetoProduto(resultSet);
                        produtosFornecedores.add(produto);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao ler produtos disponíveis!");
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
                       Stock.Quantidade as quantidade,
                       Produto.UUID as uuid,
                       Produto_Venda.preco_venda as preco_venda
                       FROM Stock
                       INNER JOIN Produto ON Produto.Id = Stock.Id_Produto
                       INNER JOIN Unidade ON Unidade.Id = Produto.Id_Unidade
                       INNER JOIN Produto_Venda ON Produto_Venda.id_produto = Stock.Id_Produto
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
            Mensagens.Erro("Erro!", "Erro ao ler stock de produtos!");
        } finally {
            BaseDados.Desligar();
        }
        return stockProdutos;
    }

    private Stock criarObjetoStock(ResultSet dados) throws SQLException {
        Produto produto = new Produto(
                dados.getString("id_produto"),
                dados.getString("descricao_produto")
        );

        ProdutoVenda uuid = new ProdutoVenda(
                dados.getString("uuid"),
                dados.getDouble("preco_venda")
        );

        Unidade unidade = new Unidade(
                dados.getInt("id_unidade"),
                dados.getString("descricao_unidade"));

        return new Stock(
                produto,
                unidade,
                dados.getInt("quantidade"),
                uuid);
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
            Mensagens.Erro("Erro!", "Erro ao gerar produto para venda!");
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
            System.out.println(e.getMessage());
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


    public Map<String, Object> obterInfoProdutoVenda(String idProduto) throws IOException {
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
            Mensagens.Erro("Erro!", "Erro ao obter informações de venda do produto");
        }

        return infoProdutoVenda;
    }

    public Map<String, Object> obterInformacoesDoStock(String idProduto, int idUnidade) throws IOException {
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
            Mensagens.Erro("Erro!", "Errp ao obter informações do stock!");
        }

        return informacoesStock;
    }


    public boolean enviarProdutosParaAPI(ProdutoVenda produto, String descricao, String unidade, double quantidade, double precoVenda) throws IOException, SQLException {
        try {
            // Construa os dados do produto para enviar
            String data = construirDadosDoProduto(produto, descricao, unidade, quantidade, precoVenda);

            // Verificar se o produto existe na base de dados
            String uuidExistente = obterUUIDNaBaseDeDados(produto);

            if (uuidExistente != null) {
                // O produto já existe localmente, então podemos usar o UUID existente
                produto.setUUID(uuidExistente);

                // Mantém o PVP atual durante a atualização
                double novoPVP = produto.getPrecoVenda();

                // Construir o JSON com o novo estoque, PVP e estado
                String novoStockJson = "{\"Stock\": " + quantidade + ", \"PVP\": " + novoPVP + ", \"Active\": true}";

                // Enviar a solicitação PUT para a API para atualizar o estoque
                String respostaUpdateAPI = updateProduct(produto.getUUID(), novoStockJson);

                System.out.println("Resposta da API para atualização de estoque: " + respostaUpdateAPI);
            } else {
                // O produto não existe localmente, então criamos na API
                String respostaAPI = createProduct(data);

                // Extrai o UUID da resposta da API
                String UUID = getUUIDFromResponse(respostaAPI);
                produto.setUUID(UUID);

                // Criar o produto na base de dados local
                boolean produtoNaBd = criarProdutoNaBaseDados(produto);

                System.out.println("Resposta da API para produto " + produto.getProduto().getId() + ": " + respostaAPI);

                if (produtoNaBd) {
                    System.out.println("Produto na base de dados " + produto.getUUID() + " criado com sucesso");
                }
            }
            return true;
        } catch (IOException e) {
            Mensagens.Erro("Erro!", "Erro ao enviar produtos para a API");
            return false;
        }
    }

    private String obterUUIDNaBaseDeDados(ProdutoVenda produto) throws IOException {
        try (Connection conn = getConexao()) {
            String query = "SELECT UUID FROM Produto WHERE Id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                // Substitui o parâmetro na consulta pelo ID do produto
                preparedStatement.setString(1, produto.getProduto().getId());

                // Executa a consulta
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Se existir um resultado, retorna o UUID
                        return resultSet.getString("UUID");
                    }
                }
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao obter produto!");
        } finally {
            BaseDados.Desligar();
        }
        return null;
    }

    private String getUUIDFromResponse(String respostaAPI) {
        try {
            // Parse do JSON usando o Gson
            JsonObject jsonObject = JsonParser.parseString(respostaAPI).getAsJsonObject();

            // Extrai o valor associado à chave "Id"
            return jsonObject.get("Product").getAsString();

        } catch (Exception e) {
            // Trate exceções se ocorrer um erro ao analisar o JSON
            e.printStackTrace();
            return null;
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

    public boolean criarProdutoNaBaseDados(ProdutoVenda produto) throws SQLException, IOException {
        Connection conn = null;

        try {
            conn = getConexao();
            BaseDados.iniciarTransacao(conn);

            String query = """
                    UPDATE Produto SET UUID = ? WHERE Id = ?
                    """;

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, produto.getUUID());
                ps.setString(2, produto.getProduto().getId());

                ps.executeUpdate();
            }
            BaseDados.commit(conn);
            return true;

        } catch (IOException e) {
            BaseDados.rollback(conn);
            Mensagens.Erro("Erro", "Erro ao adicionar UUID ao produto");
        }
        return false;
    }


    public boolean atualizarStockAPI(List<OrderLine> orderLines) throws IOException {
        for (OrderLine line : orderLines) {
            // Obtém o estoque atual do produto na API
            double estoqueAtual = obterEstoqueAtual(line.getProductCode());

            String uuidDoProduto = obterUUIDNaBaseDadosString(line.getProductCode());

            double pvp = obterPVPdoProduto(uuidDoProduto);

            // Calcula o novo estoque subtraindo a quantidade de produtos comprados
            double novoEstoque = estoqueAtual - line.getQuantity();

            if (novoEstoque < 0) {
                Mensagens.Erro("Erro!", "Erro ao atualizar stock verifique a quantidade de produtos!");
                return false;
            }

            // Constrói a string de dados para enviar à API
            String data = """
                    {
                    "PVP": %s,
                    "Stock": %s
                    }
                    """.formatted(pvp, novoEstoque);


            try {
                // Chama o método para atualizar o estoque na API
                updateProduct(uuidDoProduto, data);
            } catch (IOException e) {
                // Lidar com falhas individuais, se necessário
                Mensagens.Erro("Erro", "Erro ao atualizar stock");
            }
        }

        return true;
    }

    private double obterPVPdoProduto(String uuidDoProduto) throws IOException {
        try {
            String respostaAPI = getProduct(uuidDoProduto);
            JsonObject jsonObject = JsonParser.parseString(respostaAPI).getAsJsonObject();

            // Obtenha a lista de produtos
            JsonArray productsArray = jsonObject.getAsJsonArray("Product");

            if (!productsArray.isEmpty()) {
                // Obtenha o primeiro item da lista (assumindo que só há um produto)
                JsonObject productObject = productsArray.get(0).getAsJsonObject();

                // Extrai o valor associado à chave "Stock"
                return productObject.get("PVP").getAsDouble();
            } else {
                // Lidar com o caso em que não há produtos na resposta
                Mensagens.Erro("Erro!", "Nenhum produto encontrado na resposta da API");
                return -1.0;
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao obter PVP");
        }
        return 0.0;
    }

    // Método para obter o estoque atual do produto na API
    private double obterEstoqueAtual(String productCode) throws IOException {
        try {
            String UUID = obterUUIDNaBaseDadosString(productCode);
            String respostaAPI = getProduct(UUID);

            // Parse do JSON usando o Gson
            JsonObject jsonObject = JsonParser.parseString(respostaAPI).getAsJsonObject();

            // Obtenha a lista de produtos
            JsonArray productsArray = jsonObject.getAsJsonArray("Product");

            if (!productsArray.isEmpty()) {
                // Obtenha o primeiro item da lista (assumindo que só há um produto)
                JsonObject productObject = productsArray.get(0).getAsJsonObject();

                // Extrai o valor associado à chave "Stock"
                return productObject.get("Stock").getAsDouble();
            } else {
                // Lidar com o caso em que não há produtos na resposta
                Mensagens.Erro("Erro!", "Nenhum produto encontrado na resposta da API");
                return -1.0;
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao obter stock da API");
            return -1.0;
        }
    }


    private String obterUUIDNaBaseDadosString(String productCode) throws IOException {
        try (Connection conn = getConexao()) {
            String query = "SELECT UUID FROM Produto WHERE Id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                // Substitui o parâmetro na consulta pelo ID do produto
                preparedStatement.setString(1, productCode);

                // Executa a consulta
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Se existir um resultado, retorna o UUID
                        return resultSet.getString("UUID");
                    }
                }
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao obter produto!");
        } finally {
            BaseDados.Desligar();
        }
        return null;
    }

    public boolean atualizarStockBaseDados(List<OrderLine> orderLines) throws IOException {
        Connection conn = null;
        try {
            conn = getConexao();
            iniciarTransacao(conn);
            for (OrderLine lines : orderLines) {
                String idProduto = lines.getProductCode();

                String query = """
                        UPDATE Stock SET Quantidade = Quantidade - ?
                        WHERE Id_Produto LIKE ?
                        """;

                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setDouble(1, lines.getQuantity());
                    ps.setString(2, idProduto);


                    int rowsUpdated = ps.executeUpdate();


                    if (rowsUpdated > 0) {
                        System.out.println("Stock atualizado do produto: " + idProduto);
                    } else {
                        System.out.println("Erro ao atualizar stock do produto: " + idProduto);
                    }
                }
            }

            BaseDados.commit(conn);
            return true;

        } catch (Exception e) {
            assert conn != null;
            BaseDados.rollback(conn);
            Mensagens.Erro("Erro", "Erro ao atualizar stock na base de dados!");
        } finally {
            BaseDados.Desligar();
        }

        return false;
    }

    public String obterDescricaoDoProdutoPeloCodigo(String productCode) throws IOException {
        String jsonResponse = getAllProducts();

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Verifique se a resposta possui a chave "Products"
            if (jsonObject.has("Products")) {
                JsonArray productsArray = jsonObject.getAsJsonArray("Products");

                //Percorre o array de produtos e obtem o codigo do produto selecionado
                for (JsonElement element : productsArray) {
                    JsonObject productObject = element.getAsJsonObject();

                    //Obtem o código
                    String codigoDoProduto = productObject.get("Code").getAsString();
                    if (codigoDoProduto.equals(productCode)) {
                        //Retorna o produto
                        return productObject.get("Description").getAsString();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "Descrição não encontrada";
    }



}

