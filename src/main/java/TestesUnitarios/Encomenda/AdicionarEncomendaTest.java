package TestesUnitarios.Encomenda;

import DAL.LerEncomenda;
import Model.*;
import Utilidades.BaseDados;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdicionarEncomendaTest {
/*


    @Test
    public void testAdicionarEncomendaBaseDeDados() {
        // Criação de objetos de teste
        BaseDados baseDadosMock = Mockito.mock(BaseDados.class);
        Encomenda encomenda = criarEncomendaDeTeste();
        LerEncomenda lerencomenda = new LerEncomenda();

        try {
            // Configuração do comportamento esperado para o método ExecutarPreparementStatement
            when(baseDadosMock.ExecutarPreparementStatement(any(String.class))).thenReturn(1);

            // Chama o método a ser testado
            int idEncomenda = lerencomenda.adicionarEncomendaBaseDeDados(baseDadosMock, encomenda);

            // Verifica se o ID retornado é maior que 0, indicando que a encomenda foi adicionada com sucesso
            assertTrue(idEncomenda > 0);

            // Verifica se o método ExecutarPreparementStatement foi chamado com a query correta
            Mockito.verify(baseDadosMock).ExecutarPreparementStatement(any(String.class));

            // Verifica se o método Desligar foi chamado
            Mockito.verify(baseDadosMock).Desligar();

        } catch (IOException e) {
            // Trate a exceção ou falha, se necessário
            fail("Exceção não esperada: " + e.getMessage());
        }
    }

    // Método auxiliar para criar uma instância de Encomenda de teste
    private Encomenda criarEncomendaDeTeste() {
        // Crie uma Encomenda de teste com dados fictícios
        Encomenda encomenda = new Encomenda(1, "Ref123", LocalDate.now(), new Fornecedor(), new Pais(),
                new ArrayList<>(), Estado.Pendente);

        Unidade unidade = new Unidade(1, "Teste");

        // Adicione linhas de encomenda, produtos, etc., conforme necessário
        Fornecedor  fornecedor = new Fornecedor("");

        Produto produto1 = new Produto("1128121", fornecedor, "999101pt", "naosei", unidade, 1);

        LinhaEncomenda linha1 = new LinhaEncomenda(produto1, 1.0);

        Produto produto2 = new Produto("1128126", fornecedor, "999101pt", "naosei", unidade, 1);
        LinhaEncomenda linha2 = new LinhaEncomenda(produto2, 1.0);

        encomenda.setLinhas(new ArrayList<>(List.of(linha1, linha2)));

        return encomenda;
    }
 */
}
