package gui;

import dao.Entrada_SaidaDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Entrada_saida;

import java.util.Date;

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

        tabelaHistorico.setItems(
                FXCollections.observableArrayList(
                        dao.listar()
                )
        );
    }

    @FXML
    private Button fecharButton;

    @FXML
    private void fechar() {
        Stage stage = (Stage) fecharButton.getScene().getWindow();
        stage.close();
    }
}