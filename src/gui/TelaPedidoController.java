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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text; // Importação adicionada para a célula expansível
import javafx.stage.Stage;
import modelo.Pedido;

public class TelaPedidoController {
    @FXML private TableView<Pedido> tablePedido;

    @FXML private TableColumn<Pedido, Integer> idPedido;
    @FXML private TableColumn<Pedido, String> motivoPedido;
    @FXML private TableColumn<Pedido, String> formaPagamento;
    @FXML private TableColumn<Pedido, String> dataCriado;
    @FXML private TableColumn<Pedido, String> statusPedido;
    @FXML private TableColumn<Pedido, Float> valorTotal;
    @FXML private TableColumn<Pedido, String> criadoPor;

    @FXML private Label telaAprovados;
    @FXML private Label telaNegados;
    @FXML private Label telaEmAberto;
    @FXML private Label telaEmAnalise;
    @FXML private Label telaTotal;
    @FXML private Button botaoAdicionarPedido;

    @FXML
    public void initialize() {
        idPedido.setCellValueFactory(new PropertyValueFactory<>("iD_pedido")); //essa parte vai usar o metodo get nas coisas que estão dentro das aspas no parenteses, a primeira letra ele vai deixar maiuscula
        formaPagamento.setCellValueFactory(new PropertyValueFactory<>("Forma_de_pagamento"));
        dataCriado.setCellValueFactory(new PropertyValueFactory<>("criado"));
        statusPedido.setCellValueFactory(new PropertyValueFactory<>("status"));
        valorTotal.setCellValueFactory(new PropertyValueFactory<>("Preco_total"));
        criadoPor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNome())); // esse daqui tá diferente pois estamos guarando um objeto do usuario na classe pedido

        //codigo da linha da tabela expandir quando clica
        motivoPedido.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        motivoPedido.setCellFactory(coluna -> new TableCell<Pedido, String>() {
            private final Text textoExpandido = new Text();

            {
                // Configura o Text para quebrar a linha
                textoExpandido.wrappingWidthProperty().bind(coluna.widthProperty().subtract(10));

                this.setOnMouseClicked(event -> {
                    if (!isEmpty() && getItem() != null) {
                        // Se estiver com o texto longo visível, esconde. Se não, mostra.
                        if (this.getGraphic() == textoExpandido) {
                            this.setGraphic(null);
                            this.setText(getItem());
                        } else {
                            textoExpandido.setText(getItem());
                            this.setGraphic(textoExpandido);
                            this.setText(null); // Remove o texto original
                        }
                        // Recalcula apenas as alturas das linhas, sem reiniciar a tabela
                        getTableView().requestLayout();
                    }
                });

                this.setOnMouseExited(event -> {
                    // Ao tirar a setinha do mause, se estiver expandido, volta a encolher
                    if (!isEmpty() && this.getGraphic() == textoExpandido) {
                        this.setGraphic(null);
                        this.setText(getItem());
                        getTableView().requestLayout();
                    }
                });

                this.setStyle("-fx-cursor: hand;");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Por padroa, sempre que a tabela for recarregada, fica no modo encolhido
                    setGraphic(null);
                    setText(item);
                }
            }
        });

        atualizarTabelaEDashboard();
    }

    public void atualizarTabelaEDashboard() {
        PedidoDAO ped = new PedidoDAO();
        List<Pedido> lista = ped.listarPedidos();
        tablePedido.setItems(FXCollections.observableArrayList(lista));

        DashboardService service = new DashboardService();
        DashboardData dados = service.getDados();

        telaAprovados.setText(String.valueOf(dados.getTotalAprovados()));
        telaNegados.setText(String.valueOf(dados.getTotalNegados()));
        telaEmAberto.setText(String.valueOf(dados.getTotalEmAberto()));
        telaEmAnalise.setText(String.valueOf(dados.getTotalEmAnalise()));
        telaTotal.setText(String.valueOf(lista.size()));
    }

    @FXML
    public void adicionarTelaPedido(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedirPedidos.fxml"));
            Parent root = loader.load();

            TelaPedirPedidoController filhoController = loader.getController();
            filhoController.setPaiController(this);

            BorderPane painelPrincipal = (BorderPane) botaoAdicionarPedido.getScene().lookup("#painelPrincipal");

            if (painelPrincipal != null) {
                painelPrincipal.setCenter(root);
            } else {
                Stage stage = (Stage) botaoAdicionarPedido.getScene().getWindow();
                stage.setScene(new Scene(root));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarPedido(Pedido pedido) {
        try {
            PedidoDAO dao = new PedidoDAO();
            dao.cadastrarPedido(pedido);
            atualizarTabelaEDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}