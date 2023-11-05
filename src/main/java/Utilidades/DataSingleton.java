package Utilidades;

public class DataSingleton {

    private static DataSingleton instance = null;

    private DataSingleton() {
        // Private constructor to prevent other classes from instantiating
    }

    public static DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

}
