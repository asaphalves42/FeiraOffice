package Controller.DAL;

import Model.Encomenda;
import Model.Fornecedor;
import Model.Produto;
import Model.Unidade;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerProdutos {
    public ObservableList<Produto> lerProdutosBaseDados() throws IOException {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        Produto produto = null;

        try {

            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Produto");

            while (resultado.next()) {
                produto = criarObjeto(resultado);

            }
            produtos.add(produto);
            basedados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        }
        return produtos;
    }

    private Produto criarObjeto(ResultSet dados) throws IOException, SQLException {
        LerFornecedores lerFornecedores = new LerFornecedores();
        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(dados.getString("Id_Fornecedor"));

        LerUnidade lerUnidade = new LerUnidade();
        Unidade unidade = lerUnidade.obterUnidadePorIdBaseDados(dados.getInt("Id_Unidade"));

        return new Produto(
                dados.getString("Id"),
                fornecedor,
                dados.getString("IdExterno"),
                dados.getString("Descricao"),
                unidade,
                dados.getInt("estado")
        );
    }
}
