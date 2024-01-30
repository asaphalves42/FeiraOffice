package Model;

public class BillingAddress {
    public String Address1;
    public String Address2;
    public String PostalCode;
    public String City;
    public String Country;

    public BillingAddress(String address1, String address2, String postalCode, String city, String country) {
        Address1 = address1;
        Address2 = address2;
        PostalCode = postalCode;
        City = city;
        Country = country;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
