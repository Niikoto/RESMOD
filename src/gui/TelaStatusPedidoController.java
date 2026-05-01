package gui;

import dao.PedidoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelo.Pedido;
import modelo.Produto_has_pedido;

import java.util.List;

public class TelaStatusPedidoController {

    @FXML
    private TableView<Produto_has_pedido> tableProPed;

    @FXML private TableView<Pedido> tablePedido;
    @FXML private TableColumn<Pedido, Integer> idPedido;
    @FXML private TableColumn<Pedido, String> motivoPedido;
    @FXML private TableColumn<Pedido, String> formaPagamento;
    @FXML private TableColumn<Pedido, String> dataCriado;
    @FXML private TableColumn<Pedido, String> statusPedido;
    @FXML private TableColumn<Pedido, Float> valorTotal;
    @FXML private TableColumn<Pedido, String> criadoPor;
    @FXML private ComboBox<String> comBoxForUm;

    @FXML private HBox hBoxButton;
    @FXML private HBox hBoxProPed;
    @FXML private TextArea editar_motivo;

    private TelaPedidoController paiController;
    private Pedido pedidoAtual;

    @FXML
    public void initialize() {

        idPedido.setCellValueFactory(new PropertyValueFactory<>("iD_pedido"));
        motivoPedido.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        formaPagamento.setCellValueFactory(new PropertyValueFactory<>("Forma_de_pagamento"));
        dataCriado.setCellValueFactory(new PropertyValueFactory<>("criado"));
        statusPedido.setCellValueFactory(new PropertyValueFactory<>("status"));
        valorTotal.setCellValueFactory(new PropertyValueFactory<>("Preco_total"));
        criadoPor.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNome()));

        comBoxForUm.setItems(FXCollections.observableArrayList("em analise", "aprovado", "negado"));
    }

    public Pedido getPedido(Pedido pedido){return pedido;}

    public void setPedido(Pedido pedido) {
        this.pedidoAtual = pedido;
        tablePedido.setItems(FXCollections.observableArrayList(List.of(pedido)));
        editar_motivo.setText(pedido.getMotivo());
    }

    public void setPaiController(TelaPedidoController pai) {this.paiController = pai;}

    @FXML
    public void voltarParaPedidos(ActionEvent event) {
        Stage stage = (Stage) tablePedido.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void definirNovoStatus(ActionEvent event) {
        String novoStatus = comBoxForUm.getValue();

        if (novoStatus == null || novoStatus.isEmpty()) {return;}

        PedidoDAO dao = new PedidoDAO();
        dao.updateStatus(pedidoAtual.getID_pedido(), novoStatus, editar_motivo.getText());

        if (paiController != null) {
            paiController.atualizarTabelaEDashboard();
        }

        Stage stage = (Stage) tablePedido.getScene().getWindow();
        stage.close();
    }
}