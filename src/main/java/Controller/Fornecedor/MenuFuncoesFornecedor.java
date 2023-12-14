package Controller.Fornecedor;

import DAL.LerFornecedores;
import DAL.LerEncomenda;
import DAL.LerUtilizadores;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static Utilidades.Mensagens.Erro;


/**
 * Classe que contém as funções para lidar com um fornecedor, adicionar, eliminar e mostra os fornecedores do sistema.
 */
public class MenuFuncoesFornecedor {

    BaseDados baseDados = new BaseDados();

    LerFornecedores lerFornecedores = new LerFornecedores();
    LerEncomenda lerEncomenda = new LerEncomenda();

    @FXML
    private SplitPane anchorPaneFuncoesFornc;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNovoFornecedor;

    @FXML
    private TableView<Fornecedor> tableViewFornecedores;
    ObservableList<Fornecedor> fornecedores = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        tableViewFornecedores.getColumns().clear();
        tableViewFornecedores.getItems().clear();
        tabelaFornecedores();
    }

    /**
     * Preenche a tabela de fornecedores com dados lidos da base de dados e define as colunas da tabela, caso ainda não tenham sido definidas.
     *
     * @throws IOException Se ocorrer um erro durante a leitura da base de dados.
     */
    public void tabelaFornecedores() throws IOException {


        fornecedores.addAll(lerFornecedores.lerFornecedoresDaBaseDeDados(baseDados));

        if (!fornecedores.isEmpty()) {
            if (tableViewFornecedores.getColumns().isEmpty()) {
                // Defina as colunas da tabela
                TableColumn<Fornecedor, Integer> colunaId = new TableColumn<>("ID");
                TableColumn<Fornecedor, String> colunaNome = new TableColumn<>("Nome");
                TableColumn<Fornecedor, String> colunaMorada1 = new TableColumn<>("Morada 1");
                TableColumn<Fornecedor, String> colunaMorada2 = new TableColumn<>("Morada 2");
                TableColumn<Fornecedor, String> colunaLocalidade = new TableColumn<>("Localidade");
                TableColumn<Fornecedor, String> colunaCodPostal = new TableColumn<>("Código postal");
                TableColumn<Fornecedor, String> colunaIdPais = new TableColumn<>("País");
                TableColumn<Fornecedor, String> colunaIdUtilizador = new TableColumn<>("Tipo de utilizador");
                TableColumn<Fornecedor, String> colunaIdExterno = new TableColumn<>("ID Externo");
             

                // Associe as colunas às propriedades da classe Fornecedor
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                colunaMorada1.setCellValueFactory(new PropertyValueFactory<>("morada1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("morada2"));
                colunaLocalidade.setCellValueFactory(new PropertyValueFactory<>("localidade"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
                colunaIdPais.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPais().getNome()));
                colunaIdUtilizador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUtilizador().getTipo().name()));
                colunaIdExterno.setCellValueFactory(new PropertyValueFactory<>("idExterno"));

                // Adicione as colunas à tabela
                tableViewFornecedores.getColumns().add(colunaId);
                tableViewFornecedores.getColumns().add(colunaNome);
                tableViewFornecedores.getColumns().add(colunaMorada1);
                tableViewFornecedores.getColumns().add(colunaMorada2);
                tableViewFornecedores.getColumns().add(colunaLocalidade);
                tableViewFornecedores.getColumns().add(colunaCodPostal);
                tableViewFornecedores.getColumns().add(colunaIdPais);
                tableViewFornecedores.getColumns().add(colunaIdUtilizador);
                tableViewFornecedores.getColumns().add(colunaIdExterno);

                // Configure a fonte de dados da tabela
                tableViewFornecedores.setItems(fornecedores);

            }
        } else {
            Erro("Erro!", "Erro ao ler tabela");
        }
    }

    /**
     * Manipula o evento de clique no botão "Editar". Abre uma janela de diálogo para editar um fornecedor
     * selecionado na tabela de fornecedores. Atualiza a tabela após o processo de edição.
     *
     * @throws IOException Se ocorrer um erro durante o carregamento da interface gráfica de edição.
     */
    @FXML
    void clickEditar() throws IOException {
        Fornecedor fornecedorSelecionado = tableViewFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Fornecedor/DialogEditarFornecedor.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("EDITAR FORNECEDOR!");
            stage.setScene(scene);


            DialogEditarFornecedor controller = fxmlLoader.getController();
            controller.setFornecedorSelecionado(fornecedorSelecionado);

            stage.showAndWait();

            int selectedIndex = tableViewFornecedores.getSelectionModel().getSelectedIndex();
            fornecedores.set(selectedIndex, fornecedorSelecionado);
        }
    }


    /**
     * Manipula o evento de clique no botão "Eliminar". Abre uma caixa de diálogo de confirmação antes de
     * eliminar um fornecedor selecionado da tabela. Remove o fornecedor e o utilizador associado se a
     * confirmação for positiva.
     *
     * @throws IOException Se ocorrer um erro durante a remoção do fornecedor ou utilizador.
     */
    @FXML
    void clickEliminar() throws IOException {
        Fornecedor fornecedorSelecionado = tableViewFornecedores.getSelectionModel().getSelectedItem();

        if (fornecedorSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Eliminar fornecedor");
            alert.setContentText("Tem certeza que deseja eliminar o fornecedor selecionado?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        if (lerEncomenda.podeEliminarFornecedor(baseDados, fornecedorSelecionado.getIdUtilizador())) {
                            try {

                                boolean sucesso = lerFornecedores.removerFornecedorDaBaseDeDados(baseDados, fornecedorSelecionado.getId());

                                if (sucesso) {
                                    // Remover o fornecedor da lista
                                    fornecedores.remove(fornecedorSelecionado);

                                    // Remover o utilizador associado ao fornecedor
                                    LerUtilizadores lerUtilizadores = new LerUtilizadores();
                                    boolean remover = lerUtilizadores.removerUtilizador(baseDados, fornecedorSelecionado.getIdUtilizador());

                                    if (remover) {
                                        // Remover o utilizador da lista
                                        fornecedores.remove(fornecedorSelecionado.getIdUtilizador());
                                    } else {
                                        // Se a remoção do utilizador falhar, adicione o fornecedor de volta à lista
                                        fornecedores.add(fornecedorSelecionado);
                                    }
                                }

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        } else {
                            try {
                                Mensagens.Erro("Erro!", "Fornecedor não pode ser eliminado por ter encomendas");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * Manipula o evento de clique no botão "Novo". Abre uma janela de diálogo para adicionar um novo fornecedor,
     * atualizando a tabela após o processo de adição.
     *
     * @throws IOException Se ocorrer um erro durante o carregamento da interface gráfica de adição.
     */
    @FXML
    void clickNovo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lp3/Views/Fornecedor/dialogAdicionarFornecedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADICIONAR FORNECEDOR!");
        stage.setScene(scene);
        stage.showAndWait();

        DataSingleton data = DataSingleton.getInstance();
        fornecedores.add(data.getDataFornecedor());

    }
}
