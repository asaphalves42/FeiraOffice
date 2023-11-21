package TestesUnitarios.Login;



import Model.Utilizador;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.SQLException;

public interface UtilizadorRepository {
    ObservableList<Utilizador> lerUtilizadoresDaBaseDeDados() throws IOException;
    Utilizador verificarLoginUtilizador(String email, String password) throws SQLException;

}