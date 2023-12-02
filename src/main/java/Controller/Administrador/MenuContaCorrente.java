package Controller.Administrador;

import Controller.DAL.LerFornecedores;
import Model.ContaCorrente;
import Model.Fornecedor;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MenuContaCorrente {

    BaseDados baseDados = new BaseDados();
    LerFornecedores lerFornecedores = new LerFornecedores();

    @FXML
    private SplitPane anchorPaneFuncoesFornc;

    @FXML
    private Button btnPagar;

    @FXML
    private Label labelBIC;

    @FXML
    private Label labelCodigoPostal;

    @FXML
    private Label labelConta;

    @FXML
    private Label labelIban;

    @FXML
    private Label labelIdExterno;

    @FXML
    private Label labelIdInterno;

    @FXML
    private Label labelLocalidade;

    @FXML
    private Label labelMorada1;

    @FXML
    private Label labelMorada2;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelPais;

    @FXML
    private Label labelSaldo;

    @FXML
    private TableView<Fornecedor> tableViewEncomendaFornc;

    @FXML
        private TableView<ContaCorrente> tableViewDividas;

    ObservableList<ContaCorrente> dividasFornecedores = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tabelaDividas();
        //carregarLabels();

    }

    public void tabelaDividas() throws IOException {
        dividasFornecedores.addAll(lerFornecedores.lerDividaFornecedores(baseDados));

        if (!dividasFornecedores.isEmpty()) {
            if (tableViewDividas.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<ContaCorrente, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<ContaCorrente, String> colunaIdExterno = new TableColumn<>("ID do fornecedor");
                TableColumn<ContaCorrente, String> colunaNome = new TableColumn<>("Nome");
                TableColumn<ContaCorrente, String> colunaDivida = new TableColumn<>("Saldo devedor");

                // Associe as colunas às propriedades da classe Fornecedor
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaIdExterno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdFornecedor().getNome()));
                colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdFornecedor().getIdExterno()));
                colunaDivida.setCellValueFactory(new PropertyValueFactory<>("saldo"));

                // Adicione as colunas à tabela
                tableViewDividas.getColumns().add(colunaId);
                tableViewDividas.getColumns().add(colunaIdExterno);
                tableViewDividas.getColumns().add(colunaNome);
                tableViewDividas.getColumns().add(colunaDivida);

                // Configure a fonte de dados da tabela
                tableViewDividas.setItems(dividasFornecedores);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }

    public void carregarLabels(){
        Fornecedor fornecedor = tableViewEncomendaFornc.getSelectionModel().getSelectedItem();

        labelNome.setText(fornecedor.getNome());

    }


    @FXML
    void clickPagar(ActionEvent event) {

    }

}
