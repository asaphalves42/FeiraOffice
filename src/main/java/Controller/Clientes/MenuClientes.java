package Controller.Clientes;

import DAL.LerClientes;
import Model.Cliente;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MenuClientes {
    LerClientes lerClientes = new LerClientes();

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRecusar;

    @FXML
    private TableView<Cliente> tableViewClientes;

    ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tabelaClientes();
    }

    public void tabelaClientes() throws IOException {

        clientes.addAll(lerClientes.lerClientesDaApi());

        if(!clientes.isEmpty()){
            if(tableViewClientes.getItems().isEmpty()){
                TableColumn<Cliente, String> colunaId = new TableColumn<>("ID");
                TableColumn<Cliente, String> nomeColuna = new TableColumn<>("Nome");
                TableColumn<Cliente, String> emailColuna = new TableColumn<>("E-mail");
                TableColumn<Cliente, String> colunaMorada1 = new TableColumn<>("Morada 1");
                TableColumn<Cliente, String> colunaMorada2 = new TableColumn<>("Morada 2");
                TableColumn<Cliente, String> colunaCodPostal = new TableColumn<>("Código postal");
                TableColumn<Cliente, String> colunaCidade = new TableColumn<>("Cidade");
                TableColumn<Cliente, String> colunaPais = new TableColumn<>("País");
                TableColumn<Cliente, String> colunaNif = new TableColumn<>("NIF");

                // Associe as propriedades dos objetos Cliente às colunas
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
                emailColuna.setCellValueFactory(new PropertyValueFactory<>("email"));
                colunaMorada1.setCellValueFactory(new PropertyValueFactory<>("morada1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("morada2"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("codPostal"));
                colunaCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
                colunaPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
                colunaNif.setCellValueFactory(new PropertyValueFactory<>("nif"));

                // Adicione as colunas à TableView
                tableViewClientes.getColumns().addAll(colunaId, nomeColuna, emailColuna, colunaMorada1, colunaMorada2, colunaCodPostal,
                        colunaCidade, colunaPais, colunaNif);

                // Adicione os dados à TableView
                tableViewClientes.setItems(clientes);
            } else {
                Mensagens.Erro("Erro!", "Erro ao ler tabela ou tabela vazia!");
            }

        }

    }

    @FXML
    void clickAprovar(ActionEvent event) {

    }

    @FXML
    void clickEliminar(ActionEvent event) {

    }

    @FXML
    void clickRecusar(ActionEvent event) {

    }

}
