package DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * A classe LerEncomenda é responsável por ler e manipular dados relacionados a encomendas,
 * linhas de encomendas e operações associadas à base de dados.
 */
public class LerEncomenda {
    LerFornecedores lerFornecedores = new LerFornecedores();
    LerPaises lerPaises = new LerPaises();
    BaseDados baseDados = new BaseDados();

    /**
     * Lê as linhas de uma encomenda específica a partir da base de dados.
     *
     * @param baseDados   A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda O ID da encomenda para a qual as linhas serão lidas.
     * @return Uma lista observável de LinhaEncomenda.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<LinhaEncomenda> lerLinhaEncomenda(BaseDados baseDados, int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

            // Utilizando JOIN para obter todas as informações necessárias em uma única consulta
            String query = """
                    SELECT LE.*,
                        P.Id_Fornecedor, 
                        P.Descricao AS DescricaoProduto, 
                        P.IdExterno AS IdExternoProduto, 
                        P.Estado AS EstadoProduto,
                        U.Descricao AS DescricaoUnidade,
                        PA.Nome AS NomePais
                        
                        FROM Linha_Encomenda LE
                        JOIN Produto P ON LE.Id_Produto = P.Id
                        JOIN Unidade U ON LE.Id_Unidade = U.Id
                        JOIN Pais PA ON LE.Id_Pais_Taxa = PA.Id
                        
                        WHERE LE.Id_Encomenda = ?
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
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
            baseDados.Desligar();
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
                dados.getInt("Id"),
                dados.getString("DescricaoUnidade")

        );

        Pais pais = new Pais(
                dados.getInt("Id"),
                dados.getString("NomePais")
        );

        Produto produtoEncontrado = new Produto(
                dados.getString("Id"),
                null,
                dados.getString("DescricaoProduto"),
                dados.getString("IdExternoProduto"),
                unidade,
                dados.getInt("EstadoProduto")
        );

        Encomenda encomenda = new Encomenda(
                dados.getInt("Id_Encomenda")

        );

        return new LinhaEncomenda(
                dados.getInt("Id"),
                encomenda,
                dados.getInt("Sequencia"),
                produtoEncontrado,
                dados.getDouble("Preco_Unitario"),
                dados.getDouble("Quantidade"),
                unidade,
                pais,
                dados.getDouble("Total_Taxa"),
                dados.getDouble("Total_Incidencia")
        );
    }

    /**
     * Lê todas as encomendas da base de dados.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @return Uma lista observável de objetos Encomenda .
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Encomenda> lerEncomendaDaBaseDeDados(BaseDados baseDados) throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

            String query = """
                    SELECT * FROM Encomenda
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {

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
            baseDados.Desligar();
        }
    }


    /**
     * Lê as encomendas da base de dados que estão no estado indicado.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @return Uma lista observável de objetos Encomenda correspondentes ao estado indicado.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Encomenda> lerEncomendaDaBaseDeDadosPendentes(BaseDados baseDados) throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();


            String query = """
                    SELECT * FROM Encomenda WHERE Id_Estado = 1
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {

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
            baseDados.Desligar();
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

        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(baseDados, dados.getString("Id_fornecedor"));
        Pais pais = lerPaises.obterPaisPorId(baseDados, dados.getInt("Id_Pais"));
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
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param encomenda A encomenda a ser adicionada à base de dados.
     * @return O ID da encomenda adicionada, ou 0 se ocorrer um erro.
     * @throws IOException Se ocorrer um erro durante a adição à base de dados.
     */
    public int adicionarEncomendaBaseDeDados(BaseDados baseDados, Encomenda encomenda) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            int Id_Encomenda = getQueryEncomenda(encomenda);

            // Inserir produtos associados à encomenda na tabela Produto
            for (LinhaEncomenda linha : encomenda.getLinhas()) {

                //lerProdutos.lerProdutoPorId ja existe, nao grava
                inserirProdutoNaTabelaProduto(baseDados, linha.getProduto());
            }

            // Inserir na tabela Linha_Encomenda
            for (LinhaEncomenda linha : encomenda.getLinhas()) {
                inserirLinhaEncomenda(baseDados, Id_Encomenda, linha);
            }

            baseDados.commit(baseDados.getConexao());

            return Id_Encomenda;
        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição da base de dados!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }

        return 0;
    }

    /**
     * Insere um produto na tabela Produto, verificando se já existe antes de realizar a inserção.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param produto   O produto a ser inserido na tabela.
     */
    private void inserirProdutoNaTabelaProduto(BaseDados baseDados, Produto produto) throws IOException {
        //Verificar se o produto já eiste na tabela antes de o inserir
        if (!produtoExisteNaTabela(baseDados, produto.getId())) {
            // Construa a string da consulta SQL, escapando os valores
            String queryProduto = """
                    INSERT INTO Produto (Id, Id_Fornecedor, Descricao, Id_Unidade, IdExterno, Estado)
                    VALUES (?, ?, ?, ?, ?, 0)
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(queryProduto)) {

                preparedStatement.setString(1, produto.getId());
                preparedStatement.setString(2, produto.getFornecedor().getIdExterno());
                preparedStatement.setString(3, produto.getDescricao());
                preparedStatement.setInt(4, produto.getUnidade().getId());
                preparedStatement.setString(5, produto.getIdExterno());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                Mensagens.Erro("Erro", "Erro ao adicionar produto!");
                baseDados.rollback(baseDados.getConexao());
                e.getMessage();

            }
        }
    }

    /**
     * Verifica se um produto com o ID especificado já existe na tabela Produto.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param Id        O ID do produto a ser verificado.
     * @return True se o produto já existir na tabela, false caso contrário.
     * @throws RuntimeException Se ocorrer um erro durante a verificação.
     */
    private boolean produtoExisteNaTabela(BaseDados baseDados, String Id) throws IOException {
        try {

            String query = "SELECT Id FROM Produto WHERE Id = ?";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setString(1, Id);

                ResultSet resultado = preparedStatement.executeQuery();
                return resultado.next(); // Retorna true se o produto já existir na tabela
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro","Erro ao verificar produto existente!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Insere uma linha de encomenda na base de dados.
     *
     * @param baseDados    A instância da classe BaseDados para conexão com o banco de dados.
     * @param Id_Encomenda O ID da encomenda à qual a linha pertence.
     * @param linha        A linha de encomenda a ser inserida.
     */
    private void inserirLinhaEncomenda(BaseDados baseDados, int Id_Encomenda, LinhaEncomenda linha) throws IOException {
        // Construir a string da consulta SQL, escapando os valores com PreparedStatement
        String queryLinha = "INSERT INTO Linha_Encomenda (Id_Encomenda, Sequencia, Id_Produto, Preco_Unitario, Quantidade, Id_Unidade," +
                " Id_Pais_Taxa, Total_Taxa, Total_Incidencia, Total_Linha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(queryLinha)) {
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
            Mensagens.Erro("Erro!","Erro ao inserir linha da encomenda");
            baseDados.rollback(baseDados.getConexao());
            e.printStackTrace();
        }
    }


    /**
     * Obtém a consulta SQL para inserir uma encomenda na base de dados.
     *
     * @param encomenda A encomenda a ser inserida na base de dados.
     * @return Uma string contendo a consulta SQL para inserção da encomenda.
     * @throws IOException Se ocorrer um erro durante a obtenção do ID do país.
     */
    private int getQueryEncomenda(Encomenda encomenda) throws IOException {
        Pais idPais = lerPaises.obterPaisPorId(baseDados, encomenda.getPais().getId());

        // Construir a string da consulta SQL, escapando os valores com PreparedStatement
        String query = "INSERT INTO Encomenda (Referencia, Data, Id_Fornecedor, Id_Pais, Total_Taxa, Total_Incidencia, Total, Id_Estado, Id_estado_pagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, encomenda.getReferencia());
            preparedStatement.setString(2, encomenda.getData().toString());
            preparedStatement.setString(3, encomenda.getFornecedor().getIdExterno());
            preparedStatement.setInt(4, idPais.getId());
            preparedStatement.setDouble(5, encomenda.getTotalTaxa());
            preparedStatement.setDouble(6, encomenda.getTotalIncidencia());
            preparedStatement.setDouble(7, encomenda.getTotal());
            preparedStatement.setInt(8, encomenda.getEstado().getValue());
            preparedStatement.setInt(9, encomenda.getEstadoPagamento().getValue());

            // Retornar a string da consulta SQL
            //return preparedStatement.toString();
            int idEncomenda = preparedStatement.executeUpdate();

            if (idEncomenda > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro!","Erro ao inserir encomenda!");
            throw new RuntimeException(e);
        }

        return 0;
    }


    /**
     * Obtém uma encomenda da base de dados com base no ID fornecido.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param id        O ID da encomenda a ser obtida.
     * @return Um objeto Encomenda contendo as informações da encomenda correspondente ao ID.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Encomenda obterEncomendaPorId(BaseDados baseDados, String id) throws IOException {
        Encomenda encomenda = null;
        try {
            baseDados.Ligar();

            String query = """
                    SELECT * FROM Encomenda WHERE Id = ?"
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)){
                preparedStatement.setString(1, id);

                ResultSet resultado = preparedStatement.executeQuery();

                if (resultado.next()) {
                    encomenda = criarObjetoEncomenda(resultado);
                }

            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");

        } finally {
            baseDados.Desligar();
        }
        return encomenda;
    }

    /**
     * Lê as linhas de encomenda para aprovação da base de dados com base no ID da encomenda.
     *
     * @param baseDados   A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda O ID da encomenda para a qual as linhas devem ser lidas.
     * @return Uma lista de objetos LinhaEncomenda contendo as informações para aprovação.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */

    public List<LinhaEncomenda> lerLinhasParaAprovacao(BaseDados baseDados, int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

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

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
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
            baseDados.Desligar();
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
     * @param baseDados  A instância da classe BaseDados para conexão com o banco de dados.
     * @param idProduto  O ID do produto cujo estoque será atualizado.
     * @param idUnidade  O ID da unidade associada ao produto.
     * @param quantidade A quantidade a ser adicionada ao estoque.
     * @return True se a atualização do estoque for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarStock(BaseDados baseDados, String idProduto, int idUnidade, double quantidade) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Se existir, dá um update apenas na quantidade, soma a que tem na tabela mais a nova quantidade
            String script;
            if (produtoExiste(baseDados, idProduto)) {
                script = "UPDATE Stock SET Id_Unidade = ?, Quantidade = Quantidade + ? WHERE Id_Produto = ?";
            } else {
                // Senão, insere o produto
                script = "INSERT INTO Stock (Id_Produto, Id_Unidade, Quantidade) VALUES (?, ?, ?)";
            }

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(script)) {
                preparedStatement.setString(1, idProduto);
                preparedStatement.setInt(2, idUnidade);
                preparedStatement.setDouble(3, quantidade);

                // Execute o script
                preparedStatement.executeUpdate();
            }
            baseDados.commit(baseDados.getConexao());

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao adicionar/atualizar stock!");
            baseDados.rollback(baseDados.getConexao());
            return false;
        } finally {
            baseDados.Desligar();
        }
        return true;
    }


    /**
     * Verifica se um produto já existe na tabela de produtos da base de dados.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param idProduto O ID do produto a ser verificado.
     * @return True se o produto existir, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a verificação na base de dados.
     */
    private boolean produtoExiste(BaseDados baseDados, String idProduto) throws SQLException {
        String query = "SELECT * FROM Stock WHERE Id_Produto = ?";

        try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
            preparedStatement.setString(1, idProduto);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }


    /**
     * Atualiza o estado de uma encomenda para "Aprovada" na base de dados.
     *
     * @param baseDados   A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda O ID da encomenda a ser atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarEstadoEncomenda(BaseDados baseDados, int idEncomenda) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            String query = "UPDATE Encomenda SET Id_Estado = 2 WHERE Id = ?";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, idEncomenda);

                preparedStatement.executeUpdate();
            }
            baseDados.commit(baseDados.getConexao());

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar a encomenda!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }

        return false;
    }



    /**
     * Atualiza o estado de uma encomenda para "Recusada" na base de dados.
     *
     * @param baseDados   A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda O ID da encomenda a ser atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean actualizarEstadoEncomendaRecusada(BaseDados baseDados, int idEncomenda) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            String query = """
                    UPDATE Encomenda
                    SET Id_Estado = 3, Id_estado_pagamento = 3
                    WHERE Id = ?;
                                        
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, idEncomenda);

                preparedStatement.executeUpdate();
            }

            baseDados.commit(baseDados.getConexao());

            return true;
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar encomenda!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }

        return false;
    }


    /**
     * Atualiza o saldo devedor na conta corrente de um fornecedor na base de dados.
     *
     * @param baseDados      A instância da classe BaseDados para conexão com o banco de dados.
     * @param valorEncomenda O valor da encomenda a ser adicionado ao saldo devedor.
     * @param idFornecedor   O ID do fornecedor cuja conta corrente será atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarSaldoDevedores(BaseDados baseDados, double valorEncomenda, String idFornecedor) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Verificar se já existe um registro para o fornecedor
            String verificaExistencia = "SELECT * FROM Conta_Corrente WHERE Id_Fornecedor = ?";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(verificaExistencia)) {
                preparedStatement.setString(1, idFornecedor);
                ResultSet resultSet = preparedStatement.executeQuery();

                String script;
                if (resultSet.next()) {
                    // Se existir, faz o UPDATE
                    script = "UPDATE Conta_Corrente SET Saldo = Saldo + ? WHERE Id_Fornecedor = ?";

                    try (PreparedStatement updateStatement = baseDados.getConexao().prepareStatement(script)) {
                        updateStatement.setDouble(1, valorEncomenda);
                        updateStatement.setString(2, idFornecedor);
                        // Execute o script
                        updateStatement.executeUpdate();
                    }

                } else {
                        // Se não existir, faz a inserção
                        script = "INSERT INTO Conta_Corrente (Id_Fornecedor, Saldo) VALUES (?, ?)";

                        try (PreparedStatement updateStatement = baseDados.getConexao().prepareStatement(script)) {
                            updateStatement.setString(1, idFornecedor);
                            updateStatement.setDouble(2, valorEncomenda);

                            // Execute o script
                            updateStatement.executeUpdate();
                        }
                    }

                }

            baseDados.commit(baseDados.getConexao());

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Error", "Ocorreu um erro ao atualizar o saldo devedor");
            baseDados.rollback(baseDados.getConexao());
            return false; // Retorna false em caso de erro
        } finally {
            baseDados.Desligar();
        }
    }


    /**
     * Verifica se é possível eliminar um fornecedor com base nas encomendas associadas.
     *
     * @param baseDados  A instância da classe BaseDados para conexão com o banco de dados.
     * @param fornecedor O utilizador (fornecedor) a ser verificado para eliminação.
     * @return True se a eliminação for possível, false caso contrário.
     */
    public boolean podeEliminarFornecedor(BaseDados baseDados, Utilizador fornecedor) throws IOException {
        try {
            baseDados.Ligar();

            String queryEncomendas = "SELECT Id_Fornecedor FROM Encomenda";
            String queryFornecedores = "SELECT Id_Externo FROM Fornecedor WHERE Id_Utilizador = ?";

            try (PreparedStatement preparedStatementEncomendas = baseDados.getConexao().prepareStatement(queryEncomendas);
                 PreparedStatement preparedStatementFornecedores = baseDados.getConexao().prepareStatement(queryFornecedores)) {

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

                    baseDados.commit(baseDados.getConexao());
                    // Se não encontra igual, a eliminação pode proceder
                    return true;
                }
            }


        } catch (SQLException | IOException e) {
            Mensagens.Erro("Erro!", "Erro ao eliminar fornecedor");
            throw new RuntimeException(e);
        } finally {
            baseDados.Desligar();
        }
    }


    /**
     * Lê as encomendas associadas a um fornecedor específico com base no ID externo do fornecedor.
     *
     * @param baseDados           A instância da classe {@code BaseDados} para realizar operações no banco de dados.
     * @param idFornecedorExterno O ID externo do fornecedor para o qual deseja recuperar as encomendas.
     * @return Uma lista observável de encomendas associadas ao fornecedor específico.
     * @throws IOException Se ocorrer um erro durante a leitura das encomendas.
     */
    public ObservableList<Encomenda> lerEncomendasPorFornecedor(BaseDados baseDados, String idFornecedorExterno) throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

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
                        AND Estado.Id = 2
                    """;

            // Preparar a declaração SQL com o fornecedor externo como parâmetro
            PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);
            preparedStatement.setString(1, idFornecedorExterno);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next()) {
                Encomenda encomenda = criarObjetoEncomendaContaCorrente(resultado);
                encomendas.add(encomenda);
            }

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao carregar encomendas");
        } finally {
            baseDados.Desligar();
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

}