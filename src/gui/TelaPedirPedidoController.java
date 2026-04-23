package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import modelo.Pedido;
import modelo.Session; // IMPORTANTE: Para saber quem está a fazer o pedido

public class TelaPedirPedidoController {

    @FXML private TextArea campoMotivo;

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

            //dados necessarios pegados do banco
            novoPedido.setStatus("em aberto"); // Status inicial padrão
            novoPedido.setCOD_email(Session.getUsuario().getId_Email()); // Vincula ao usuário logado
            novoPedido.setUsuario(Session.getUsuario()); // Previne erros na tabela visual

            if (paiController != null) {
                paiController.adicionarPedido(novoPedido);
            }

            // Depois de criar, volta para a tela de pedidos automaticamente
            voltarParaPedidos(event);
        }
    }
}