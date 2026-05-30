package gui;

import dao.PedidoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.DashboardData;
import modelo.Pedido;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TelaCentroCustoController implements Initializable {


    @FXML private ComboBox<String> comboSetor;
    @FXML private ComboBox<String> comboCentroCusto;

    // cards de valores totais
    @FXML private Label labelTotalSetor;
    @FXML private Label labelNomeSetor;
    @FXML private Label labelTotalCentro;
    @FXML private Label labelNomeCentro;
    @FXML private Label labelQtdPedidos;
    @FXML private Label labelTotalTabela;

    // tabela
    @FXML private TableView<Pedido> tablePedidos;
    @FXML private TableColumn<Pedido, Integer> colId;
    @FXML private TableColumn<Pedido, String> colMotivo;
    @FXML private TableColumn<Pedido, String> colSetor;
    @FXML private TableColumn<Pedido, String> colCentro;
    @FXML private TableColumn<Pedido, String> colStatus;
    @FXML private TableColumn<Pedido, Float> colValor;
    @FXML private TableColumn<Pedido, String> colCriado;
    @FXML private TableColumn<Pedido, String> colCriadoPor;

    private final PedidoDAO dao = new PedidoDAO();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunas();
        popularCombos();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("iD_pedido"));
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colCentro.setCellValueFactory(new PropertyValueFactory<>("centro_custo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("preco_total"));
        colCriado.setCellValueFactory(new PropertyValueFactory<>("criado"));
        colCriadoPor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getUsuario() != null ? cellData.getValue().getUsuario().getNome() : ""));
    }


    private void popularCombos() {
        List<Pedido> todos = dao.listarPedidos();

        List<String> setores = todos.stream()
                .map(Pedido::getSetor)
                .filter(s -> s != null && !s.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> centros = todos.stream()
                .map(Pedido::getCentro_custo)
                .filter(c -> c != null && !c.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        comboSetor.setItems(FXCollections.observableArrayList(setores));
        comboCentroCusto.setItems(FXCollections.observableArrayList(centros));
    }
    @FXML
    private void aplicarFiltro() {
        String setor  = comboSetor.getValue();
        String centro = comboCentroCusto.getValue();

        List<Pedido> pedidos = dao.listarPedidosFiltrados(setor, centro);
        ObservableList<Pedido> lista = FXCollections.observableArrayList(pedidos);
        tablePedidos.setItems(lista);


        labelQtdPedidos.setText(String.valueOf(pedidos.size()));

        float totalTabela = (float) pedidos.stream()
                .mapToDouble(p -> p.getPreco_total())
                .sum();
        labelTotalTabela.setText(String.format("R$ %,.2f", totalTabela));

        atualizarCardSetor(setor);

        atualizarCardCentro(centro);
    }

    @FXML
    private void limparFiltro() {
        comboSetor.setValue(null);
        comboCentroCusto.setValue(null);
        tablePedidos.setItems(FXCollections.observableArrayList());
        labelQtdPedidos.setText("0");
        labelTotalTabela.setText("R$ 0,00");
        labelTotalSetor.setText("R$ 0,00");
        labelNomeSetor.setText("(todos)");
        labelTotalCentro.setText("R$ 0,00");
        labelNomeCentro.setText("(todos)");
    }



    private void atualizarCardSetor(String setor) {
        if (setor == null || setor.isEmpty()) {
            labelNomeSetor.setText("(todos)");
            labelTotalSetor.setText("R$ 0,00");
            return;
        }

        List<Pedido> pedidosSetor = dao.listarPedidosFiltrados(setor, null);
        float total = (float) pedidosSetor.stream()
                .mapToDouble(Pedido::getPreco_total)
                .sum();

        labelNomeSetor.setText(setor);
        labelTotalSetor.setText(String.format("R$ %,.2f", total));
    }

    private void atualizarCardCentro(String centro) {
        if (centro == null || centro.isEmpty()) {
            labelNomeCentro.setText("(todos)");
            labelTotalCentro.setText("R$ 0,00");
            return;
        }

        List<Pedido> pedidosCentro = dao.listarPedidosFiltrados(null, centro);
        float total = (float) pedidosCentro.stream()
                .mapToDouble(Pedido::getPreco_total)
                .sum();

        labelNomeCentro.setText(centro);
        labelTotalCentro.setText(String.format("R$ %,.2f", total));
    }

}