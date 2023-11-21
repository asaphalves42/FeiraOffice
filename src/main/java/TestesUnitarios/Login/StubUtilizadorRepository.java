// Arquivo: StubUtilizadorRepository.java
package TestesUnitarios.Login;

import Model.Utilizador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StubUtilizadorRepository implements UtilizadorRepository {

    private ObservableList<Utilizador> utilizadores;
    private Utilizador utilizador;

    public StubUtilizadorRepository(ObservableList<Utilizador> utilizadores, Utilizador utilizador) {
        this.utilizadores = utilizadores;
        this.utilizador = utilizador;
    }

    @Override
    public ObservableList<Utilizador> lerUtilizadoresDaBaseDeDados() {
        return utilizadores != null ? utilizadores : FXCollections.observableArrayList();
    }

    @Override
    public Utilizador verificarLoginUtilizador(String email, String password) {
        return utilizador;
    }

    // Outros métodos da interface...
}
