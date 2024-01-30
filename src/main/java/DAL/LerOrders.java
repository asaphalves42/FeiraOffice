package DAL;

import Model.Cliente;
import Model.ClienteAPI;
import Model.OrderRequest;
import Model.OrderWeb;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static Utilidades.API.getAllClients;
import static Utilidades.API.getAllOrders;

public class LerOrders {
    public List<OrderWeb> lerOrders() {

        try {
            String ordersJson = getAllOrders();

            Gson gson = new Gson();
            OrderRequest orders = gson.fromJson(ordersJson, OrderRequest.class);

            // Converte o array de clientes para uma lista e retorna
            return orders.getOrders();

        } catch (IOException e) {
            // Lide com a exceção de alguma forma apropriada para o seu aplicativo.
            e.printStackTrace();
            return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
        }
    }

}

