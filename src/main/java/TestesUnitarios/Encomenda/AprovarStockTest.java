package TestesUnitarios.Encomenda;

import Controller.Encomenda.AprovarStock;
import Model.Encomenda;
import Model.TipoUtilizador;
import Model.Utilizador;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class UtilizadorTest extends Utilizador {
    public UtilizadorTest(int id, TipoUtilizador tipo) {
        super(id, tipo);
    }

    // Adicione métodos setters conforme necessário
    public void setEmail(String email) {
        super.setEmail(email);
    }

}

public class AprovarStockTest {

    @Test
    void testClickAprovar() {
        // Crie uma instância de AprovarStock
        AprovarStock aprovarStock = new AprovarStock();

        // Mock para um utilizador
        Utilizador utilizadorMock = new UtilizadorTest(1, TipoUtilizador.Administrador);
        utilizadorMock.setEmail("test@example.com");


        // Configure os dados iniciais
        aprovarStock.iniciaData(utilizadorMock);

        // Crie uma encomenda de teste
        Encomenda encomendaTeste = new Encomenda();
        encomendaTeste.setId(1);
        encomendaTeste.setValorTotal(100.0);

        // Adicione a encomenda à tabela de encomendas
        aprovarStock.encomendas.add(encomendaTeste);

        // Selecione a encomenda de teste
        aprovarStock.tableViewEncomendas.getSelectionModel().select(encomendaTeste);

        // Execute o método de aprovação
        assertDoesNotThrow(() -> aprovarStock.clickAprovar());

        // Verifique se a tabela de encomendas está vazia após a aprovação
        assertTrue(aprovarStock.encomendas.isEmpty());
    }
}