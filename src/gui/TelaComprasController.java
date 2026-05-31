package gui;

import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

import dao.CompraDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.Compra;

public class TelaComprasController {
    @FXML
    private TableView<Compra> tableCompra;

    @FXML
    private TableColumn<Compra, Integer> colCompras, colValCom, colNPedido;

    @FXML
    private TableColumn<Compra, String> colObs, colDataCompra, colCriado;

    @FXML
    private TableColumn<Compra, Void> colAnex;

    @FXML
    private Button btnAlterCons;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnComprar;

    CompraDAO daoCompra = new CompraDAO();

    @FXML
    public void initialize() {
        colCompras.setCellValueFactory(new PropertyValueFactory<>("ID_compra"));
        colObs.setCellValueFactory(new PropertyValueFactory<>("Obs"));
        colValCom.setCellValueFactory(new PropertyValueFactory<>("Valor_da_compra"));
        colAnex.setCellFactory(par -> new TableCell<>() {
            private final Button btnAnexo = new Button("📄 Arquivo");
            {
                btnAnexo.setStyle(
                        "-fx-background-color:  #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6px; -fx-cursor: hand; -fx-font-size: 14px;");
                btnAnexo.setOnAction(e -> {
                    try {
                        Compra compra = getTableView().getItems().get(getIndex()); 
                        File file = new File("src/resources/sources/"+compra.getAnexo_fiscal());
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnAnexo);
                }
            }
        });
        colDataCompra.setCellValueFactory(new PropertyValueFactory<>("data_compra"));
        colNPedido.setCellValueFactory(new PropertyValueFactory<>("COD_pedido"));
        colCriado.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNome()));

        tableCompra.setItems(FXCollections.observableArrayList(daoCompra.listaCompras()));

        tableCompra.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnExcluir.setDisable(false);
                btnAlterCons.setDisable(false);
            } else {
                btnExcluir.setDisable(true);
                btnAlterCons.setDisable(true);
            }
        });
    }

    @FXML
    public void addCompra(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaComprar.fxml"));
            Parent root = loader.load();

            TelaComprarController filhoController = loader.getController();
            filhoController.setPaiController(this);

            BorderPane painel = (BorderPane) btnComprar.getScene().lookup("#painelPrincipal");

            if (painel != null) {
                painel.setCenter(root);
            } else {
                Stage stage = (Stage) btnComprar.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void alterConsultar(ActionEvent event) {
        Compra selecionada = tableCompra.getSelectionModel().getSelectedItem();
        if (selecionada == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaEditarCompra.fxml"));
            Parent root = loader.load();

            TelaEditarCompraController editController = loader.getController();
            editController.setDados(selecionada, this);
            editController.preencherDados();

            Stage stage = new Stage();
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Compra");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void excluirCompra(){
        CompraDAO dao = new CompraDAO();
        int idCompra = tableCompra.getSelectionModel().getSelectedItem().getID_compra();
        dao.deletarCompra(idCompra);
        tableCompra.getItems().remove(tableCompra.getSelectionModel().getSelectedItem());
    }

    public void recarregarTabela() {
        tableCompra.setItems(FXCollections.observableArrayList(daoCompra.listaCompras()));
    }
}
