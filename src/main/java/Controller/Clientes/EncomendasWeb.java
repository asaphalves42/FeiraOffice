package Controller.Clientes;

import DAL.LerOrders;
import Model.Cliente;
import Model.OrderWeb;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EncomendasWeb {

    LerOrders lerOrders = new LerOrders();

    @FXML
    private AnchorPane anchorPaneEncomendasWeb;

    @FXML
    private Button btnAprovarEncomenda;

    @FXML
    private TableView<OrderWeb> tableViewEncomendasWeb;
    @FXML
    private TableView<?> tableViewProdutos;


    ObservableList<OrderWeb> orders = FXCollections.observableArrayList();

    public void tabelaEncomendasWeb() throws IOException {

        orders.addAll(lerOrders.lerOrders());

        if (!orders.isEmpty()) {
            if (tableViewEncomendasWeb.getItems().isEmpty()) {
                TableColumn<OrderWeb, String> colunaId = new TableColumn<>("ID da Order");
                TableColumn<OrderWeb, String> colunaData = new TableColumn<>("Data");
                TableColumn<OrderWeb, String> colunaIdCliente = new TableColumn<>("Id cliente");
                TableColumn<OrderWeb, String> colunaNomeCliente = new TableColumn<>("Nome cliente");
                TableColumn<OrderWeb, String> colunaMorada2 = new TableColumn<>("Morada 2");
                TableColumn<OrderWeb, String> colunaCodPostal = new TableColumn<>("Código postal");
                TableColumn<OrderWeb, String> colunaCidade = new TableColumn<>("Cidade");
                TableColumn<OrderWeb, String> colunaPais = new TableColumn<>("País");
                TableColumn<OrderWeb, String> colunaNif = new TableColumn<>("NIF");
                TableColumn<OrderWeb, String> colunaEstado = new TableColumn<>("Estado");

                // Associe as propriedades dos objetos Cliente às colunas
                colunaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
                colunaData.setCellValueFactory(new PropertyValueFactory<>("Name"));
                colunaIdCliente.setCellValueFactory(new PropertyValueFactory<>("Email"));
                colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("Address1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("Address2"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
                colunaCidade.setCellValueFactory(new PropertyValueFactory<>("City"));
                colunaPais.setCellValueFactory(new PropertyValueFactory<>("Country"));
                colunaNif.setCellValueFactory(new PropertyValueFactory<>("TaxIdentificationNumber"));
                colunaEstado.setCellValueFactory(new PropertyValueFactory<>("Active"));

                // Adicione as colunas à TableView
                tableViewEncomendasWeb.getColumns().addAll(colunaId, colunaData, colunaIdCliente, colunaNomeCliente, colunaMorada2, colunaCodPostal,
                        colunaCidade, colunaPais, colunaNif, colunaEstado);

                // Adicione os dados à TableView
                tableViewEncomendasWeb.setItems(orders);
            } else {
                Mensagens.Erro("Erro!", "Erro ao ler tabela ou tabela vazia!");
            }

        }
    }

    @FXML
    void clickAprovarEncomenda(ActionEvent event) {



    }

}