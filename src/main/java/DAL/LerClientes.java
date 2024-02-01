package DAL;

import Model.API.Cliente;
import Model.API.ClienteAPI;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static Utilidades.API.getAllClients;

public class LerClientes {
    public List<Cliente> lerClientesDaApi() {
        try {
            String clientesJson = getAllClients();

            Gson gson = new Gson();
            ClienteAPI clientes = gson.fromJson(clientesJson, ClienteAPI.class);

            // Converte o array de clientes para uma lista e retorna
            return clientes.getClientes();
            
        } catch (IOException e) {

            // Lide com a exceção de alguma forma apropriada para o seu aplicativo.
            e.printStackTrace();
            return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
        }
    }
}
