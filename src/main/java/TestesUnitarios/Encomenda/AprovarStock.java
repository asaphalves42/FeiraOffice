package TestesUnitarios.Encomenda;

import DAL.LerContaCorrente;
import DAL.LerEncomenda;
import Model.Encomenda;
import Model.LinhaEncomenda;
import Model.Produto;
import Model.Utilizador;

import java.io.IOException;
import java.util.List;

// Refactored AprovarStock class with the logic separated from the UI components
public class AprovarStock {
    /*
    private Utilizador utilizador;
    private LerEncomenda lerEncomenda;
    private LerContaCorrente lerContaCorrente;

    public AprovarStock(LerEncomenda lerEncomenda, LerContaCorrente lerContaCorrente) {
        this.lerEncomenda = lerEncomenda;
        this.lerContaCorrente = lerContaCorrente;
    }

    public void iniciaData(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public boolean aprovarStock(Encomenda encomenda) throws IOException {
        if (encomenda == null) {
            return false;
        }

        List<LinhaEncomenda> linhasEncomenda = lerEncomenda.lerLinhasParaAprovacao(encomenda.getId());
        boolean sucesso = false;
        boolean sucessoEncomenda = false;
        boolean atualizado = false;
        boolean quemAprovou = false;
        double total = encomenda.getValorTotal();

        for (LinhaEncomenda linhas : linhasEncomenda) {
            Produto produto = linhas.getProduto();
            double quantidade = linhas.getQuantidade();
            sucesso = lerEncomenda.atualizarStock(produto.getId(), produto.getUnidade().getId(), quantidade);
        }

        sucessoEncomenda = lerEncomenda.atualizarEstadoEncomenda(encomenda.getId());
        atualizado = lerContaCorrente.atualizarSaldoDevedores(total, encomenda.getFornecedor().getIdExterno());
        if (utilizador != null) {
            quemAprovou = lerEncomenda.quemAprovouEncomenda(encomenda.getId(), utilizador.getId());
        }

        return sucesso && sucessoEncomenda && atualizado && quemAprovou;
    }*/
}
