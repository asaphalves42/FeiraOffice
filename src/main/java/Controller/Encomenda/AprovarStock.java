package Controller.Encomenda;

import Controller.DAL.LerEncomenda;
import Model.Encomenda;
import Model.Fornecedor;
import Model.LinhaEncomenda;
import Model.Pais;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AprovarStock implements Initializable {

    @FXML
    private SplitPane anchorPaneFuncoesFornc;

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnRecusar;

    @FXML
    private TableView<Encomenda> tableViewEncomendas;

    @FXML
    private TableView<LinhaEncomenda> tableViewLinhasEncomenda;

    ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tabelaEncomendas();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void tabelaEncomendas() throws IOException {

        LerEncomenda lerEncomenda = new LerEncomenda();
       encomendas.addAll(lerEncomenda.lerEncomendaDaBaseDeDados());


        if(!encomendas.isEmpty()) {
            // Defina as colunas da tabela
            TableColumn<Encomenda, Integer> colunaId = new TableColumn<>("ID");
            TableColumn<Encomenda, String> colunaReferencia = new TableColumn<>("Referência");
            TableColumn<Encomenda, LocalDate> colunaData = new TableColumn<>("Data");
            TableColumn<Encomenda, Fornecedor> colunaIdFornecedor = new TableColumn<>("Id do Fornecedor");
            TableColumn<Encomenda, Pais> colunaidPais = new TableColumn<>("País");
            TableColumn<Encomenda, Double> colunaTotalTaxa = new TableColumn<>("Total dos impostos");
            TableColumn<Encomenda, Double> colunaIncidencia = new TableColumn<>("Total sem impostos");
            TableColumn<Encomenda, Double> colunaTotal = new TableColumn<>("Valor total");
            TableColumn<Encomenda, Integer> colunaEstado = new TableColumn<>("Estado");

            // Associe as colunas às propriedades da classe Encomenda
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));

            colunaIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));

            colunaIdFornecedor.setCellFactory(column -> {
                return new TableCell<Encomenda, Fornecedor>() {
                    @Override
                    protected void updateItem(Fornecedor fornecedor, boolean empty) {
                        super.updateItem(fornecedor, empty);

                        if (fornecedor == null || empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(fornecedor.getIdExterno())); // ID do fornecedor
                        }
                    }
                };
            });

            colunaidPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
            colunaTotalTaxa.setCellValueFactory(new PropertyValueFactory<>("valorImposto"));

            colunaIncidencia.setCellValueFactory(new PropertyValueFactory<>("valorIncidencia"));

            colunaTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
            colunaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));


            // Adicione as colunas à tabela
            tableViewEncomendas.getColumns().add(colunaId);
            tableViewEncomendas.getColumns().add(colunaReferencia);
            tableViewEncomendas.getColumns().add(colunaData);
            tableViewEncomendas.getColumns().add(colunaIdFornecedor);
            tableViewEncomendas.getColumns().add(colunaidPais);
            tableViewEncomendas.getColumns().add(colunaTotalTaxa);
            tableViewEncomendas.getColumns().add(colunaIncidencia);
            tableViewEncomendas.getColumns().add(colunaTotal);
            tableViewEncomendas.getColumns().add(colunaEstado);

            tableViewEncomendas.setItems(encomendas);


        } else{
            Mensagens.Erro("Erro!","Erro ao ler tabela!");
        }

    }



    @FXML
    void clickAprovar() {





    }

    @FXML
    void clickRecusar() {


    }


}
