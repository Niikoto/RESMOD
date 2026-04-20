package gui;

import dao.DashboardDAO;
import dashboard.DashboardData;
import dashboard.DashboardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelo.Atualizacao;
import modelo.Session;
import java.util.List;

public class TelaPrincipalController {

    @FXML private BorderPane painelPrincipal;
    private Node painelDashboardOriginal;

    @FXML private Label nomeUsuarioSessao;
    @FXML private ImageView minhaImagem;
    @FXML private Button botaoLogout;

    // Botões do Menu Lateral
    @FXML private Button botaoDashboard;
    @FXML private Button botaoProdutos;
    @FXML private Button botaoPedidos;
    @FXML private Button botaoFornecedores;
    @FXML private Button botaoCriarConta;

    // Elementos das Atualizações
    @FXML private VBox vboxAtualizacoes;
    @FXML private VBox vboxAdminPost;
    @FXML private Button botaoNovaAtualizacao;
    @FXML private TextField campoTituloAtualizacao;
    @FXML private TextArea campoMensagemAtualizacao;

    // Elementos dos Gráficos
    @FXML private PieChart graficoPizza;
    @FXML private BarChart<String, Number> graficoBarras;

    public void initialize() {
        if (Session.getUsuario() != null && Session.getUsuario().getNome() != null) {
            nomeUsuarioSessao.setText("Olá, " + Session.getUsuario().getNome());
        }

        int meuCargo = Session.getUsuario().getCargo();

        if (meuCargo != 1) {// Isso daqui vai verificar se a pessoa logada é administração
            if (botaoCriarConta != null) {// Caso não seja botão na aparece
                botaoCriarConta.setVisible(false);
                botaoCriarConta.setManaged(false);
            }
        }

        // SEGURANÇA: Apenas Cargo 1 (Admin) pode ver o botão de postar atualizações
        if (meuCargo == 1) {
            if (botaoNovaAtualizacao != null) {
                botaoNovaAtualizacao.setVisible(true);
                botaoNovaAtualizacao.setManaged(true);
            }
        } else {
            if (botaoNovaAtualizacao != null) {
                botaoNovaAtualizacao.setVisible(false);
                botaoNovaAtualizacao.setManaged(false);
            }
        }

        if (vboxAdminPost != null) {
            vboxAdminPost.setVisible(false);
            vboxAdminPost.setManaged(false);
        }

        carregarGraficos();

        try {
            carregarAtualizacoes();
        } catch (Exception e) {
            System.out.println("Aviso: Falha ao carregar atualizações: " + e.getMessage());
        }

        // aq é so para adaptar pra tela do usuario
        javafx.application.Platform.runLater(() -> {
            if (painelPrincipal != null) {
                painelDashboardOriginal = painelPrincipal.getCenter();

                // Pega a janela atual
                Stage stage = (Stage) painelPrincipal.getScene().getWindow();

                // Pega o tamanho real do monitor (descontando a barra de tarefas do Windows)
                Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

                // Define para 95% da largura e 90% da altura
                stage.setWidth(bounds.getWidth() * 0.95);
                stage.setHeight(bounds.getHeight() * 0.90);

                // Centraliza bonitinho
                stage.centerOnScreen();
            }
        });
    }

    // aq ele "pinta" oq ta selecionado, se quiserem mudar a cor é aqui
    private void atualizarMenuLateral(Button botaoAtivo) {
        String estiloInativo = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;";
        String estiloAtivo = "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;";

        if (botaoDashboard != null) botaoDashboard.setStyle(estiloInativo);
        if (botaoProdutos != null) botaoProdutos.setStyle(estiloInativo);
        if (botaoPedidos != null) botaoPedidos.setStyle(estiloInativo);
        if (botaoFornecedores != null) botaoFornecedores.setStyle(estiloInativo);
        if (botaoCriarConta != null) botaoCriarConta.setStyle(estiloInativo);

        if (botaoAtivo != null) botaoAtivo.setStyle(estiloAtivo);
    }

    private void carregarGraficos() {
        if (graficoPizza != null) {
            DashboardService service = new DashboardService();
            DashboardData dados = service.getDados();

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

            if (dados.getTotalAprovados() == 0 && dados.getTotalEmAberto() == 0 && dados.getTotalNegados() == 0) {
                pieData.addAll(
                        new PieChart.Data("Aprovados (Exemplo)", 41),
                        new PieChart.Data("Em aberto (Exemplo)", 24),
                        new PieChart.Data("Negados (Exemplo)", 11)
                );
            } else {
                pieData.addAll(
                        new PieChart.Data("Aprovados", dados.getTotalAprovados()),
                        new PieChart.Data("Em aberto", dados.getTotalEmAberto() + dados.getTotalEmAnalise()),
                        new PieChart.Data("Negados", dados.getTotalNegados())
                );
            }
            graficoPizza.setData(pieData);
        }

        if (graficoBarras != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Setor 1", 8));
            series.getData().add(new XYChart.Data<>("Setor 2", 12));
            series.getData().add(new XYChart.Data<>("Setor 3", 21));
            series.getData().add(new XYChart.Data<>("Setor 4", 22));
            series.getData().add(new XYChart.Data<>("Setor 5", 30));
            graficoBarras.getData().add(series);
        }
    }

    @FXML
    public void alternarPainelAtualizacao() {
        if (vboxAdminPost != null) {
            boolean isVisivel = vboxAdminPost.isVisible();
            vboxAdminPost.setVisible(!isVisivel);
            vboxAdminPost.setManaged(!isVisivel);
        }
    }

    private void carregarAtualizacoes() throws Exception {
        DashboardDAO dao = new DashboardDAO();
        String meuEmail = Session.getUsuario().getId_Email();
        int meuCargo = Session.getUsuario().getCargo();
        List<Atualizacao> lista = dao.listarAtualizacoes(meuEmail);

        if (vboxAtualizacoes != null) {
            vboxAtualizacoes.getChildren().clear();

            for (Atualizacao a : lista) {
                VBox card = new VBox(5);
                card.setPadding(new Insets(12));

                String corFundo = a.isLida() ? "#f8fafc" : "#ffe8cc";
                card.setStyle("-fx-background-color: " + corFundo + "; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");

                HBox header = new HBox();
                header.setAlignment(Pos.CENTER_LEFT);

                Label lblTitulo = new Label(a.getDataHora() + "  |  " + a.getTitulo());
                lblTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155; -fx-font-size: 13px;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                header.getChildren().addAll(lblTitulo, spacer);

                // Apenas Cargo 1 pode ver o botão de excluir
                if (meuCargo == 1) {

                    Button btnExcluir = new Button("✖");
                    btnExcluir.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-cursor: hand; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 0;");

                    btnExcluir.setOnAction(event -> {
                        try {
                            dao.deletarAtualizacao(a.getId());
                            carregarAtualizacoes();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        event.consume();
                    });

                    header.getChildren().add(btnExcluir);
                }

                Label lblMensagem = new Label(a.getMensagem());
                lblMensagem.setWrapText(true);
                lblMensagem.setStyle("-fx-text-fill: #64748b; -fx-padding: 8 0 0 0; -fx-font-size: 13px;");
                lblMensagem.setVisible(false);
                lblMensagem.setManaged(false);

                card.getChildren().addAll(header, lblMensagem);

                card.setOnMouseClicked(e -> {
                    boolean estaEscondida = !lblMensagem.isVisible();
                    lblMensagem.setVisible(estaEscondida);
                    lblMensagem.setManaged(estaEscondida);

                    if (!a.isLida() && estaEscondida) {
                        try {
                            dao.marcarComoLida(a.getId(), meuEmail);
                            a.setLida(true);
                            card.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }
                });

                vboxAtualizacoes.getChildren().add(card);
            }
        }
    }

    @FXML
    public void postarAtualizacao(ActionEvent event) {
        String titulo = campoTituloAtualizacao.getText();
        String mensagem = campoMensagemAtualizacao.getText();

        if (titulo == null || titulo.trim().isEmpty() || mensagem == null || mensagem.trim().isEmpty()) {
            return;
        }

        DashboardDAO dao = new DashboardDAO();
        try {
            dao.inserirAtualizacao(titulo, mensagem);
            campoTituloAtualizacao.clear();
            campoMensagemAtualizacao.clear();
            alternarPainelAtualizacao();
            carregarAtualizacoes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirTelaDashboard(ActionEvent event) {
        if (painelPrincipal != null && painelDashboardOriginal != null) {
            painelPrincipal.setCenter(painelDashboardOriginal);
            atualizarMenuLateral(botaoDashboard); // Fica Azul
        }
    }

    @FXML
    public void abrirTelaPedidos(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaPedidos.fxml"));
            painelPrincipal.setCenter(root);
            atualizarMenuLateral(botaoPedidos); // Fica Azul
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void abrirTelaCadastro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaCadastro.fxml"));
            painelPrincipal.setCenter(root);
            atualizarMenuLateral(botaoCriarConta); // Fica Azul
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void abrirTelaProdutos(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaProdutos.fxml"));
            painelPrincipal.setCenter(root);
            atualizarMenuLateral(botaoProdutos); // fic azul
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void abrirTelaFornecedores(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaFornecedores.fxml"));
            painelPrincipal.setCenter(root);
            atualizarMenuLateral(botaoFornecedores); // fica azul
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void fazerLogout(ActionEvent event) {
        Session.encerrar(); // limpa o usuário da memória
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaLogin.fxml"));
            Stage stagePrincipal = (Stage) minhaImagem.getScene().getWindow();
            stagePrincipal.setScene(new Scene(root));
            stagePrincipal.centerOnScreen();

            for (Window window : Window.getWindows()) {
                if (window instanceof Stage && window != stagePrincipal) {
                    ((Stage) window).close();
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}