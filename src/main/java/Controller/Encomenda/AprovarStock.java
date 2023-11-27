package Controller.Encomenda;

import Controller.DAL.LerEncomenda;
import Model.*;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AprovarStock {

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
    ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();


    public void initialize() throws IOException {
        tabelaEncomendas();

    }


    public void tabelaEncomendas() throws IOException {

        try {

            LerEncomenda lerEncomenda = new LerEncomenda();
            encomendas.addAll(lerEncomenda.lerEncomendaDaBaseDeDados());


            if (!encomendas.isEmpty()) {
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


            }

        } catch (IOException e) {
            Mensagens.Erro("Erro!", "Erro ao ler tabela!");
        }

    }



    public void tabelaLinhasEncomenda(Encomenda encomenda) throws IOException {
        try {

            if (encomenda != null) {

                // Ler apenas as linhas de encomenda para a encomenda selecionada
                LerEncomenda lerEncomenda = new LerEncomenda();


                linhasEncomenda.addAll(lerEncomenda.lerLinhaEncomendaBaseDados(encomenda.getId()));

                if (!linhasEncomenda.isEmpty()) {
                    TableColumn<LinhaEncomenda, Integer> colunaId = new TableColumn<>("ID");
                    TableColumn<LinhaEncomenda, Encomenda> colunaIdEncomenda = new TableColumn<>("Id da encomenda");
                    TableColumn<LinhaEncomenda, Integer> colunaSequencia = new TableColumn<>("Sequência");
                    TableColumn<LinhaEncomenda, Produto> colunaidProduto = new TableColumn<>("Id do produto");
                    TableColumn<LinhaEncomenda, Integer> colunaQuantidade = new TableColumn<>("Quantidade");
                    TableColumn<LinhaEncomenda, Unidade> colunaIdUnidade = new TableColumn<>("Unidade");
                    TableColumn<LinhaEncomenda, Pais> colunaIdPais = new TableColumn<>("Taxa do país");
                    TableColumn<LinhaEncomenda, Double> colunaTotalTaxa = new TableColumn<>("Total taxa");
                    TableColumn<LinhaEncomenda, Double> colunaTotalIncidencia = new TableColumn<>("Total incidência");
                    TableColumn<LinhaEncomenda, Double> colunaTotalLinha = new TableColumn<>("Total da linha");

                    colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colunaIdEncomenda.setCellValueFactory(new PropertyValueFactory<>("IdEncomenda"));
                    colunaSequencia.setCellValueFactory(new PropertyValueFactory<>("sequencia"));
                    colunaidProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
                    colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
                    colunaIdUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
                    colunaIdPais.setCellValueFactory(new PropertyValueFactory<>("taxa"));
                    colunaTotalTaxa.setCellValueFactory(new PropertyValueFactory<>("totalTaxa"));
                    colunaTotalIncidencia.setCellValueFactory(new PropertyValueFactory<>("totalIncidencia"));
                    colunaTotalLinha.setCellValueFactory(new PropertyValueFactory<>("totalLinha"));

                    tableViewLinhasEncomenda.getColumns().add(colunaId);
                    tableViewLinhasEncomenda.getColumns().add(colunaIdEncomenda);
                    tableViewLinhasEncomenda.getColumns().add(colunaSequencia);
                    tableViewLinhasEncomenda.getColumns().add(colunaidProduto);
                    tableViewLinhasEncomenda.getColumns().add(colunaQuantidade);
                    tableViewLinhasEncomenda.getColumns().add(colunaIdUnidade);
                    tableViewLinhasEncomenda.getColumns().add(colunaIdPais);
                    tableViewLinhasEncomenda.getColumns().add(colunaTotalTaxa);
                    tableViewLinhasEncomenda.getColumns().add(colunaTotalIncidencia);
                    tableViewLinhasEncomenda.getColumns().add(colunaTotalLinha);

                    tableViewLinhasEncomenda.setItems(linhasEncomenda);
                }

            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao ler tabela!");
        }


    }


    @FXML
    void clickAprovar() {


    }

    @FXML
    void clickRecusar() {


    }

    public void clickSelecionarCabecalho(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        int idEncomenda = tableViewEncomendas.getSelectionModel().getSelectedItem().getId();

        tabelaLinhasEncomenda(tableViewEncomendas.getSelectionModel().getSelectedItem());

    }
}
