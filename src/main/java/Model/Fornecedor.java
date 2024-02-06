package Model;

import eu.hansolo.toolbox.properties.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe com atributos, getters e setters referentes ao fornecedor.
 */
public class Fornecedor {
    /**
     * O utilizador fornecedor.
     */
    private UtilizadorFornecedor utilizador;

    /**
     * O ID do fornecedor.
     */
    private int id;

    /**
     * O ID externo do fornecedor.
     */
    private String idExterno;

    /**
     * O nome do fornecedor.
     */
    private String nome;

    /**
     * A primeira linha do endereço do fornecedor.
     */
    private String morada1;

    /**
     * A segunda linha do endereço do fornecedor.
     */
    private String morada2;

    /**
     * A localidade do fornecedor.
     */
    private String localidade;

    /**
     * O código postal do fornecedor.
     */
    private String codigoPostal;

    /**
     * O país do fornecedor.
     */
    private Pais idPais;

    /**
     * O utilizador do fornecedor.
     */
    private UtilizadorFornecedor idUtilizador;

    /**
     * O BIC (Código de Identificação Bancária) do fornecedor.
     */
    private String bic;

    /**
     * O IBAN (Número Internacional de Conta Bancária) do fornecedor.
     */
    private String iban;

    /**
     * O número da conta do fornecedor.
     */
    private String conta;

    /**
     * Construtor padrão.
     */
    public Fornecedor() {

    }
    /**
     * Construtor que define o ID externo do fornecedor.
     *
     * @param id O ID externo do fornecedor.
     */
    public Fornecedor(String id){
        this.idExterno = id;
    }
    /**
     * Construtor que inicializa todos os atributos do fornecedor.
     *
     * @param id           O ID do fornecedor.
     * @param nome         O nome do fornecedor.
     * @param idExterno    O ID externo do fornecedor.
     * @param morada1      A primeira linha do endereço do fornecedor.
     * @param morada2      A segunda linha do endereço do fornecedor.
     * @param localidade   A localidade do fornecedor.
     * @param codigoPostal O código postal do fornecedor.
     * @param idPais       O país do fornecedor.
     * @param idUtilizador O utilizador do fornecedor.
     * @param bic          O BIC (Código de Identificação Bancária) do fornecedor.
     * @param conta        O número da conta do fornecedor.
     * @param iban         O IBAN (Número Internacional de Conta Bancária) do fornecedor.
     */
    public Fornecedor(int id, String nome, String idExterno, String morada1, String morada2, String localidade, String codigoPostal, Pais idPais,
                      UtilizadorFornecedor idUtilizador, String bic, String conta, String iban) {
        this.id = id;
        this.nome = nome;
        this.idExterno = idExterno;
        this.morada1 = morada1;
        this.morada2 = morada2;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.idPais = idPais;
        this.idUtilizador = idUtilizador;
        this.bic = bic;
        this.conta = conta;
        this.iban =iban;
    }
    /**
     * Construtor que inicializa um fornecedor com alguns detalhes.
     *
     * @param id O ID do fornecedor.
     * @param nome O nome do fornecedor.
     * @param idExterno O ID externo do fornecedor.
     * @param morada1 A primeira linha do endereço do fornecedor.
     * @param morada2 A segunda linha do endereço do fornecedor.
     * @param localidade A localidade do fornecedor.
     * @param codigoPostal O código postal do fornecedor.
     * @param idPais O país do fornecedor.
     * @param idUtilizador O utilizador do fornecedor.
     */
    public Fornecedor(int id, String nome, String idExterno, String morada1, String morada2, String localidade, String codigoPostal, Pais idPais, UtilizadorFornecedor idUtilizador) {
        this.id = id;
        this.nome = nome;
        this.idExterno = idExterno;
        this.morada1 = morada1;
        this.morada2 = morada2;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.idPais = idPais;
        this.idUtilizador = idUtilizador;
    }
    /**
     * Construtor que inicializa um fornecedor com o nome, ID externo e país.
     *
     * @param nome O nome do fornecedor.
     * @param idExterno O ID externo do fornecedor.
     * @param idPais O país do fornecedor.
     */
    public Fornecedor(String nome, String idExterno, Pais idPais){
        this.nome = nome;
        this.idExterno = idExterno;
        this.idPais = idPais;
    }
    /**
     * Construtor que inicializa um fornecedor com o nome e ID externo.
     *
     * @param nome O nome do fornecedor.
     * @param idExterno O ID externo do fornecedor.
     */
    public Fornecedor(String nome, String idExterno){
        this.nome = nome;
        this.idExterno = idExterno;
    }
    /**
     * Construtor que inicializa um fornecedor com  alguns  detalhes, incluindo conta, BIC e IBAN.
     *
     * @param idInterno O ID interno do fornecedor.
     * @param nomeFornecedor O nome do fornecedor.
     * @param idExterno O ID externo do fornecedor.
     * @param morada1 A primeira linha do endereço do fornecedor.
     * @param morada2 A segunda linha do endereço do fornecedor.
     * @param localidade A localidade do fornecedor.
     * @param codigoPostal O código postal do fornecedor.
     * @param pais O país do fornecedor.
     * @param conta O número da conta do fornecedor.
     * @param bic O BIC do fornecedor.
     * @param iban O IBAN do fornecedor.
     */
    public Fornecedor(int idInterno, String nomeFornecedor, String idExterno, String morada1, String morada2, String localidade, String codigoPostal, Pais pais,
                      String conta, String bic, String iban) {
        this.id = idInterno;
        this.nome = nomeFornecedor;
        this.idExterno = idExterno;
        this.morada1 = morada1;
        this.morada2 = morada2;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.idPais = pais;
        this.conta = conta;
        this.bic = bic;
        this.iban = iban;
    }
    public Fornecedor(int id, String nome, String idExterno, String morada1, String morada2) {
    }
    /**
     * Retorna o BIC (Código de Identificação Bancária) do fornecedor.
     *
     * @return O BIC do fornecedor.
     */
    public String getBic() {
        return bic;
    }
    /**
     * Define o BIC (Código de Identificação Bancária) do fornecedor.
     *
     * @param bic O BIC do fornecedor.
     */
    public void setBic(String bic) {
        this.bic = bic;
    }
    /**
     * Retorna o IBAN (Número Internacional de Conta Bancária) do fornecedor.
     *
     * @return O IBAN do fornecedor.
     */
    public String getIban() {
        return iban;
    }
    /**
     * Define o IBAN (Número Internacional de Conta Bancária) do fornecedor.
     *
     * @param iban O IBAN do fornecedor.
     */
    public void setIban(String iban) {
        this.iban = iban;
    }
    /**
     * Retorna o número da conta do fornecedor.
     *
     * @return O número da conta do fornecedor.
     */
    public String getConta() {
        return conta;
    }
    /**
     * Define o número da conta do fornecedor.
     *
     * @param conta O número da conta do fornecedor.
     */
    public void setConta(String conta) {
        this.conta = conta;
    }
    /**
     * Retorna o ID externo do fornecedor.
     *
     * @return O ID externo do fornecedor.
     */
    public String getIdExterno() {
        return idExterno;
    }
    /**
     * Define o ID externo do fornecedor.
     *
     * @param idExterno O ID externo do fornecedor.
     */
    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }
    /**
     * Retorna o ID do fornecedor.
     *
     * @return O ID do fornecedor.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o ID do fornecedor.
     *
     * @param id O ID do fornecedor.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Retorna o nome do fornecedor.
     *
     * @return O nome do fornecedor.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome do fornecedor.
     *
     * @param nome O nome do fornecedor.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Retorna a primeira linha do endereço do fornecedor.
     *
     * @return A primeira linha do endereço do fornecedor.
     */
    public String getMorada1() {
        return morada1;
    }

    /**
     * Define a primeira linha do endereço do fornecedor.
     *
     * @param morada1 A primeira linha do endereço do fornecedor.
     */
    public void setMorada1(String morada1) {
        this.morada1 = morada1;
    }
    /**
     * Retorna a segunda linha do endereço do fornecedor.
     *
     * @return A segunda linha do endereço do fornecedor.
     */
    public String getMorada2() {
        return morada2;
    }
    /**
     * Define a segunda linha do endereço do fornecedor.
     *
     * @param morada2 A segunda linha do endereço do fornecedor.
     */
    public void setMorada2(String morada2) {
        this.morada2 = morada2;
    }

    /**
     * Retorna a localidade do fornecedor.
     *
     * @return A localidade do fornecedor.
     */
    public String getLocalidade() {
        return localidade;
    }
    /**
     * Define a localidade do fornecedor.
     *
     * @param localidade A localidade do fornecedor.
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Retorna o código postal do fornecedor.
     *
     * @return O código postal do fornecedor.
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Define o código postal do fornecedor.
     *
     * @param codigoPostal O código postal do fornecedor.
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    /**
     * Retorna o país do fornecedor.
     *
     * @return O país do fornecedor.
     */
    public Pais getIdPais() {
        return idPais;
    }
    /**
     * Define o país do fornecedor.
     *
     * @param idPais O país do fornecedor.
     */
    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }
    /**
     * Retorna o utilizador do fornecedor.
     *
     * @return O utilizador do fornecedor.
     */
    public UtilizadorFornecedor getIdUtilizador() {
        return idUtilizador;
    }
    /**
     * Define o utilizador do fornecedor.
     *
     * @param idUtilizador O utilizador do fornecedor.
     */
    public void setIdUtilizador(UtilizadorFornecedor idUtilizador) {
        this.idUtilizador = idUtilizador;
    }
    /**
     * Retorna o utilizador fornecedor.
     *
     * @return O utilizador fornecedor.
     */
    public UtilizadorFornecedor getUtilizador() {
        return utilizador;
    }

    /**
     * Define o utilizador fornecedor.
     *
     * @param utilizador O utilizador fornecedor.
     */
    public void setUtilizador(UtilizadorFornecedor utilizador) {
        this.utilizador = utilizador;
    }
}
