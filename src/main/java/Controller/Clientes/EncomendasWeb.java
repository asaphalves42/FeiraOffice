package Controller.Clientes;

import DAL.LerOrders;
import Model.Cliente;
import Model.OrderWeb;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleStringProperty;
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
                TableColumn<OrderWeb, String> emailCliente = new TableColumn<>("E-mail cliente");

                // Associe as propriedades dos objetos Cliente às colunas
                colunaId.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));
                colunaData.setCellValueFactory(new PropertyValueFactory<>("Date"));
                colunaIdCliente.setCellValueFactory(new PropertyValueFactory<>("Client"));
                colunaNomeCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().Id));
                emailCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getEmail()));

                // Adicione as colunas à TableView
                tableViewEncomendasWeb.getColumns().addAll(colunaId, colunaData, colunaIdCliente, colunaNomeCliente, emailCliente);

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