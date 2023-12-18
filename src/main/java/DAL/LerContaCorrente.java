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




}
