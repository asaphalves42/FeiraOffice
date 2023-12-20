package Controller.Operador;

import DAL.LerUtilizadores;
import Model.Utilizador;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe que mostra os operadores existentes no sistema e contém funções de manipulação dos dados, adicionar, editar e eliminar.
 */
public class MenuFuncOperador{
    LerUtilizadores lerUtilizadoresOperador = new LerUtilizadores();
    BaseDados baseDados = new BaseDados();
    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNovoOperador;

    @FXML
    private TableView<Utilizador> tableViewOperador;
    ObservableList<Utilizador> utilizador= FXCollections.observableArrayList();
    /**
     * Inicializa a interface, limpando e preenchendo a tabela de operadores.
     *
     * @throws IOException Se ocorrer um erro durante a leitura dos operadores.
     */
    public void initialize() throws IOException {
        tableViewOperador.getColumns().clear();
        tableViewOperador.getItems().clear();
        tabelaUtilizadoresOperador();
    }
    /**
     * Preenche a tabela de operadores com os dados da base de dados.
     *
     * @throws IOException Se ocorrer um erro durante a leitura dos operadores.
     */
    public void tabelaUtilizadoresOperador() throws IOException {


        utilizador.addAll(lerUtilizadoresOperador.lerOperadoresDaBaseDados());



            // Defina as colunas da tabela
            TableColumn<Utilizador, Integer> colunaId = new TableColumn<>("ID");
            TableColumn<Utilizador, String> colunaEmail = new TableColumn<>("E-mail");
            TableColumn<Utilizador, String> colunaPassword = new TableColumn<>("Password encriptada");
            TableColumn<Utilizador, String> colunaTipo = new TableColumn<>("Tipo de utilizador");

            // Associe as colunas às propriedades da classe UtilizadorOperador
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colunaPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

            // Adicione as colunas à tabela
            tableViewOperador.getColumns().add(colunaId);
            tableViewOperador.getColumns().add(colunaEmail);
            tableViewOperador.getColumns().add(colunaPassword);
            tableViewOperador.getColumns().add(colunaTipo);

            // Configure a fonte de dados da tabela
            tableViewOperador.setItems(utilizador);

    }

    /**
     * Manipula o evento de clique no botão "Novo Operador".
     *
     * @param event O evento de ação associado ao clique no botão "Novo Operador".
     * @throws IOException Se ocorrer um erro durante a exibição da janela de adição de operador.
     */
    @FXML
    void clickNovo(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Operador/dialogAdicionarOperador.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADICIONAR OPERADOR!");
        stage.setScene(scene);
        stage.showAndWait();


        DataSingleton data = DataSingleton.getInstance();
        utilizador.add(data.getDataOperador());
    }
    /**
     * Manipula o evento de clique no botão "Editar".
     *
     * @param event O evento de ação associado ao clique no botão "Editar".
     * @throws IOException Se ocorrer um erro durante a exibição da janela de edição de operador.
     */
    @FXML
    void clickEditar(ActionEvent event) throws IOException {
        Utilizador operadorSelecionado = tableViewOperador.getSelectionModel().getSelectedItem();

        if (operadorSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Operador/dialogEditarOperador.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("EDITAR OPERADOR");
            stage.setScene(scene);

            // Pass the selected operator to the controller of the edit dialog
            DialogEditarOperador editController = fxmlLoader.getController();
            editController.setOperador(operadorSelecionado);

            stage.showAndWait();

            // Update the table view after editing
            tableViewOperador.refresh();
        }
    }
    /**
     * Manipula o evento de clique no botão "Eliminar operador".
     * Remove o operador selecionado da base de dados e da lista de operadores exibida na tabela.
     */
    @FXML
    void clickEliminar() {
        Utilizador operadorSelecionado = tableViewOperador.getSelectionModel().getSelectedItem();

        if (operadorSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Eliminar operador");
            alert.setContentText("Tem certeza que deseja eliminar o operador selecionado?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {

                        boolean sucesso = lerUtilizadoresOperador.removerOperadorDaBaseDeDados(operadorSelecionado.getId());

                        if (sucesso) {
                            utilizador.remove(operadorSelecionado);
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }




}
