package Model;

public class Cliente {
    public String Id;
    public String GroupId;
    public String Name;
    public String Email;
    public String Address1;
    public String Address2;
    public String PostalCode;
    public String City;
    public String Country;
    public String TaxIdentificationNumber;
    public boolean Active;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getTaxIdentificationNumber() {
        return TaxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        TaxIdentificationNumber = taxIdentificationNumber;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public Cliente(String id, String groupId, String name, String email, String address1, String address2, String postalCode, String city, String country, String taxIdentificationNumber, boolean active) {
        Id = id;
        GroupId = groupId;
        Name = name;
        Email = email;
        Address1 = address1;
        Address2 = address2;
        PostalCode = postalCode;
        City = city;
        Country = country;
        TaxIdentificationNumber = taxIdentificationNumber;
        Active = active;
    }
}


