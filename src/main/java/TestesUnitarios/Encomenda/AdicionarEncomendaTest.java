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

public class AdicionarEncomendaTest {

    @BeforeClass
    public static void configurarHeadlessToolkit() {

        new JFXPanel();

        // Configura o Toolkit para usar o HeadlessToolkit
        Platform.runLater(() -> {

            System.setProperty("javafx.macosx.embedded", "true");
            System.setProperty("javafx.headless", "true");
        });
    }
    @Test
    public void adicionarEncomenda() throws SQLException {

        LerEncomenda lerEncomenda = new LerEncomenda();

        Fornecedor fornecedor = new Fornecedor("Nome do Fornecedor", "123456789");
        Pais pais = new Pais(1,"Portugal","PT",0.23,"EUR");

        Unidade unidade = new Unidade(1, "Box");

        Encomenda encomenda = new Encomenda(
                210,  // Id da Encomenda (pode ser fictício)
                "REF123",  // Referência da Encomenda (pode ser fictício)
                LocalDate.now(),  // Data da Encomenda (pode ser fictício, usando a data atual)
                fornecedor,  // Uma instância fictícia de Fornecedor
                pais,  // Uma instância fictícia de Pais
                25.0,  // Total da Taxa (pode ser fictício)
                50.0,  // Total da Incidência (pode ser fictício)
                100.0,  // Total (pode ser fictício)
                EstadoEncomenda.Pendente  // Uma instância fictícia de Estado
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
                1,  // Id da Linha de Encomenda (pode ser fictício)
                encomenda,  // Uma instância fictícia de Encomenda
                1,  // Sequência da Linha de Encomenda (pode ser fictício)
                produto1,  // Uma instância fictícia de Produto
                10.0,  // Preço unitário (pode ser fictício)
                2.5,  // Quantidade (pode ser fictício)
                pais,  // Uma instância fictícia de Pais
                5.0,  // Total da taxa (pode ser fictício)
                15.0,  // Total da incidência (pode ser fictício)
                30.0  // Total da linha (pode ser fictício)
        );

        LinhaEncomenda linha2 = new LinhaEncomenda(
                2,  // Id da Linha de Encomenda (pode ser fictício)
                encomenda,  // Uma instância fictícia de Encomenda
                2,  // Sequência da Linha de Encomenda (pode ser fictício)
                produto2,  // Uma instância fictícia de Produto
                15.0,  // Preço unitário (pode ser fictício)
                3.0,  // Quantidade (pode ser fictício)
                pais,  // Outra instância fictícia de Pais
                8.0,  // Total da taxa (pode ser fictício)
                20.0,  // Total da incidência (pode ser fictício)
                45.0  // Total da linha (pode ser fictício)
        );

        // Adicionar as linhas à encomenda
        encomenda.setLinhas(new ArrayList<>());
        encomenda.setLinha(linha1);
        encomenda.setLinha(linha2);

        try {

            int resultado = lerEncomenda.adicionarEncomendaBaseDeDados(encomenda,false);

            // Obter encomenda da base de dados
           // Encomenda encomendaDoBanco = lerEncomenda.obterEncomendaPorId("1",false); // Substitua "1" pelo ID real da encomenda que você adicionou no teste
            Encomenda encomendaDoBanco = lerEncomenda.obterEncomendaPorId("208", false);
            System.out.println("Encomenda do banco: " + encomendaDoBanco);
            // Realizar comparação entre encomenda adicionada e encomenda da base de dados
           // assertNotNull(encomendaDoBanco); // Verifica se a encomenda do banco não é nula

            // Comparar os atributos relevantes das encomendas
            assertEquals(encomendaDoBanco.getReferencia(), encomenda.getReferencia());
            assertEquals(encomendaDoBanco.getData(), encomenda.getData());
            assertEquals(encomendaDoBanco.getFornecedor(), encomenda.getFornecedor());
            // Adicione mais comparações conforme necessário

            // Verificar se as linhas também correspondem
            assertEquals(encomendaDoBanco.getLinhas().size(), encomenda.getLinhas().size());
            // Faça mais comparações se necessário

        } catch (IOException e) {
            System.out.println("Exceção não esperada: " + e.getMessage());
        }
    }

   @Test
    public void excluirEncomendaDaBaseDeDados() throws IOException {
        // Lógica para excluir a encomenda da base de dados
        Connection conexao = null;
        try {
            conexao = BaseDados.getConexao();
            BaseDados.iniciarTransacao(conexao);

            // Exemplo de lógica de exclusão na tabela Encomenda (adapte conforme necessário)
            String query = "DELETE FROM Encomenda WHERE id = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, 220);
                statement.executeUpdate();
            }

            // Exemplo de lógica de exclusão na tabela Linha_Encomenda (adapte conforme necessário)
            query = "DELETE FROM Linha_Encomenda WHERE Id_Encomenda = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, 220);
                statement.executeUpdate();
            }

            BaseDados.commit(conexao);
        } catch (Exception e) {
            // Lidar com exceções, se necessário
            e.printStackTrace();
            BaseDados.rollback(conexao);
        } finally {
            BaseDados.Desligar();
        }
    }
}
