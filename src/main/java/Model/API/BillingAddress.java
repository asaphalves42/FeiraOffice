package Model.API;
/**
 * A classe BillingAddress representa um endereço de faturação  com os seus atributos, como linha de endereço 1,
 * linha de endereço 2, código postal, cidade e país.
 */
public class BillingAddress {
    /**
     * A primeira linha do endereço de faturação.
     */
    public String BillingAddress1;
    /**
     * A segunda linha do endereço de faturação.
     */
    public String BillingAddress2;
    /**
     * O código postal do endereço de faturação.
     */
    public String BillingPostalCode;
    /**
     * A cidade do endereço de faturação.
     */
    public String BillingCity;
    /**
     * O país do endereço de faturação.
     */
    public String BillingCountry;
    /**
     * Constrói um novo BillingAddress com os detalhes especificados.
     *
     * @param billingAddress1   A primeira linha do endereço de faturação.
     * @param billingAddress2   A segunda linha do endereço de faturação.
     * @param billingPostalCode O código postal do endereço de faturação.
     * @param billingCity       A cidade do endereço de faturação.
     * @param billingCountry    O país do endereço de faturação.
     */
    public BillingAddress(String billingAddress1, String billingAddress2, String billingPostalCode, String billingCity, String billingCountry) {
        this.BillingAddress1 = billingAddress1;
        this.BillingAddress2 = billingAddress2;
        this.BillingPostalCode = billingPostalCode;
        this.BillingCity = billingCity;
        this.BillingCountry = billingCountry;
    }
    /**
     * Retorna a primeira linha do endereço de faturação.
     *
     * @return A primeira linha do endereço de faturação.
     */
    public String getBillingAddress1() {
        return BillingAddress1;
    }
    /**
     * Define a primeira linha do endereço de faturação.
     *
     * @param billingAddress1 A primeira linha do endereço de faturação a definir.
     */
    public void setBillingAddress1(String billingAddress1) {
        this.BillingAddress1 = billingAddress1;
    }
    /**
     * Retorna a segunda linha do endereço de faturação.
     *
     * @return A segunda linha do endereço de faturação.
     */
    public String getBillingAddress2() {
        return BillingAddress2;
    }
    /**
     * Define a segunda linha do endereço de faturação.
     *
     * @param billingAddress2 A segunda linha do endereço de faturação a definir.
     */
    public void setBillingAddress2(String billingAddress2) {
        this.BillingAddress2 = billingAddress2;
    }

    /**
     * Retorna o código postal do endereço de faturação.
     *
     * @return O código postal do endereço de faturação.
     */
    public String getBillingPostalCode() {
        return BillingPostalCode;
    }
    /**
     * Define o código postal do endereço de faturação.
     *
     * @param billingPostalCode O código postal do endereço de faturação a definir.
     */
    public void setBillingPostalCode(String billingPostalCode) {
        this.BillingPostalCode = billingPostalCode;
    }
    /**
     * Retorna a cidade do endereço de faturação.
     *
     * @return A cidade do endereço de faturação.
     */
    public String getBillingCity() {
        return BillingCity;
    }
    /**
     * Define a cidade do endereço de faturação.
     *
     * @param billingCity A cidade do endereço de faturação a definir.
     */
    public void setBillingCity(String billingCity) {
        this.BillingCity = billingCity;
    }
    /**
     * Retorna o país do endereço de faturação.
     *
     * @return O país do endereço de faturação.
     */
    public String getBillingCountry() {
        return BillingCountry;
    }
    /**
     * Define o país do endereço de faturação.
     *
     * @param billingCountry O país do endereço de faturação a definir.
     */
    public void setBillingCountry(String billingCountry) {
        this.BillingCountry = billingCountry;
    }
}
