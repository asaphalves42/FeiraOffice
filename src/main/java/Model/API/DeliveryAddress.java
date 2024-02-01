package Model.API;

public class DeliveryAddress {
    public String DeliveryAddress1;
    public String DeliveryAddress2;
    public String DeliveryPostalCode;
    public String DeliveryCity;
    public String DeliveryCountry;

    public DeliveryAddress(String deliveryAddress1, String deliveryAddress2, String deliveryPostalCode, String deliveryCity, String deliveryCountry) {
        this.DeliveryAddress1 = deliveryAddress1;
        this.DeliveryAddress2 = deliveryAddress2;
        this.DeliveryPostalCode = deliveryPostalCode;
        this.DeliveryCity = deliveryCity;
        this.DeliveryCountry = deliveryCountry;
    }

    public String getDeliveryAddress1() {
        return DeliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.DeliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return DeliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.DeliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryPostalCode() {
        return DeliveryPostalCode;
    }

    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.DeliveryPostalCode = deliveryPostalCode;
    }

    public String getDeliveryCity() {
        return DeliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.DeliveryCity = deliveryCity;
    }

    public String getDeliveryCountry() {
        return DeliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.DeliveryCountry = deliveryCountry;
    }
}
