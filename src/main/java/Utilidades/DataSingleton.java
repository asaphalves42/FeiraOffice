package Utilidades;

import Model.Encomenda;
import Model.Fornecedor;
import Model.UtilizadorOperador;


/**
 * Classe com dados e contrutores para o método DataSingleton.
 */
public class DataSingleton {

    private static DataSingleton instance = null;
    private Fornecedor dataFornecedor;
    private UtilizadorOperador dataOperador;

    private Encomenda dataEncomenda;

    private DataSingleton() {
        // Private constructor to prevent other classes from instantiating
    }

    public Encomenda getDataEncomenda() {
        return dataEncomenda;
    }

    public void setDataEncomenda(Encomenda dataEncomenda) {
        this.dataEncomenda = dataEncomenda;
    }

    public static void setInstance(DataSingleton instance) {
        DataSingleton.instance = instance;
    }

    public UtilizadorOperador getDataOperador() {
        return dataOperador;
    }

    public void setDataOperador(UtilizadorOperador dataOperador) {
        this.dataOperador = dataOperador;
    }

    public Fornecedor getDataFornecedor() {
        return dataFornecedor;
    }

    public void setDataFornecedor(Fornecedor dataFornecedor) {
        this.dataFornecedor = dataFornecedor;
    }

    public static DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

}
