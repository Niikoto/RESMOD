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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import modelo.Pedido;

public class TelaPedidoController {
    @FXML private ListView<Pedido> listaPedidos;
    @FXML private Label telaAprovados;
    @FXML private Label telaNegados;
    @FXML private Label telaEmAberto;
    @FXML private Label telaEmAnalise;
    @FXML private Label telaTotal;
    @FXML private Button botaoAdicionarPedido;

    
    @FXML
    public void initialize() {
        PedidoDAO ped= new PedidoDAO();
        List<Pedido> lista = ped.listarPedidos();
        listaPedidos.setItems(FXCollections.observableArrayList(lista));//Guarda todos os pedidos em forma de String
    }

    @FXML public void adicionarTelaPedido(ActionEvent event)throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedirPedidos.fxml"));
            Parent root = loader.load();  

            TelaPedirPedidoController filhoController = loader.getController();
            filhoController.setPaiController(this);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage stagezinho = (Stage) botaoAdicionarPedido.getScene().getWindow(); 
            stagezinho.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void adicionarPedido(Pedido pedido){
        try {
            PedidoDAO dao = new PedidoDAO();
            dao.cadastrarPedido(pedido);
            listaPedidos.getItems().add(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

