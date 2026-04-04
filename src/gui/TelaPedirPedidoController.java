package gui;

import dao.PedidoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import modelo.Pedido;
import modelo.Session;
import modelo.Usuario;

public class TelaPedirPedidoController {
    @FXML private TextArea motivoTexto;
    @FXML private Button botaoCadastro;
    private TelaPedidoController paiController;

    @FXML public void cadastrarPedido(ActionEvent event) {
        Usuario session = Session.getUsuario();
        Pedido novoPedido = new Pedido();
        novoPedido.setMotivo(motivoTexto.getText());
        novoPedido.setStatus("Em aberto");
        novoPedido.setCOD_email(session.getId_Email());
        try {
            PedidoDAO dao = new PedidoDAO();
            dao.cadastrarPedido(novoPedido);
            paiController.adicionarPedido(novoPedido);
            Stage stage = (Stage) botaoCadastro.getScene().getWindow(); 
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
            Parent root = loader.load();  

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPaiController(TelaPedidoController paiController){
        this.paiController = paiController;
    }


}
