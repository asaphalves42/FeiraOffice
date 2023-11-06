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
    public  ObservableList<Fornecedor> lerFornecedoresDaBaseDeDados() throws IOException {

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
            return fornecedores; // A leitura foi bem-sucedida, retorna true.
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        }
    }

    /**
     * Adiciona um fornecedor à base de dados, juntamente com informações de país e utilizador, e retorna o fornecedor adicionado.
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

            String query = "exec [Inserir_Fornecedor] @username = '" + fornecedor.getIdUtilizador().getEmail() +
                    "', @password = '" + fornecedor.getIdUtilizador().getPassword() +
                    "', @id_role = '" + utilizador.getTipo().getValue() +
                    "', @nome = '" + fornecedor.getNome() +
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

}

