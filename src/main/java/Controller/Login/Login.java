package Controller.Login;

import Controller.Administrador.MenuAdm;
import DAL.LerUtilizadores;
import Controller.Fornecedor.MenuFornecedor;
import Controller.Operador.MenuOperador;
import Model.TipoUtilizador;
import Model.Utilizador;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe que lida com os dados de acesso, email e password, e faz o correto acesso ao sistema.
 */
public class Login {

    BaseDados baseDados = new BaseDados();
    @FXML
    private Button btnLogin;

    @FXML
    private TextField labelEmail;

    @FXML
    private PasswordField labelPassword;


    @FXML
    void clickLogin() throws IOException, SQLException {
        Utilizador utilizador = getUtilizador();
        abrirMenuCorrespondente(utilizador);
    }

    /**
     * Abre o menu correspondente ao tipo de utilizador após a autenticação bem-sucedida. Fecha a janela de login
     * atual e exibe a interface gráfica do menu apropriado.
     *
     * @param utilizador O utilizador autenticado.
     * @throws IOException Se ocorrer um erro durante o carregamento da interface gráfica do menu.
     */
    private void abrirMenuCorrespondente(Utilizador utilizador) throws IOException {
        String resource = null;
        String title = null;

        if(utilizador == null) {
            Mensagens.Erro("Erro", "Credenciais inválidas. Ocorreu um erro ao realizar login!");
        }

        else if (utilizador.getTipo() == TipoUtilizador.Administrador) {
            resource = "/lp3/Views/Admin/menuAdm.fxml";
            title = "MENU ADMINISTRADOR!";

            abrirMenu(resource, title, utilizador);
            fecharJanelaAtual();

        } else if (utilizador.getTipo() == TipoUtilizador.Operador) {
            resource = "/lp3/Views/Operador/menuOperador.fxml";
            title = "MENU OPERADOR!";

            abrirMenu(resource, title, utilizador);
            fecharJanelaAtual();

        } else if (utilizador.getTipo() == TipoUtilizador.Fornecedor) {
            resource = "/lp3/Views/Fornecedor/menuFornecedor.fxml";
            title = "MENU FORNECEDOR!";

            abrirMenu(resource, title, utilizador);
            fecharJanelaAtual();
        } else {
            Mensagens.Erro("Erro", "Credenciais inválidas. Ocorreu um erro ao realizar login!");
        }

    }

    /**
     * Abre a interface gráfica do menu correspondente ao tipo de utilizador e inicia os dados necessários.
     *
     * @param resource   O caminho do recurso FXML do menu.
     * @param title      O título da janela do menu.
     * @param utilizador O utilizador autenticado.
     * @throws IOException Se ocorrer um erro durante o carregamento da interface gráfica do menu.
     */
    private void abrirMenu(String resource, String title, Utilizador utilizador) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());

        if (utilizador.getTipo() == TipoUtilizador.Administrador) {
            MenuAdm menuAdm = fxmlLoader.getController();
            menuAdm.iniciaData(utilizador);
        } else if (utilizador.getTipo() == TipoUtilizador.Operador) {
            MenuOperador menuOperador = fxmlLoader.getController();
            menuOperador.iniciaData(utilizador);
        } else if (utilizador.getTipo() == TipoUtilizador.Fornecedor) {
            MenuFornecedor menuFornecedor = fxmlLoader.getController();
            menuFornecedor.iniciaData(utilizador);
        }

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Fecha a janela atual em que o botão de login está localizado.
     */
    private void fecharJanelaAtual() {
        Stage currentStage = (Stage) btnLogin.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Obtém o utilizador com base nas credenciais inseridas na interface gráfica de login.
     *
     * @return O utilizador autenticado ou null se as credenciais forem inválidas.
     * @throws SQLException Se ocorrer um erro durante a leitura da base de dados.
     */
    private Utilizador getUtilizador() throws SQLException {
        // Instância da classe para ler os utilizadores.
        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        // Recupera o nome de usuário e senha inseridos dos elementos da interface gráfica.
        String username = labelEmail.getText(); // Nome de utilizador
        String password = labelPassword.getText(); // Senha

        // Verifica as credenciais de 'login' e determina o tipo de utilizador.
        return lerUtilizadores.verificarLoginUtilizador(baseDados,username, password);
    }


    /**
     * Manipula o evento de pressionar a tecla Enter. Chama o método de login quando a tecla Enter é pressionada.
     *
     * @param keyEvent O evento de tecla pressionada.
     * @throws SQLException Se ocorrer um erro durante a leitura da base de dados.
     * @throws IOException Se ocorrer um erro durante a execução do login.
     */
    public void clickEnter(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode().toString().equals("ENTER")){
            clickLogin();
        }
    }


}






















