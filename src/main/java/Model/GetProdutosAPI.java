package Model;

import java.util.ArrayList;

public class GetProdutosAPI {
    public String Status;
    public ArrayList<ProdutoAPI> products;

    public GetProdutosAPI(String status, ArrayList<ProdutoAPI> products) {
        Status = status;
        this.products = products;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ArrayList<ProdutoAPI> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProdutoAPI> products) {
        this.products = products;
    }
}
