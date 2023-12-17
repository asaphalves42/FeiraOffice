package DAL;

import Model.ContaCorrente;
import Model.FeiraOffice;
import Model.Fornecedor;
import Model.Pais;
import Utilidades.BaseDados;
import Utilidades.Mensagens;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerContaCorrente {
    public ContaCorrente lerContaCorrente(BaseDados baseDados, int idConta) throws SQLException, IOException {
        ContaCorrente conta = null;
        try {
            baseDados.Ligar();

            String query = """
                                        
                    SELECT
                    	Conta.Id as id,
                    	Fornecedor.Nome as nome,
                    	Fornecedor.Id_Externo as id_fornecedor,
                        Conta.Saldo as saldo_devedor
                                        
                    FROM Conta_Corrente Conta
                    	INNER JOIN Fornecedor ON Fornecedor.Id_Externo = Conta.Id_Fornecedor
                    WHERE Conta.Id = ?
                    	
                    """;

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(query)) {
                preparedStatement.setInt(1, idConta);

                ResultSet resultado = preparedStatement.executeQuery();

                if (resultado.next()) {
                    conta = criarObjetoConta(resultado);
                }

            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao ler dados da conta corrente!");
        } finally {
            baseDados.Desligar();
        }

        return conta;
    }

    private ContaCorrente criarObjetoConta(ResultSet dados) throws SQLException {
        Fornecedor fornecedor = new Fornecedor(
                dados.getString("nome"),
                dados.getString("id_fornecedor")
        );

        return new ContaCorrente(
                dados.getInt("id"),
                fornecedor,
                dados.getDouble("saldo_devedor")

        );
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
                dados.getString("nome_pais")
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


}
