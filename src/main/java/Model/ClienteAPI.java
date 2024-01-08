package Model;

public class ClienteAPI {

    private String Status;
    private Cliente[] Clientes;

    public ClienteAPI(String status, Cliente[] clientes) {
        Status = status;
        Clientes = clientes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Cliente[] getClientes() {
        return Clientes;
    }

    public void setClientes(Cliente[] clientes) {
        Clientes = clientes;
    }
}
