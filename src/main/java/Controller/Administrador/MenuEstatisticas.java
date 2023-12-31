package Controller.Administrador;

import DAL.LerEncomenda;
import Model.EncomendaFornecedor;
import Model.FornecedorProdutoData;
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
    private TableView<?> tableViewRecusadas;

    ObservableList<EncomendaFornecedor> encomenda = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewAprovadas.getColumns().clear();
        tableViewAprovadas.getItems().clear();
        tabelaEncomendasAprovadas();
    }
    public void tabelaEncomendasAprovadas() throws IOException {
        encomenda.addAll(lerEncomenda.lerEncomendaAprovada());
        if (!encomenda.isEmpty()) {
            TableColumn<EncomendaFornecedor, Integer> colunaId = new TableColumn<>("ID Encomenda");
            TableColumn<EncomendaFornecedor, String> colunaReferencia = new TableColumn<>("Referência");
            TableColumn<EncomendaFornecedor, LocalDate> colunaData = new TableColumn<>("Data");
            TableColumn<EncomendaFornecedor, String> colunaNomeFornecedor = new TableColumn<>("Fornecedor");
            TableColumn<EncomendaFornecedor, Double> colunaValorTotal = new TableColumn<>("Valor Total");
            TableColumn<EncomendaFornecedor, String> colunaNomeUtilizador = new TableColumn<>("Utilizador");

            // Configurar como cada coluna obtém seus valores do modelo de dados (EncomendaFornecedor)
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id")); // Substitua "id" pelo nome do atributo correspondente na classe EncomendaFornecedor
            colunaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia")); // Substitua "referencia" pelo nome do atributo correspondente na classe EncomendaFornecedor
            colunaData.setCellValueFactory(new PropertyValueFactory<>("data")); // Substitua "data" pelo nome do atributo correspondente na classe EncomendaFornecedor
            colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor")); // Substitua "nomeFornecedor" pelo nome do atributo correspondente na classe EncomendaFornecedor
            colunaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
            colunaNomeUtilizador.setCellValueFactory(new PropertyValueFactory<>("nomeUtilizador")); // Substitua "nomeUtilizador" pelo nome do atributo correspondente na classe EncomendaFornecedor


            // Adicionar as colunas à tabela (suponho que tableViewEncomendas seja o nome da sua TableView)
            tableViewAprovadas.getColumns().addAll(colunaId, colunaReferencia, colunaData, colunaNomeFornecedor, colunaValorTotal, colunaNomeUtilizador);

            // Definir os dados na tabela
            tableViewAprovadas.setItems(encomenda);
        } else {
            // Tratar caso a lista esteja vazia
            Mensagens.Erro("Erro!", "Erro ao ler tabela de encomendas aprovadas");
        }
    }


}
