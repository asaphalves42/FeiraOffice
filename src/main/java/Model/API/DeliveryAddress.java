package Model.API;
/**
 * A classe DeliveryAddress representa um endereço de entrega com os seus atributos, como morada 1, morada 2, código postal, cidade e país.
 */

public class DeliveryAddress {
    /**
     * A primeira linha do endereço de entrega.
     */
    public String DeliveryAddress1;
    /**
     * A primeira linha do endereço de entrega.
     */
    public String DeliveryAddress2;
    /**
     * O código postal do endereço de entrega.
     */
    public String DeliveryPostalCode;
    /**
     * A cidade do endereço de entrega.
     */
    public String DeliveryCity;

    /**
     * O país do endereço de entrega.
     */
    public String DeliveryCountry;

    /**
     * Constrói um novo DeliveryAddress com os detalhes especificados.
     *
     * @param deliveryAddress1   A primeira linha do endereço de entrega.
     * @param deliveryAddress2   A segunda linha do endereço de entrega.
     * @param deliveryPostalCode O código postal do endereço de entrega.
     * @param deliveryCity       A cidade do endereço de entrega.
     * @param deliveryCountry    O país do endereço de entrega.
     */
    public DeliveryAddress(String deliveryAddress1, String deliveryAddress2, String deliveryPostalCode, String deliveryCity, String deliveryCountry) {
        this.DeliveryAddress1 = deliveryAddress1;
        this.DeliveryAddress2 = deliveryAddress2;
        this.DeliveryPostalCode = deliveryPostalCode;
        this.DeliveryCity = deliveryCity;
        this.DeliveryCountry = deliveryCountry;
    }
    /**
     * Obtém a primeira linha do endereço de entrega.
     *
     * @return A primeira linha do endereço de entrega.
     */
    public String getDeliveryAddress1() {
        return DeliveryAddress1;
    }
    /**
     * Define a primeira linha do endereço de entrega.
     *
     * @param deliveryAddress1 A primeira linha do endereço de entrega a definir.
     */
    public void setDeliveryAddress1(String deliveryAddress1) {
        this.DeliveryAddress1 = deliveryAddress1;
    }
    /**
     * Obtém a segunda linha do endereço de entrega.
     *
     * @return A segunda linha do endereço de entrega.
     */
    public String getDeliveryAddress2() {
        return DeliveryAddress2;
    }
    /**
     * Define a segunda linha do endereço de entrega.
     *
     * @param deliveryAddress2 A segunda linha do endereço de entrega a definir.
     */
    public void setDeliveryAddress2(String deliveryAddress2) {
        this.DeliveryAddress2 = deliveryAddress2;
    }
    /**
     * Obtém o código postal do endereço de entrega.
     *
     * @return O código postal do endereço de entrega.
     */
    public String getDeliveryPostalCode() {
        return DeliveryPostalCode;
    }
    /**
     * Define o código postal do endereço de entrega.
     *
     * @param deliveryPostalCode O código postal do endereço de entrega a definir.
     */
    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.DeliveryPostalCode = deliveryPostalCode;
    }
    /**
     * Obtém a cidade do endereço de entrega.
     *
     * @return A cidade do endereço de entrega.
     */
    public String getDeliveryCity() {
        return DeliveryCity;
    }
    /**
     * Define a cidade do endereço de entrega.
     *
     * @param deliveryCity A cidade do endereço de entrega a definir.
     */
    public void setDeliveryCity(String deliveryCity) {
        this.DeliveryCity = deliveryCity;
    }
    /**
     * Obtém o país do endereço de entrega.
     *
     * @return O país  de entrega.
     */
    public String getDeliveryCountry() {
        return DeliveryCountry;
    }
    /**
     * Define o país do  de entrega.
     *
     * @param deliveryCountry O país do endereço de entrega a definir.
     */
    public void setDeliveryCountry(String deliveryCountry) {
        this.DeliveryCountry = deliveryCountry;
    }
}
