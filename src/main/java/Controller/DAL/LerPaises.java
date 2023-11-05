package Controller.DAL;

import Model.Pais;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerPaises {

    public ObservableList<Pais> getListaDePaises() throws IOException {
        ObservableList<Pais> listaDePaises = FXCollections.observableArrayList();
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Pais");

            while (resultado.next()) {
                Pais pais = new Pais(
                        resultado.getInt("Id"),
                        resultado.getString("Nome")
                );

                listaDePaises.add(pais);
            }
            basedados.Desligar();

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return listaDePaises;
    }

    public Pais obterPaisPorId(int id) throws IOException {
        Pais pais = null;
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Pais WHERE id = " + id);

            if (resultado.next()) {
                pais = new Pais(
                        resultado.getInt("Id"),
                        resultado.getString("Nome")
                );
            }
            basedados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return pais;
    }
}
