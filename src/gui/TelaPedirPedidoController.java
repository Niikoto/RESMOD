package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modelo.Pedido;
import modelo.Session; // IMPORTANTE: Para saber quem está a fazer o pedido

public class TelaPedirPedidoController {

    @FXML
    private TextArea campoMotivo;

    @FXML
    private ComboBox<String> comBoxForUm;
    @FXML
    private ComboBox<String> comBoxForDois;

    @FXML
    private HBox hBoxButton;
    @FXML
    private HBox hBoxProPed;

    private String valorCombo1Anterior = null;
    private String valorCombo2Anterior = null;

    @FXML
    public void initialize() {
        hBoxProPed.setVisible(false);
        hBoxProPed.setManaged(false);

        comBoxForUm.getItems().addAll("Pix", "Boleto", "Deposito Bancario", "Transferência Bancaria",
                "Cartão de Credito");

        comBoxForDois.getItems().addAll("Pix", "Boleto", "Deposito Bancario", "Transferência Bancaria",
                "Cartão de Credito");

        comBoxForUm.setOnAction(e -> {
            String atual = comBoxForUm.getValue();

            if (valorCombo1Anterior != null) {
                comBoxForDois.getItems().add(valorCombo1Anterior);
            }

            if(atual != null){
                comBoxForDois.getItems().remove(atual);
            }
        });
        comBoxForDois.setOnAction(e -> {
            String atual = comBoxForDois.getValue();

            if (valorCombo2Anterior != null) {
                comBoxForUm.getItems().add(valorCombo2Anterior);
            }

            if(atual != null){
                comBoxForUm.getItems().remove(atual);
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
                paiController.adicionarPedido(novoPedido);
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
    }

}