package Controller.Clientes;

import DAL.LerOrders;
import DAL.LerProdutos;
import Model.API.Cliente;
import Model.API.Order;
import Model.API.OrderLine;
import Model.API.ProdutoAPI;
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
import java.util.List;
import java.util.stream.Collectors;

import static Utilidades.API.updateOrder;

public class EncomendasWeb {

    LerOrders lerOrders = new LerOrders();
    LerProdutos lerProdutos = new LerProdutos();

    @FXML
    private AnchorPane anchorPaneEncomendasWeb;

    @FXML
    private Button btnAprovarEncomenda;

    @FXML
    private TableView<Order> tableViewEncomendasWeb;
    @FXML
    private TableView<OrderLine> tableViewProdutos;
    ObservableList<Order> orders = FXCollections.observableArrayList();
   public void initialize() throws IOException {
       tabelaEncomendasWeb();
   }
    /**
     * Preenche e exibe a TableView com dados de encomendas provenientes da web.
     * Este método lê as encomendas da web, preenche a TableView com informações relevantes,
     * e adiciona ouvintes de seleção para preencher a tabela de produtos com base na ordem selecionada.
     * As colunas da TableView incluem ID da ordem, status, data, ID do cliente, nome do cliente, e-mail do cliente,
     * total sem taxas, total das taxas e total da compra.
     *
     * @throws IOException Se ocorrer um erro ao ler as encomendas da web.
     */
    public void tabelaEncomendasWeb() throws IOException {

        orders.addAll(lerOrders.lerOrders());


        if (!orders.isEmpty()) {
            if (tableViewEncomendasWeb.getItems().isEmpty()) {
                TableColumn<Order, String> colunaId = new TableColumn<>("ID da Order");
                TableColumn<Order, String> colunaStatus = new TableColumn<>("Status");
                TableColumn<Order, String> colunaData = new TableColumn<>("Data");
                TableColumn<Order, String> colunaIdCliente = new TableColumn<>("Id cliente");
                TableColumn<Order, String> colunaNomeCliente = new TableColumn<>("Nome cliente");
                TableColumn<Order, String> emailCliente = new TableColumn<>("E-mail cliente");
                TableColumn<Order, String> colunaSemTaxa = new TableColumn<>("Total sem taxas");
                TableColumn<Order, String> colunaTaxa = new TableColumn<>("Total das taxas");
                TableColumn<Order, String> colunaTotal = new TableColumn<>("Total da compra");

                // Associe as propriedades dos objetos Order às colunas
                colunaId.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));
                colunaData.setCellValueFactory(new PropertyValueFactory<>("Date"));
                colunaSemTaxa.setCellValueFactory(new PropertyValueFactory<>("NetAmount"));
                colunaTaxa.setCellValueFactory(new PropertyValueFactory<>("TaxAmount"));
                colunaTotal.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
                colunaStatus.setCellValueFactory(new PropertyValueFactory<>("StatusDescription"));

                colunaIdCliente.setCellValueFactory(cellData -> {
                    List<Cliente> clients = cellData.getValue().getClient();
                    String nomesClientes = clients.stream()
                            .map(client -> String.valueOf(client.Id))
                            .collect(Collectors.joining(", "));
                    return new SimpleStringProperty(nomesClientes);
                });

                colunaNomeCliente.setCellValueFactory(cellData -> {
                    List<Cliente> clients = cellData.getValue().getClient();
                    String nomesClientes = clients.stream()
                            .map(client -> client.Name)
                            .collect(Collectors.joining(", "));
                    return new SimpleStringProperty(nomesClientes);
                });

                emailCliente.setCellValueFactory(cellData -> {
                    List<Cliente> clients = cellData.getValue().getClient();
                    String emailsClientes = clients.stream()
                            .map(client -> client.Email)
                            .collect(Collectors.joining(", "));
                    return new SimpleStringProperty(emailsClientes);
                });
                // Adicione as colunas à TableView
                tableViewEncomendasWeb.getColumns().addAll(colunaId,colunaStatus, colunaData, colunaIdCliente, colunaNomeCliente, emailCliente,colunaSemTaxa, colunaTaxa,colunaTotal);

                // Adicione os dados à TableView
                tableViewEncomendasWeb.getItems().addAll(orders);

                // Adicione um ouvinte de seleção à tabela de ordens
                tableViewEncomendasWeb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    // Chame a função para preencher a tabela de produtos com base na ordem selecionada
                    tabelaProdutos(newValue);
                });

            } else {
                Mensagens.Erro("Erro!", "Erro ao ler tabela ou tabela vazia!");
            }
        }
    }
    /**
     * Preenche a TableView de produtos com base na ordem selecionada na TableView de encomendas web.
     * Este método obtém as linhas da ordem selecionada, cria colunas para exibir informações relevantes
     * sobre os produtos na TableView, e adiciona as linhas à TableView de produtos.
     * As colunas incluem o número da linha, ID do produto, descrição, quantidade, unidade e preço.
     *
     * @param order A ordem selecionada na TableView de encomendas web.
     */
    public void tabelaProdutos(Order order) {
        order = tableViewEncomendasWeb.getSelectionModel().getSelectedItem();

        tableViewProdutos.getItems().clear();
        tableViewProdutos.getColumns().clear();

        if (order != null) {
            // Obtenha as linhas da ordem selecionada
            List<OrderLine> orderLines = order.getOrderLines();

            // Adicione as colunas à tabela de produtos, semelhante à tabela de ordens
            TableColumn<OrderLine, String> colunaLineNumber = new TableColumn<>("Linha");
            TableColumn<OrderLine, String> colunaIdProduto = new TableColumn<>("ID do Produto");
            TableColumn<OrderLine, String> colunaDescricao = new TableColumn<>("Descrição");
            TableColumn<OrderLine, String> colunaQuantidade = new TableColumn<>("Quantidade");
            TableColumn<OrderLine, String> colunaUnidade = new TableColumn<>("Unidade");
            TableColumn<OrderLine, String> colunaPreco = new TableColumn<>("Preço");

            colunaLineNumber.setCellValueFactory(new PropertyValueFactory<>("LineNumber"));
            colunaIdProduto.setCellValueFactory(new PropertyValueFactory<>("ProductCode"));
            colunaDescricao.setCellValueFactory(cellData -> {
                try {
                    return new SimpleStringProperty(lerProdutos.obterDescricaoDoProdutoPeloCodigo(cellData.getValue().getProductCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            colunaUnidade.setCellValueFactory(new PropertyValueFactory<>("Unit"));
            colunaPreco.setCellValueFactory(new PropertyValueFactory<>("Price"));


            // Adicione as colunas à tabela de produtos
            tableViewProdutos.getColumns().addAll(colunaLineNumber, colunaIdProduto,  colunaDescricao, colunaQuantidade, colunaUnidade, colunaPreco);

            // Adicione os dados à tabela de produtos diretamente da lista de OrderLines
            tableViewProdutos.getItems().addAll(orderLines);
        }
    }

    /**
     * Manipula o evento de clique no botão para aprovar uma encomenda na TableView de encomendas web.
     * Se uma encomenda estiver selecionada e ainda não tiver sido aprovada, o método atualiza o status
     * da encomenda para "Aprovada" e realiza a aprovação no sistema, atualizando os estoques na API e
     * na base de dados local.
     *
     * @throws IOException Se ocorrer um erro durante a leitura ou gravação de dados.
     */
    @FXML
    void clickAprovarEncomenda() throws IOException {

        Order orderSelecionada = tableViewEncomendasWeb.getSelectionModel().getSelectedItem();

        if (orderSelecionada != null) {
            if (orderSelecionada.getStatus() == 2) {
                Mensagens.Erro("Erro!", "Encomenda já foi aprovada!");
            } else {
                try {
                    String data = """
                        { "Status": 2 }
                        """;
                    String idOrder = orderSelecionada.getOrderNumber();

                    boolean sucesso1 = lerProdutos.atualizarStockAPI(orderSelecionada.getOrderLines());
                    boolean successo2 = lerProdutos.atualizarStockBaseDados(orderSelecionada.getOrderLines());

                    updateOrder(idOrder, data);

                    if (sucesso1 && successo2) {
                        Mensagens.Informacao("Sucesso!", "Encomenda aprovada com sucesso");
                    } else {
                        Mensagens.Erro("Erro", "Erro ao aprovar encomenda!");
                    }

                } catch (Exception e) {
                    Mensagens.Erro("Erro!", "Erro ao aprovar encomenda!");
                }
            }
        }
    }

}