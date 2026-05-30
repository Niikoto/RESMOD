package gui;

import dao.Entrada_SaidaDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Entrada_saida;

import java.util.Date;
import java.util.List;

public class TelaHistoricoEstoqueController {

    @FXML
    private TableView<Entrada_saida> tabelaHistorico;

    @FXML
    private TableColumn<Entrada_saida, Boolean> colTipo;

    @FXML
    private TableColumn<Entrada_saida, Integer> colQuantidade;

    @FXML
    private TableColumn<Entrada_saida, Date> colData;

    @FXML
    private TableColumn<Entrada_saida, String> colEmail;

    @FXML
    private TableColumn<Entrada_saida, Integer> colProduto;

    @FXML
    public void initialize() {

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );

        colProduto.setCellValueFactory(
                new PropertyValueFactory<>("COD_produto")
        );

        colTipo.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Entrada" : "Saída");
                }
            }
        });

        colQuantidade.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );

        colData.setCellValueFactory(
                new PropertyValueFactory<>("data")
        );

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        Entrada_SaidaDAO dao = new Entrada_SaidaDAO();

        historico = dao.listar();

        int paginas = (int) Math.ceil(
                (double) historico.size() / ITENS_POR_PAGINA
        );

        pagination.setPageCount(Math.max(1, paginas));

        pagination.currentPageIndexProperty().addListener(
                (obs, oldValue, newValue) ->
                        carregarPagina(newValue.intValue())
        );

        carregarPagina(0);


    }

    @FXML
    private Button fecharButton;

    @FXML
    private Pagination pagination;
    private List<Entrada_saida> historico;
    private final int ITENS_POR_PAGINA = 15;
    private void carregarPagina(int pagina) {

        int inicio = pagina * ITENS_POR_PAGINA;

        int fim = Math.min(
                inicio + ITENS_POR_PAGINA,
                historico.size()
        );

        tabelaHistorico.setItems(
                FXCollections.observableArrayList(
                        historico.subList(inicio, fim)
                )
        );
    }


    @FXML
    private void fechar() {
        Stage stage = (Stage) fecharButton.getScene().getWindow();
        stage.close();
    }
}