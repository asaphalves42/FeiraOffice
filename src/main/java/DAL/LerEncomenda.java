package DAL;

import DAL.LerFornecedores;
import DAL.LerPaises;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda O ID da encomenda para a qual as linhas serão lidas.
     * @return  Uma lista observável de LinhaEncomenda.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<LinhaEncomenda> lerLinhaEncomenda(BaseDados baseDados, int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

            // Utilizando JOIN para obter todas as informações necessárias em uma única consulta
            String query = "SELECT LE.*, " +
                    "P.Id_Fornecedor, P.Descricao AS DescricaoProduto, P.IdExterno AS IdExternoProduto, P.Estado AS EstadoProduto, " +
                    "U.Descricao AS DescricaoUnidade, " +
                    "PA.Nome AS NomePais " +
                    "FROM Linha_Encomenda LE " +
                    "JOIN Produto P ON LE.Id_Produto = P.Id " +
                    "JOIN Unidade U ON LE.Id_Unidade = U.Id " +
                    "JOIN Pais PA ON LE.Id_Pais_Taxa = PA.Id " +
                    "WHERE LE.Id_Encomenda = " + idEncomenda;

            ResultSet resultado = baseDados.Selecao(query);

            while (resultado.next()) {
                LinhaEncomenda linhaEncomenda = criarObjetoLinha(resultado);
                linhasEncomenda.add(linhaEncomenda);
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return linhasEncomenda;
    }

    /**
     * Cria um objeto LinhaEncomenda a partir dos dados de um ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados da linha de encomenda.
     * @return Um objeto LinhaEncomenda com as informações obtidas do ResultSet.
     * @throws IOException   Se ocorrer um erro durante a obtenção de informações adicionais.
     * @throws SQLException  Se ocorrer um erro ao acessar os dados do ResultSet.
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
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda");

            while (resultado.next()) {
                Encomenda encomenda = criarObjetoEncomenda(resultado);
                encomendas.add(encomenda);
            }

            baseDados.Desligar();

            return encomendas;

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            return null; // ou lançar uma exceção, dependendo do comportamento desejado
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
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda WHERE Id_Estado = 1");

            while (resultado.next()) {
                Encomenda encomenda = criarObjetoEncomenda(resultado);
                encomendas.add(encomenda);
            }

            baseDados.Desligar();

            return encomendas;

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            return null; // ou lançar uma exceção, dependendo do comportamento desejado
        }
    }

    /**
     * Cria um objeto Encomenda a partir dos dados de um ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados da encomenda.
     * @return Um objeto Encomenda com as informações obtidas do ResultSet.
     * @throws IOException   Se ocorrer um erro durante a obtenção de informações adicionais.
     * @throws SQLException  Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Encomenda criarObjetoEncomenda(ResultSet dados) throws IOException, SQLException {

        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(baseDados, dados.getString("Id_fornecedor"));
        Pais pais = lerPaises.obterPaisPorId(baseDados, dados.getInt("Id_Pais"));
        Estado estado = Estado.valueOfId(dados.getInt("Id_Estado"));

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
     * @param baseDados  A instância da classe BaseDados para conexão com o banco de dados.
     * @param encomenda  A encomenda a ser adicionada à base de dados.
     * @return O ID da encomenda adicionada, ou 0 se ocorrer um erro.
     * @throws IOException Se ocorrer um erro durante a adição à base de dados.
     */
    public int adicionarEncomendaBaseDeDados(BaseDados baseDados, Encomenda encomenda) throws IOException {
        try {
            baseDados.Ligar();

            String queryEncomenda = getQueryEncomenda(encomenda);

            int Id_Encomenda = baseDados.ExecutarPreparementStatement(queryEncomenda);


            // Inserir produtos associados à encomenda na tabela Produto
            for (LinhaEncomenda linha : encomenda.getLinhas()) {

                //lerProdutos.lerProdutoPorId ja existe, nao grava
                inserirProdutoNaTabelaProduto(baseDados, linha.getProduto());
            }

            // Inserir na tabela Linha_Encomenda
            for (LinhaEncomenda linha : encomenda.getLinhas()) {
                inserirLinhaEncomenda(baseDados, Id_Encomenda, linha);
            }

            baseDados.Desligar();

            return Id_Encomenda;
        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição da base de dados!");
        }

        return 0;
    }

    /**
     * Insere um produto na tabela Produto, verificando se já existe antes de realizar a inserção.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param produto   O produto a ser inserido na tabela.
     */
    private void inserirProdutoNaTabelaProduto(BaseDados baseDados, Produto produto) {
        //Verificar se o produto já eiste na tabela antes de o inserir
        if (!produtoExisteNaTabela(baseDados, produto.getId())) {
            // Construa a string da consulta SQL, escapando os valores
            String queryProduto = "INSERT INTO Produto (Id, Id_Fornecedor, Descricao, Id_Unidade, IdExterno, Estado) " +
                    "VALUES (" +
                    "'" + produto.getId() + "'," +
                    "'" + produto.getFornecedor().getIdExterno() + "'," +
                    "'" + produto.getDescricao() + "'," +
                    produto.getUnidade().getId() + "," +
                    "'" + produto.getIdExterno() + "'," +
                    "0" +
                    ")";
            baseDados.Executar(queryProduto);
        }
    }

    /**
     * Verifica se um produto com o ID especificado já existe na tabela Produto.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param Id        O ID do produto a ser verificado.
     * @return          True se o produto já existir na tabela, false caso contrário.
     * @throws RuntimeException Se ocorrer um erro durante a verificação.
     */
    private boolean produtoExisteNaTabela(BaseDados baseDados, String Id) {
        try {
            ResultSet resultado = baseDados.Selecao("SELECT Id FROM Produto WHERE Id = '" + Id + "'");
            return resultado.next(); // Retorna true se o produto já existir na tabela
        } catch (SQLException e) {
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
    private void inserirLinhaEncomenda(BaseDados baseDados, int Id_Encomenda, LinhaEncomenda linha) {
        // Construa a string da consulta SQL, escapando os valores
        String queryLinha = "INSERT INTO Linha_Encomenda (Id_Encomenda, Sequencia, Id_Produto, Preco_Unitario, Quantidade, Id_Unidade," +
                " Id_Pais_Taxa, Total_Taxa, Total_Incidencia, Total_Linha) " +
                "VALUES (" +
                Id_Encomenda + "," +
                "'" + linha.getSequencia() + "'," +
                "'" + linha.getProduto().getId() + "'," +
                linha.getPreco() + "," +
                linha.getQuantidade() + "," +
                linha.getUnidade().getId() + "," +
                linha.getTaxa().getId() + "," +
                linha.getTotalTaxa() + "," +
                linha.getTotalIncidencia() + "," +
                linha.getTotal() +
                ")";

        baseDados.Executar(queryLinha);
    }

    /**
     * Obtém a consulta SQL para inserir uma encomenda na base de dados.
     *
     * @param encomenda A encomenda a ser inserida na base de dados.
     * @return Uma string contendo a consulta SQL para inserção da encomenda.
     * @throws IOException Se ocorrer um erro durante a obtenção do ID do país.
     */
    @NotNull
    private String getQueryEncomenda(Encomenda encomenda) throws IOException {

        Pais idPais = lerPaises.obterPaisPorId(baseDados, encomenda.getPais().getId());

        //gravar encomenda
        String referencia = "'" + encomenda.getReferencia() + "'";
        String data = "'" + encomenda.getData() + "'";
        String idFornecedor = "'" + encomenda.getFornecedor().getIdExterno() + "'";
        int idPaisInt = idPais.getId();
       int idEstado = encomenda.getEstado().getValue();

        // Construa a string da consulta SQL, escapando os valores
        return "INSERT INTO Encomenda (Referencia, Data, Id_Fornecedor, Id_Pais, Total_Taxa, Total_Incidencia, Total, Id_Estado) " +
                "VALUES (" +
                referencia + "," +
                data + "," +
                idFornecedor + "," +
                idPaisInt + "," +
                encomenda.getTotalTaxa() + "," +
                encomenda.getTotalIncidencia() + "," +
                encomenda.getTotal() + "," +
                idEstado + ")";
    }

    /**
     * Obtém uma encomenda da base de dados com base no ID fornecido.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param id        O ID da encomenda a ser obtida.
     * @return          Um objeto Encomenda contendo as informações da encomenda correspondente ao ID.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Encomenda obterEncomendaPorId(BaseDados baseDados, String id) throws IOException {
        Encomenda encomenda = null;
        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda WHERE Id = " + id);

            if (resultado.next()) {
                encomenda = criarObjetoEncomenda(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return encomenda;
    }

    /**
     * Lê as linhas de encomenda para aprovação da base de dados com base no ID da encomenda.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
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
                    "WHERE Linha_Encomenda.Id_Encomenda = " + idEncomenda;

            ResultSet resultado = baseDados.Selecao(query);

            while (resultado.next()) {
                LinhaEncomenda linhaEncomenda = criarObjetoLinhaParaAprovacao(resultado);
                linhasEncomenda.add(linhaEncomenda);
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param idProduto O ID do produto cujo estoque será atualizado.
     * @param idUnidade O ID da unidade associada ao produto.
     * @param quantidade A quantidade a ser adicionada ao estoque.
     * @return True se a atualização do estoque for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarStock(BaseDados baseDados, String idProduto, int idUnidade, double quantidade) throws IOException {
        try {
            baseDados.Ligar();

            // Se existir, dá um update apenas na quantidade, soma a que tem na tabela mais a nova quantidade
            String script;
            if (produtoExiste(baseDados, idProduto)) {
                script = "UPDATE Stock SET Id_Unidade = " + idUnidade + ", Quantidade = Quantidade + " + quantidade +
                        " WHERE Id_Produto = '" + idProduto + "'";
            } else {
                // Senão, insere o produto
                script = "INSERT INTO Stock (Id_Produto, Id_Unidade, Quantidade) " +
                        "VALUES ('" + idProduto + "', " + idUnidade + ", " + quantidade + ")";
            }

            // Execute o script
            baseDados.Executar(script);

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao adicionar/atualizar stock!");
            return false;
        } finally {
            baseDados.Desligar();
        }
        return true;
    }

    /**
     * Verifica se um produto já existe na tabela de produtos da base de dados.
     *
     * @param baseDados  A instância da classe BaseDados para conexão com o banco de dados.
     * @param idProduto  O ID do produto a ser verificado.
     * @return True se o produto existir, false caso contrário.
     * @throws SQLException Se ocorrer um erro durante a verificação na base de dados.
     */
    private boolean produtoExiste(BaseDados baseDados, String idProduto) throws SQLException {
        String query = "SELECT * FROM Stock WHERE Id_Produto = '" + idProduto + "'";
        ResultSet resultSet = baseDados.Selecao(query);
        return resultSet.next();
    }

    /**
     * Atualiza o estado de uma encomenda para "Aprovada" na base de dados.
     *
     * @param baseDados  A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda  O ID da encomenda a ser atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarEstadoEncomenda(BaseDados baseDados, int idEncomenda) throws IOException {
        try {
            baseDados.Ligar();

            String query = "UPDATE Encomenda SET Id_Estado = 2 WHERE Id = " + idEncomenda;

            baseDados.Executar(query);

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar a encomenda!");
        } finally {
            baseDados.Desligar();
        }

        return false;
    }


    /**
     * Atualiza o estado de uma encomenda para "Recusada" na base de dados.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param idEncomenda O ID da encomenda a ser atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean actualizarEstadoEncomendaRecusada(BaseDados baseDados, int idEncomenda) throws IOException {
        try {
            baseDados.Ligar();

            String query = "UPDATE Encomenda Set Id_Estado = 3 WHERE Id = " + idEncomenda;

            baseDados.Executar(query);
            return true;
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar encomenda!");
            baseDados.Desligar();
        }
        return false;

    }

    /**
     * Atualiza o saldo devedor na conta corrente de um fornecedor na base de dados.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @param valorEncomenda O valor da encomenda a ser adicionado ao saldo devedor.
     * @param idFornecedor  O ID do fornecedor cuja conta corrente será atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException   Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarSaldoDevedores(BaseDados baseDados, double valorEncomenda, String idFornecedor) throws IOException {
        try {
            baseDados.Ligar();

            // Verificar se já existe um registro para o fornecedor
            String verificaExistencia = "SELECT * FROM Conta_Corrente WHERE Id_Fornecedor = '" + idFornecedor + "'";
            ResultSet resultSet = baseDados.Selecao(verificaExistencia);

            String script;
            if (resultSet.next()) {
                // Se existir, faz o UPDATE
                script = "UPDATE Conta_Corrente SET Saldo = Saldo + " + valorEncomenda + " WHERE Id_Fornecedor = '" + idFornecedor + "'";
            } else {
                // Se não existir, faz a inserção
                script = "INSERT INTO Conta_Corrente (Id_Fornecedor, Saldo) VALUES ('" + idFornecedor + "', " + valorEncomenda + ")";
            }

            baseDados.Executar(script);

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Error", "Ocorreu um erro ao atualizar o saldo devedor");
            return false; // Retorna false em caso de erro
        } finally {
            baseDados.Desligar();
        }
    }

    /**
     * Verifica se é possível eliminar um fornecedor com base nas encomendas associadas.
     *
     * @param baseDados    A instância da classe BaseDados para conexão com o banco de dados.
     * @param fornecedor   O utilizador (fornecedor) a ser verificado para eliminação.
     * @return             True se a eliminação for possível, false caso contrário.
     */
    public boolean podeEliminarFornecedor(BaseDados baseDados,Utilizador fornecedor) {
        try {
            baseDados.Ligar();

            try (ResultSet resultado = baseDados.Selecao("SELECT Id_Fornecedor FROM Encomenda");
                 ResultSet resultado2 = baseDados.Selecao("SELECT Id_Externo FROM Fornecedor WHERE Id_Utilizador=" +fornecedor.getId())){

                while (resultado.next()) {
                    String encomendaIdExterno = resultado.getString("Id_Fornecedor");

                    while (resultado2.next()) {
                        String idExternoFornecedor = resultado2.getString("Id_Externo");

                        if (encomendaIdExterno.equals(idExternoFornecedor)) {
                            // Se alguma EncomendaIdExterno == IdExternoFornecedor, impede a eliminação
                            return false;
                        }
                    }
                }return true;
            }

            // Se não encontra igual, a eliminação pode proceder
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            baseDados.Desligar();
        }
    }

    /**
     * Lê as encomendas associadas a um fornecedor específico com base no ID externo do fornecedor.
     *
     * @param baseDados A instância da classe {@code BaseDados} para realizar operações no banco de dados.
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
                    Estado.Id as estado
                FROM Encomenda 
                    INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Encomenda.Id_Fornecedor
                    INNER JOIN Estado ON Estado.Id = Encomenda.Id_Estado
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

        Estado estado = Estado.valueOfId(dados.getInt("estado"));

        return new Encomenda(
                dados.getInt("Id_encomenda"),
                fornecedor,
                dados.getString("referencia"),
                dados.getDate("data_encomenda").toLocalDate(),
                dados.getDouble("total"),
                estado
        );
    }

}



