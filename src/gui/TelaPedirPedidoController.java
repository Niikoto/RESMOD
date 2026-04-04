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
        // Pega o usuário na sessão
        Usuario session = Session.getUsuario();

        // Cria um novo pedido e preenche os dados
        // usamos setters para colocar tudo num lugar só!
        Pedido novoPedido = new Pedido();
        novoPedido.setMotivo(motivoTexto.getText());
        novoPedido.setStatus("Em aberto");
        novoPedido.setCOD_email(session.getId_Email());

        try {
            // Salva no banco e adiciona na lista da tela pai (a tela de pedidos)
            paiController.adicionarPedido(novoPedido);

            // voolta para a tela de pedidos na mesma janela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) botaoCadastro.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recebe e guarda a referência da tela pai para poder comunicar com ela
    // pois são interligadas
    public void setPaiController(TelaPedidoController paiController){
        this.paiController = paiController;
    }


}
