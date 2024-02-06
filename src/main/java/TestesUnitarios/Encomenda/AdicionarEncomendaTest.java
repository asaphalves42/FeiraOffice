package TestesUnitarios.Encomenda;

import DAL.LerEncomenda;
import Model.*;
import Utilidades.BaseDados;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Classe de teste para adicionar uma encomenda.
 */
public class AdicionarEncomendaTest {

    /**
     * Configura o ambiente para execução do JavaFX em modo headless.
     */
    @BeforeClass
    public static void configurarHeadlessToolkit() {

        new JFXPanel();

        // Configura o Toolkit para usar o HeadlessToolkit
        Platform.runLater(() -> {

            System.setProperty("javafx.macosx.embedded", "true");
            System.setProperty("javafx.headless", "true");
        });
    }
    /**
     * Testa o processo de adição de uma encomenda.
     *
     * @throws SQLException Exceção lançada em caso de problemas com a base de dados.
     */
    @Test
    public void adicionarEncomenda() throws SQLException {

        LerEncomenda lerEncomenda = new LerEncomenda();
        Pais pais = new Pais(1,"Portugal","PT",0.23,"EUR");
        UtilizadorFornecedor utilizadorFornecedor = new UtilizadorFornecedor(1,"f2@f2.pt","123");
        Fornecedor fornecedor = new Fornecedor(104,"Antonio das Perras", "1111AA", "Algures", "Algures 2", "Algures",
                "1234-12345",  pais, utilizadorFornecedor);

        Unidade unidade = new Unidade(1, "Box");

        Encomenda encomenda = new Encomenda(
                0,  // Id da Encomenda
                "REF123",  // Referência da Encomenda
                LocalDate.now(),  // Data da Encomenda
                fornecedor,  // Uma instância fictícia de Fornecedor
                pais,  // Uma instância fictícia de Pais
                25.0,  // Total da Taxa
                50.0,  // Total da Incidência
                100.0,  // Total
                EstadoEncomenda.Pendente,
                EstadoPagamento.NaoPago
        );

        // Adicionar produtos à encomenda
        Produto produto1 = new Produto(
                "121",
                "TesteADD",
                unidade, fornecedor, 14.00, "1ASAA"
        );
        Produto produto2 = new Produto(
                "541",
                "TesteADDV2",
                unidade, fornecedor, 13.00, "IAA12"
        );
        LinhaEncomenda linha1 = new LinhaEncomenda(
                1,  // Id da Linha de Encomenda
                encomenda,  // Uma instância fictícia de Encomenda
                1,  // Sequência da Linha de Encomenda
                produto1,  // Uma instância fictícia de Produto
                10.0,  // Preço unitário
                2.5,
                unidade,// Quantidade
                pais,  // Uma instância fictícia de Pais
                5.0,  // Total da taxa
                15.0  // Total da incidência (pode ser fictício)
        );

        LinhaEncomenda linha2 = new LinhaEncomenda(
                2,  // Id da Linha de Encomenda
                encomenda,  // Uma instância fictícia de Encomenda
                2,  // Sequência da Linha de Encomenda
                produto2,  // Uma instância fictícia de Produto
                15.0,  // Preço unitário
                3.0,
                unidade,// Quantidade
                pais,  // Outra instância fictícia de Pais
                8.0,  // Total da taxa
                20.0  // Total da incidência
        );

        // Adicionar as linhas à encomenda
        encomenda.setLinhas(new ArrayList<>());
        encomenda.setLinha(linha1);
        encomenda.setLinha(linha2);

        try {

            int resultado = lerEncomenda.adicionarEncomendaBaseDeDados(encomenda,false);

            // Obter encomenda da base de dados
            Encomenda encomendaDoBanco = lerEncomenda.obterEncomendaPorId(String.valueOf(resultado), false);
            System.out.println("Encomenda do banco: " + encomendaDoBanco);

            assertEquals(resultado, encomendaDoBanco.getId());
            excluirEncomendaDaBaseDeDados(encomendaDoBanco.getId());

        } catch (IOException e) {
            System.out.println("Exceção não esperada: " + e.getMessage());
        }
    }
    /**
     * Remove uma encomenda da base de dados.
     *
     * @param id Identificador da encomenda a ser removida.
     * @throws IOException Exceção de E/S que pode ser lançada durante o processo.
     */
    public void excluirEncomendaDaBaseDeDados(int id) throws IOException {
        // Lógica para excluir a encomenda da base de dados
        Connection conexao = null;
        try {
            conexao = BaseDados.getConexao();
            BaseDados.iniciarTransacao(conexao);

            // Exemplo de lógica de exclusão na tabela Linha_Encomenda
            String query = "DELETE FROM Linha_Encomenda WHERE Id_Encomenda = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }

            // Exemplo de lógica de exclusão na tabela Encomenda
            String query2 = "DELETE FROM Encomenda WHERE id = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query2)) {
                statement.setInt(1,id);
                statement.executeUpdate();
            }

            BaseDados.commit(conexao);
        } catch (Exception e) {
            // Lidar com exceções, se necessário
            e.printStackTrace();
            assert conexao != null;
            BaseDados.rollback(conexao);
        } finally {
            BaseDados.Desligar();
        }
    }
}
