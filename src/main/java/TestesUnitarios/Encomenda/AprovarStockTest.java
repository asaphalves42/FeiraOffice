package TestesUnitarios.Encomenda;

import DAL.LerContaCorrente;
import DAL.LerEncomenda;
import Model.Encomenda;
import Model.LinhaEncomenda;
import Model.Produto;
import Model.Utilizador;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AprovarStockTest {
    /*
    private AprovarStock aprovarStock;
    private LerEncomenda mockLerEncomenda;
    private LerContaCorrente mockLerContaCorrente;
    private Utilizador mockUtilizador;

    @BeforeEach
    public void setUp() {
        mockLerEncomenda = mock(LerEncomenda.class);
        mockLerContaCorrente = mock(LerContaCorrente.class);
        mockUtilizador = mock(Utilizador.class);
        aprovarStock = new AprovarStock(mockLerEncomenda, mockLerContaCorrente);
        aprovarStock.iniciaData(mockUtilizador);
    }

    @Test
    public void testAprovarStock() throws IOException {
        Encomenda mockEncomenda = mock(Encomenda.class);
        when(mockEncomenda.getValorTotal()).thenReturn(100.0);
        when(mockEncomenda.getFornecedor().getIdExterno()).thenReturn("F1");
        when(mockEncomenda.getId()).thenReturn(1);
        when(mockUtilizador.getId()).thenReturn(1);

        List<LinhaEncomenda> mockLinhasEncomenda = mock(List.class);
        when(mockLerEncomenda.lerLinhasParaAprovacao(mockEncomenda.getId())).thenReturn(mockLinhasEncomenda);

        boolean result = aprovarStock.aprovarStock(mockEncomenda);

        // Verify the interactions with the mock objects
        verify(mockLerEncomenda).atualizarEstadoEncomenda(anyInt());
        verify(mockLerContaCorrente).atualizarSaldoDevedores(anyDouble(), anyString());
        verify(mockLerEncomenda).quemAprovouEncomenda(anyInt(), anyInt());

        // Assert that the result is true if the stock approval was successful
        assertTrue(result);
    }

     */
}

