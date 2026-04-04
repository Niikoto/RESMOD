package gui;

import java.io.IOException;
import java.util.List;

import dao.PedidoDAO;
import dashboard.DashboardData;
import dashboard.DashboardService;
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
        // Carrega todos os pedidos do banco e exibe na lista
        PedidoDAO ped = new PedidoDAO();
        List<Pedido> lista = ped.listarPedidos();
        listaPedidos.setItems(FXCollections.observableArrayList(lista));

        // Busca os dados do dashboard e exibe nos painéis
        // (MUDAR)
        // (DEPOIS)
        // (PARA OUTRO JEITO
        DashboardService service = new DashboardService();
        DashboardData dados = service.getDados();

        telaAprovados.setText(String.valueOf(dados.getTotalAprovados()));
        telaNegados.setText(String.valueOf(dados.getTotalNegados()));
        telaEmAberto.setText(String.valueOf(dados.getTotalEmAberto()));
        telaEmAnalise.setText(String.valueOf(dados.getTotalEmAnalise()));
        telaTotal.setText(String.valueOf(lista.size()));
    }

    @FXML public void adicionarTelaPedido(ActionEvent event) throws IOException {
        try {
            // Carrega a tela de pedir pedido
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedirPedidos.fxml"));
            Parent root = loader.load();

            // Passa a referência deste controller para a tela filha
            TelaPedirPedidoController filhoController = loader.getController();
            filhoController.setPaiController(this);

            // Troca a cena na mesma janela
            Stage stage = (Stage) botaoAdicionarPedido.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarPedido(Pedido pedido) {
        try {
            // é o dao, ele cadastra
            PedidoDAO dao = new PedidoDAO();
            dao.cadastrarPedido(pedido);
            listaPedidos.getItems().add(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}