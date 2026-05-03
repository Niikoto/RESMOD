package gui;

import java.io.IOException;
import java.util.List;

import dao.PedidoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.DashboardData;
import modelo.DashboardService;
import modelo.Pedido;

public class TelaPedidoController {
    @FXML
    private TableView<Pedido> tablePedido;

    @FXML
    private TableColumn<Pedido, Integer> idPedido;
    @FXML
    private TableColumn<Pedido, String> motivoPedido;
    @FXML
    private TableColumn<Pedido, String> formaPagamento;
    @FXML
    private TableColumn<Pedido, String> dataCriado;
    @FXML
    private TableColumn<Pedido, String> statusPedido;
    @FXML
    private TableColumn<Pedido, Float> valorTotal;
    @FXML
    private TableColumn<Pedido, String> criadoPor;

    @FXML
    private Label telaAprovados;
    @FXML
    private Label telaNegados;
    @FXML
    private Label telaEmAberto;
    @FXML
    private Label telaEmAnalise;
    @FXML
    private Label telaTotal;
    @FXML
    private Button botaoAdicionarPedido;

    @FXML
    public void initialize() {
        idPedido.setCellValueFactory(new PropertyValueFactory<>("iD_pedido"));
        formaPagamento.setCellValueFactory(new PropertyValueFactory<>("Forma_de_pagamento"));
        dataCriado.setCellValueFactory(new PropertyValueFactory<>("criado"));
        statusPedido.setCellValueFactory(new PropertyValueFactory<>("status"));
        valorTotal.setCellValueFactory(new PropertyValueFactory<>("Preco_total"));
        criadoPor.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNome()));

        motivoPedido.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        motivoPedido.setCellFactory(coluna -> new TableCell<Pedido, String>() {
            private final Text textoExpandido = new Text();

            {
                textoExpandido.wrappingWidthProperty().bind(coluna.widthProperty().subtract(10));

                this.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        Pedido pedidoSelecionado = getTableView().getSelectionModel().getSelectedItem();
                        if (pedidoSelecionado != null) {
                            abrirPopupStatus(pedidoSelecionado);
                        }
                        return;
                    }

                    if (!isEmpty() && getItem() != null) {
                        if (this.getGraphic() == textoExpandido) {
                            this.setGraphic(null);
                            this.setText(getItem());
                        } else {
                            textoExpandido.setText(getItem());
                            this.setGraphic(textoExpandido);
                            this.setText(null);
                        }
                        getTableView().requestLayout();
                    }
                });

                this.setOnMouseExited(event -> {
                    if (!isEmpty() && this.getGraphic() == textoExpandido) {
                        this.setGraphic(null);
                        this.setText(getItem());
                        getTableView().requestLayout();
                    }
                });

                this.setStyle("-fx-cursor: hand;");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(null);
                    setText(item);
                }
            }
        });

        // DIRETOR CLICA DUAS VEZES PARA SELECIONAR PEDIDO
        tablePedido.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Pedido pedidoSelecionado = tablePedido.getSelectionModel().getSelectedItem();
                if (pedidoSelecionado != null) {
                    abrirPopupStatus(pedidoSelecionado);
                }
            }
        });

        atualizarTabelaEDashboard();
    }

    // PARA ABRIR A TELA DE CONFIRMAR SE O DIRETOR
    // QUER ANALISAR O PEDIDO
    private void abrirPopupStatus(Pedido pedido) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaStatusPedido.fxml"));
                Parent root = loader.load();

                TelaStatusPedidoController statusController = loader.getController();
                statusController.setPedido(pedido);
                statusController.setPaiController(this);

                Stage popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(tablePedido.getScene().getWindow());
                popup.setTitle("Definir Status do Pedido #" + pedido.getID_pedido());
                popup.setScene(new Scene(root));
                popup.showAndWait();

                atualizarTabelaEDashboard();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void atualizarTabelaEDashboard() {
        PedidoDAO ped = new PedidoDAO();
        List<Pedido> lista = ped.listarPedidos();
        tablePedido.setItems(FXCollections.observableArrayList(lista));

        DashboardService service = new DashboardService();
        DashboardData dados = service.getDados();

        telaAprovados.setText(String.valueOf(dados.getTotalAprovados()));
        telaNegados.setText(String.valueOf(dados.getTotalNegados()));
        telaEmAberto.setText(String.valueOf(dados.getTotalEmAberto()));
        telaEmAnalise.setText(String.valueOf(dados.getTotalEmAnalise()));
        telaTotal.setText(String.valueOf(lista.size()));
    }

    @FXML
    public void adicionarTelaPedido(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedirPedidos.fxml"));
            Parent root = loader.load();

            TelaPedirPedidoController filhoController = loader.getController();
            filhoController.setPaiController(this);

            BorderPane painelPrincipal = (BorderPane) botaoAdicionarPedido.getScene().lookup("#painelPrincipal");

            if (painelPrincipal != null) {
                painelPrincipal.setCenter(root);
            } else {
                Stage stage = (Stage) botaoAdicionarPedido.getScene().getWindow();
                stage.setScene(new Scene(root));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int adicionarPedido(Pedido pedido) {
        int idGerado = 0;

        try {
            PedidoDAO dao = new PedidoDAO();
            idGerado = dao.cadastrarPedido(pedido);
            atualizarTabelaEDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGerado;
    }
}