package Model.API;

import java.util.ArrayList;

/**
 * A classe GetProdutosAPI representa uma API de obtenção de produtos, contendo informações sobre o status da operação e uma lista de produtos.
 */
public class GetProdutosAPI {
    /**
     * O status da operação da API.
     */
    public String Status;
    /**
     * A lista de produtos retornada pela API.
     */
    public ArrayList<ProdutoAPI> products;
    /**
     * Constrói uma nova instância de GetProdutosAPI com o status e a lista de produtos especificados.
     *
     * @param status   O status da operação da API.
     * @param products A lista de produtos.
     */
    public GetProdutosAPI(String status, ArrayList<ProdutoAPI> products) {
        Status = status;
        this.products = products;
    }
    /**
     * Obtém o status da operação da API.
     *
     * @return O status da operação da API.
     */
    public String getStatus() {
        return Status;
    }
    /**
     * Define o status da operação da API.
     *
     * @param status O status da operação da API a definir.
     */
    public void setStatus(String status) {
        Status = status;
    }
    /**
     * Obtém a lista de produtos.
     *
     * @return A lista de produtos.
     */
    public ArrayList<ProdutoAPI> getProducts() {
        return products;
    }
    /**
     * Define a lista de produtos.
     *
     * @param products A lista de produtos a definir.
     */
    public void setProducts(ArrayList<ProdutoAPI> products) {
        this.products = products;
    }
}
