package gui;

import dao.Historico_Pedido_DAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo. *;

import java.util.Date;
import java.util.List;

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

        historico = dao.listar();

        int paginas = (int) Math.ceil(
                (double) historico.size() / ITENS_POR_PAGINA
        );

        pedipagination.setPageCount(Math.max(1, paginas));

        pedipagination.currentPageIndexProperty().addListener(
                (obs, oldValue, newValue) ->
                        carregarPagina(newValue.intValue())
        );

        carregarPagina(0);
    }

    @FXML
    private Pagination pedipagination;
    private List<Historico_pedido> historico;
    private final int ITENS_POR_PAGINA = 15;
    private void carregarPagina(int pagina) {

        int inicio = pagina * ITENS_POR_PAGINA;

        int fim = Math.min(
                inicio + ITENS_POR_PAGINA,
                historico.size()
        );

        tabelaHistoricoPedido.setItems(
                FXCollections.observableArrayList(
                        historico.subList(inicio, fim)
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