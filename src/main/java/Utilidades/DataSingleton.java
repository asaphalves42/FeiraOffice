package Utilidades;

import Model.Fornecedor;

public class DataSingleton {

    private static DataSingleton instance = null;
    private Fornecedor dataFornecedor;

    private DataSingleton() {
        // Private constructor to prevent other classes from instantiating
    }

    public static void setInstance(DataSingleton instance) {
        DataSingleton.instance = instance;
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
