package Controller.Fornecedor;

import Controller.DAL.LerFornecedores;
import Controller.DAL.LerEncomenda;
import Controller.DAL.LerUtilizadores;
import Model.*;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
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
                TableColumn<Fornecedor, Integer> colunaIdPais = new TableColumn<>("País");
                TableColumn<Fornecedor, Integer> colunaIdUtilizador = new TableColumn<>(" Tipo de utilizador");
                TableColumn<Fornecedor, String> colunaIdExterno = new TableColumn<>(" ID Externo");

                // Associe as colunas às propriedades da classe Fornecedor
                colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                colunaMorada1.setCellValueFactory(new PropertyValueFactory<>("morada1"));
                colunaMorada2.setCellValueFactory(new PropertyValueFactory<>("morada2"));
                colunaLocalidade.setCellValueFactory(new PropertyValueFactory<>("localidade"));
                colunaCodPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
                colunaIdPais.setCellValueFactory(new PropertyValueFactory<>("idPaisString"));
                colunaIdUtilizador.setCellValueFactory(new PropertyValueFactory<>("idUtilizadorString"));
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
                    if (lerEncomenda.podeEliminarFornecedor(baseDados, fornecedorSelecionado.getIdUtilizador())==true){
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

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }



                    }else{
                        try {
                            Mensagens.Erro("Erro!", "Fornecedor não pode ser eliminado por ter encomendas");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

        // }
    /*
@FXML

void clickEliminar() {
    Fornecedor fornecedorSelecionado = tableViewFornecedores.getSelectionModel().getSelectedItem();
    int id_util= fornecedorSelecionado.getIdUtilizador().getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Eliminar fornecedor");
        alert.setContentText("Tem certeza que deseja eliminar o fornecedor selecionado?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (!lerEncomenda.podeEliminarFornecedor(id_util)) {
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
                                // Note: Make sure fornecedores is a list of users
                                fornecedores.remove(fornecedorSelecionado.getIdUtilizador());
                            } else {
                                // Se a remoção do utilizador falhar, adicione o fornecedor de volta à lista
                                fornecedores.add(fornecedorSelecionado);
                            }
                        }

                    } catch (SQLException e) {
                        // Handle SQLException more gracefully
                        e.printStackTrace(); // Consider logging the exception
                    } catch (IOException e) {
                        // Handle IOException more gracefully
                        throw new RuntimeException(e); // Consider logging the exception
                    }
                }
            }
        });
    }
*/

        @FXML
        void clickNovo () throws IOException {
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
