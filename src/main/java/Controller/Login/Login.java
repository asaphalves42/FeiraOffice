package Controller.Login;

import Controller.DAL.LerUtilizadores;
import Model.TipoUtilizador;
import Utilidades.Mensagens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Login {


    @FXML
    private Button btnLogin;

    @FXML
    private TextField labelEmail;

    @FXML
    private PasswordField labelPassword;


    /**
     * Manipula o ‘login’ do utilizador quando o botão "Login" é clicado numa aplicação JavaFX.
     * Ele lê o nome de utilizador e senha inseridos, verifica-os num banco de dados e
     * abre o menu correspondente com base no tipo de utilizador ou exibe uma mensagem de erro.
     *
     * @param event Um ActionEvent representando o evento de clique no botão.
     *
     * @throws IOException Se ocorrer uma exceção de entrada/saída ao abrir o menu.
     * @throws SQLException Se ocorrer uma exceção SQL durante operações de banco de dados.
     */
    @FXML
    void clickLogin(ActionEvent event) throws IOException, SQLException {
        // Cria uma instância de LerUtilizadores para lidar com dados de utilizador.
        LerUtilizadores lerUtilizadores = new LerUtilizadores();

        // Recupera o nome de usuário e senha inseridos dos elementos da interface gráfica.
        String username = labelEmail.getText();
        String password = labelPassword.getText();

        // Verifica se os dados do utilizador são lidos com sucesso do banco de dados.
        if (lerUtilizadores.lerUtilizadoresDaBaseDeDados()) {
            // Verifica as credenciais de ‘login’ e determina o tipo de utilizador.
            TipoUtilizador tipo = lerUtilizadores.verificarLoginUtilizador(username, password);

            // Abre o menu correspondente com base no tipo de utilizador.
            if (tipo == TipoUtilizador.Administrador) {
                abrirMenu("/lp3/Views/Admin/menuAdm.fxml", "MENU ADMINISTRADOR!");
            } else if (tipo == TipoUtilizador.Operador) {
                abrirMenu("/lp3/Views/Operador/menuOperador.fxml", "MENU OPERADOR!");
            } else if (tipo == TipoUtilizador.Fornecedor) {
                abrirMenu("/lp3/Views/Fornecedor/menuFornecedor.fxml", "MENU FORNECEDOR!");
            } else {
                // Exibe uma mensagem de erro para credenciais inválidas.
                Mensagens.Erro("Erro", "Credenciais inválidas. Ocorreu um erro ao realizar login!");
            }

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //Fechar a janela de login
            currentStage.close();

        } else {
            // Exibe uma mensagem de erro para erro na recuperação de dados do banco de dados.
            Mensagens.Erro("Erro", "Ocorreu um erro ao realizar login!");
        }
    }
    private void abrirMenu(String resource, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


}






















