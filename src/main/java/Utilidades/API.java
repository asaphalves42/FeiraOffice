package Utilidades;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class API {

    private static final String BASE_URL = "https://services.inapa.com/FeiraOffice/api/";
    private static final String USERNAME = "FG2";
    private static final String PASSWORD = "W0gyYJ!)Y6";

    // Métodos para gestão de clientes
    public static String criarCliente(String data) throws IOException {
        String url = BASE_URL + "client/";
        return sendRequest(url, "POST", data);
    }

    public static String updateCliente(String clientId, String data) throws IOException {
        String url = BASE_URL + "client/" + clientId + "/";
        return sendRequest(url, "PUT", data);
    }

    public static String deleteCliente(String clientId) throws IOException {
        String url = BASE_URL + "client/" + clientId + "/";
        return sendRequest(url, "DELETE", null);
    }

    public static String getCliente(int clientId) throws IOException {
        String url = BASE_URL + "client/" + clientId + "/";
        return sendRequest(url, "GET", null);
    }

    public static String getAllClients() throws IOException {
        String url = BASE_URL + "client/";
        return sendRequest(url, "GET", null);
    }

    // Métodos para gestão de stock de produtos
    public static String createProduct(String data) throws IOException {
        String url = BASE_URL + "product/";
        return sendRequest(url, "POST", data);
    }

    public static String updateProduct(String productId, String data) throws IOException {
        String url = BASE_URL + "product/" + productId + "/";
        return sendRequest(url, "PUT", data);
    }

    public static String getProduct(String productId) throws IOException {
        String url = BASE_URL + "product/" + productId;
        return sendRequest(url, "GET", null);
    }

    public static String getAllProducts() throws IOException {
        String url = BASE_URL + "product/";
        return sendRequest(url, "GET", null);
    }

    // Métodos para registo e listagem de encomendas

    public static String createOrder(String data) throws IOException {
        String url = BASE_URL + "order/";
        return sendRequest(url, "POST", data);
    }

    public static String getOrdersByClient(int clientId) throws IOException {
        String url = BASE_URL + "order/client/" + clientId + "/";
        return sendRequest(url, "GET", null);
    }

    public static String getAllOrders() throws IOException {
        String url = BASE_URL + "order/";
        return sendRequest(url, "GET", null);
    }

    public static String updateOrder(String idOrder, String data) throws IOException {
        String url = BASE_URL + "order/" + idOrder + "/";
        return sendRequest(url, "PUT", data);
    }


    // Método genérico para enviar requisições
    private static String sendRequest(String url, String method, String data) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(url, method, data);

        int code= connection.getResponseCode();

        if(code!=200 && code!= 201 && code!= 202) {
            Mensagens.Erro("API Failed!","Error code: " + code);
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

