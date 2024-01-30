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
        Pais pais = new Pais("Nome do País", "Código do País");



        Unidade unidade = new Unidade(1,"Box");

        Encomenda encomenda = new Encomenda(
                220,
                "PL33333",
                LocalDate.now(),
                fornecedor,
                pais,
                150.0,
                50.0,
                200.0,
                EstadoEncomenda.Pendente
        );

        // Adicionar produtos à encomenda
        Produto produto1 = new Produto(
                "121",
                "TesteADD",
                unidade
        );
        Produto produto2 = new Produto(
                "541",
                "TesteADDV2",
                unidade
        );
        LinhaEncomenda linha1 = new LinhaEncomenda(produto1, 2);
        LinhaEncomenda linha2 = new LinhaEncomenda(produto2, 3);

// Adicionar as linhas à encomenda
    encomenda.setLinhas(new ArrayList<>());
        encomenda.setLinha(linha1);
        encomenda.setLinha(linha2);
        try {
            int resultado = lerEncomenda.adicionarEncomendaBaseDeDados(encomenda,false);
            System.out.println("Resultado: " + resultado);
            //realizar comparação com a funçao obter encomenda por id e ver se retorna true ou false
            // Implemente ou remova o método excluirEncomendaDaBaseDeDados(resultado) conforme necessário.

        } catch (IOException e) {
            System.out.println("Exceção não esperada: " + e.getMessage());
        }
    }
   @After
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
