package gui;

import java.io.IOException;
import java.util.List;

import dao.PedidoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.DashboardData;
import modelo.DashboardService;
import modelo.Pedido;
import modelo.Produto;

public class TelaPedidoController {
    @FXML
    private TableView<Pedido> tablePedido;

    @FXML
    private TableColumn<Pedido, Integer> idPedido;
    @FXML
    private TableColumn<Pedido, String> motivoPedido;
    @FXML
    private TableColumn<Pedido, String> formaPagamento;
    @FXML
    private TableColumn<Pedido, String> dataCriado;
    @FXML
    private TableColumn<Pedido, String> statusPedido;
    @FXML
    private TableColumn<Pedido, Float> valorTotal;
    @FXML
    private TableColumn<Pedido, String> criadoPor;
    @FXML
    private TableColumn<Pedido, String>  colSetor;
    @FXML
    private TableColumn<Pedido, String>  colCentroCusto;

    @FXML
    private Label telaAprovados;
    @FXML
    private Label telaNegados;
    @FXML
    private Label telaEmAberto;
    @FXML
    private Label telaEmAnalise;
    @FXML
    private Label telaTotal;

    @FXML
    private Button botaoAdicionarPedido;
    @FXML
    private Button buttonExcluir;
    @FXML
    private Button btnCompra;


    @FXML
    public void initialize() {
        idPedido.setCellValueFactory(new PropertyValueFactory<>("iD_pedido"));
        formaPagamento.setCellValueFactory(new PropertyValueFactory<>("Forma_de_pagamento"));
        dataCriado.setCellValueFactory(new PropertyValueFactory<>("criado"));
        statusPedido.setCellValueFactory(new PropertyValueFactory<>("status"));
        valorTotal.setCellValueFactory(new PropertyValueFactory<>("Preco_total"));
        criadoPor.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNome()));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colCentroCusto.setCellValueFactory(new PropertyValueFactory<>("centro_custo"));
        motivoPedido.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        motivoPedido.setCellFactory(coluna -> new TableCell<Pedido, String>() {
            private final Text textoExpandido = new Text();

            {
                textoExpandido.wrappingWidthProperty().bind(coluna.widthProperty().subtract(10));

                this.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        Pedido pedidoSelecionado = getTableView().getSelectionModel().getSelectedItem();
                        if (pedidoSelecionado != null) {
                            abrirPopupStatus(pedidoSelecionado);
                        }
                        return;
                    }

                    if (!isEmpty() && getItem() != null) {
                        if (this.getGraphic() == textoExpandido) {
                            this.setGraphic(null);
                            this.setText(getItem());
                        } else {
                            textoExpandido.setText(getItem());
                            this.setGraphic(textoExpandido);
                            this.setText(null);
                        }
                        getTableView().requestLayout();
                    }
                });

                this.setOnMouseExited(event -> {
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
                    setGraphic(null);
                    setText(item);
                }
            }
        });

        tablePedido.setRowFactory(dica ->{
            TableRow<Pedido> row = new TableRow<>();
            row.hoverProperty().addListener((obs, oldValue, newValue) ->{
                if (newValue && !row.isEmpty()) {
                    row.setStyle("-fx-cursor: hand;");
                }else{
                    row.setStyle("");
                }
            });
      
            row.setTooltip(new Tooltip("Clique duas vezes para abrir o pedido"));

            // DIRETOR CLICA DUAS VEZES PARA SELECIONAR PEDIDO
            tablePedido.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Pedido pedidoSelecionado = tablePedido.getSelectionModel().getSelectedItem();
                    if (pedidoSelecionado != null) {
                        abrirPopupStatus(pedidoSelecionado);
                    }
                }
            });
            return row;
        });

        atualizarTabelaEDashboard();

        buttonExcluir.setDisable(true);
        tablePedido.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                buttonExcluir.setDisable(false);
                String status = newSelection.getStatus();
                if (!status.equals("finalizado") && !status.equals("negado")) {
                    btnCompra.setDisable(false);
                } else {
                    btnCompra.setDisable(true);
                }
            }
            else {
            buttonExcluir.setDisable(true);
            btnCompra.setDisable(true);
        }
    });
        int paginas = (int) Math.ceil(
                (double) pedidos.size() / ITENS_POR_PAGINA
        );

        pedidopagination.setPageCount(Math.max(1, paginas));

        pedidopagination.currentPageIndexProperty().addListener(
                (obs, oldValue, newValue) ->
                        carregarPagina(newValue.intValue())
        );

        carregarPagina(0);
    }

    // PARA ABRIR A TELA DE CONFIRMAR SE O DIRETOR
    // QUER ANALISAR O PEDIDO
    private void abrirPopupStatus(Pedido pedido) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaStatusPedido.fxml"));
                Parent root = loader.load();

                TelaStatusPedidoController statusController = loader.getController();
                statusController.setPedido(pedido);
                statusController.setPaiController(this);

                Stage popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(tablePedido.getScene().getWindow());
                popup.setTitle("Definir Status do Pedido #" + pedido.getID_pedido());
                popup.setScene(new Scene(root));
                popup.showAndWait();

                atualizarTabelaEDashboard();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void atualizarTabelaEDashboard() {
        PedidoDAO ped = new PedidoDAO();
        pedidos = ped.listarPedidos();
        tablePedido.setItems(FXCollections.observableArrayList(pedidos));

        DashboardService service = new DashboardService();
        DashboardData dados = service.getDados();

        telaAprovados.setText(String.valueOf(dados.getTotalAprovados()));
        telaNegados.setText(String.valueOf(dados.getTotalNegados()));
        telaEmAberto.setText(String.valueOf(dados.getTotalEmAberto()));
        telaEmAnalise.setText(String.valueOf(dados.getTotalEmAnalise()));
        telaTotal.setText(String.valueOf(pedidos.size()));
    }

    @FXML
    public void excluirPedido(ActionEvent event) {
        Pedido pedidoSelecionado = tablePedido.getSelectionModel().getSelectedItem();
        if (pedidoSelecionado == null) return;

        // Confirmação antes de excluir
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Excluir pedido #" + pedidoSelecionado.getID_pedido() + "?");
        confirmacao.setContentText("Esta ação removerá o pedido e todos os registros vinculados a ele.\nEssa operação não pode ser desfeita.");
        confirmacao.initOwner(tablePedido.getScene().getWindow());

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                PedidoDAO dao = new PedidoDAO();
                boolean sucesso = dao.buttonExcluirpedido(pedidoSelecionado.getID_pedido());

                if (sucesso) {
                    atualizarTabelaEDashboard();
                    buttonExcluir.setDisable(true);
                    btnCompra.setDisable(true);
                } else {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro ao excluir");
                    erro.setHeaderText(null);
                    erro.setContentText("Não foi possível excluir o pedido. Tente novamente.");
                    erro.initOwner(tablePedido.getScene().getWindow());
                    erro.showAndWait();
                }
            }
        });
    }

    @FXML
    private Pagination pedidopagination;
    private List<Pedido> pedidos;
    private final int ITENS_POR_PAGINA = 19;
    private void carregarPagina(int pagina) {

        int inicio = pagina * ITENS_POR_PAGINA;

        int fim = Math.min(
                inicio + ITENS_POR_PAGINA,
                pedidos.size()
        );

        tablePedido.setItems(
                FXCollections.observableArrayList(
                        pedidos.subList(inicio, fim)
                )
        );
    }

    @FXML
    private void abrirHistoricoPedido() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/TelaHistoricoPedido.fxml")
            );

            Parent root = loader.load();

            Stage modal = new Stage();

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Histórico dos Pedidos");
            modal.initStyle(StageStyle.UNDECORATED);
            modal.setScene(new Scene(root));

            modal.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public int adicionarPedido(Pedido pedido) {
        int idGerado = 0;

        try {
            PedidoDAO dao = new PedidoDAO();
            idGerado = dao.cadastrarPedido(pedido);
            atualizarTabelaEDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGerado;
    }

    public void finalizarCompra(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCompraPedido.fxml"));
            Parent root = loader.load();

            TelaComprarPedidoController filhoCompra = loader.getController(); 
            Pedido pedidoselecionado = tablePedido.getSelectionModel().getSelectedItem();

            filhoCompra.setPaiController(this,pedidoselecionado);
            
            BorderPane painel = (BorderPane) btnCompra.getScene().lookup("#painelPrincipal");

            if (painel != null) {
                painel.setCenter(root);
            }
            else{
                Stage stage = (Stage) btnCompra.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}