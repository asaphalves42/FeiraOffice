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

    /**
     * Lê a lista de fornecedores a partir da base de dados e retorna uma lista observável de fornecedores.
     *
     * @return Uma ObservableList contendo os fornecedores lidos da base de dados, ou null se ocorrer um erro na leitura.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Fornecedor> lerFornecedoresDaBaseDeDados() throws IOException {

        ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();

        try {

            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Fornecedor");


            while (resultado.next()) { //Ler os forncedores da base de dados, um a um e cria um objeto novo

                int idPais = resultado.getInt("Id_Pais");
                int idUtilizador = resultado.getInt("Id_Utilizador");

                LerPaises lerPaises = new LerPaises();
                Pais pais = lerPaises.obterPaisPorId(idPais);

                LerUtilizadores lerUtilizores = new LerUtilizadores();
                UtilizadorFornecedor utilizador = lerUtilizores.obterUtilizadorPorIdFornecedor(idUtilizador);

                Fornecedor aux = new Fornecedor(
                        resultado.getInt("id"),
                        resultado.getString("Nome"),
                        resultado.getString("Id_Externo"),
                        resultado.getString("Morada1"),
                        resultado.getString("Morada2"),
                        resultado.getString("Localidade"),
                        resultado.getString("CodigoPostal"),
                        pais,
                        utilizador
                );
                fornecedores.add(aux);

            }
            basedados.Desligar();
            return fornecedores; // A leitura foi bem-sucedida
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        }
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
    public Fornecedor adicionarFornecedorBaseDeDados(Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException {

        try {
            BaseDados baseDados = new BaseDados();
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
    public boolean removerFornecedorDaBaseDeDados(int fornecedorId) throws SQLException {
        try {
            BaseDados baseDados = new BaseDados();
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
    public Fornecedor atualizarFornecedorNaBaseDeDados(Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException {

        BaseDados baseDados = new BaseDados();
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
            e.printStackTrace();
            throw new IOException("Erro na atualização na base de dados!");
        }
    }


}

