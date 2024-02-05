package DAL;

import Model.API.Cliente;
import Model.API.ClienteAPI;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static Utilidades.API.getAllClients;

public class LerClientes {
    /**
     * Lê clientes da API.

     * Este método chama a API para obter uma representação JSON dos clientes, em seguida,
     * converte esse JSON em uma lista de objetos Cliente utilizando a biblioteca Gson.
     *
     * @return Uma lista de objetos Cliente lida da API.
     *         Retorna uma lista vazia se ocorrer uma exceção durante o processo de leitura.
     */
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
