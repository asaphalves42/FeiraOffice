package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Fornecedor");


            while (resultado.next()) { //Ler os forncedores da base de dados, um a um e cria um objeto novo
                fornecedor = criarObjetoFornecedor(resultado);

                fornecedores.add(fornecedor);

            }

            baseDados.Desligar();
            return fornecedores; // A leitura foi bem-sucedida
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        }
    }

    /**
     * Cria um objeto Fornecedor a partir dos dados de um ResultSet.
     *
     * @param dados Resultado da consulta que contém os dados do fornecedor.
     * @return Um objeto Fornecedor com as informações obtidas do ResultSet.
     * @throws IOException   Se ocorrer um erro durante a obtenção de informações adicionais.
     * @throws SQLException  Se ocorrer um erro ao acessar os dados do ResultSet.
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
     * @param baseDados     A instância da classe BaseDados para conexão com o banco de dados.
     * @param idFornecedor  O identificador externo do fornecedor a ser obtido.
     * @return Um objeto Fornecedor correspondente ao identificador externo fornecido, ou null se não encontrado.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public Fornecedor obterFornecedorPorId(BaseDados baseDados, String idFornecedor) throws IOException {
        Fornecedor fornecedor = null;
        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Fornecedor WHERE Id_Externo = '" + idFornecedor + "'");

            if (resultado.next()) {
                fornecedor = criarObjetoFornecedor(resultado);

            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
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
            da função 'MAX' do SQL, quando obtido o ID, insiro o mesmo na tabela de fornecedor.
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

            baseDados.Desligar();

            return fornecedor; // retorna o fornecedor

        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
        }
        return null;
    }

    /**
     * Remove um fornecedor da base de dados com base no ID fornecido.
     *
     * @param fornecedorId O ID do fornecedor a ser removido.
     * @return true se a remoção for bem-sucedida, false caso contrário.
     * @throws SQLException Se ocorrer um erro ao interagir com a base de dados.
     */
    public boolean removerFornecedorDaBaseDeDados(BaseDados baseDados, int fornecedorId) throws SQLException {
        try {

            baseDados.Ligar();

            String query = ("DELETE FROM Fornecedor WHERE id = " + fornecedorId);

            boolean linhasAfetadas = baseDados.Executar(query);

            baseDados.Desligar();

            if (linhasAfetadas) {
                return true; // Retorna true se alguma linha foi afetada (remoção bem-sucedida)
            }

        } catch (Exception e) {
            try {
                Mensagens.Erro("Erro na remoção!", "Erro na remoção da base de dados! Ou fornecedor tem encomendas");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return false; // Retorna false se alguma linha não foi afetada (remoção falhou)
        }
        return false;
    }


    /**
     * Atualiza um fornecedor na base de dados.
     *
     * @param fornecedor O fornecedor a ser atualizado.
     * @return O fornecedor atualizado, ou null se ocorrer um erro durante a operação.
     * @throws IOException Se ocorrer um erro durante a operação.
     */
    public Fornecedor atualizarFornecedorNaBaseDeDados(BaseDados baseDados, Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException {

        baseDados.Ligar();

        String query = "UPDATE Fornecedor SET " +
                "nome = '" + fornecedor.getNome() + "', " +
                "id_externo = '" + fornecedor.getIdExterno() + "', " +
                "morada1 = '" + fornecedor.getMorada1() + "', " +
                "morada2 = '" + fornecedor.getMorada2() + "', " +
                "localidade = '" + fornecedor.getLocalidade() + "', " +
                "codigopostal = '" + fornecedor.getCodigoPostal() + "', " +
                "id_pais = '" + pais.getId() + "' " +
                "WHERE id_Utilizador = " + fornecedor.getIdUtilizador().getId();

        String query2 = "UPDATE Utilizador SET " +
                "id_role = '" + utilizador.getTipo().getValue() + "', " +
                "username = '" + fornecedor.getIdUtilizador().getEmail() + "', " +
                "password = '" + fornecedor.getIdUtilizador().getPassword() + "' " +
                "WHERE id_util = " + fornecedor.getIdUtilizador().getId();

        try {



            boolean sucesso1 = baseDados.Executar(query);
            boolean sucesso2 = baseDados.Executar(query2);

            baseDados.Desligar();

            if (sucesso1 && sucesso2) {
                return fornecedor; // Retorna o fornecedor atualizado
            } else {
                throw new IOException("Erro na atualização na base de dados!");

            }
        } catch (Exception e) {

            throw new IOException("Erro na atualização na base de dados!");
        }
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
            ResultSet resultado = baseDados.Selecao("SELECT Nome FROM Fornecedor WHERE Id_Externo = '" + idExterno + "'");

            if (resultado.next()) {
                String nomeFornecedor = resultado.getString("Nome");
                return nomeFornecedor;
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
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
                    "Fornecedor.Id_Externo as id_fornecedor, " +
                    "Fornecedor.Nome as nome_fornecedor, " +
                    "Conta_Corrente.Saldo as saldo " +
                    "FROM Conta_Corrente " +
                    "INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Conta_Corrente.Id_Fornecedor";

            ResultSet resultado = baseDados.Selecao(query);
            // Processar os resultados do ResultSet
            while (resultado.next()) {
                ContaCorrente contaCorrente = criarObjetoDivida(resultado);
                contas.add(contaCorrente);
            }

            baseDados.Desligar();
        } catch (Exception e) {
            Mensagens.Erro("Erro!!", "Erro ao ler tabela!");
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
        Fornecedor fornecedor = new Fornecedor(
                dados.getString("id_fornecedor"),
                dados.getString("nome_fornecedor")
        );

        return new ContaCorrente(
                dados.getInt("id"),
                fornecedor,
                dados.getDouble("saldo")
        );
    }

}