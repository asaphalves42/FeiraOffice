package Model;

public class OrderWeb {
    public String GroupId;
    public String OrderNumber;

    public String Date;
    public Cliente Client;
    public DeliveryAddress DeliveryAddress;
    public BillingAddress BillingAddress;

    public OrderWeb(String groupId, String orderNumber, String date, Cliente client, Model.DeliveryAddress deliveryAddress, Model.BillingAddress billingAddress) {
        GroupId = groupId;
        OrderNumber = orderNumber;
        Date = date;
        Client = client;
        DeliveryAddress = deliveryAddress;
        BillingAddress = billingAddress;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Cliente getClient() {
        return Client;
    }

    public void setClient(Cliente client) {
        Client = client;
    }

    public Model.DeliveryAddress getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(Model.DeliveryAddress deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public Model.BillingAddress getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(Model.BillingAddress billingAddress) {
        BillingAddress = billingAddress;
    }
}
