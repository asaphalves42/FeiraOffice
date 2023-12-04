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
    BaseDados baseDados = new BaseDados();
    LerFornecedores lerFornecedores = new LerFornecedores();
    LerUnidade lerUnidade = new LerUnidade();

    /**
     * Lê os produtos da base de dados e os retorna como uma lista observável.
     *
     * @param baseDados A instância da base de dados.
     * @return Uma lista observável de produtos lidos da base de dados.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public ObservableList<Produto> lerProdutosBaseDados(BaseDados baseDados) throws IOException {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        Produto produto = null;

        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Produto");

            while (resultado.next()) {
                produto = criarObjeto(resultado);
                produtos.add(produto);
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
            return null; // A leitura falhou, retorna false.
        }
        return produtos;
    }

    /**
     * Cria um objeto Produto a partir dos dados do ResultSet.
     *
     * @param dados O ResultSet contendo os dados do produto.
     * @return Um objeto Produto criado a partir dos dados do ResultSet.
     * @throws IOException Se ocorrer um erro durante a leitura.
     * @throws SQLException Se ocorrer um erro ao acessar os dados no ResultSet.
     */
    private Produto criarObjeto(ResultSet dados) throws IOException, SQLException {
        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(baseDados, dados.getString("Id_Fornecedor"));
        Unidade unidade = lerUnidade.obterUnidadePorIdBaseDados(baseDados, dados.getInt("Id_Unidade"));

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
