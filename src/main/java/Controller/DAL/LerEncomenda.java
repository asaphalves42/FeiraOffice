package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerEncomenda {
    LerUnidade lerUnidade = new LerUnidade();
    LerProdutos lerProduto = new LerProdutos();
    LerFornecedores lerFornecedores = new LerFornecedores();
    LerPaises lerPaises = new LerPaises();
    BaseDados baseDados = new BaseDados();

    public ObservableList<LinhaEncomenda> lerLinhaEncomendaBaseDados(BaseDados baseDados, int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        LinhaEncomenda linhaEncomenda = null;
        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Linha_Encomenda WHERE Id_Encomenda = " + idEncomenda);

            while (resultado.next()) {
                linhaEncomenda = criarObjetoLinha(resultado);
                linhasEncomenda.add(linhaEncomenda);
            }

            baseDados.Desligar();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return linhasEncomenda;
    }

    private LinhaEncomenda criarObjetoLinha(ResultSet dados) throws IOException, SQLException {
        Encomenda idEncomenda = obterEncomendaPorId(baseDados, dados.getString("Id_Encomenda"));

        Produto produtoEncontrado = null;

        for (Produto produto : lerProduto.lerProdutosBaseDados(baseDados)) {
            if (produto.getId().equals(dados.getString("Id_Produto"))) {
                produtoEncontrado = produto;
            }
        }

        Unidade unidade = lerUnidade.obterUnidadePorIdBaseDados(baseDados, dados.getInt("Id_Unidade"));

        Pais pais = lerPaises.obterPaisPorId(baseDados, dados.getInt("Id_Pais_Taxa"));


        return new LinhaEncomenda(
                dados.getInt("Id"),
                idEncomenda,
                dados.getInt("Sequencia"),
                produtoEncontrado,
                dados.getDouble("Preco_Unitario"),
                dados.getDouble("Quantidade"),
                unidade,
                pais,
                dados.getDouble("Total_Taxa"),
                dados.getDouble("Total_Incidencia")
        );
    }

    public ObservableList<Encomenda> lerEncomendaDaBaseDeDados(BaseDados baseDados) throws IOException {
        ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda WHERE Estado = 0");

            while (resultado.next()) {
                Encomenda encomenda = criarObjetoEncomenda(resultado);
                encomendas.add(encomenda);
            }

            baseDados.Desligar();

            return encomendas;

        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            return null; // ou lançar uma exceção, dependendo do comportamento desejado
        }
    }

    private Encomenda criarObjetoEncomenda(ResultSet dados) throws IOException, SQLException {

        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(dados.getString("Id_fornecedor"));


        Pais pais = lerPaises.obterPaisPorId(baseDados, dados.getInt("Id_Pais"));


        return new Encomenda(
                dados.getInt("Id"),
                dados.getString("Referencia"),
                dados.getDate("Data").toLocalDate(),
                fornecedor,
                pais,
                dados.getDouble("Total_Taxa"),
                dados.getDouble("Total_Incidencia"),
                dados.getDouble("Total"),
                dados.getInt("Estado")
        );
    }

    public int adicionarEncomendaBaseDeDados(BaseDados baseDados, Encomenda encomenda) throws IOException {
        try {
            baseDados.Ligar();

            String queryEncomenda = getQueryEncomenda(encomenda);

            int Id_Encomenda = baseDados.ExecutarPreparementStatement(queryEncomenda);


            // Inserir produtos associados à encomenda na tabela Produto
            for (LinhaEncomenda linha : encomenda.getLinhas()) {

                //lerProdutos.lerProdutoPorId ja existe, nao grava
                inserirProdutoNaTabelaProduto(baseDados, linha.getProduto());
            }

            // Inserir na tabela Linha_Encomenda
            for (LinhaEncomenda linha : encomenda.getLinhas()) {
                inserirLinhaEncomenda(baseDados, Id_Encomenda, linha);
            }

            baseDados.Desligar();

            return Id_Encomenda;
        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição da base de dados!");
        }

        return 0;
    }

    private void inserirProdutoNaTabelaProduto(BaseDados baseDados, Produto produto) {
        // Construa a string da consulta SQL, escapando os valores
        String queryProduto = "INSERT INTO Produto (Id, Id_Fornecedor, Descricao, Id_Unidade, IdExterno, Estado) " +
                "VALUES (" +
                "'" + produto.getId() + "'," +
                "'" + produto.getFornecedor().getIdExterno() + "'," +
                "'" + produto.getDescricao() + "'," +
                produto.getUnidade().getId() + "," +
                "'" + produto.getIdExterno() + "'," +
                "0" +
                ")";

        baseDados.Executar(queryProduto);
    }

    private void inserirLinhaEncomenda(BaseDados baseDados, int Id_Encomenda, LinhaEncomenda linha) {
        // Construa a string da consulta SQL, escapando os valores
        String queryLinha = "INSERT INTO Linha_Encomenda (Id_Encomenda, Sequencia, Id_Produto, Preco_Unitario, Quantidade, Id_Unidade," +
                " Id_Pais_Taxa, Total_Taxa, Total_Incidencia, Total_Linha) " +
                "VALUES (" +
                Id_Encomenda + "," +
                "'" + linha.getSequencia() + "'," +
                "'" + linha.getProduto().getId() + "'," +
                linha.getPreco() + "," +
                linha.getQuantidade() + "," +
                linha.getUnidade().getId() + "," +
                linha.getTaxa().getId() + "," +
                linha.getTotalTaxa() + "," +
                linha.getTotalIncidencia() + "," +
                linha.getTotal() +
                ")";

        baseDados.Executar(queryLinha);
    }

    @NotNull
    private String getQueryEncomenda(Encomenda encomenda) throws IOException {

        Pais idPais = lerPaises.obterPaisPorId(baseDados,encomenda.getPais().getId());

        //gravar encomenda
        String referencia = "'" + encomenda.getReferencia() + "'";
        String data = "'" + encomenda.getData() + "'";
        String idFornecedor = "'" + encomenda.getFornecedor().getIdExterno() + "'";
        int idPaisInt = idPais.getId();

        // Construa a string da consulta SQL, escapando os valores
        return "INSERT INTO Encomenda (Referencia, Data, Id_Fornecedor, Id_Pais, Total_Taxa, Total_Incidencia, Total, Estado) " +
                "VALUES (" +
                referencia + "," +
                data + "," +
                idFornecedor + "," +
                idPaisInt + "," +
                encomenda.getTotalTaxa() + "," +
                encomenda.getTotalIncidencia() + "," +
                encomenda.getTotal() + ", 0)";
    }

    public Encomenda obterEncomendaPorReferencia(BaseDados baseDados, String referencia) throws IOException {
        Encomenda encomenda = null;
        try {
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda WHERE Referencia = " + referencia);

            if (resultado.next()) {
                encomenda = criarObjetoEncomenda(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return encomenda;
    }

    public Encomenda obterEncomendaPorId(BaseDados baseDados, String id) throws IOException {
        Encomenda encomenda = null;
        try {

            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda WHERE Id = " + id);

            if (resultado.next()) {
                encomenda = criarObjetoEncomenda(resultado);
            }
            baseDados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return encomenda;
    }
}
