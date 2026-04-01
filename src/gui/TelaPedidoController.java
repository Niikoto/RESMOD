package gui;

import java.util.List;

import dao.PedidoDAO;
import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

    //@FXML public void adicionarTelaPedido(ActionEvent event){
        
    //}
}

