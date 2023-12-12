package Controller.Administrador;

import DAL.LerEncomenda;
import DAL.LerFornecedores;
import Model.ContaCorrente;
import Model.Encomenda;
import Model.Fornecedor;
import Model.Pais;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

/**
 * Menu com as funções da conta corrente, ler saldo em dívida.
 */
public class MenuContaCorrente {

    BaseDados baseDados = new BaseDados();
    LerFornecedores lerFornecedores = new LerFornecedores();

    LerEncomenda lerEncomenda = new LerEncomenda();

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
    private TableView<Encomenda> tableViewEncomendaFornc;

    @FXML
    private TableView<ContaCorrente> tableViewDividas;

    ObservableList<ContaCorrente> dividasFornecedores = FXCollections.observableArrayList();

    ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();

    /**
     * Inicializa a aplicação carregando a tabela de dívidas dos fornecedores.
     *
     * @throws IOException Se ocorrer um erro durante a leitura ou inicialização.
     */
    public void initialize() throws IOException {
        tabelaDividas();

        tableViewDividas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContaCorrente>() {
            @Override
            public void changed(ObservableValue<? extends ContaCorrente> observable, ContaCorrente oldValue, ContaCorrente newValue) {
                // Chamado quando a seleção da tabela muda
                carregarLabels();
                try {
                    carregarEncomendas();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
             }
        });
    }


    /**
     * Popula a tabela de dívidas dos fornecedores com os dados provenientes do arquivo de base de dados.
     *
     * @throws IOException Se ocorrer um erro durante a leitura dos dados.
     */
    public void tabelaDividas() throws IOException {
        dividasFornecedores.addAll(lerFornecedores.lerDividaFornecedores(baseDados));

        if (!dividasFornecedores.isEmpty()) {
            if (tableViewDividas.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<ContaCorrente, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<ContaCorrente, String> colunaIdExterno = new TableColumn<>("ID do fornecedor");
                TableColumn<ContaCorrente, String> colunaNome = new TableColumn<>("Nome");
                TableColumn<ContaCorrente, String> colunaDivida = new TableColumn<>("Saldo devedor");
                TableColumn<ContaCorrente, String> colunaMoeda = new TableColumn<>("Moeda de pagamento");

                // Associe as colunas às propriedades da classe Fornecedor
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaIdExterno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdFornecedor().getIdExterno()));
                colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdFornecedor().getNome()));
                colunaDivida.setCellValueFactory(new PropertyValueFactory<>("saldo"));
                colunaMoeda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdFornecedor().getIdPais().getMoeda()));


                // Adicione as colunas à tabela
                tableViewDividas.getColumns().add(colunaId);
                tableViewDividas.getColumns().add(colunaIdExterno);
                tableViewDividas.getColumns().add(colunaNome);
                tableViewDividas.getColumns().add(colunaDivida);
                tableViewDividas.getColumns().add(colunaMoeda);

                // Configure a fonte de dados da tabela
                tableViewDividas.setItems(dividasFornecedores);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }

    /**
     * Carrega as labels com informações do fornecedor selecionado na tabela de encomendas.
     */
    public void carregarLabels() {
        ContaCorrente contaCorrenteSelecionada = tableViewDividas.getSelectionModel().getSelectedItem();

        if (contaCorrenteSelecionada != null) {

            Double saldo = contaCorrenteSelecionada.getSaldo();
            labelSaldo.setText(String.valueOf(saldo));

            Fornecedor fornecedor = contaCorrenteSelecionada.getIdFornecedor();

            if (fornecedor != null) {
                String idFornecedorExterno = fornecedor.getIdExterno();
                labelIdExterno.setText(idFornecedorExterno);

                int idFornecedorInterno = fornecedor.getId();
                labelIdInterno.setText(String.valueOf(idFornecedorInterno));

                String nomeFornecedor = fornecedor.getNome();
                labelNome.setText(nomeFornecedor);

                String morada1 = fornecedor.getMorada1();
                labelMorada1.setText(morada1);

                String morada2 = fornecedor.getMorada2();
                labelMorada2.setText(morada2);

                String localidade = fornecedor.getLocalidade();
                labelLocalidade.setText(localidade);

                String codPostal = fornecedor.getCodigoPostal();
                labelCodigoPostal.setText(codPostal);

                String pais = fornecedor.getIdPais().getNome();
                labelPais.setText(pais);

                //adicionar mais dados ao fornecedor
            }
        }


    }

    /**
     * Carrega as encomendas referentes ao fornecedor selecionado
     */
    public void carregarEncomendas() throws IOException {

        tableViewEncomendaFornc.getItems().clear();

        ContaCorrente contaCorrenteSelecionada = tableViewDividas.getSelectionModel().getSelectedItem();

        if (contaCorrenteSelecionada != null) {
            Fornecedor fornecedor = contaCorrenteSelecionada.getIdFornecedor();
            encomendas.addAll(lerEncomenda.lerEncomendasPorFornecedor(baseDados, fornecedor.getIdExterno()));

            if(!encomendas.isEmpty()){
                if(tableViewEncomendaFornc.getColumns().isEmpty()) {

                    TableColumn<Encomenda, Integer> colunaId = new TableColumn<>("Id da encomenda");
                    TableColumn<Encomenda, String> colunaIdFornecedor = new TableColumn<>("ID do fornecedor");
                    TableColumn<Encomenda, String> colunaReferencia = new TableColumn<>("Referência");
                    TableColumn<Encomenda, String> colunaData = new TableColumn<>("Data");
                    TableColumn<Encomenda, String> colunaTotal = new TableColumn<>("Total");

                    // Associe as colunas às propriedades da classe Fornecedor
                    colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colunaIdFornecedor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFornecedor().getIdExterno()));
                    colunaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
                    colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
                    colunaTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

                    tableViewEncomendaFornc.getColumns().add(colunaId);
                    tableViewEncomendaFornc.getColumns().add(colunaIdFornecedor);
                    tableViewEncomendaFornc.getColumns().add(colunaReferencia);
                    tableViewEncomendaFornc.getColumns().add(colunaData);
                    tableViewEncomendaFornc.getColumns().add(colunaTotal);

                    tableViewEncomendaFornc.setItems(encomendas);
                }

            } else {
                Mensagens.Erro("Erro!","Erro ao ler tabela ou tabela vazia!");
            }
        }

    }

    /**
     * Método associado ao clique no botão "Pagar".
     *
     * @param event O evento associado ao clique no botão "Pagar".
     */
    @FXML
    void clickPagar(ActionEvent event) {

    }

}
