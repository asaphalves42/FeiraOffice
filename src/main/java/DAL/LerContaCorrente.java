package DAL;

import Model.*;
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

    /**
     * Atualiza o saldo devedor na conta corrente de um fornecedor na base de dados.
     *
     * @param baseDados      A instância da classe BaseDados para conexão com o banco de dados.
     * @param valorEncomenda O valor da encomenda a ser adicionado ao saldo devedor.
     * @param idFornecedor   O ID do fornecedor cuja conta corrente será atualizada.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws IOException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizarSaldoDevedores(BaseDados baseDados, double valorEncomenda, String idFornecedor) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            // Verificar se já existe um registro para o fornecedor
            String verificaExistencia = "SELECT * FROM Conta_Corrente WHERE Id_Fornecedor = ?";

            try (PreparedStatement preparedStatement = baseDados.getConexao().prepareStatement(verificaExistencia)) {
                preparedStatement.setString(1, idFornecedor);
                ResultSet resultSet = preparedStatement.executeQuery();

                String script;
                if (resultSet.next()) {
                    // Se existir, faz o UPDATE
                    script = "UPDATE Conta_Corrente SET Saldo = Saldo + ? WHERE Id_Fornecedor = ?";

                    try (PreparedStatement updateStatement = baseDados.getConexao().prepareStatement(script)) {
                        updateStatement.setDouble(1, valorEncomenda);
                        updateStatement.setString(2, idFornecedor);
                        // Execute o script
                        updateStatement.executeUpdate();
                    }

                } else {
                    // Se não existir, faz a inserção
                    script = "INSERT INTO Conta_Corrente (Id_Fornecedor, Saldo) VALUES (?, ?)";

                    try (PreparedStatement updateStatement = baseDados.getConexao().prepareStatement(script)) {
                        updateStatement.setString(1, idFornecedor);
                        updateStatement.setDouble(2, valorEncomenda);

                        // Execute o script
                        updateStatement.executeUpdate();
                    }
                }

            }

            baseDados.commit(baseDados.getConexao());

            return true;

        } catch (Exception e) {
            Mensagens.Erro("Error", "Ocorreu um erro ao atualizar o saldo devedor");
            baseDados.rollback(baseDados.getConexao());
            return false; // Retorna false em caso de erro
        } finally {
            baseDados.Desligar();
        }
    }

    public boolean atualizarSaldoAposPagamento(BaseDados baseDados, Pagamento pagamento) throws IOException {
        try {
            baseDados.Ligar();
            baseDados.iniciarTransacao(baseDados.getConexao());

            String query = """
                UPDATE Conta_Corrente SET Saldo = Saldo - ? WHERE Id_Fornecedor = ?
                """;
            try (PreparedStatement ps = baseDados.getConexao().prepareStatement(query)) {
                ps.setDouble(1, pagamento.getValor());
                ps.setString(2, pagamento.getContaCorrente().getIdFornecedor().getIdExterno());
                ps.executeUpdate();
            }

            baseDados.commit(baseDados.getConexao());
            return true;

        } catch (SQLException | IOException e) {
            baseDados.rollback(baseDados.getConexao());
           Mensagens.Erro("Erro!","Erro ao atualizar o saldo!");
        } finally {
            baseDados.Desligar();
        }

        return false;
    }

}
