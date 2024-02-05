package Controller.Administrador;

import DAL.LerEncomenda;
import Model.EncomendaFornecedor;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class MenuEstatisticas {

    LerEncomenda lerEncomenda = new LerEncomenda();
    @FXML
    private SplitPane anchorPaneFuncoesFornc;

    @FXML
    private TableView<EncomendaFornecedor> tableViewAprovadas;

    @FXML
    private TableView<EncomendaFornecedor> tableViewRecusadas;

    ObservableList<EncomendaFornecedor> encomenda = FXCollections.observableArrayList();
    ObservableList<EncomendaFornecedor> encomendarecusada = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewAprovadas.getColumns().clear();
        tableViewAprovadas.getItems().clear();
        tabelaEncomendasAprovadas();
        tableViewRecusadas.getColumns().clear();
        tableViewRecusadas.getItems().clear();
        tabelaEncomendasRecusadas();

    }
    /**
     * Preenche uma tabela com informações sobre encomendas aprovadas.
     * Este método lê e exibe informações sobre encomendas recusadas em uma tabela de visualização (TableView).
     * As informações incluem o ID da encomenda, referência, data, fornecedor, valor total e utilizador associado.
     * Caso a lista de encomendas aprovadas esteja vazia ou ocorra um erro na leitura da tabela, uma mensagem de erro é exibida.
     *
     * @throws IOException Se ocorrer um erro durante a leitura das encomendas recusadas.
     */
    public void tabelaEncomendasAprovadas() throws IOException {
        encomenda.addAll(lerEncomenda.lerEncomendaAprovada());
        if (!encomenda.isEmpty()) {
            TableColumn<EncomendaFornecedor, Integer> colunaId = new TableColumn<>("ID Encomenda");
            TableColumn<EncomendaFornecedor, String> colunaReferencia = new TableColumn<>("Referência");
            TableColumn<EncomendaFornecedor, LocalDate> colunaData = new TableColumn<>("Data");
            TableColumn<EncomendaFornecedor, String> colunaNomeFornecedor = new TableColumn<>("Fornecedor");
            TableColumn<EncomendaFornecedor, Double> colunaValorTotal = new TableColumn<>("Valor Total");
            TableColumn<EncomendaFornecedor, String> colunaNomeUtilizador = new TableColumn<>("Utilizador");


            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
            colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
            colunaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
            colunaNomeUtilizador.setCellValueFactory(new PropertyValueFactory<>("emailUtilizador"));



            tableViewAprovadas.getColumns().addAll(colunaId, colunaReferencia, colunaData, colunaNomeFornecedor, colunaValorTotal, colunaNomeUtilizador);

            // Definir os dados na tabela
            tableViewAprovadas.setItems(encomenda);
        } else {
            // Tratar caso a lista esteja vazia
            Mensagens.Erro("Erro!", "Erro ao ler tabela de encomendas aprovadas");
        }
    }

    /**
     * Preenche uma tabela com informações sobre encomendas recusadas.
     * Este método lê e exibe informações sobre encomendas recusadas em uma tabela de visualização (TableView).
     * As informações incluem o ID da encomenda, referência, data, fornecedor, valor total e utilizador associado.
     * Caso a lista de encomendas recusadas esteja vazia ou ocorra um erro na leitura da tabela, uma mensagem de erro é exibida.
     *
     * @throws IOException Se ocorrer um erro durante a leitura das encomendas recusadas.
     */
    public void tabelaEncomendasRecusadas() throws IOException {
        encomendarecusada.addAll(lerEncomenda.lerEncomendaRecusada());
        if (encomendarecusada.isEmpty()) {
            // Tratar caso a lista esteja vazia
            Mensagens.Erro("Erro!", "A lista de encomendas recusadas está vazia. Não há dados para exibir na tabela.");
        } else if (encomendarecusada.get(0) == null) {
            // Tratar erro ao ler a tabela
            Mensagens.Erro("Erro!", "Erro ao ler a tabela de encomendas recusadas.");
        } else {
            TableColumn<EncomendaFornecedor, Integer> colunaId = new TableColumn<>("ID Encomenda");
            TableColumn<EncomendaFornecedor, String> colunaReferencia = new TableColumn<>("Referência");
            TableColumn<EncomendaFornecedor, LocalDate> colunaData = new TableColumn<>("Data");
            TableColumn<EncomendaFornecedor, String> colunaNomeFornecedor = new TableColumn<>("Fornecedor");
            TableColumn<EncomendaFornecedor, Double> colunaValorTotal = new TableColumn<>("Valor Total");
            TableColumn<EncomendaFornecedor, String> colunaNomeUtilizador = new TableColumn<>("Utilizador");

            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
            colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
            colunaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
            colunaNomeUtilizador.setCellValueFactory(new PropertyValueFactory<>("emailUtilizador"));

            tableViewRecusadas.getColumns().addAll(colunaId, colunaReferencia, colunaData, colunaNomeFornecedor, colunaValorTotal, colunaNomeUtilizador);

            // Definir os dados na tabela
            tableViewRecusadas.setItems(encomendarecusada);
        }
    }


}
