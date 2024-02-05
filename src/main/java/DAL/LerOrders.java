package DAL;

import Model.API.OrderRequest;
import Model.API.Order;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static Utilidades.API.getAllOrders;
/**
 * Classe responsável por ler as informações sobre os pedidos (orders) a partir de uma fonte externa.
 */
public class LerOrders {
    /**
     * Lê as informações sobre os pedidos (orders) a partir de uma fonte externa.
     *
     * @return Uma lista de objetos Order representando os pedidos obtidos.
     */public List<Order> lerOrders() {

        try {
            String ordersJson = getAllOrders();


            Gson gson = new Gson();
            OrderRequest orders = gson.fromJson(ordersJson, OrderRequest.class);

            // Converte o array de orders para uma lista e retorna
            return orders.getOrders();

        } catch (IOException e) {
            // Lide com a exceção de alguma forma apropriada para o seu aplicativo.
            e.printStackTrace();
            return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
        }
    }

}

