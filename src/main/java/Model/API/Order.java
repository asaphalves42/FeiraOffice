package Model.API;

import java.util.List;

public class Order {
    public String GroupId;
    public String OrderNumber;
    public String Date;
    public List<Cliente> Client;
    public List<DeliveryAddress> DeliveryAddress;
    public List<BillingAddress> BillingAddress;
    public double NetAmount;
    public double TaxAmount;
    public double TotalAmount;
    public String Currency;
    public int Status;
    public String StatusDescription;
    public List<OrderLine> OrderLines;

    public Order(String groupId, String orderNumber, String date, List<Cliente> client, List<Model.API.DeliveryAddress> deliveryAddress, List<Model.API.BillingAddress> billingAddress, double netAmount,
                 double taxAmount, double totalAmount, String currency,
                 int status, String statusDescription, List<OrderLine> orderLines) {
        GroupId = groupId;
        OrderNumber = orderNumber;
        Date = date;
        Client = client;
        DeliveryAddress = deliveryAddress;
        BillingAddress = billingAddress;
        NetAmount = netAmount;
        TaxAmount = taxAmount;
        TotalAmount = totalAmount;
        Currency = currency;
        Status = status;
        StatusDescription = statusDescription;
        OrderLines = orderLines;
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

    public List<Cliente> getClient() {
        return Client;
    }

    public void setClient(List<Cliente> client) {
        Client = client;
    }

    public List<Model.API.DeliveryAddress> getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(List<Model.API.DeliveryAddress> deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public List<Model.API.BillingAddress> getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(List<Model.API.BillingAddress> billingAddress) {
        BillingAddress = billingAddress;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    public double getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        TaxAmount = taxAmount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public List<OrderLine> getOrderLines() {
        return OrderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        OrderLines = orderLines;
    }
}
