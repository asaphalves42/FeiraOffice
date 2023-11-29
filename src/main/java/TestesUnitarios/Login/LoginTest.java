package TestesUnitarios.Login;

import Controller.DAL.LerUtilizadores;
import Model.Utilizador;
import Model.UtilizadorAdm;
import Model.UtilizadorFornecedor;
import Model.UtilizadorOperador;
import Utilidades.BaseDados;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class LoginTest {

    private LerUtilizadores lerUtilizadores;
    BaseDados baseDados = new BaseDados();

    @Before
    public void setUp() {
        // Inicializar o LerUtilizadores sem usar StubUtilizadorRepository
        lerUtilizadores = new LerUtilizadores();
    }



    @Test
    public void testVerificarLoginUtilizadorAdm() throws SQLException {
        // Adicionar lógica de simulação de dados diretamente no LerUtilizadores
        lerUtilizadores.setUtilizadoresSimulados(FXCollections.observableArrayList(
                new UtilizadorAdm(1, "admin@admin.pt", "123"),          // Simula um administrador
                new UtilizadorFornecedor(2, "fornecedor", "senha"),  // Simula um fornecedor
                new UtilizadorOperador(2, "fornecedor", "senha")

        ));

        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(baseDados,"admin@admin.pt", "123");

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorAdm.class, utilizador.getClass());
    }

    @Test
    public void testVerificarLoginUtilizadorFornecedor() throws SQLException {
        // Adicionar lógica de simulação de dados diretamente no LerUtilizadores
        lerUtilizadores.setUtilizadoresSimulados(FXCollections.observableArrayList(
                new UtilizadorAdm(1, "admin@admin.pt", "123"),
                new UtilizadorFornecedor(3, "papeldoporto@fornecedor.pt", "123"),
                new UtilizadorOperador(2, "fornecedor", "senha"),
                new UtilizadorFornecedor(3,"rjr@isep.ipp.pt", "luc")
        ));

        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(baseDados,"admin@admin.pt", "123");

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorAdm.class, utilizador.getClass());
    }

    @Test
    public void testVerificarLoginOperador() throws SQLException {
        // Adicionar lógica de simulação de dados diretamente no LerUtilizadores
        lerUtilizadores.setUtilizadoresSimulados(FXCollections.observableArrayList(
                new UtilizadorAdm(1, "admin@admin.pt", "123"),
                new UtilizadorFornecedor(3, "papeldoporto@fornecedor.pt", "123"),
                new UtilizadorOperador(2, "teste@operador.pt", "123")
        ));

        // Executar o método a ser testado
        Utilizador utilizador = lerUtilizadores.verificarLoginUtilizador(baseDados,"teste@operador.pt", "123");

        // Verificar se o método retornou o utilizador esperado
        assertEquals(UtilizadorOperador.class, utilizador.getClass());
    }


}
