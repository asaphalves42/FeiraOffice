package Controller.DAL;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LerEncomenda {

    /*
     public ObservableList<LinhaEncomenda> lerLinhaEncomendaBaseDados() throws IOException {
        ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();
            ResultSet resultado = baseDados.Selecao("SELECT * FROM Linha_Encomenda");

            LerProdutos lerProdutos = new LerProdutos();
            Produto produto = lerProdutos.obterProdutoPorId(resultado.getInt("id"));

            LerUnidade lerUnidade = new LerUnidade();
            Unidade unidade = lerUnidade.obterUnidadePorIdBaseDados(resultado.getInt("Id_Unidade"));

            LerPaises lerPaises = new LerPaises();
            Pais pais = lerPaises.obterPaisPorId(resultado.getInt("Id_Pais_Taxa"));

            Encomenda encomenda = obterEncomendaPorId(resultado.getInt("Id"));




            while (resultado.next()) {

                LinhaEncomenda linhaEncomenda = new LinhaEncomenda(
                        resultado.getInt("Id"),
                        encomenda,
                        resultado.getInt("Sequencia"),
                        produto,
                        resultado.getDouble("Preco_Unitario"),
                        resultado.getInt("Quantidade"),
                        unidade,
                        pais,
                        resultado.getDouble("Total_Taxa"),
                        resultado.getDouble("Total_Incidencia"),
                        resultado.getDouble("Total_Linha")
                        );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        public ObservableList<Encomenda> lerEncomendaDaBaseDeDados () throws IOException {
            ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

            try {
                BaseDados baseDados = new BaseDados();
                baseDados.Ligar();
                ResultSet resultado = baseDados.Selecao("SELECT * FROM Encomenda");

                while (resultado.next()) {
                    String idUtilizador = resultado.getString("Id_Fornecedor");
                    int idPais = resultado.getInt("Id_pais");

                    LerFornecedores lerFornecedores = new LerFornecedores();
                    for (Fornecedor utilFornecedor : lerFornecedores.lerFornecedoresDaBaseDeDados()) {
                        if (utilFornecedor.getIdExterno().equals(idUtilizador)) {
                            LerPaises lerPaises = new LerPaises();
                            Pais pais = lerPaises.obterPaisPorId(idPais);

                            // Corrigindo a criação da data se estiver utilizando LocalDate
                            LocalDate data = resultado.getDate("Data").toLocalDate();

                            // Obtenha as linhas da encomenda chamando o método lerLinhaEncomendaBaseDados
                            ObservableList<LinhaEncomenda> linhasEncomenda = lerLinhaEncomendaBaseDados();

                            // Corrigindo a sintaxe do construtor da Encomenda e adicionando a data e as linhas
                            Encomenda aux = new Encomenda(
                                    resultado.getInt("Id"),
                                    resultado.getString("Referencia"),
                                    data, // Utilizando o LocalDate
                                    utilFornecedor,
                                    pais,
                                    linhasEncomenda
                            );
                            encomendas.add(aux);
                        }
                    }
                }

                baseDados.Desligar();
            } catch (SQLException e) {
                Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados");
            }

            return encomendas;
        }
    }

    private Encomenda criarObjeto(ResultSet dados) throws IOException, SQLException {
        LerFornecedores lerFornecedores = new LerFornecedores();
        Fornecedor fornecedor = lerFornecedores.obterFornecedorPorId(dados.getString("Id_fornecedor"));

        LerPaises lerPaises = new LerPaises();
        Pais pais = lerPaises.obterPaisPorId(dados.getInt("Id_Pais"));


        return new Encomenda(
                dados.getInt("Id"),
                dados.getString("Referencia"),
                dados.getDate("Data"),
                fornecedor,
                pais,
                dados.getDouble("Total_Taxa"),
                dados.getDouble("Total_Incidencia"),
                dados.getDouble("Total"),
                dados.getInt("Estado")
        );
    }

    public Encomenda obterEncomendaPorReferencia(String referencia) throws IOException {
        Encomenda encomenda = null;
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Encomenda WHERE Refencia = " + referencia);

            if (resultado.next()) {
                encomenda = criarObjeto(resultado);
            }
            basedados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return encomenda;
    }

    public Encomenda obterEncomendaPorId(int id) throws IOException {
        Encomenda encomenda = null;
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Encomenda WHERE Id = " + id);

            if (resultado.next()) {
                encomenda = criarObjeto(resultado);
            }
            basedados.Desligar();
        } catch (SQLException e) {
            Mensagens.Erro("Erro na leitura!", "Erro na leitura da base de dados!");
        }
        return encomenda;
    }
     */

    public int adicionarEncomendaBaseDeDados(Encomenda encomenda) throws IOException {

        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            //gravar encomenda
            String queryEncomenda = "INSERT INTO Encomenda (Id, Referencia, Data, Id_Fornecedor, Id_Pais, Total_Taxa, Total_Incidencia, Total, Estado) " +
                    "VALUES " +
                    "(" + encomenda.getId() + "," +
                    "'"+encomenda.getReferencia()+"'," +
                    "'"+encomenda.getData()+"'," +
                    "'"+encomenda.getFornecedor().getId()+"'," +
                    "'"+encomenda.getPais().getISO()+"'," +
                    encomenda.getTotalTaxa()+"," +
                    encomenda.getTotalIncidencia()+"," +
                    encomenda.getTotal()+"," +
                    "0" +
                    ")";

            int Id_Encomenda = baseDados.ExecutarInsert(queryEncomenda);

            for (LinhaEncomenda Linha: encomenda.getLinhas()) {

                String queryLinha = "INSERT INTO Linha_Encomenda (Id_Encomenda, Sequencia, Id_Produto, Preco_Unitario, Quantidade, Id_Unidade," +
                        " Id_Pais_Taxa, Total_Taxa, Total_Incidencia, Total_Linha) " +
                        "VALUES " +
                        "(" +
                        Id_Encomenda+"," +
                        "'"+Linha.getSequencia()+"'," +
                        "'"+Linha.getProduto().getId()+"'," +
                        Linha.getPreco()+"," +
                        Linha.getQuantidade()+"," +
                        "'"+Linha.getUnidade().getId()+"'," +
                        "'"+Linha.getTaxa().getISO()+"'," +
                        Linha.getTotalTaxa()+"," +
                        Linha.getTotalIncidencia()+"," +
                        Linha.getTotal() +
                        ")";

                baseDados.Executar(queryLinha);
            }
            baseDados.Desligar();

            return Id_Encomenda;
        } catch (Exception e) {
            Mensagens.Erro("Erro na base de dados!", "Erro na adição da bade de dados!");
        }
        return 0;
    }
}
