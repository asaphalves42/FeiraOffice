package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerFornecedores {
    BaseDados baseDados = new BaseDados();

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
               fornecedor = criarObjeto(resultado);

                fornecedores.add(fornecedor);

            }

            baseDados.Desligar();
            return fornecedores; // A leitura foi bem-sucedida
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        }
    }

    public Fornecedor obterFornecedorPorId(BaseDados baseDados, String idFornecedor) throws IOException {
        Fornecedor fornecedor = null;
        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Fornecedor WHERE Id_Externo = '" + idFornecedor + "'");

            if (resultado.next()) {
                fornecedor = criarObjeto(resultado);

            }
            baseDados.Desligar();
    } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return fornecedor;
    }

    private Fornecedor criarObjeto(ResultSet dados) throws IOException, SQLException {
        int idPais = dados.getInt("Id_Pais");
        int idUtilizador = dados.getInt("Id_Utilizador");

        LerPaises lerPaises = new LerPaises();
        Pais pais = lerPaises.obterPaisPorId(baseDados,idPais);

        LerUtilizadores lerUtilizores = new LerUtilizadores();
        UtilizadorFornecedor utilizador = lerUtilizores.obterUtilizadorPorIdFornecedor(baseDados,idUtilizador);

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
     * Adiciona um fornecedor à base de dados, com informações de país e utilizador, e retorna o fornecedor adicionado.
     *
     * @param fornecedor O fornecedor a ser adicionado à base de dados.
     * @param pais O país associado ao fornecedor.
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
                Mensagens.Erro("Erro na remoção!", "Erro na remoção da base de dados!");
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
            System.out.println( "Query 1 "+ query);
            System.out.println("Query2"+ query2);



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


}

