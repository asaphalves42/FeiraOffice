package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LerFornecedor {

    ArrayList<Fornecedor> fornecedores = new ArrayList<>();

    public boolean lerFornecedoresDaBaseDeDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Fornecedor");

            while (resultado.next()) {
                Fornecedor aux;
                int idRole = resultado.getInt("id_Utilizador");
                int idPais = resultado.getInt("id_Pais");
                String id = resultado.getString("Id");
                String nome = resultado.getString("Nome");
                String morada = resultado.getString("Morada");
                String NIF = resultado.getString("NIF");
                String telefone = resultado.getString("Telefone");


                    Pais pais = new Pais(idPais, nome);
                    UtilizadorFornecedor utilizadorFornecedor = new UtilizadorFornecedor(idRole);
                    aux = new Fornecedor(
                            id,
                            utilizadorFornecedor,
                            nome,
                            morada,
                            NIF,
                            telefone,
                            pais
                    );
                    fornecedores.add(aux);

            }
            basedados.Desligar();
            return true; // A leitura foi bem-sucedida, retorna true.
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // A leitura falhou, retorna false.
        }
    }
}