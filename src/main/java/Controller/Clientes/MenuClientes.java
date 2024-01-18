package Controller.Clientes;

import DAL.LerClientes;
import Model.Cliente;
import Utilidades.API;
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

import static Utilidades.API.deleteCliente;

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

        if (!clientes.isEmpty()) {
            if (tableViewClientes.getItems().isEmpty()) {
                TableColumn<Cliente, String> colunaId = new TableColumn<>("ID");
                TableColumn<Cliente, String> nomeColuna = new TableColumn<>("Nome");
                TableColumn<Cliente, String> emailColuna = new TableColumn<>("E-mail");
                TableColumn<Cliente, String> colunaMorada1 = new TableColumn<>("Morada 1");
                TableColumn<Cliente, String> colunaMorada2 = new TableColumn<>("Morada 2");
                TableColumn<Cliente, String> colunaCodPostal = new TableColumn<>("Código postal");
                TableColumn<Cliente, String> colunaCidade = new TableColumn<>("Cidade");
                TableColumn<Cliente, String> colunaPais = new TableColumn<>("País");
                TableColumn<Cliente, String> colunaNif = new TableColumn<>("NIF");
                TableColumn<Cliente, String> colunaEstado = new TableColumn<>("Estado");

                // Associe as propriedades dos objetos Cliente às colunas
                colunaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
                nomeColuna.setCellValueFactory(new PropertyValueFactory<>("Name"));
                emailColuna.setCellValueFactory(new PropertyValueFactory<>("Email"));
                colunaMorada1.setCellValueFactory(new PropertyValueFactory<>("Address1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("Address2"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
                colunaCidade.setCellValueFactory(new PropertyValueFactory<>("City"));
                colunaPais.setCellValueFactory(new PropertyValueFactory<>("Country"));
                colunaNif.setCellValueFactory(new PropertyValueFactory<>("TaxIdentificationNumber"));
                colunaEstado.setCellValueFactory(new PropertyValueFactory<>("Active"));

                // Adicione as colunas à TableView
                tableViewClientes.getColumns().addAll(colunaId, nomeColuna, emailColuna, colunaMorada1, colunaMorada2, colunaCodPostal,
                        colunaCidade, colunaPais, colunaNif, colunaEstado);

                // Adicione os dados à TableView
                tableViewClientes.setItems(clientes);
            } else {
                Mensagens.Erro("Erro!", "Erro ao ler tabela ou tabela vazia!");
            }

        }

    }

    @FXML
    void clickAprovar(ActionEvent event) throws IOException {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {

            String idCliente = clienteSelecionado.getId();

            try {

                String data = "{ \"Active\": true }";
                API.updateCliente(idCliente, data);
                Mensagens.Informacao("Cliente Aprovado!", "Cliente Aprovado com sucesso");


            } catch (IOException e) {
                Mensagens.Erro("Erro!", "Erro ao aprovar cliente!");
            }
        }
    }


    @FXML
    void clickEliminar(ActionEvent event) throws IOException {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {

            String idCliente = clienteSelecionado.getId();

            try {


                deleteCliente(idCliente);
                Mensagens.Informacao("Cliente Eliminado!", "Cliente Eliminado com sucesso");


            } catch (IOException e) {
                Mensagens.Erro("Erro!", "Erro ao Eliminar cliente!");
            }
        }

    }



    @FXML
    void clickRecusar(ActionEvent event) throws IOException {

        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {

            String idCliente = clienteSelecionado.getId();

            try {

                String data = "{ \"Active\": false }";
                API.updateCliente(idCliente, data);
                Mensagens.Informacao("Cliente Recusado!", "Cliente Recusado com sucesso");



            } catch (IOException e) {
                Mensagens.Erro("Erro!", "Erro ao aprovar cliente!");
            }
        }

    }

}

