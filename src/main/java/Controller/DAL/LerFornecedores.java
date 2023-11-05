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
    public static ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();

    public boolean lerFornecedoresDaBaseDeDados() throws IOException {

        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Fornecedor");


            while (resultado.next()) {

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
            return true; // A leitura foi bem-sucedida, retorna true.
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return false; // A leitura falhou, retorna false.
        }
    }

    public boolean adicionarFornecedorBaseDeDados(Fornecedor fornecedor, Pais pais, UtilizadorFornecedor utilizador) throws IOException {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            String query = "INSERT INTO Fornecedor (Nome, Morada1, Morada2, Localidade, CodigoPostal, Id_Pais, Id_Utilizador) " +
                    "VALUES ('" + fornecedor.getNome() + "', '" + fornecedor.getMorada1() + "', '" + fornecedor.getMorada2() + "', " +
                    "'" + fornecedor.getLocalidade() + "', '" + fornecedor.getCodigoPostal() + "', " +
                    pais.getId() + ", " + utilizador.getId() + ")";

            baseDados.Executar(query);

            baseDados.Desligar();

            return true; // Sucesso ao adicionar o fornecedor

        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição na base de dados!");
        }
        return false;
    }

}

