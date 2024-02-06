package Model.API;

import java.util.List;

public class Order {

    /**
     * O ID do grupo do pedido.
     */
    public String GroupId;

    /**
     * O número do pedido.
     */
    public String OrderNumber;

    /**
     * A data do pedido.
     */
    public String Date;

    /**
     * O cliente associado ao pedido.
     */
    public List<Cliente> Client;

    /**
     * O endereço de entrega do pedido.
     */
    public List<DeliveryAddress> DeliveryAddress;

    /**
     * O endereço de faturação do pedido.
     */
    public List<BillingAddress> BillingAddress;

    /**
     * O montante líquido do pedido.
     */
    public double NetAmount;

    /**
     * O montante do imposto do pedido.
     */
    public double TaxAmount;

    /**
     * O montante total do pedido.
     */
    public double TotalAmount;

    /**
     * A moeda do pedido.
     */
    public String Currency;

    /**
     * O estado do pedido.
     */
    public int Status;

    /**
     * A descrição do estado do pedido.
     */
    public String StatusDescription;

    /**
     * As linhas de pedido associadas ao pedido.
     */
    public List<OrderLine> OrderLines;
    /**
     * Constrói um novo objeto Order com os detalhes especificados.
     *
     * @param groupId          O ID do grupo do pedido.
     * @param orderNumber      O número do pedido.
     * @param date             A data do pedido.
     * @param client           O cliente associado ao pedido.
     * @param deliveryAddress  O endereço de entrega do pedido.
     * @param billingAddress   O endereço de faturação do pedido.
     * @param netAmount        O montante líquido do pedido.
     * @param taxAmount        O montante do imposto do pedido.
     * @param totalAmount      O montante total do pedido.
     * @param currency         A moeda do pedido.
     * @param status           O estado do pedido.
     * @param statusDescription A descrição do estado do pedido.
     * @param orderLines       As linhas de pedido associadas ao pedido.
     */
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
    /**
     * Obtém o ID do grupo do pedido.
     *
     * @return O ID do grupo do pedido.
     */
    public String getGroupId() {
        return GroupId;
    }
    /**
     * Define o ID do grupo do pedido.
     *
     * @param groupId O ID do grupo do pedido a definir.
     */
    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
    /**
     * Obtém o número do pedido.
     *
     * @return O número do pedido.
     */
    public String getOrderNumber() {
        return OrderNumber;
    }
    /**
     * Define o número do pedido.
     *
     * @param orderNumber O número do pedido a definir.
     */
    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }
    /**
     * Obtém a data do pedido.
     *
     * @return A data do pedido.
     */
    public String getDate() {
        return Date;
    }
    /**
     * Define a data do pedido.
     *
     * @param date A data do pedido a definir.
     */
    public void setDate(String date) {
        Date = date;
    }
    /**
     * Obtém o cliente associado ao pedido.
     *
     * @return O cliente associado ao pedido.
     */
    public List<Cliente> getClient() {
        return Client;
    }
    /**
     * Define o cliente associado ao pedido.
     *
     * @param client O cliente associado ao pedido a definir.
     */
    public void setClient(List<Cliente> client) {
        Client = client;
    }
    /**
     * Obtém o endereço de entrega do pedido.
     *
     * @return O endereço de entrega do pedido.
     */
    public List<Model.API.DeliveryAddress> getDeliveryAddress() {
        return DeliveryAddress;
    }
    /**
     * Define o endereço de entrega do pedido.
     *
     * @param deliveryAddress O endereço de entrega do pedido a definir.
     */
    public void setDeliveryAddress(List<Model.API.DeliveryAddress> deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }
    /**
     * Obtém o endereço de faturação do pedido.
     *
     * @return O endereço de faturação do pedido.
     */
    public List<Model.API.BillingAddress> getBillingAddress() {
        return BillingAddress;
    }
    /**
     * Define o endereço de faturação do pedido.
     *
     * @param billingAddress O endereço de faturação do pedido a definir.
     */
    public void setBillingAddress(List<Model.API.BillingAddress> billingAddress) {
        BillingAddress = billingAddress;
    }
    /**
     * Obtém o montante líquido do pedido.
     *
     * @return O montante líquido do pedido.
     */
    public double getNetAmount() {
        return NetAmount;
    }
    /**
     * Define o montante líquido do pedido.
     *
     * @param netAmount O montante líquido do pedido a definir.
     */
    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }
    /**
     * Obtém o montante do imposto do pedido.
     *
     * @return O montante do imposto do pedido.
     */
    public double getTaxAmount() {
        return TaxAmount;
    }
    /**
     * Define o montante do imposto do pedido.
     *
     * @param taxAmount O montante do imposto do pedido a definir.
     */
    public void setTaxAmount(double taxAmount) {
        TaxAmount = taxAmount;
    }
    /**
     * Obtém o montante total do pedido.
     *
     * @return O montante total do pedido.
     */
    public double getTotalAmount() {
        return TotalAmount;
    }
    /**
     * Define o montante total do pedido.
     *
     * @param totalAmount O montante total do pedido a definir.
     */
    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }
    /**
     * Obtém a moeda do pedido.
     *
     * @return A moeda do pedido.
     */
    public String getCurrency() {
        return Currency;
    }
    /**
     * Define a moeda do pedido.
     *
     * @param currency A moeda do pedido a definir.
     */
    public void setCurrency(String currency) {
        Currency = currency;
    }
    /**
     * Obtém o estado do pedido.
     *
     * @return O estado do pedido.
     */
    public int getStatus() {
        return Status;
    }
    /**
     * Define o estado do pedido.
     *
     * @param status O estado do pedido a definir.
     */
    public void setStatus(int status) {
        Status = status;
    }
    /**
     * Obtém a descrição do estado do pedido.
     *
     * @return A descrição do estado do pedido.
     */
    public String getStatusDescription() {
        return StatusDescription;
    }
    /**
     * Define a descrição do estado do pedido.
     *
     * @param statusDescription A descrição do estado do pedido a definir.
     */
    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    /**
     * Obtém as linhas de pedido associadas ao pedido.
     *
     * @return As linhas de pedido associadas ao pedido.
     */
    public List<OrderLine> getOrderLines() {
        return OrderLines;
    }
    /**
     * Define as linhas de pedido associadas ao pedido.
     *
     * @param orderLines As linhas de pedido associadas ao pedido a definir.
     */
    public void setOrderLines(List<OrderLine> orderLines) {
        OrderLines = orderLines;
    }
}
