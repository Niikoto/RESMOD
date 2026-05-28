package gui;

import dao.Historico_Pedido_DAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo. *;

import java.util.Date;

public class TelaHistoricoPedidoController {

    @FXML
    private TableView<Historico_pedido> tabelaHistoricoPedido;

    @FXML
    private TableColumn<Historico_pedido, String> colEmail;

    @FXML
    private TableColumn<Historico_pedido, String> colStatus;

    @FXML
    private TableColumn<Historico_pedido, Date> colDataAlteracao;

    @FXML
    private TableColumn<Historico_pedido, Integer> colPedido;

    @FXML
    public void initialize() {

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("COD_email")
        );

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        colDataAlteracao.setCellValueFactory(
                new PropertyValueFactory<>("data_alteracao")
        );

        colPedido.setCellValueFactory(
                new PropertyValueFactory<>("COD_pedido")
        );

        Historico_Pedido_DAO dao = new Historico_Pedido_DAO();

        tabelaHistoricoPedido.setItems(
                FXCollections.observableArrayList(
                        dao.listar()
                )
        );
    }

    @FXML
    private Button fecharButtonPedido;

    @FXML
    private void fecharPed() {
        Stage stage = (Stage) fecharButtonPedido.getScene().getWindow();
        stage.close();
    }
}