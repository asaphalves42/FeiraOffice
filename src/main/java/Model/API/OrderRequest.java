package Model.API;

import Model.API.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    public String Status;
    public List<Order> Orders;

    public OrderRequest(String status, ArrayList<Order> orders) {
        this.Status = status;
        this.Orders = orders;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        this.Orders = orders;
    }
}
