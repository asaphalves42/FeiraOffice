package DAL;

import Model.Encomenda;
import Model.FeiraOffice;
import Model.Pagamento;
import Model.Pais;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LerPagamento {
    public boolean inserirPagamentoNaBaseDados(BaseDados baseDados, Pagamento pagamento) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Inserir na tabela Pagamento
            String query = """
                INSERT INTO Pagamento (referencia, data, id_conta_corrente, id_feira_office)
                    VALUES (?,?,?,?)
                """;

            try (PreparedStatement ps = baseDados.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, pagamento.getReferencia());
                ps.setDate(2, java.sql.Date.valueOf(pagamento.getData()));
                ps.setInt(3, pagamento.getContaCorrente().getId());
                ps.setInt(4, pagamento.getFeiraOffice());

                ps.executeUpdate();

                // Obter o ID gerado na tabela Pagamento
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idPagamento = generatedKeys.getInt(1);

                    // Inserir na tabela Pagamento_Encomenda
                    String query2 = """
                        INSERT INTO Pagamento_Encomenda (id_encomenda, id_pagamento)
                        VALUES (?, ?)
                    """;

                    try (PreparedStatement ps2 = baseDados.getConexao().prepareStatement(query2)) {
                        for (Encomenda encomenda : pagamento.getEncomendas()) {
                            ps2.setInt(1, encomenda.getId());
                            ps2.setInt(2, idPagamento);
                            ps2.executeUpdate();
                        }
                    }


                } else {
                    throw new SQLException("Falha ao obter o ID gerado para Pagamento.");
                }
            }

            baseDados.commit(baseDados.getConexao());
            return true;

        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao adicionar pagamento à base de dados!");
            baseDados.rollback(baseDados.getConexao());
        } finally {
            baseDados.Desligar();
        }
        return false;
    }


    public FeiraOffice lerDadosDaEmpresa(BaseDados baseDados) throws SQLException, IOException {
        FeiraOffice feiraOffice = null;
        try {
            baseDados.Ligar();

            String query = """
                    SELECT
                        Feira_Office.id AS id,
                    	Feira_Office.nome AS nome,
                    	Feira_Office.morada AS morada,
                    	Feira_Office.localidade AS localidade,
                    	Feira_Office.codigo_postal AS codigo_postal,
                    	Pais.id AS id_pais,
                    	Pais.Nome AS nome_pais,
                    	Pais.ISO AS iso_pais,
                    	Feira_Office.iban AS iban,
                    	Feira_Office.bic AS bic
                                        
                    FROM Feira_Office
                    	INNER JOIN Pais ON Pais.id = Feira_Office.id_pais
                    """;
            try (PreparedStatement statement = baseDados.getConexao().prepareStatement(query)) {

                ResultSet resultado = statement.executeQuery();

                if (resultado.next()) {
                    feiraOffice = criarObjetoFeiraOffice(resultado);
                }

            }

        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao carregar dados bancários da Feira & Office");
        }finally {
            baseDados.Desligar();
        }
        return feiraOffice;
    }

    private FeiraOffice criarObjetoFeiraOffice(ResultSet dados) throws SQLException {
        Pais pais = new Pais(
                dados.getInt("id_pais"),
                dados.getString("nome_pais"),
                dados.getString("iso_pais")
        );
        return new FeiraOffice(
                dados.getInt("id"),
                dados.getString("nome"),
                dados.getString("morada"),
                dados.getString("localidade"),
                dados.getString("codigo_postal"),
                pais,
                dados.getString("iban"),
                dados.getString("bic")
        );
    }

    public boolean verificarReferencia(BaseDados baseDados, Pagamento pagamento) throws IOException {
        try {
            baseDados.Ligar();

            String query = """
                SELECT TOP 1 *
                FROM Pagamento WHERE referencia = ?
                """;
            try (PreparedStatement ps = baseDados.getConexao().prepareStatement(query)) {
                ps.setString(1, pagamento.getReferencia());

                // ExecuteQuery para obter resultados de SELECT
                try (ResultSet rs = ps.executeQuery()) {
                    // Se houver resultados, a referência existe
                    if (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            Mensagens.Erro("Erro!", "Erro ao verificar referência!");
            System.out.println(e.getMessage());
        } finally {
            baseDados.Desligar();
        }

        return false;
    }

}