package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import dao.CompraDAO;
import dao.PedidoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Compra;
import modelo.Pedido;
import modelo.Session;

public class TelaComprarPedidoController {
    @FXML
    private Label nTxt; //Numero do pedido
    @FXML
    private Label vTxt; //Valor do Pedido
    @FXML
    private Label txtArq;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextArea txtAreaObs;

    private int numeroPed;
    private float valorPed;

    private TelaPedidoController controller;

    public void setPaiController(TelaPedidoController controller, Pedido p){
        this.controller = controller;

        numeroPed = p.getID_pedido();
        valorPed = p.getPreco_total();

        nTxt.setText("Pedido N°: "+Integer.toString(numeroPed));
        vTxt.setText(Float.toString(valorPed));
    }

    public void selecioneArq(ActionEvent event){
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

        txtArq.setText(arquivo.getName());
    }
    
    public void cancelarVoltar(ActionEvent event){
        try {
            // Carrega a tela com a tabela de pedidos novamente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
            Parent root = loader.load();
    
            // Procura o BorderPane principal (o chassi da nossa SPA)
            BorderPane painel = (BorderPane) btnCancelar.getScene().lookup("#painelPrincipal");
    
            if (painel != null) {
                // Coloca a tabela de volta no centro!
                painel.setCenter(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finalizarCompra(ActionEvent event){
        Compra c = new Compra();
        CompraDAO dao = new CompraDAO();

        c.setAnexo_fiscal(txtArq.getText());
        c.setObs_compra(txtAreaObs.getText());
        c.setCOD_pedido(numeroPed);
        c.setCOD_email(Session.getUsuario().getId_Email());
        c.setValor_da_compra(valorPed);

        try {            
            dao.finalizarCompraPed(c);

            PedidoDAO dao2 = new PedidoDAO();

            dao2.upDataStatus("finalizado", numeroPed);

            exibirAlerta("Sucesso", "Compra finalizada com sucesso");
            fecharTela(event);
        } catch (Exception e) {
            exibirAlerta("Erro", "Não foi possivel finalizar a compra");
        }
    }

    public void fecharTela(ActionEvent event){
        try {
            // Carrega a tela com a tabela de pedidos novamente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
            Parent root = loader.load();
    
            // Procura o BorderPane principal (o chassi da nossa SPA)
            BorderPane painel = (BorderPane) btnCancelar.getScene().lookup("#painelPrincipal");
    
            if (painel != null) {
                // Coloca a tabela de volta no centro!
                painel.setCenter(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
