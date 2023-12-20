package Controller.Produtos;

import DAL.LerFornecedores;
import DAL.LerProdutos;
import DAL.LerStock;
import Model.Fornecedor;
import Model.Produto;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class MenuProdutos {

    BaseDados baseDados = new BaseDados();
    LerStock lerStock = new LerStock();
    LerProdutos lerProdutos = new LerProdutos();
    LerFornecedores lerFornecedor = new LerFornecedores();

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableView<Produto> tableView2;

    ObservableList<Produto> produtos = FXCollections.observableArrayList();
    ObservableList<Produto> produtos2 = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewProdutos.getColumns().clear();
        tableViewProdutos.getItems().clear();
        tabelaProdutos();
        tableView2.getColumns().clear();
        tableView2.getItems().clear();
        tabela2();
    }

    public void tabelaProdutos() throws IOException {
        produtos.addAll(lerProdutos.lerProdutosBaseDados());

        if (!produtos.isEmpty()) {
            if (tableViewProdutos.getColumns().isEmpty()) {
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("Unidade");
                TableColumn<Produto, Integer> colunaQuantidade = new TableColumn<>("Quantidade");

                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaIdUnidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricaoUnidade()));
                colunaQuantidade.setCellValueFactory(cellData -> {
                    int quantidade = 0;
                    try {
                        quantidade = lerStock.obterQuantidadePorIdProduto(cellData.getValue().getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return new SimpleIntegerProperty(quantidade).asObject();
                });

                tableViewProdutos.getColumns().addAll(colunaId, colunaDescricao, colunaIdUnidade, colunaQuantidade);
                tableViewProdutos.setItems(produtos);
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }
    }

    public void tabela2() throws IOException {
        produtos2.addAll(lerProdutos.lerProdutosBaseDados());

        if (!produtos2.isEmpty()) {
            if (tableView2.getColumns().isEmpty()) {
                TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID Produto");
                TableColumn<Produto, String> colunaDescricao = new TableColumn<>("Descrição");
                TableColumn<Produto, String> colunaIdFornecedor = new TableColumn<>("Id no Fornecedor");
                TableColumn<Produto, String> colunaNomeFornecedor = new TableColumn<>("Fornecedor");
                TableColumn<Produto, String> colunaIdUnidade = new TableColumn<>("Unidade");

                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                colunaIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("idFornecedorAsString"));
                colunaNomeFornecedor.setCellValueFactory(cellData -> {
                    Produto produto = cellData.getValue();
                    if (produto != null) {
                        try {
                            String idFornecedor = produto.getIdFornecedorAsString();
                            String nomeFornecedor = lerFornecedor.obterNomeFornecedorPorIdExterno(idFornecedor);
                            return new SimpleStringProperty(nomeFornecedor != null ? nomeFornecedor : "");
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new SimpleStringProperty("");
                        }
                    } else {
                        return new SimpleStringProperty("");
                    }
                });

                tableView2.getColumns().addAll(colunaId, colunaDescricao, colunaIdFornecedor, colunaNomeFornecedor, colunaIdUnidade);
                tableView2.setItems(produtos2);

                colunaIdUnidade.setCellValueFactory(cellData -> {
                    Produto produtoTabela1 = cellData.getValue();
                    if (produtoTabela1 != null) {
                        return new SimpleStringProperty(produtoTabela1.getDescricaoUnidade());
                    } else {
                        return new SimpleStringProperty("");
                    }
                });
            }
        } else {
            Mensagens.Erro("Erro!", "Erro ao ler tabela");
        }

        // Adicione um listener para detectar cliques na tableView2
        tableView2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    mostrarFornecedoresDoProduto(newSelection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void mostrarFornecedoresDoProduto(Produto produto) throws IOException {
        try {
            String idProduto = produto.getIdFornecedorAsString();
            List<Fornecedor> fornecedores = lerFornecedor.obterFornecedoresPorProduto(idProduto);

            if (!fornecedores.isEmpty()) {
                StringBuilder mensagem = new StringBuilder("Fornecedores do Produto:\n\n");

                for (Fornecedor fornecedor : fornecedores) {
                    if (fornecedor != null) {
                        mensagem.append("Nome: ").append(produto.getNomeFornecedor()).append("\n");
                        mensagem.append("ID no Fornecedor: ").append(produto.getIdFornecedorAsString()).append("\n\n");
                    } else {
                        mensagem.append("Fornecedor nulo encontrado.\n\n");
                    }
                }

                Mensagens.Informacao("Fornecedores do Produto", mensagem.toString());
            } else {
                Mensagens.Informacao("Fornecedores do Produto", "Não há fornecedores associados a este produto.");
            }
        } catch (IOException e) {
            Mensagens.Erro("Erro!", "Erro ao obter informações dos fornecedores.");
            e.printStackTrace();
        }
    }

}
