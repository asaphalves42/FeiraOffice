package Model.API;

public class BillingAddress {
    public String BillingAddress1;
    public String BillingAddress2;
    public String BillingPostalCode;
    public String BillingCity;
    public String BillingCountry;

    public BillingAddress(String billingAddress1, String billingAddress2, String billingPostalCode, String billingCity, String billingCountry) {
        this.BillingAddress1 = billingAddress1;
        this.BillingAddress2 = billingAddress2;
        this.BillingPostalCode = billingPostalCode;
        this.BillingCity = billingCity;
        this.BillingCountry = billingCountry;
    }

    public String getBillingAddress1() {
        return BillingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.BillingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return BillingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.BillingAddress2 = billingAddress2;
    }

    public String getBillingPostalCode() {
        return BillingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        this.BillingPostalCode = billingPostalCode;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public void setBillingCity(String billingCity) {
        this.BillingCity = billingCity;
    }

    public String getBillingCountry() {
        return BillingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.BillingCountry = billingCountry;
    }
}
