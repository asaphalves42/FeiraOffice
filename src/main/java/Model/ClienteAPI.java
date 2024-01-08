package Model;

import java.util.ArrayList;

public class ClienteAPI {

    public String Status;
    public ArrayList<Cliente> Clients;

    public ArrayList<Cliente> getClientes() {
        return Clients;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        Clients = clientes;
    }

    public ClienteAPI(String status, ArrayList<Cliente> clientes) {
        Status = status;
        Clients = clientes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}
