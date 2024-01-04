package Controller.Fornecedor;

import Model.*;
import DAL.LerEncomenda;
import DAL.LerFornecedores;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class VerEncomendasFornecedor {

    LerEncomenda lerEncomenda = new LerEncomenda();

    private Utilizador utilizador;

    @FXML
    private SplitPane anchorPaneMenuFornecedor;

    @FXML
    private TableView<Encomenda> tableViewEncomendas;

    @FXML
    private TableView<LinhaEncomenda> tableViewLinhasEncomenda;

    ObservableList<Encomenda> encomendas = FXCollections.observableArrayList();
    ObservableList<LinhaEncomenda> linhasEncomenda = FXCollections.observableArrayList();

    public void iniciaData(Utilizador utilizador) throws IOException {
        this.utilizador = utilizador;
        System.out.println(utilizador.getTipo());
        System.out.println(utilizador.getEmail());

    }
    public void initialize() throws IOException {
        tabelaEncomendasPendentes();

        tableViewEncomendas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    tabelaLinhasEncomenda(newSelection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    /**
     * Preenche a tabela de encomendas pendentes com dados da base de dados.
     * Configura as colunas da tabela e associa-as às propriedades da classe Encomenda.
     * Adiciona a tabela à interface do usuário e exibe as encomendas pendentes.
     *
     * @throws IOException Se ocorrer um erro ao ler a tabela.
     */

    public void tabelaEncomendasPendentes() throws IOException {
        LerFornecedores fornecedor = new LerFornecedores();
        Fornecedor fornecedorLogado = null;
        for (Fornecedor fornec : fornecedor.lerFornecedoresDaBaseDeDados()) {
            if (utilizador.getId() == fornec.getIdUtilizador().getId()) {
                fornecedorLogado = fornec;
            }
        }
        assert fornecedorLogado != null;

        if (!encomendas.isEmpty()) {

            encomendas.addAll(lerEncomenda.lerEncomendaDaBaseDeDadosFornecedor(fornecedorLogado.getIdExterno()));

            // Defina as colunas da tabela
            TableColumn<Encomenda, Integer> colunaId = new TableColumn<>("ID");
            TableColumn<Encomenda, String> colunaReferencia = new TableColumn<>("Referência");
            TableColumn<Encomenda, LocalDate> colunaData = new TableColumn<>("Data");
            TableColumn<Encomenda, Fornecedor> colunaNomeFornecedor = new TableColumn<>("Fornecedor");
            TableColumn<Encomenda, Fornecedor> colunaIdFornecedor = new TableColumn<>("Id do fornecedor");
            TableColumn<Encomenda, Pais> colunaidPais = new TableColumn<>("País");
            TableColumn<Encomenda, Double> colunaTotalTaxa = new TableColumn<>("Total dos impostos");
            TableColumn<Encomenda, Double> colunaIncidencia = new TableColumn<>("Total sem impostos");
            TableColumn<Encomenda, Double> colunaTotal = new TableColumn<>("Valor total");


            // Associe as colunas às propriedades da classe Encomenda
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));

            colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));

            colunaNomeFornecedor.setCellFactory(column -> {
                return new TableCell<Encomenda, Fornecedor>() {
                    @Override
                    protected void updateItem(Fornecedor fornecedor, boolean empty) {
                        super.updateItem(fornecedor, empty);

                        if (fornecedor == null || empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(fornecedor.getNome())); // Nome do fornecedor
                        }
                    }
                };
            });

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


            // Adicione as colunas à tabela
            tableViewEncomendas.getColumns().add(colunaId);
            tableViewEncomendas.getColumns().add(colunaReferencia);
            tableViewEncomendas.getColumns().add(colunaData);
            tableViewEncomendas.getColumns().add(colunaNomeFornecedor);
            tableViewEncomendas.getColumns().add(colunaIdFornecedor);
            tableViewEncomendas.getColumns().add(colunaidPais);
            tableViewEncomendas.getColumns().add(colunaTotalTaxa);
            tableViewEncomendas.getColumns().add(colunaIncidencia);
            tableViewEncomendas.getColumns().add(colunaTotal);


            tableViewEncomendas.setItems(encomendas);


        } else {
            Mensagens.Erro("Erro", "Erro ao ler tabela, ou dados inexistentes");
        }

    }


    /**
     * Preenche a tabela de linhas de encomenda com base na encomenda selecionada.
     * Configura as colunas da tabela e associa-as às propriedades da classe LinhaEncomenda.
     *
     * @param encomenda A encomenda selecionada.
     * @throws IOException Se ocorrer um erro ao ler a tabela.
     */

    public void tabelaLinhasEncomenda(Encomenda encomenda) throws IOException {
        try {

            if (encomenda != null) {

                tableViewLinhasEncomenda.getItems().clear();

                // Ler apenas as linhas de encomenda para a encomenda selecionada
                linhasEncomenda.addAll(lerEncomenda.lerLinhaEncomenda(encomenda.getId()));

                if (!linhasEncomenda.isEmpty()) {
                    TableColumn<LinhaEncomenda, Integer> colunaId = new TableColumn<>("ID");
                    TableColumn<LinhaEncomenda, Encomenda> colunaIdEncomenda = new TableColumn<>("Encomenda");
                    TableColumn<LinhaEncomenda, Integer> colunaSequencia = new TableColumn<>("Sequência");
                    TableColumn<LinhaEncomenda, String> colunaDescricaoProduto = new TableColumn<>("Descrição");
                    TableColumn<LinhaEncomenda, Integer> colunaQuantidade = new TableColumn<>("Quantidade");
                    TableColumn<LinhaEncomenda, String> colunaDescricaoUnidade = new TableColumn<>("Unidade");
                    TableColumn<LinhaEncomenda, String> colunaNomePais = new TableColumn<>("País");
                    TableColumn<LinhaEncomenda, Double> colunaTotalTaxa = new TableColumn<>("Total taxa");
                    TableColumn<LinhaEncomenda, Double> colunaTotalIncidencia = new TableColumn<>("Total incidência");
                    TableColumn<LinhaEncomenda, Double> colunaTotalLinha = new TableColumn<>("Total da linha");

                    colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colunaIdEncomenda.setCellValueFactory(new PropertyValueFactory<>("IdEncomenda"));

                    colunaIdEncomenda.setCellFactory(column -> {
                        return new TableCell<LinhaEncomenda, Encomenda>() {
                            @Override
                            protected void updateItem(Encomenda encomenda, boolean empty) {
                                super.updateItem(encomenda, empty);

                                if (encomenda == null || empty) {
                                    setText(null);
                                } else {
                                    setText(String.valueOf(encomenda.getId())); // ID do fornecedor
                                }
                            }
                        };
                    });
                    colunaSequencia.setCellValueFactory(new PropertyValueFactory<>("sequencia"));
                    colunaDescricaoProduto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getIdExterno()));
                    colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
                    colunaDescricaoUnidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidade().getDescricao()));
                    colunaNomePais.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaxa().getNome()));
                    colunaTotalTaxa.setCellValueFactory(new PropertyValueFactory<>("totalTaxa"));
                    colunaTotalIncidencia.setCellValueFactory(new PropertyValueFactory<>("totalIncidencia"));
                    colunaTotalLinha.setCellValueFactory(new PropertyValueFactory<>("total"));

                    tableViewLinhasEncomenda.getColumns().addAll(
                            colunaId, colunaIdEncomenda, colunaSequencia,
                            colunaDescricaoProduto, colunaQuantidade, colunaDescricaoUnidade,
                            colunaNomePais, colunaTotalTaxa, colunaTotalIncidencia, colunaTotalLinha
                    );

                    tableViewLinhasEncomenda.setItems(linhasEncomenda);
                }
            }
        } catch (Exception e) {
            Mensagens.Erro("Erro!", "Erro ao ler tabela!");
        }
    }

}


