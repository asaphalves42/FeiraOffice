package Model.API;

import Model.API.Order;

import java.util.ArrayList;
import java.util.List;
/**
 * A classe OrderRequest representa um pedido de vários pedidos, contendo informações sobre o status do pedido e uma lista de pedidos.
 */
public class OrderRequest {
    /**
     * O status do pedido.
     */
    public String Status;
    /**
     * A lista de pedidos.
     */
    public List<Order> Orders;
    /**
     * Constrói um novo objeto OrderRequest com o status e a lista de pedidos especificados.
     *
     * @param status O status do pedido.
     * @param orders A lista de pedidos.
     */
    public OrderRequest(String status, ArrayList<Order> orders) {
        this.Status = status;
        this.Orders = orders;
    }
    /**
     * Obtém o status do pedido.
     *
     * @return O status do pedido.
     */
    public String getStatus() {
        return Status;
    }
    /**
     * Define o status do pedido.
     *
     * @param status O status do pedido a definir.
     */
    public void setStatus(String status) {
        Status = status;
    }
    /**
     * Obtém a lista de pedidos.
     *
     * @return A lista de pedidos.
     */
    public List<Order> getOrders() {
        return Orders;
    }
    /**
     * Define a lista de pedidos.
     *
     * @param orders A lista de pedidos a definir.
     */
    public void setOrders(List<Order> orders) {
        this.Orders = orders;
    }
}
