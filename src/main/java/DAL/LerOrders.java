package DAL;

import Model.API.OrderRequest;
import Model.API.Order;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static Utilidades.API.getAllOrders;

public class LerOrders {
    public List<Order> lerOrders() {

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

