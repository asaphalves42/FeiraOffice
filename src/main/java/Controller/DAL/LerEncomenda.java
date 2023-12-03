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
import java.util.List;

public class LerEncomenda {

    LerFornecedores lerFornecedores = new LerFornecedores();
    LerPaises lerPaises = new LerPaises();
    BaseDados baseDados = new BaseDados();

    public ObservableList<LinhaEncomenda> lerLinhaEncomenda(BaseDados baseDados, int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

            // Utilizando JOIN para obter todas as informações necessárias em uma única consulta
            String query = "SELECT LE.*, " +
                    "P.Id_Fornecedor, P.Descricao AS DescricaoProduto, P.IdExterno AS IdExternoProduto, P.Estado AS EstadoProduto, " +
                    "U.Descricao AS DescricaoUnidade, " +
                    "PA.Nome AS NomePais " +
                    "FROM Linha_Encomenda LE " +
                    "JOIN Produto P ON LE.Id_Produto = P.Id " +
                    "JOIN Unidade U ON LE.Id_Unidade = U.Id " +
                    "JOIN Pais PA ON LE.Id_Pais_Taxa = PA.Id " +
                    "WHERE LE.Id_Encomenda = " + idEncomenda;

            ResultSet resultado = baseDados.Selecao(query);

            while (resultado.next()) {
                LinhaEncomenda linhaEncomenda = criarObjetoLinha(resultado);
                linhasEncomenda.add(linhaEncomenda);
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return linhasEncomenda;
    }

    private LinhaEncomenda criarObjetoLinha(ResultSet dados) throws IOException, SQLException {

        Unidade unidade = new Unidade(
                dados.getInt("Id"),
                dados.getString("DescricaoUnidade")

        );

        Pais pais = new Pais(
                dados.getInt("Id"),
                dados.getString("NomePais")
        );

        Produto produtoEncontrado = new Produto(
                dados.getString("Id"),
                null,
                dados.getString("DescricaoProduto"),
                dados.getString("IdExternoProduto"),
                unidade,
                dados.getInt("EstadoProduto")
        );

        Encomenda encomenda = new Encomenda(
                dados.getInt("Id_Encomenda")

        );

        return new LinhaEncomenda(
                dados.getInt("Id"),
                encomenda,
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

        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(baseDados, dados.getString("Id_fornecedor"));
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
        //Verificar se o produto já eiste na tabela antes de o inserir
        if (!produtoExisteNaTabela(baseDados, produto.getId())) {
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
    }
    private boolean produtoExisteNaTabela(BaseDados baseDados, String Id) {
        try {
            ResultSet resultado = baseDados.Selecao("SELECT Id FROM Produto WHERE Id = '" + Id + "'");
            return resultado.next(); // Retorna true se o produto já existir na tabela
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        Pais idPais = lerPaises.obterPaisPorId(baseDados, encomenda.getPais().getId());

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

    public List<LinhaEncomenda> lerLinhasParaAprovacao(BaseDados baseDados, int idEncomenda) throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            baseDados.Ligar();

            // Utilizando JOIN para obter todas as informações necessárias em uma única consulta
            String query = "SELECT " +
                    "Linha_Encomenda.Id_Produto AS id_produto, " +
                    "Produto.Descricao AS descricao_produto, " +
                    "Linha_Encomenda.Id_Unidade AS id_unidade, " +
                    "Unidade.Descricao AS descricao_unidade, " +
                    "Linha_Encomenda.Quantidade AS quantidade " +
                    "FROM Linha_Encomenda " +
                    "INNER JOIN Produto ON Produto.Id = Linha_Encomenda.Id_Produto " +
                    "INNER JOIN Unidade ON Unidade.Id = Produto.Id_Unidade " +
                    "WHERE Linha_Encomenda.Id_Encomenda = " + idEncomenda;

            ResultSet resultado = baseDados.Selecao(query);

            while (resultado.next()) {
                LinhaEncomenda linhaEncomenda = criarObjetoLinhaParaAprovacao(resultado);
                linhasEncomenda.add(linhaEncomenda);
            }

            baseDados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return linhasEncomenda;
    }

    private LinhaEncomenda criarObjetoLinhaParaAprovacao(ResultSet dados) throws SQLException {

        Unidade unidade = new Unidade(
                dados.getInt("id_unidade"),
                dados.getString("descricao_unidade")
        );

        Produto produtoEncontrado = new Produto(
                dados.getString("id_produto"),
                dados.getString("descricao_produto"),
                unidade
        );

        return new LinhaEncomenda(
                produtoEncontrado,
                dados.getDouble("Quantidade")
        );
    }

    public boolean atualizarStock(BaseDados baseDados, String idProduto, int idUnidade, double quantidade) throws IOException {
        try {
            baseDados.Ligar();

            // Se existir, dá um update apenas na quantidade, soma a que tem na tabela mais a nova quantidade
            String script;
            if (produtoExiste(baseDados, idProduto)) {
                script = "UPDATE Stock SET Id_Unidade = " + idUnidade + ", Quantidade = Quantidade + " + quantidade +
                        " WHERE Id_Produto = '" + idProduto + "'";
            } else {
                // Senão, insere o produto
                script = "INSERT INTO Stock (Id_Produto, Id_Unidade, Quantidade) " +
                        "VALUES ('" + idProduto + "', " + idUnidade + ", " + quantidade + ")";
            }

            // Execute o script
            baseDados.Executar(script);

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao adicionar/atualizar stock!");
            return false;
        } finally {
            baseDados.Desligar();
        }
        return true;
    }

    private boolean produtoExiste(BaseDados baseDados, String idProduto) throws SQLException {
        String query = "SELECT * FROM Stock WHERE Id_Produto = '" + idProduto + "'";
        ResultSet resultSet = baseDados.Selecao(query);
        return resultSet.next();
    }
    public boolean atualizarEstadoEncomenda(BaseDados baseDados, int idEncomenda) throws IOException {
        try {
            baseDados.Ligar();

            String query = "UPDATE Encomenda SET Estado = 1 WHERE Id = " + idEncomenda;

            baseDados.Executar(query);

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar a encomenda!");
        } finally {
            baseDados.Desligar();
        }

        return false;
    }

    public boolean actualizarEstadoEncomendaRecusada(BaseDados baseDados, int idEncomenda) throws IOException {
        try {
            baseDados.Ligar();

            String query = "UPDATE Encomenda Set Estado = 2 WHERE Id = " + idEncomenda;

            baseDados.Executar(query);
            return true;
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao atualizar encomenda!");
            baseDados.Desligar();
        }
        return false;

    }

    public boolean atualizarSaldoDevedores(BaseDados baseDados, double valorEncomenda, String idFornecedor) throws IOException {
        try {
            baseDados.Ligar();

            // Verificar se já existe um registro para o fornecedor
            String verificaExistencia = "SELECT * FROM Conta_Corrente WHERE Id_Fornecedor = '" + idFornecedor + "'";
            ResultSet resultSet = baseDados.Selecao(verificaExistencia);

            String script;
            if (resultSet.next()) {
                // Se existir, faz o UPDATE
                script = "UPDATE Conta_Corrente SET Saldo = Saldo + " + valorEncomenda + " WHERE Id_Fornecedor = '" + idFornecedor + "'";
            } else {
                // Se não existir, faz a inserção
                script = "INSERT INTO Conta_Corrente (Id_Fornecedor, Saldo) VALUES ('" + idFornecedor + "', " + valorEncomenda + ")";
            }

            baseDados.Executar(script);

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Error", "Ocorreu um erro ao atualizar o saldo devedor");
            return false; // Retorna false em caso de erro
        } finally {
            baseDados.Desligar();
        }
    }

}


