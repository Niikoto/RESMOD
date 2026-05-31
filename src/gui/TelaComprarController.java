package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import dao.CompraDAO;
import dao.PedidoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Compra;
import modelo.Pedido;
import modelo.Session;

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

    @FXML
    private TextField txtValorCompra;

    TelaComprasController controller;

    @FXML
    public void initialize(){
        PedidoDAO dao = new PedidoDAO();

        comboPed.setItems(FXCollections.observableArrayList(dao.listarPedidosNaoNegado()));
        comboPed.setOnAction(e -> {
            txtValorCompra.setText(Float.toString(comboPed.getValue().getPreco_total()));
            txtValorCompra.setDisable(false);
        });
    }

    public void setPaiController(TelaComprasController controller){
        this.controller = controller;
    }

    @FXML
    public void voltar(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCompras.fxml"));
            Parent root = loader.load();

            BorderPane painel = (BorderPane) btnVoltar.getScene().lookup("#painelPrincipal");

            if (painel != null) {
                painel.setCenter(root);
            } else {
                Stage stage = (Stage) btnVoltar.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void selecArqu(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Escolha um arquivo como nota fiscal");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos", "*.png","*.jpg","*.jpeg","*.pdf","*.docx","*.doc"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//cria a tela para seleção do arquivo

        File arquivo = chooser.showOpenDialog(stage);//guarda o arquivo escolhido na tela

        Path destino = Paths.get("src/resources/sources/"+arquivo.getName());//Onde vai ser salvo a imagem

        try {            
            Files.copy(arquivo.toPath(),destino,StandardCopyOption.REPLACE_EXISTING);//salva o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgNotFound.setText(arquivo.getName());
    }

    @FXML
    public void compraPed(ActionEvent event){
        Compra compra = new Compra();
        CompraDAO dao = new CompraDAO();
        Pedido ped = new Pedido();

        ped.setID_pedido(comboPed.getValue().getID_pedido());

        compra.setObs_compra(txtArea.getText());
        compra.setValor_da_compra(Float.parseFloat(txtValorCompra.getText()));
        compra.setCOD_pedido(ped.getID_pedido());
        compra.setAnexo_fiscal(imgNotFound.getText());
        compra.setCOD_email(Session.getUsuario().getId_Email());

        dao.finalizarCompraPed(compra);

        voltar(event);
    }
}
