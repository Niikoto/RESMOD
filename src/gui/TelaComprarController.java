package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import modelo.Pedido;

public class TelaComprarController {
    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnSelecionar;
    @FXML
    private Button btnComprarPed;

    @FXML
    private Label imgNotFound;

    @FXML
    private ComboBox<Pedido> comboPed;

    @FXML
    private TextArea txtArea;

    TelaComprasController controller;

    public void setPaiController(TelaComprasController controller){
        this.controller = controller;
    }

    @FXML
    public void voltar(ActionEvent event){

    }

    @FXML
    public void selecArqu(ActionEvent event){

    }

    @FXML
    public void compraPed(ActionEvent event){

    }
}
