package Model;

import eu.hansolo.toolbox.properties.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe com atributos, getters e setters referentes ao fornecedor.
 */
public class Fornecedor {
    private UtilizadorFornecedor utilizador;
    private int id;
    private String idExterno;
    private String nome;
    private String morada1;
    private String morada2;
    private String localidade;
    private String codigoPostal;
    private Pais idPais;
    private UtilizadorFornecedor idUtilizador;

    public Fornecedor() {

    }

    public Fornecedor(String id){
        this.idExterno = id;
    }

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

    public Fornecedor(String nome, String idExterno, Pais idPais){
        this.nome = nome;
        this.idExterno = idExterno;
        this.idPais = idPais;
    }

    public Fornecedor(int idInterno, String nomeFornecedor, String idExterno, String morada1, String morada2, String localidade, String codigoPostal, Pais pais) {
        this.id = idInterno;
        this.nome = nomeFornecedor;
        this.idExterno = idExterno;
        this.morada1 = morada1;
        this.morada2 = morada2;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.idPais = pais;
    }


    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada1() {
        return morada1;
    }

    public void setMorada1(String morada1) {
        this.morada1 = morada1;
    }

    public String getMorada2() {
        return morada2;
    }

    public void setMorada2(String morada2) {
        this.morada2 = morada2;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public UtilizadorFornecedor getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(UtilizadorFornecedor idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public UtilizadorFornecedor getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UtilizadorFornecedor utilizador) {
        this.utilizador = utilizador;
    }
}
