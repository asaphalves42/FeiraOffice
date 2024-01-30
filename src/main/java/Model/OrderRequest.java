package Model;

import java.util.ArrayList;

public class OrderRequest {
    public String Status;
    public ArrayList<OrderWeb> orders;

    public OrderRequest(String status, ArrayList<OrderWeb> orders) {
        Status = status;
        this.orders = orders;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ArrayList<OrderWeb> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderWeb> orders) {
        this.orders = orders;
    }
}
