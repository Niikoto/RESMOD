package gui;

import java.sql.SQLException;
import java.util.List;

import dao.ProdutoDAO;
import dao.Produto_has_pedidoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modelo.Pedido;
import modelo.Produto;
import modelo.Produto_has_pedido;
import modelo.Session; // IMPORTANTE: Para saber quem está a fazer o pedido

public class TelaPedirPedidoController {

    @FXML
    private TextArea campoMotivo;

    @FXML
    private TextField txtQuant;
    @FXML
    private TextField txtPreco;

    @FXML
    private ComboBox<String> comBoxForUm;
    @FXML
    private ComboBox<String> comBoxForDois;
    @FXML
    private ComboBox<Produto> comboProd;

    @FXML
    private TableView<Produto_has_pedido> tableProPed;

    @FXML
    private TableColumn<Produto_has_pedido, Integer> colIdProd;
    @FXML
    private TableColumn<Produto_has_pedido, String> colNomeProd;
    @FXML
    private TableColumn<Produto_has_pedido, Float> colPrecoProd;
    @FXML
    private TableColumn<Produto_has_pedido, String> colFor;
    @FXML
    private TableColumn<Produto_has_pedido, Integer> colQua;
    @FXML
    private TableColumn<Produto_has_pedido, Float> colPrecoTotal;

    @FXML
    private HBox hBoxButton;
    @FXML
    private HBox hBoxProPed;

    @FXML
    private Button btnCadProd;

    private String valorCombo1Anterior = null;
    private String valorCombo2Anterior = null;

    private int idPed;

    ProdutoDAO daoProd = new ProdutoDAO();

    

    @FXML
    public void initialize() {
        hBoxProPed.setVisible(false);
        hBoxProPed.setManaged(false);

        txtQuant.setDisable(true);
        btnCadProd.setDisable(true);
        txtPreco.setDisable(true);

        comBoxForUm.getItems().addAll("Pix", "Boleto", "Deposito Bancario", "Transferência Bancaria",
                "Cartão de Credito");

        comBoxForDois.getItems().addAll("Pix", "Boleto", "Deposito Bancario", "Transferência Bancaria",
                "Cartão de Credito");

        comBoxForUm.setOnAction(e -> {
            String atual = comBoxForUm.getValue();

            if (valorCombo1Anterior != null) {
                comBoxForDois.getItems().add(valorCombo1Anterior);
            }

            if (atual != null) {
                comBoxForDois.getItems().remove(atual);
            }
        });
        comBoxForDois.setOnAction(e -> {
            String atual = comBoxForDois.getValue();

            if (valorCombo2Anterior != null) {
                comBoxForUm.getItems().add(valorCombo2Anterior);
            }

            if (atual != null) {
                comBoxForUm.getItems().remove(atual);
            }
        });

        comboProd.setItems(FXCollections.observableArrayList(daoProd.listarProdutos()));

        comboProd.setOnAction(e ->{
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
    }

    // A referência para o controller pai
    private TelaPedidoController paiController;

    public void setPaiController(TelaPedidoController paiController) {
        this.paiController = paiController;
    }

    @FXML
    public void voltarParaPedidos(ActionEvent event) {
        try {
            // Carrega a tela com a tabela de pedidos novamente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
            Parent root = loader.load();

            // Procura o BorderPane principal (o chassi da nossa SPA)
            BorderPane painelPrincipal = (BorderPane) campoMotivo.getScene().lookup("#painelPrincipal");

            if (painelPrincipal != null) {
                // Coloca a tabela de volta no centro!
                painelPrincipal.setCenter(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void criarPedido(ActionEvent event) {
        String motivo = campoMotivo.getText();

        if (motivo != null && !motivo.trim().isEmpty()) {
            Pedido novoPedido = new Pedido();

            novoPedido.setMotivo(motivo);

            if (comBoxForUm.getValue() != "") {
                novoPedido.setForma_de_pagamento(comBoxForUm.getValue());
            } else {
                novoPedido.setForma_de_pagamento(null);
            }

            if (comBoxForDois.getValue() != "") {
                novoPedido.setSegunda_forma_de_pagamento(comBoxForDois.getValue());
            } else {
                novoPedido.setSegunda_forma_de_pagamento(null);
            }

            // dados necessarios pegados do banco
            novoPedido.setStatus("em aberto"); // Status inicial padrão
            novoPedido.setCOD_email(Session.getUsuario().getId_Email()); // Vincula ao usuário logado
            novoPedido.setUsuario(Session.getUsuario()); // Previne erros na tabela visual

            if (paiController != null) {
                idPed = paiController.adicionarPedido(novoPedido);
            }

            // Depois de criar, volta para a tela de pedidos automaticamente
            cadastrarProdutoPed();
        }
    }

    public void cadastrarProdutoPed() {
        hBoxProPed.setVisible(true);
        hBoxProPed.setManaged(true);

        hBoxButton.setVisible(false);
        hBoxButton.setManaged(false);

        campoMotivo.setDisable(true);

        comBoxForUm.setDisable(true);
        comBoxForDois.setDisable(true);

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
    }

    @FXML
    public void enviarProdPed(ActionEvent event) {
        Produto_has_pedido p = new Produto_has_pedido();
        Produto_has_pedidoDAO dao = new Produto_has_pedidoDAO();

        p.setQuantidade(Integer.parseInt(txtQuant.getText()));
        p.setCOD_produto(comboProd.getValue().getID_produto());
        p.setPreco_unitario(Float.parseFloat(txtPreco.getText()));

        try {
            dao.inserirProdPed(idPed, p);

            List<Produto_has_pedido> list = dao.listarProdutosPedidos(idPed);
            tableProPed.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtQuant.clear();
        txtPreco.clear();

        txtQuant.setDisable(true);
        txtPreco.setDisable(true);
        btnCadProd.setDisable(true);

        comboProd.setValue(null);
    }
}