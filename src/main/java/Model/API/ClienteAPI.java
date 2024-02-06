package Model.API;

import Model.API.Cliente;

import java.util.ArrayList;

public class ClienteAPI {

    /**
     * O status da operação da API.
     */
    public String Status;
    /**
     * A lista de clientes retornada pela API.
     */
    public ArrayList<Cliente> Clients;
    /**
     * Obtém a lista de clientes.
     *
     * @return A lista de clientes.
     */
    public ArrayList<Cliente> getClientes() {
        return Clients;
    }
    /**
     * Define a lista de clientes.
     *
     * @param clientes A lista de clientes a definir.
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        Clients = clientes;
    }

    /**
     * Constrói uma nova instância de ClienteAPI com o status e a lista de clientes especificados.
     *
     * @param status   O status da operação da API.
     * @param clientes A lista de clientes.
     */
    public ClienteAPI(String status, ArrayList<Cliente> clientes) {
        Status = status;
        Clients = clientes;
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

}
