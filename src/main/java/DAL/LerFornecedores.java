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
import java.util.ArrayList;
import java.util.List;

/**
 * Classe com funções de acesso à base de dados e leitura, referentes aos fornecedores.
 */
public class LerFornecedores {
    BaseDados baseDados = new BaseDados();
    LerPaises lerPaises = new LerPaises();
    LerUtilizadores lerUtilizadores = new LerUtilizadores();

    /**
     * Lê a lista de fornecedores a partir da base de dados e retorna uma lista observável de fornecedores.
     *
     * @return Uma ObservableList contendo os fornecedores lidos da base de dados, ou null se ocorrer um erro na leitura.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Fornecedor> lerFornecedoresDaBaseDeDados(BaseDados baseDados) throws IOException {

        ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();
        Fornecedor fornecedor = null;
        try {
            baseDados.Ligar();

            String query = """
                    SELECT * FROM Fornecedor
                    """;

            PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next()) { //Ler os forncedores da base de dados, um a um e cria um objeto novo
                fornecedor = criarObjetoFornecedor(resultado);

                fornecedores.add(fornecedor);

            }

            return fornecedores; // A leitura foi bem-sucedida
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        } finally {
            baseDados.Desligar();
        }

    }

    /**
     * Cria um objeto Fornecedor a partir dos dados de um ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados do fornecedor.
     * @return Um objeto Fornecedor com as informações obtidas do ResultSet.
     * @throws IOException  Se ocorrer um erro durante a obtenção de informações adicionais.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Fornecedor criarObjetoFornecedor(ResultSet dados) throws IOException, SQLException {
        int idPais = dados.getInt("Id_Pais");
        int idUtilizador = dados.getInt("Id_Utilizador");


        Pais pais = lerPaises.obterPaisPorId(baseDados, idPais);
        UtilizadorFornecedor utilizador = lerUtilizadores.obterUtilizadorPorIdFornecedor(baseDados, idUtilizador);

        return new Fornecedor(
                dados.getInt("id"),
                dados.getString("Nome"),
                dados.getString("Id_Externo"),
                dados.getString("Morada1"),
                dados.getString("Morada2"),
                dados.getString("Localidade"),
                dados.getString("CodigoPostal"),
                pais,
                utilizador
        );
    }

    /**
     * Obtém um fornecedor da base de dados com base no seu identificador externo.
     *
     * @param baseDados    A instância da classe BaseDados para conexão com o banco de dados.
     * @param idFornecedor O identificador externo do fornecedor a ser obtido.
     * @return Um objeto Fornecedor correspondente ao identificador externo fornecido, ou null se não encontrado.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Fornecedor obterFornecedorPorId(BaseDados baseDados, String idFornecedor) throws IOException {
        Fornecedor fornecedor = null;

        try {
            baseDados.Ligar();

            String query = """
                    SELECT * FROM Fornecedor WHERE Id_Externo = ?
                    """;
            PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);

            preparedStatement.setString(1, idFornecedor);
            ResultSet resultado = preparedStatement.executeQuery();

            if (resultado.next()) {
                fornecedor = criarObjetoFornecedor(resultado);
            }


        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        } finally {
            baseDados.Desligar();
        }

        return fornecedor;

    }


    /**
     * Adiciona um fornecedor à base de dados, com informações de país e utilizador, e retorna o fornecedor adicionado.
     *
     * @param fornecedor O fornecedor a ser adicionado à base de dados.
     * @param pais       O país associado ao fornecedor.
     * @param utilizador O utilizador associado ao fornecedor.
     * @return O fornecedor adicionado à base de dados, ou null se ocorrer um erro durante a operação.
     * @throws IOException Se ocorrer um erro durante a operação.
     */
    public Fornecedor adicionarFornecedorBaseDeDados(BaseDados baseDados, Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException {

        try {

            baseDados.Ligar();

            /*
            Procedure com as variáveis que preciso inserir nas tabelas, Fornecedor e Utilizador. Quando insere o utilizador, vou buscar o ultimo id que acabei de inserir através
            da função 'ScopeIdentity()' do SQL, quando obtido o ID, insiro o mesmo na tabela de fornecedor.
             */
            String query = "exec [Inserir_Fornecedor] @username = '" + fornecedor.getIdUtilizador().getEmail() +
                    "', @password = '" + fornecedor.getIdUtilizador().getPassword() +
                    "', @id_role = '" + utilizador.getTipo().getValue() +
                    "', @nome = '" + fornecedor.getNome() +
                    "', @id_externo = '" + fornecedor.getIdExterno() +
                    "', @morada1 = '" + fornecedor.getMorada1() +
                    "', @morada2 = '" + fornecedor.getMorada2() +
                    "', @localidade = '" + fornecedor.getLocalidade() +
                    "', @codigo_postal = '" + fornecedor.getCodigoPostal() +
                    "', @id_pais = '" + pais.getId() + "'";

            baseDados.Executar(query);

            return fornecedor; // retorna o fornecedor

        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
        } finally {
            baseDados.Desligar();
        }
        return null;
    }

    /**
     * Remove um fornecedor da base de dados com base no ID fornecido.
     *
     * @param fornecedorId O ID do fornecedor a ser removido.
     * @return true se a remoção for bem-sucedida, false caso contrário.
     */
    public boolean removerFornecedorDaBaseDeDados(BaseDados baseDados, int fornecedorId) {
        try {
            baseDados.Ligar();

            String query = "DELETE FROM Fornecedor WHERE id = ?";
            PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);
            preparedStatement.setInt(1, fornecedorId);

            int linhasAfetadas = preparedStatement.executeUpdate();

            baseDados.Desligar();

            if (linhasAfetadas > 0) {
                return true; // Retorna true se alguma linha foi afetada (remoção bem-sucedida)
            }

        } catch (SQLException e) {
            try {
                Mensagens.Erro("Erro na remoção!", "Erro na remoção da base de dados! Ou fornecedor tem encomendas");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            baseDados.Desligar();
        }
        return false; // Retorna false se nenhuma linha foi afetada (remoção falhou)
    }

    /**
     * Atualiza um fornecedor na base de dados.
     *
     * @param fornecedor O fornecedor a ser atualizado.
     * @return O fornecedor atualizado, ou null se ocorrer um erro durante a operação.
     * @throws IOException Se ocorrer um erro durante a operação.
     */
    public Fornecedor atualizarFornecedorNaBaseDeDados(BaseDados baseDados, Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException {
        try {

            baseDados.Ligar();

            String queryFornecedor = """
                    UPDATE Fornecedor SET 
                       nome = ?,
                       id_externo = ?, 
                       morada1 = ?, 
                       morada2 = ?, 
                       localidade = ?, 
                       codigopostal = ?, 
                       id_pais = ? 
                    WHERE id_Utilizador = ?
                    """;

            // Atualizar Fornecedor


            PreparedStatement preparedStatementFornecedor = baseDados.getConexao().prepareStatement(queryFornecedor);
            preparedStatementFornecedor.setString(1, fornecedor.getNome());
            preparedStatementFornecedor.setString(2, fornecedor.getIdExterno());
            preparedStatementFornecedor.setString(3, fornecedor.getMorada1());
            preparedStatementFornecedor.setString(4, fornecedor.getMorada2());
            preparedStatementFornecedor.setString(5, fornecedor.getLocalidade());
            preparedStatementFornecedor.setString(6, fornecedor.getCodigoPostal());
            preparedStatementFornecedor.setInt(7, pais.getId());
            preparedStatementFornecedor.setInt(8, fornecedor.getIdUtilizador().getId());

            // Executar a atualização do Fornecedor
            int linhasAfetadasFornecedor = preparedStatementFornecedor.executeUpdate();

            // Atualizar Utilizador
            String queryUtilizador = """
                    UPDATE Utilizador SET 
                      id_role = ?, 
                      username = ?,
                      password = ?
                    WHERE id_util = ?               
                    """;

            PreparedStatement preparedStatementUtilizador = baseDados.getConexao().prepareStatement(queryUtilizador);
            preparedStatementUtilizador.setInt(1, utilizador.getTipo().getValue());
            preparedStatementUtilizador.setString(2, fornecedor.getIdUtilizador().getEmail());
            preparedStatementUtilizador.setString(3, fornecedor.getIdUtilizador().getPassword());
            preparedStatementUtilizador.setInt(4, fornecedor.getIdUtilizador().getId());

            // Executar a atualização do Utilizador
            int linhasAfetadasUtilizador = preparedStatementUtilizador.executeUpdate();

            baseDados.Desligar();

            // Verificar se ambas as atualizações foram bem-sucedidas
            if (linhasAfetadasFornecedor > 0 && linhasAfetadasUtilizador > 0) {
                return fornecedor; // Retorna o fornecedor atualizado
            } else {
                Mensagens.Erro("Erro!", "Erro ao atualizar fornecedor!");
            }
        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar fornecedor!");
        } finally {
            baseDados.Desligar();
        }
        return null;
    }


    /**
     * Obtém o nome do fornecedor associado a um Id_Externo.
     *
     * @param baseDados A instância da classe BaseDados.
     * @param idExterno O Id_Externo do fornecedor.
     * @return O nome do fornecedor associado ao Id_Externo, ou null se não encontrado.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public String obterNomeFornecedorPorIdExterno(BaseDados baseDados, String idExterno) throws IOException {
        try {

            baseDados.Ligar();

            String query = """
                    SELECT Nome FROM Fornecedor WHERE Id_Externo = ?
                    """;

            PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);
            preparedStatement.setString(1, idExterno);

            ResultSet resultado = preparedStatement.executeQuery();

            if (resultado.next()) {
                return resultado.getString("Nome");
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        } finally {
            baseDados.Desligar();
        }

        return null; // Retorna null se não encontrado
    }


    /**
     * Lê informações sobre a dívida dos fornecedores na base de dados.
     *
     * @param baseDados A instância da classe BaseDados para conexão com o banco de dados.
     * @return Uma lista observável de objetos ContaCorrente representando a dívida dos fornecedores.
     * @throws IOException Se ocorrer um erro durante a leitura da base de dados.
     */
    public ObservableList<ContaCorrente> lerDividaFornecedores(BaseDados baseDados) throws IOException {
        ObservableList<ContaCorrente> contas = FXCollections.observableArrayList();
        try {
            baseDados.Ligar();

            // Complete a string da query SQL
            String query = "SELECT Conta_Corrente.Id as id, " +
                    "Fornecedor.Id as id_interno, " +
                    "Fornecedor.Nome as nome_fornecedor, " +
                    "Fornecedor.Id_Externo as id_externo, " +
                    "Fornecedor.Morada1 as morada1, " +
                    "Fornecedor.Morada2 as morada2, " +
                    "Fornecedor.Localidade as localidade, " +
                    "Fornecedor.CodigoPostal as codigo_postal, " +
                    "Conta_Corrente.Saldo as saldo, " +
                    "Pais.Nome as nome_pais, " +
                    "Pais.Moeda as moeda_pais " +
                    "FROM Conta_Corrente " +
                    "INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Conta_Corrente.Id_Fornecedor " +
                    "INNER JOIN Pais ON Fornecedor.Id_Pais = Pais.Id";

            PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query);
            ResultSet resultado = preparedStatement.executeQuery();


            while (resultado.next()) {
                ContaCorrente contaCorrente = criarObjetoDivida(resultado);
                contas.add(contaCorrente);
            }

            baseDados.Desligar();
        } catch (Exception e) {
            Mensagens.Erro("Erro!!", "Erro ao ler tabela!");
        } finally {
            baseDados.Desligar();
        }
        return contas;
    }

    /**
     * Cria um objeto ContaCorrente a partir dos dados de um ResultSet representando a dívida de um fornecedor.
     *
     * @param dados Resultado da consulta que contém os dados da dívida do fornecedor.
     * @return Um objeto ContaCorrente com as informações obtidas do ResultSet.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private ContaCorrente criarObjetoDivida(ResultSet dados) throws SQLException {
        Pais pais = new Pais(
                dados.getString("nome_pais"),
                dados.getString("moeda_pais")
        );

        Fornecedor fornecedor = new Fornecedor(
                dados.getInt("id_interno"),
                dados.getString("nome_fornecedor"),
                dados.getString("id_externo"),
                dados.getString("morada1"),
                dados.getString("morada2"),
                dados.getString("localidade"),
                dados.getString("codigo_postal"),
                pais
        );

        return new ContaCorrente(
                dados.getInt("id"),
                fornecedor,
                dados.getDouble("saldo")
        );
    }
    public List<Fornecedor> obterFornecedoresPorProduto(BaseDados baseDados, int idProduto) throws IOException {
        List<Fornecedor> fornecedores = new ArrayList<>();

        try {
            baseDados.Ligar();

            String query = "SELECT Fornecedor.* " +
                    "FROM Fornecedor " +
                    "INNER JOIN Produtos ON Fornecedor.id = Produto.id_fornecedor " +
                    "WHERE Produtos.id = ?";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, idProduto); // Bind do parâmetro de forma segura

                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    while (resultado.next()) {
                        Fornecedor fornecedor = criarObjetoFornecedor(resultado);
                        fornecedores.add(fornecedor);
                    }
                }
            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            baseDados.Desligar();
        }

        return fornecedores;
    }

}