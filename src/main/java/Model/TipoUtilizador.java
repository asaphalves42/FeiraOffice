package Model;

public enum TipoUtilizador {
    Operador(2),
    Administrador(1),
    Fornecedor(3),
    Default(0);

    private int value;

    public int getValue() {
        return value;
    }

    TipoUtilizador(int i) {
        this.value = i;
    }
}
