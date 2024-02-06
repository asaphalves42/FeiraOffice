package Model.API;

public class Cliente {
    /**
     * O ID do cliente.
     */
    public String Id;
    /**
     * O ID do grupo do cliente.
     */
    public String GroupId;
    /**
     * O nome do cliente.
     */
    public String Name;
    /**
     * O email do cliente.
     */
    public String Email;
    /**
     * A primeira linha do endereço do cliente.
     */
    public String Address1;
    /**
     * A segunda linha do endereço do cliente.
     */
    public String Address2;
    /**
     * O código postal do cliente.
     */
    public String PostalCode;
    /**
     * A cidade do cliente.
     */
    public String City;
    /**
     * O número de identificação fiscal do cliente.
     */
    public String Country;
    /**
     * O número de identificação fiscal do cliente.
     */
    public String TaxIdentificationNumber;
    /**
     * O estado de atividade do cliente.
     */
    public boolean Active;
    /**
     * Obtém o ID do cliente.
     *
     * @return O ID do cliente.
     */
    public String getId() {
        return Id;
    }
    /**
     * Define o ID do cliente.
     *
     * @param id O ID do cliente a definir.
     */
    public void setId(String id) {
        Id = id;
    }
    /**
     * Obtém o ID do grupo do cliente.
     *
     * @return O ID do grupo do cliente.
     */
    public String getGroupId() {
        return GroupId;
    }
    /**
     * Define o ID do grupo do cliente.
     *
     * @param groupId O ID do grupo do cliente a definir.
     */
    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
    /**
     * Obtém o nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getName() {
        return Name;
    }
    /**
     * Define o nome do cliente.
     *
     * @param name O nome do cliente a definir.
     */
    public void setName(String name) {
        Name = name;
    }
    /**
     * Obtém o email do cliente.
     *
     * @return O email do cliente.
     */
    public String getEmail() {
        return Email;
    }
    /**
     * Define o email do cliente.
     *
     * @param email O email do cliente a definir.
     */
    public void setEmail(String email) {
        Email = email;
    }
    /**
     * Obtém a primeira linha do endereço do cliente.
     *
     * @return A primeira linha do endereço do cliente.
     */
    public String getAddress1() {
        return Address1;
    }
    /**
     * Define a primeira linha do endereço do cliente.
     *
     * @param address1 A primeira linha do endereço do cliente a definir.
     */
    public void setAddress1(String address1) {
        Address1 = address1;
    }
    /**
     * Obtém a segunda linha do endereço do cliente.
     *
     * @return A segunda linha do endereço do cliente.
     */
    public String getAddress2() {
        return Address2;
    }
    /**
     * Define a segunda linha do endereço do cliente.
     *
     * @param address2 A segunda linha do endereço do cliente a definir.
     */
    public void setAddress2(String address2) {
        Address2 = address2;
    }
    /**
     * Obtém o código postal do cliente.
     *
     * @return O código postal do cliente.
     */
    public String getPostalCode() {
        return PostalCode;
    }
    /**
     * Define o código postal do cliente.
     *
     * @param postalCode O código postal do cliente a definir.
     */
    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }
    /**
     * Obtém a cidade do cliente.
     *
     * @return A cidade do cliente.
     */
    public String getCity() {
        return City;
    }

    /**
     * Define a cidade do cliente.
     *
     * @param city A cidade do cliente a definir.
     */
    public void setCity(String city) {
        City = city;
    }

    /**
     * Obtém o país do cliente.
     *
     * @return O país do cliente.
     */
    public String getCountry() {
        return Country;
    }
    /**
     * Define o país do cliente.
     *
     * @param country O país do cliente a definir.
     */
    public void setCountry(String country) {
        Country = country;
    }
    /**
     * Obtém o número de identificação fiscal do cliente.
     *
     * @return O número de identificação fiscal do cliente.
     */
    public String getTaxIdentificationNumber() {
        return TaxIdentificationNumber;
    }
    /**
     * Define o número de identificação fiscal do cliente.
     *
     * @param taxIdentificationNumber O número de identificação fiscal do cliente a definir.
     */
    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        TaxIdentificationNumber = taxIdentificationNumber;
    }
    /**
     * Verifica se o cliente está ativo.
     *
     * @return true se o cliente estiver ativo, false caso contrário.
     */
    public boolean isActive() {
        return Active;
    }
    /**
     * Define o estado de atividade do cliente.
     *
     * @param active O estado de atividade do cliente a definir.
     */
    public void setActive(boolean active) {
        Active = active;
    }
    /**
     * Constrói um novo Cliente com os detalhes especificados.
     *
     * @param id                      O ID do cliente.
     * @param groupId                 O ID do grupo do cliente.
     * @param name                    O nome do cliente.
     * @param email                   O email do cliente.
     * @param address1                A primeira linha do endereço do cliente.
     * @param address2                A segunda linha do endereço do cliente.
     * @param postalCode              O código postal do cliente.
     * @param city                    A cidade do cliente.
     * @param country                 O país do cliente.
     * @param taxIdentificationNumber O número de identificação fiscal do cliente.
     * @param active                  O estado de atividade do cliente.
     */
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


