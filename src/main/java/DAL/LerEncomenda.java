package DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static Utilidades.BaseDados.*;

/**
 * A classe LerEncomenda é responsável por ler e manipular dados relacionados a encomendas,
 * linhas de encomendas e operações associadas à base de dados.
 */
public class LerEncomenda {
    LerFornecedores lerFornecedores = new LerFornecedores();
    LerPaises lerPaises = new LerPaises();

    /**
     * Lê as linhas de uma encomenda específica a partir da base de dados.
     *
     * @param idEncomenda O ID da encomenda para a qual as linhas serão lidas.
     * @return Uma lista observável de LinhaEncomenda.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<LinhaEncomenda> lerLinhaEncomenda(int idEncomenda) throws IOException {

        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
           Connection conn = getConexao();

            // Utilizando JOIN para obter todas as informações necessárias em uma única consulta
            String query = """
                    SELECT	Linha_Encomenda.Id,
                    		Id_Encomenda as id_encomenda,
                    		Sequencia as sequencia,
                    		Produto.Id as id_produto,
                    		Produto.Descricao AS descricao_produto,
                    		Unidade.Id AS Id_Unidade,
                    		Unidade.Descricao as unidade,
                    		Preco_Unitario as preco_unitario,
                    		Quantidade as quantidade,
                    		Pais.id AS id_pais,
                    		Pais.Nome as pais,
                    		Total_taxa as total_taxa,
                    		Total_Incidencia as total_incidencia,
                    		Total_Linha as total_linha
                    FROM Linha_Encomenda
                    	INNER JOIN Produto ON Produto.Id = Id_Produto
                    	INNER JOIN Unidade ON Unidade.Id = Linha_encomenda.Id_Unidade
                    	INNER JOIN Pais ON Pais.id = Id_Pais_Taxa
                                        
                    WHERE Id_Encomenda = ?               
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, idEncomenda);

                ResultSet resultado = preparedStatement.executeQuery();

                while (resultado.next()) {
                    LinhaEncomenda linhaEncomenda = criarObjetoLinha(resultado);
                    linhasEncomenda.add(linhaEncomenda);
                }
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao ler linhas da encomenda!");

        } finally {
            BaseDados.Desligar();
        }

        return linhasEncomenda;
    }

    /**
     * Cria um objeto LinhaEncomenda a partir dos dados de um ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados da linha de encomenda.
     * @return Um objeto LinhaEncomenda com as informações obtidas do ResultSet.
     * @throws IOException  Se ocorrer um erro durante a obtenção de informações adicionais.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private LinhaEncomenda criarObjetoLinha(ResultSet dados) throws IOException, SQLException {

        Unidade unidade = new Unidade(
                dados.getInt("Id_Unidade"),
                dados.getString("unidade")

        );

        Pais pais = new Pais(
                dados.getInt("id_pais"),
                dados.getString("pais")
        );

        Produto produtoEncontrado = new Produto(
                dados.getString("id_produto"),
                dados.getString("descricao_produto"),
                unidade
        );

        Encomenda encomenda = new Encomenda(
                dados.getInt("id_encomenda")

        );

        return new LinhaEncomenda(
                dados.getInt("Id"),
                encomenda,
                dados.getInt("Sequencia"),
                produtoEncontrado,
                dados.getDouble("preco_unitario"),
                dados.getDouble("quantidade"),
                pais,
                dados.getDouble("total_taxa"),
                dados.getDouble("total_incidencia"),
                dados.getDouble("total_linha")
        );
    }

    /**
     * Lê todas as encomendas da base de dados.
     *
     * @return Uma lista observável de objetos Encomenda .
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Encomenda> lerEncomendaDaBaseDeDados() throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            Connection conn = getConexao();

            String query = """
                    SELECT * FROM Encomenda
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                ResultSet resultado = preparedStatement.executeQuery();

                while (resultado.next()) {
                    Encomenda encomenda = criarObjetoEncomenda(resultado);
                    encomendas.add(encomenda);
                }

            }
            return encomendas;

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            return null; // Retorna null apenas se houver exceção
        } finally {
            BaseDados.Desligar();
        }
    }


    /**
     * Lê as encomendas da base de dados que estão no estado indicado.
     *
     * @return Uma lista observável de objetos Encomenda correspondentes ao estado indicado.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Encomenda> lerEncomendaDaBaseDeDadosPendentes() throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            Connection conn = getConexao();


            String query = """
                    SELECT * FROM Encomenda WHERE Id_Estado = 1
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                ResultSet resultado = preparedStatement.executeQuery();

                while (resultado.next()) {
                    Encomenda encomenda = criarObjetoEncomenda(resultado);
                    encomendas.add(encomenda);
                }
            }


            return encomendas;

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            return null; // Retorna null apenas se houver exceção
        } finally {
            BaseDados.Desligar();
        }
    }

    /**
     * Lê as encomendas associadas a um fornecedor a partir da base de dados.
     *
     * @param idFornecedorExterno O identificador externo do fornecedor.
     * @return Uma lista observável de objetos Encomenda associados ao fornecedor.
     * @throws IOException em caso de erro de leitura.
     */
    public ObservableList<Encomenda> lerEncomendaDaBaseDeDadosFornecedor(String idFornecedorExterno) throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            Connection conn = getConexao();

            String query = """
                    SELECT * FROM Encomenda WHERE Id_Fornecedor = ?
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1,idFornecedorExterno);

                ResultSet resultado = preparedStatement.executeQuery();

                while (resultado.next()) {
                    Encomenda encomenda = criarObjetoEncomendaFornecedor(resultado);
                    encomendas.add(encomenda);
                }
            }


            return encomendas;

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            return null; // Retorna null apenas se houver exceção
        } finally {
            BaseDados.Desligar();
        }
    }


    /**
     * Cria um objeto Encomenda a partir dos dados de um ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados da encomenda.
     * @return Um objeto Encomenda com as informações obtidas do ResultSet.
     * @throws IOException  Se ocorrer um erro durante a obtenção de informações adicionais.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Encomenda criarObjetoEncomenda(ResultSet dados) throws IOException, SQLException {

        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(dados.getString("Id_fornecedor"));
        Pais pais = lerPaises.obterPaisPorId(dados.getInt("Id_Pais"));
        EstadoEncomenda estado = EstadoEncomenda.valueOfId(dados.getInt("Id_Estado"));

        return new Encomenda(
                dados.getInt("Id"),
                dados.getString("Referencia"),
                dados.getDate("Data").toLocalDate(),
                fornecedor,
                pais,
                dados.getDouble("Total_Taxa"),
                dados.getDouble("Total_Incidencia"),
                dados.getDouble("Total"),
                estado
        );
    }

    /**
     * Cria e retorna um objeto Encomenda com base nos dados fornecidos pelo ResultSet.
     *
     * @param dados O ResultSet contendo os dados da encomenda.
     * @return Um objeto Encomenda criado com base nos dados fornecidos.
     * @throws IOException em caso de erro de leitura.
     * @throws SQLException em caso de erro ao acessar os dados do ResultSet.
     */
    private Encomenda criarObjetoEncomendaFornecedor(ResultSet dados) throws IOException, SQLException {

        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(dados.getString("Id_fornecedor"));
        Pais pais = lerPaises.obterPaisPorId(dados.getInt("Id_Pais"));
        EstadoEncomenda estado = EstadoEncomenda.valueOfId(dados.getInt("Id_Estado"));

        return new Encomenda(
                dados.getInt("Id"),
                dados.getString("Referencia"),
                dados.getDate("Data").toLocalDate(),
                fornecedor,
                pais,
                dados.getDouble("Total_Taxa"),
                dados.getDouble("Total_Incidencia"),
                dados.getDouble("Total"),
                estado
        );
    }

    /**
     * Adiciona uma encomenda à base de dados, incluindo a inserção de produtos associados e suas linhas.
     *
     * @param encomenda A encomenda a ser adicionada à base de dados.
     * @return O ID da encomenda adicionada, ou 0 se ocorrer um erro.
     * @throws IOException Se ocorrer um erro durante a adição à base de dados.
     */
    public int adicionarEncomendaBaseDeDados(Encomenda encomenda, boolean mostrarMensagemErro) throws IOException {
        Connection conexao = null;
        try {
            conexao = getConexao();
            BaseDados.iniciarTransacao(conexao);

            int Id_Encomenda = getQueryEncomenda(conexao, encomenda);

            // Inserir produtos associados à encomenda na tabela Produto
            for (LinhaEncomenda linha : encomenda.getLinhas()) {
                // lerProdutos.lerProdutoPorId ja existe, nao grava
                inserirProdutoNaTabelaProduto(conexao, linha.getProduto());
            }

            // Inserir na tabela Linha_Encomenda
            try {
                for (LinhaEncomenda linha : encomenda.getLinhas()) {
                    inserirLinhaEncomenda(Id_Encomenda, linha, conexao);
                }
            } catch (RuntimeException e) {
                BaseDados.rollback(conexao);
                if (mostrarMensagemErro) {
                    System.out.println(e.getMessage());
                }
                return 0;
            }

            BaseDados.commit(conexao);

            return Id_Encomenda;
        } catch (Exception e) {
            if (mostrarMensagemErro) {
                  Mensagens.Erro("Erro na base de dados!", "Erro na adição da base de dados!");
            }
        } finally {
            BaseDados.Desligar();
        }

        return 0;
    }

    /**
     * Adiciona ou atualiza o mapeamento de produtos associados a uma encomenda na base de dados.
     *
     * @param encomenda A encomenda contendo as linhas de produtos a serem mapeadas.
     * @return 1 se a operação for bem-sucedida, 0 em caso de erro ao adicionar/atualizar o produto.
     * @throws IOException em caso de erro de leitura ou gravação.
     */
    public int adicionarMapeamento(Encomenda encomenda) throws IOException {
        Connection conn = null;
        try {
            conn = BaseDados.getConexao();
            BaseDados.iniciarTransacao(conn);

            String updateQuery = """
                UPDATE Produto_Fornecedor SET preco_unitario = ? WHERE id_externo = ?
                """;

            String insertQuery = """
                INSERT INTO Produto_Fornecedor (id_produto, id_fornecedor,id_externo, preco_unitario)
                VALUES (?,?,?,?)
                """;

            try (PreparedStatement psInsert = conn.prepareStatement(insertQuery)) {
                PreparedStatement psUpdate = conn.prepareStatement(updateQuery);


                for (LinhaEncomenda produto : encomenda.getLinhas()) {
                    psUpdate.setDouble(1, produto.getPreco());
                    psUpdate.setString(2, produto.getProduto().getIdExterno());

                    int rowsUpdated = psUpdate.executeUpdate();

                    if(rowsUpdated == 0){
                        psInsert.setString(1, produto.getProduto().getId());
                        psInsert.setString(2, encomenda.getFornecedor().getIdExterno());
                        psInsert.setString(3, produto.getProduto().getIdExterno());
                        psInsert.setDouble(4, produto.getPreco());

                        // Executa a atualização para cada produto
                        psInsert.executeUpdate();
                    }
                }
            }
            // Confirma a transação
            BaseDados.commit(conn);

        } catch (Exception e) {
            Mensagens.Erro("Erro!","Erro ao adicionar produto!");
            // Reverte a transação em caso de exceção
            assert conn != null;
            BaseDados.rollback(conn);
            return 0; // ou retorne um código de erro
        } finally {
            BaseDados.Desligar();
        }

        return 1; // ou retorne um código de sucesso
    }


    /**
     * Insere um produto na tabela Produto, verificando se já existe antes de realizar a inserção.
     *
     * @param produto   O produto a ser inserido na tabela.
     */
    private void inserirProdutoNaTabelaProduto(Connection conexao, Produto produto) throws IOException {
        //Verificar se o produto já eiste na tabela antes de o inserir
        if (!produtoExisteNaTabela(conexao, produto.getId())) {
            // Construa a string da consulta SQL, escapando os valores
            String queryProduto = """
                    INSERT INTO Produto (Id, Descricao, Id_Unidade)
                    VALUES (?, ?, ?)
                    """;

            try (PreparedStatement preparedStatement = conexao.prepareStatement(queryProduto)) {

                preparedStatement.setString(1, produto.getId());
                preparedStatement.setString(2, produto.getDescricao());
                preparedStatement.setInt(3, produto.getUnidade().getId());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                Mensagens.Erro("Erro", "Erro ao adicionar produto!");
                BaseDados.rollback(getConexao());
                e.getMessage();

            }
        }
    }

    /**
     * Verifica se um produto com o ID especificado já existe na tabela Produto.
     *
     * @param Id        O ID do produto a ser verificado.
     * @return True se o produto já existir na tabela, false caso contrário.
     * @throws RuntimeException Se ocorrer um erro durante a verificação.
     */
    private boolean produtoExisteNaTabela(Connection conexao, String Id) throws IOException {
        try {

            String query = "SELECT Id FROM Produto WHERE Id = ?";

            try (PreparedStatement preparedStatement = conexao.prepareStatement(query)) {
                preparedStatement.setString(1, Id);

                ResultSet resultado = preparedStatement.executeQuery();
                return resultado.next(); // Retorna true se o produto já existir na tabela
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro", "Erro ao verificar produto existente!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Insere uma linha de encomenda na base de dados.
     *
     * @param Id_Encomenda O ID da encomenda à qual a linha pertence.
     * @param linha        A linha de encomenda a ser inserida.
     */
    private void inserirLinhaEncomenda(int Id_Encomenda, LinhaEncomenda linha, Connection connection) throws IOException{

        // Construir a string da consulta SQL, escapando os valores com PreparedStatement
        String queryLinha = "INSERT INTO Linha_Encomenda (Id_Encomenda, Sequencia, Id_Produto, Preco_Unitario, Quantidade, Id_Unidade," +
                " Id_Pais_Taxa, Total_Taxa, Total_Incidencia, Total_Linha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryLinha)) {
            preparedStatement.setInt(1, Id_Encomenda);
            preparedStatement.setInt(2, linha.getSequencia());
            preparedStatement.setString(3, linha.getProduto().getId());
            preparedStatement.setDouble(4, linha.getPreco());
            preparedStatement.setDouble(5, linha.getQuantidade());
            preparedStatement.setInt(6, linha.getUnidade().getId());
            preparedStatement.setInt(7, linha.getTaxa().getId());
            preparedStatement.setDouble(8, linha.getTotalTaxa());
            preparedStatement.setDouble(9, linha.getTotalIncidencia());
            preparedStatement.setDouble(10, linha.getTotal());

            // Executar a instrução preparada
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao inserir linha da encomenda!");
            BaseDados.rollback(connection);
            throw new RuntimeException("Erro");

        }
    }


    /**
     * Obtém a consulta SQL para inserir uma encomenda na base de dados.
     *
     * @param conexao Obtém a conexão daquela query
     * @param encomenda A encomenda a ser inserida na base de dados.
     * @return Uma string contendo a consulta SQL para inserção da encomenda.
     * @throws IOException Se ocorrer um erro durante a obtenção do ID do país.
     */
    private int getQueryEncomenda(Connection conexao, Encomenda encomenda) throws IOException, SQLException {

        Pais idPais = lerPaises.obterPaisPorId(encomenda.getPais().getId());

        // Construir a string da consulta SQL, escapando os valores com PreparedStatement
        String query = "INSERT INTO Encomenda (Referencia, Data, Id_Fornecedor, Id_Pais, Total_Taxa, Total_Incidencia, Total, Id_Estado, Id_estado_pagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, encomenda.getReferencia());
            preparedStatement.setString(2, encomenda.getData().toString());
            preparedStatement.setString(3, encomenda.getFornecedor().getIdExterno());
            preparedStatement.setInt(4, idPais.getId());
            preparedStatement.setDouble(5, encomenda.getTotalTaxa());
            preparedStatement.setDouble(6, encomenda.getTotalIncidencia());
            preparedStatement.setDouble(7, encomenda.getTotal());
            preparedStatement.setInt(8, encomenda.getEstado().getValue());
            preparedStatement.setInt(9, encomenda.getEstadoPagamento().getValue());


            int idEncomenda = preparedStatement.executeUpdate();

            if (idEncomenda > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao inserir encomenda!");
            BaseDados.rollback(conexao);
            throw new RuntimeException(e);
        }

        return 0;
    }


    /**
     * Obtém uma encomenda da base de dados com base no ID fornecido.
     *
     * @param id        O ID da encomenda a ser obtida.
     * @return Um objeto Encomenda contendo as informações da encomenda correspondente ao ID.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Encomenda obterEncomendaPorId(String id,boolean erro ) throws IOException {
        Encomenda encomenda = null;
        Connection conn = null;
        try {
            conn = getConexao();

            String query = """
                    SELECT * FROM Encomenda WHERE Id = ?
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, id);

                ResultSet resultado = preparedStatement.executeQuery();

                if (resultado.next()) {
                    encomenda = criarObjetoEncomenda(resultado);
                }

            }

        } catch (SQLException e) {
            if(erro) {
                Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            }
        } finally {
            BaseDados.Desligar();
        }
        return encomenda;
    }

    /**
     * Lê as linhas de encomenda para aprovação da base de dados com base no ID da encomenda.
     *
     * @param idEncomenda O ID da encomenda para a qual as linhas devem ser lidas.
     * @return Uma lista de objetos LinhaEncomenda contendo as informações para aprovação.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */

    public List<LinhaEncomenda> lerLinhasParaAprovacao(int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            Connection conn = getConexao();

            // Utilizando JOIN para obter todas as informações necessárias em uma única consulta
            String query = "SELECT " +
                    "Linha_Encomenda.Id_Produto AS id_produto, " +
                    "Produto.Descricao AS descricao_produto, " +
                    "Linha_Encomenda.Id_Unidade AS id_unidade, " +
                    "Unidade.Descricao AS descricao_unidade, " +
                    "Linha_Encomenda.Quantidade AS quantidade " +
                    "FROM Linha_Encomenda " +
                    "INNER JOIN Produto ON Produto.Id = Linha_Encomenda.Id_Produto " +
                    "INNER JOIN Unidade ON Unidade.Id = Produto.Id_Unidade " +
                    "WHERE Linha_Encomenda.Id_Encomenda = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, idEncomenda);

                ResultSet resultado = preparedStatement.executeQuery();

                while (resultado.next()) {
                    LinhaEncomenda linhaEncomenda = criarObjetoLinhaParaAprovacao(resultado);
                    linhasEncomenda.add(linhaEncomenda);
                }


            } catch (SQLException e) {
                Mensagens.Erro("Erro!", "Erro ao ler linhas da encomenda");
                throw new RuntimeException(e);
            }

        } finally {
            BaseDados.Desligar();
        }

        return linhasEncomenda;
    }


    /**
     * Cria um objeto LinhaEncomenda para aprovação a partir dos dados do ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados da linha de encomenda.
     * @return Um objeto LinhaEncomenda para aprovação.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private LinhaEncomenda criarObjetoLinhaParaAprovacao(ResultSet dados) throws SQLException {

        Unidade unidade = new Unidade(
                dados.getInt("id_unidade"),
                dados.getString("descricao_unidade")
        );

        Produto produtoEncontrado = new Produto(
                dados.getString("id_produto"),
                dados.getString("descricao_produto"),
                unidade
        );

        return new LinhaEncomenda(
                produtoEncontrado,
                dados.getDouble("Quantidade")
        );
    }


    /**
     * Atualiza o estoque de um produto na base de dados.
     *
     * @param idProduto  O ID do produto cujo estoque será atualizado.
     * @param idUnidade  O ID da unidade associada ao produto.
     * @param quantidade A quantidade a ser adicionada ao estoque.
     * @return True se a atualização do estoque for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarStock(String idProduto, int idUnidade, double quantidade) throws IOException {
        Connection conn = null;
        try {
            conn = getConexao();
            iniciarTransacao(conn);

            // Se existir, dá um update apenas na quantidade, soma a que tem na tabela mais a nova quantidade
            String script;
            if (produtoExiste(conn, idProduto)) {
                script = "UPDATE Stock SET Id_Unidade = ?, Quantidade = Quantidade + ? WHERE Id_Produto = ?";
            } else {
                // Senão, insere o produto
                script = "INSERT INTO Stock (Id_Produto, Id_Unidade, Quantidade) VALUES (?, ?, ?)";
            }

            try (PreparedStatement ps = conn.prepareStatement(script)) {
                if (produtoExiste(conn, idProduto)) {
                    // Configuração para o caso de UPDATE
                    ps.setInt(1, idUnidade);
                    ps.setDouble(2, quantidade);
                    ps.setString(3, idProduto);
                } else {
                    // Configuração para o caso de INSERT
                    ps.setString(1, idProduto);
                    ps.setInt(2, idUnidade);
                    ps.setDouble(3, quantidade);
                }

                // Execute o script
                ps.executeUpdate();
            }

            BaseDados.commit(conn);

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao adicionar/atualizar stock!");
            assert conn != null;
            BaseDados.rollback(conn);
            return false;
        } finally {
            BaseDados.Desligar();
        }
        return true;
    }



    /**
     * Verifica se um produto já existe na tabela de produtos da base de dados.
     *
     * @param idProduto O ID do produto a ser verificado.
     * @return True se o produto existir, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a verificação na base de dados.
     */
    private boolean produtoExiste(Connection conn, String idProduto) throws SQLException {
        String query = "SELECT * FROM Stock WHERE Id_Produto = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, idProduto);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }


    /**
     * Atualiza o estado de uma encomenda para "Aprovada" na base de dados.
     *
     * @param idEncomenda O ID da encomenda a ser atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarEstadoEncomenda(int idEncomenda) throws IOException {
        Connection conn = null;
        try {
            BaseDados.Ligar();
            conn = getConexao();
            iniciarTransacao(conn);

            String query = "UPDATE Encomenda SET Id_Estado = 2 WHERE Id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, idEncomenda);

                preparedStatement.executeUpdate();
            }
            BaseDados.commit(conn);

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar a encomenda!");
            assert conn != null;
            BaseDados.rollback(conn);
        } finally {
            BaseDados.Desligar();
        }

        return false;
    }


    /**
     * Atualiza o estado de uma encomenda para "Recusada" na base de dados.
     *
     * @param idEncomenda O ID da encomenda a ser atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean actualizarEstadoEncomendaRecusada(int idEncomenda) throws IOException {
        Connection conn = null;
        try {
            BaseDados.Ligar();
            conn = getConexao();
            iniciarTransacao(conn);

            String query = """
                    UPDATE Encomenda
                    SET Id_Estado = 3, Id_estado_pagamento = 3
                    WHERE Id = ?;
                                        
                    """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, idEncomenda);

                preparedStatement.executeUpdate();
            }

            BaseDados.commit(conn);

            return true;
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar encomenda!");
            assert conn != null;
            BaseDados.rollback(conn);
        } finally {
            BaseDados.Desligar();
        }

        return false;
    }

    /**
     * Verifica se é possível eliminar um fornecedor com base nas encomendas associadas.
     *
     * @param fornecedor O utilizador (fornecedor) a ser verificado para eliminação.
     * @return True se a eliminação for possível, false caso contrário.
     */
    public boolean podeEliminarFornecedor(Utilizador fornecedor) throws IOException {
        try {
            Connection conn = getConexao();

            String queryEncomendas = "SELECT Id_Fornecedor FROM Encomenda";
            String queryFornecedores = "SELECT Id_Externo FROM Fornecedor WHERE Id_Utilizador = ?";

            try (PreparedStatement preparedStatementEncomendas = conn.prepareStatement(queryEncomendas);
                 PreparedStatement preparedStatementFornecedores = conn.prepareStatement(queryFornecedores)) {

                preparedStatementFornecedores.setInt(1, fornecedor.getId());

                try (ResultSet resultadoEncomendas = preparedStatementEncomendas.executeQuery();
                     ResultSet resultadoFornecedores = preparedStatementFornecedores.executeQuery()) {

                    while (resultadoEncomendas.next()) {
                        String encomendaIdExterno = resultadoEncomendas.getString("Id_Fornecedor");

                        while (resultadoFornecedores.next()) {
                            String idExternoFornecedor = resultadoFornecedores.getString("Id_Externo");

                            if (encomendaIdExterno.equals(idExternoFornecedor)) {
                                // Se alguma EncomendaIdExterno == IdExternoFornecedor, impede a eliminação
                                return false;
                            }
                        }
                    }

                    BaseDados.commit(conn);
                    // Se não encontra igual, a eliminação pode proceder
                    return true;
                }
            }


        } catch (SQLException | IOException e) {
            Mensagens.Erro("Erro!", "Erro ao eliminar fornecedor");
            throw new RuntimeException(e);
        } finally {
            BaseDados.Desligar();
        }
    }


    /**
     * Lê as encomendas associadas a um fornecedor específico com base no ID externo do fornecedor.
     *
     * @param idFornecedorExterno O ID externo do fornecedor para o qual deseja recuperar as encomendas.
     * @return Uma lista observável de encomendas associadas ao fornecedor específico.
     * @throws IOException Se ocorrer um erro durante a leitura das encomendas.
     */
    public ObservableList<Encomenda> lerEncomendasPorFornecedor(String idFornecedorExterno) throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            Connection conn = getConexao();

            String query = """
                    SELECT 
                        Encomenda.Id as id_encomenda, 
                        Fornecedor.Id_Externo as id_fornecedor,
                        Encomenda.Referencia as referencia, 
                        Encomenda.Data as data_encomenda,
                        Encomenda.Total as total, 
                        Estado.Id as estado,
                        Estado_Pagamento.id as estado_pagamento
                    FROM Encomenda 
                        INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Encomenda.Id_Fornecedor
                        INNER JOIN Estado ON Estado.Id = Encomenda.Id_Estado
                        INNER JOIN Estado_Pagamento ON Estado_Pagamento.id = Encomenda.Id_estado_pagamento
                        WHERE Fornecedor.Id_Externo = ? 
                        AND Estado.Id = 2 AND Estado_Pagamento.id = 1
                    """;

            // Preparar a declaração SQL com o fornecedor externo como parâmetro
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, idFornecedorExterno);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next()) {
                Encomenda encomenda = criarObjetoEncomendaContaCorrente(resultado);
                encomendas.add(encomenda);
            }

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao carregar encomendas");
        } finally {
            BaseDados.Desligar();
        }

        return encomendas;
    }

    /**
     * Cria um objeto Encomenda com base nos dados do ResultSet, considerando a associação com Conta Corrente.
     *
     * @param dados O conjunto de dados resultante de uma consulta SQL.
     * @return Um objeto Encomenda populado com os dados do ResultSet.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Encomenda criarObjetoEncomendaContaCorrente(ResultSet dados) throws SQLException, IOException {

        Fornecedor fornecedor = new Fornecedor(
                dados.getString("id_fornecedor")
        );

        EstadoEncomenda estado = EstadoEncomenda.valueOfId(dados.getInt("estado"));
        EstadoPagamento estadoPagamento = EstadoPagamento.valueOfId(dados.getInt("estado_pagamento"));

        return new Encomenda(
                dados.getInt("Id_encomenda"),
                fornecedor,
                dados.getString("referencia"),
                dados.getDate("data_encomenda").toLocalDate(),
                dados.getDouble("total"),
                estado,
                estadoPagamento
        );
    }

    /**
     * Atualiza o estado de pagamento das encomendas associadas a um pagamento na base de dados.
     *
     * @param pagamento O objeto de pagamento contendo as encomendas a serem atualizadas.
     * @return true se a atualização do estado de pagamento for bem-sucedida, false caso contrário.
     * @throws IOException em caso de erro de leitura ou gravação.
     * @throws SQLException em caso de erro ao acessar a base de dados.
     */
    public boolean atualizarEstadoPagamentoEncomenda(Pagamento pagamento) throws IOException, SQLException {
        Connection conn = null;
        try {
            BaseDados.Ligar();
            conn = getConexao();
            iniciarTransacao(conn);

            String query = """
                UPDATE Encomenda SET id_estado_pagamento = 2
                WHERE Encomenda.Id = ?
                """;

            try (PreparedStatement ps =conn.prepareStatement(query)) {
                for (Encomenda encomenda : pagamento.getEncomendas()) {
                    ps.setInt(1, encomenda.getId());
                    ps.executeUpdate();
                }

                BaseDados.commit(conn);
                return true;
            } catch (SQLException e) {
                BaseDados.rollback(conn);
                Mensagens.Erro("Erro!", "Erro ao atualizar o estado de pagamento das encomendas!");
                throw e; // Lança a exceção novamente para que a chamada externa possa lidar com ela
            }
        } finally {
            BaseDados.Desligar();
        }
    }

    /**
     * Lê as encomendas aprovadas na base de dados, retornando uma lista observável de objetos EncomendaFornecedor.
     *
     * @return Uma lista observável de encomendas aprovadas associadas aos fornecedores.
     * @throws IOException em caso de erro de leitura.
     */
    public ObservableList<EncomendaFornecedor> lerEncomendaAprovada() throws IOException {
        ObservableList<EncomendaFornecedor> encomendasAprovadas = FXCollections.observableArrayList();

        try (Connection conn = BaseDados.getConexao()) {
            BaseDados.iniciarTransacao(conn);

            String query = """
               SELECT
                 Encomenda.Id AS id,
                 Encomenda.Referencia AS referencia,
                 Encomenda.Data AS data,
                 Fornecedor.Nome AS nomeFornecedor,
                 Encomenda.Total AS valortotal,
                 Aprovacao_Encomenda.id_utilizador as idutilizador,
                 Utilizador.username AS emailUtilizador
                 FROM
                 Encomenda
                  INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Encomenda.Id_Fornecedor
                  INNER JOIN Aprovacao_Encomenda ON Aprovacao_Encomenda.Id_Encomenda = Encomenda.Id
                 INNER JOIN Utilizador ON Utilizador.id_util = Aprovacao_Encomenda.id_utilizador
                 WHERE
                 Encomenda.Id_Estado = 2;
                """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");


                        LocalDateTime dataEncomenda = LocalDateTime.parse(resultSet.getString("data"), formatter);

                        EncomendaFornecedor encomendaFornecedor = new EncomendaFornecedor(
                                resultSet.getInt("id"),
                                resultSet.getString("referencia"),
                                dataEncomenda.toLocalDate(),
                                resultSet.getString("nomeFornecedor"),
                                resultSet.getDouble("valortotal"),
                                resultSet.getString("emailUtilizador"),
                                resultSet.getInt("idutilizador") //
                        );


                        encomendasAprovadas.add(encomendaFornecedor);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.Desligar();
        }

        return encomendasAprovadas;
    }

    /**
     * Lê as encomendas recusadas na base de dados, retornando uma lista observável de objetos EncomendaFornecedor.
     *
     * @return Uma lista observável de encomendas recusadas associadas aos fornecedores.
     * @throws IOException em caso de erro de leitura.
     */
    public ObservableList<EncomendaFornecedor> lerEncomendaRecusada() throws IOException {
        ObservableList<EncomendaFornecedor> encomendasRecusadas = FXCollections.observableArrayList();

        try (Connection conn = BaseDados.getConexao()) {
            BaseDados.iniciarTransacao(conn);

            String query = """
               SELECT
                 Encomenda.Id AS id,
                 Encomenda.Referencia AS referencia,
                 Encomenda.Data AS data,
                 Fornecedor.Nome AS nomeFornecedor,
                 Encomenda.Total AS valortotal,
                 Aprovacao_Encomenda.id_utilizador as idutilizador,
                 Utilizador.username AS emailUtilizador
                 FROM
                 Encomenda
                  INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Encomenda.Id_Fornecedor
                  INNER JOIN Aprovacao_Encomenda ON Aprovacao_Encomenda.Id_Encomenda = Encomenda.Id
                 INNER JOIN Utilizador ON Utilizador.id_util = Aprovacao_Encomenda.id_utilizador
                 WHERE
                 Encomenda.Id_Estado = 3;
                """;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");


                        LocalDateTime dataEncomenda = LocalDateTime.parse(resultSet.getString("data"), formatter);

                        EncomendaFornecedor encomendaFornecedor = new EncomendaFornecedor(
                                resultSet.getInt("id"),
                                resultSet.getString("referencia"),
                                dataEncomenda.toLocalDate(),
                                resultSet.getString("nomeFornecedor"),
                                resultSet.getDouble("valortotal"),
                                resultSet.getString("emailUtilizador"),
                                resultSet.getInt("idutilizador") //
                        );


                        encomendasRecusadas.add(encomendaFornecedor);
                    }
                }
            }

            BaseDados.commit(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.Desligar();
        }

        return encomendasRecusadas;
    }


    /**
     * Registra a aprovação de uma encomenda por um utilizador na base de dados.
     *
     * @param idEncomenda O identificador da encomenda a ser aprovada.
     * @param utilizador O identificador do utilizador que está a aprovar a encomenda.
     * @return true se a aprovação for bem-sucedida, false caso contrário.
     * @throws IOException em caso de erro de leitura ou gravação.
     */
    public boolean quemAprovouEncomenda(int idEncomenda, int utilizador) throws IOException {

        Connection conn = null;
        try{

            conn = getConexao();
            BaseDados.iniciarTransacao(conn);

            String query = """
                INSERT INTO Aprovacao_Encomenda (id_encomenda, id_utilizador)
                VALUES (?, ?)
                """;

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, idEncomenda);
                ps.setInt(2, utilizador);

                // Execute the SQL query
                ps.executeUpdate();

                BaseDados.commit(conn);
                return true;

            } catch (Exception e){
                BaseDados.rollback(conn);
                Mensagens.Erro("Erro!", "Erro ao atualizar dados de mapeamento de encomenda!");
            }
            return false;
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar dados de mapeamento de encomenda!");
        } finally {
            BaseDados.Desligar();
        }

        return false;
    }


}

