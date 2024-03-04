package Utilidades;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Classe com métodos e conexões com a API
 */
public class API {

    private static final String BASE_URL = "Sua URL aqui";
    private static final String USERNAME = "Seu username";
    private static final String PASSWORD = "Sua password";

    // Métodos para gestão de clientes
    /**
     * Cria um cliente na API remota.
     *
     * @param data Os dados do cliente a serem enviados para a criação. Deve estar no formato adequado para a API.
     * @return A resposta da API após a criação do cliente.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String criarCliente(String data) throws IOException {
        String url = BASE_URL + "client/";
        return sendRequest(url, "POST", data);
    }

    /**
     * Atualiza um cliente existente na API remota.
     *
     * @param clientId O ID do cliente a ser atualizado.
     * @param data     Os novos dados do cliente a serem enviados para a atualização. Deve estar no formato adequado para a API.
     * @return A resposta da API após a atualização do cliente.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String updateCliente(String clientId, String data) throws IOException {
        String url = BASE_URL + "client/" + clientId + "/";
        return sendRequest(url, "PUT", data);
    }

    /**
     * Remove um cliente existente na API remota.
     *
     * @param clientId O ID do cliente a ser removido.
     * @return A resposta da API após a remoção do cliente.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String deleteCliente(String clientId) throws IOException {
        String url = BASE_URL + "client/" + clientId + "/";
        return sendRequest(url, "DELETE", null);
    }


    /**
     * Obtém informações de um cliente específico da API remota.
     *
     * @param clientId O ID do cliente cujas informações serão obtidas.
     * @return A resposta da API contendo as informações do cliente solicitado.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */

    public static String getCliente(int clientId) throws IOException {
        String url = BASE_URL + "client/" + clientId + "/";
        return sendRequest(url, "GET", null);
    }


    /**
     * Obtém informações de todos os clientes da API remota.
     *
     * @return A resposta da API contendo as informações de todos os clientes.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String getAllClients() throws IOException {
        String url = BASE_URL + "client/";
        return sendRequest(url, "GET", null);
    }


    /**
     * Cria um novo produto no sistema de gestão de estoque da API remota.
     *
     * @param data Os dados do produto a serem enviados para a criação. Deve estar no formato adequado para a API.
     * @return A resposta da API após a criação do produto.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String createProduct(String data) throws IOException {
        String url = BASE_URL + "product/";
        return sendRequest(url, "POST", data);
    }

    /**
     * Atualiza um produto existente no sistema de gestão de estoque da API remota.
     *
     * @param productId O ID do produto a ser atualizado.
     * @param data      Os novos dados do produto a serem enviados para a atualização. Deve estar no formato adequado para a API.
     * @return A resposta da API após a atualização do produto.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String updateProduct(String productId, String data) throws IOException {
        String url = BASE_URL + "product/" + productId + "/";
        return sendRequest(url, "PUT", data);
    }


    /**
     * Obtém informações de um produto específico do sistema de gestão de estoque da API remota.
     *
     * @param productId O ID do produto cujas informações serão obtidas.
     * @return A resposta da API contendo as informações do produto solicitado.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String getProduct(String productId) throws IOException {
        String url = BASE_URL + "product/" + productId;
        return sendRequest(url, "GET", null);
    }


    /**
     * Obtém informações de todos os produtos do sistema de gestão de estoque da API remota.
     *
     * @return A resposta da API contendo as informações de todos os produtos.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String getAllProducts() throws IOException {
        String url = BASE_URL + "product/";
        return sendRequest(url, "GET", null);
    }


    /**
     * Obtém informações de todas as ordens do sistema da API remota.
     *
     * @return A resposta da API contendo as informações de todas as ordens.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String getAllOrders() throws IOException {
        String url = BASE_URL + "order/";
        return sendRequest(url, "GET", null);
    }


    /**
     * Atualiza uma ordem existente no sistema da API remota.
     *
     * @param idOrder O ID da ordem a ser atualizada.
     * @param data    Os novos dados da ordem a serem enviados para a atualização. Deve estar no formato adequado para a API.
     * @return A resposta da API após a atualização da ordem.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com a API.
     */
    public static String updateOrder(String idOrder, String data) throws IOException {
        String url = BASE_URL + "order/" + idOrder + "/";
        return sendRequest(url, "PUT", data);
    }


    /**
     * Envia uma requisição HTTP para a URL especificada utilizando o método especificado e opcionalmente enviando dados.
     *
     * @param url    A URL para a qual a requisição será enviada.
     * @param method O método HTTP a ser utilizado para a requisição (por exemplo, "GET", "POST", "PUT", "DELETE").
     * @param data   Os dados a serem enviados na requisição, se houver (pode ser nulo).
     * @return A resposta da requisição como uma String, ou null se a requisição falhar.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com o servidor.
     */
    private static String sendRequest(String url, String method, String data) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(url, method, data);

        int code = connection.getResponseCode();

        if (code != 200 && code != 201 && code != 202) {
            Mensagens.Erro("API Failed!", "Error code: " + code);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }


    /**
     * Cria e configura uma conexão HTTP para a URL especificada com o método, dados e cabeçalhos fornecidos.
     *
     * @param url    A URL para a qual a conexão será estabelecida.
     * @param method O método HTTP a ser utilizado para a conexão (por exemplo, "GET", "POST", "PUT", "DELETE").
     * @param data   Os dados a serem enviados na conexão, se houver (pode ser nulo).
     * @return Uma conexão HttpURLConnection configurada de acordo com os parâmetros fornecidos.
     * @throws IOException Se ocorrer um erro de entrada/saída durante a comunicação com o servidor.
     */
    @NotNull
    private static HttpURLConnection getHttpURLConnection(String url, String method, String data) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // Adiciona autenticação básica
        String userCredentials = USERNAME + ":" + PASSWORD;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        connection.setRequestProperty("Authorization", basicAuth);

        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        if (data != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }
        return connection;
    }


}

