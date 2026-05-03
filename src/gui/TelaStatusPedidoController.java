package gui;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.Produto_has_pedidoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Pedido;
import modelo.Produto;
import modelo.Produto_has_pedido;
import modelo.Session;

import java.sql.SQLException;
import java.util.List;

public class TelaStatusPedidoController {

    @FXML private Label pedido_selecionado;

    @FXML private ComboBox<String> comBoxForUm;
    @FXML private HBox hBoxButton;
    @FXML private HBox hBoxProPed;
    @FXML private TextArea editar_motivo;

    @FXML private TableView<Produto_has_pedido> tableProPed;

    @FXML private TableColumn<Produto_has_pedido, Integer> colIdProd;
    @FXML private TableColumn<Produto_has_pedido, String> colNomeProd;
    @FXML private TableColumn<Produto_has_pedido, Float> colPrecoProd;
    @FXML private TableColumn<Produto_has_pedido, String> colFor;
    @FXML private TableColumn<Produto_has_pedido, Integer> colQua;
    @FXML private TableColumn<Produto_has_pedido, Float> colPrecoTotal;

    @FXML private VBox vBox_editar_motivo;
    @FXML private HBox hBox_status_pedido;
    @FXML private HBox hBox_add_prod;

    @FXML private ComboBox<Produto> comboProd;
    @FXML private Button btnCadProd;
    @FXML private TextField txtQuant;
    @FXML private TextField txtPreco;

    @FXML private Label labelTotal;

    private TelaPedidoController paiController;
    private Pedido pedidoAtual;

    private int idPed;
    private float totalPedido;

    ProdutoDAO daoProd = new ProdutoDAO();

    @FXML
    public void initialize() {
        comboProd.setItems(FXCollections.observableArrayList(daoProd.listarProdutos()));
        comboProd.setOnAction(e -> {
            if (comboProd.getValue() != null) {
                txtQuant.setDisable(false);
            }
        });

        txtQuant.setOnAction(e -> {
            if (txtQuant.getText() != null && comboProd.getValue() != null) {
                txtPreco.setDisable(false);

                float valor = comboProd.getValue().getPreco();
                valor = valor * Integer.parseInt(txtQuant.getText());
                txtPreco.setText(Float.toString(valor));

                btnCadProd.setDisable(false);
            }
        });

        vBox_editar_motivo.setVisible(false);
        vBox_editar_motivo.setManaged(false);
        hBox_status_pedido.setVisible(false);
        hBox_status_pedido.setManaged(false);
        hBox_add_prod.setManaged(false);
        hBox_add_prod.setVisible(false);

        colIdProd.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getProduto().getID_produto()).asObject());
        colNomeProd.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProduto().getNome_produto()));
        colPrecoProd.setCellValueFactory(cellData -> new javafx.beans.property.SimpleFloatProperty(
                cellData.getValue().getProduto().getPreco()).asObject());
        colFor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFornecedor().getNome_fornecedor()));
        colQua.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
        colPrecoTotal.setCellValueFactory(new PropertyValueFactory<>("preco_unitario"));
        comBoxForUm.setItems(FXCollections.observableArrayList("em analise", "aprovado", "negado"));
    }

    public Pedido getPedido(Pedido pedido){return pedido;}

    public void setPedido(Pedido pedido) {
        this.pedidoAtual = pedido;
        idPed = pedido.getID_pedido();
        pedido_selecionado.setText(String.valueOf(pedido.getID_pedido()));
        if((pedido.getCOD_email().equals(Session.getUsuario().getId_Email()) && !pedido.getStatus().equals("em analise")) || Session.getCargo().isAdm()) {
            editar_motivo.setText(pedido.getMotivo());
            vBox_editar_motivo.setVisible(true);
            vBox_editar_motivo.setManaged(true);
            hBox_status_pedido.setVisible(true);
            hBox_status_pedido.setManaged(true);
            hBox_add_prod.setManaged(true);
            hBox_add_prod.setVisible(true);
        } else {
            System.out.println("ERRO");
        }
        Produto_has_pedidoDAO dao = new Produto_has_pedidoDAO();
        List<Produto_has_pedido> list = dao.listarProdutosPedidos(Integer.parseInt(pedido_selecionado.getText()));
        tableProPed.setItems(FXCollections.observableArrayList(list));
    }

    public void setPaiController(TelaPedidoController pai) {this.paiController = pai;}

    @FXML
    public void voltarParaPedidos(ActionEvent event) {
        Stage stage = (Stage) pedido_selecionado.getScene().getWindow();
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

        Stage stage = (Stage) pedido_selecionado.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void enviarProdPed(ActionEvent event) {
        Produto_has_pedido p = new Produto_has_pedido();
        Produto_has_pedidoDAO dao = new Produto_has_pedidoDAO();
        PedidoDAO daoPedido = new PedidoDAO();

        p.setQuantidade(Integer.parseInt(txtQuant.getText()));
        p.setCOD_produto(comboProd.getValue().getID_produto());
        p.setPreco_unitario(Float.parseFloat(txtPreco.getText()));

        try {
            dao.inserirProdPed(idPed, p);

            List<Produto_has_pedido> list = dao.listarProdutosPedidos(idPed);
            tableProPed.setItems(FXCollections.observableArrayList(list));

            for (Produto_has_pedido item : list) {
                totalPedido = totalPedido + item.getPreco_unitario();
            }

            daoPedido.upDatePrecoTotal(idPed, totalPedido);
            labelTotal.setText(String.format("Total: R$ %.2f", totalPedido));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        totalPedido = 0;

        txtQuant.clear();
        txtPreco.clear();

        txtQuant.setDisable(true);
        txtPreco.setDisable(true);
        btnCadProd.setDisable(true);

        comboProd.setValue(null);
    }
}
