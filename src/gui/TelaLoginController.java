package gui;

import dao.CargoDAO;
import dao.UsuarioDAO;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelo.Cargo;
import modelo.Session;
import modelo.Usuario;
import java.sql.SQLException;

public class TelaLoginController {

    @FXML
    private TextField campoEmail; // id do FXML
    @FXML
    private PasswordField campoSenha; // id do FXML
    @FXML
    private TextField campoSenhaVisivel; 
    @FXML
    private Button botaoMostrarSenha; // botão de olho
    @FXML
    private Label mensagemErro; // mensagem exibida na tela quando a senha estiver errada

    private boolean senhaVisivel = false;

    public TextField getCampoEmail() {
        return campoEmail;
    }

    @FXML
    public void initialize() {
        
        senhaVisivel = false;
        if (botaoMostrarSenha != null) {
            botaoMostrarSenha.setGraphic(createEyeGraphic(false));
        }
        if (campoSenhaVisivel != null) {
            campoSenhaVisivel.setVisible(false);
            campoSenhaVisivel.setManaged(false);
        }
        if (campoSenha != null) {
            campoSenha.setVisible(true);
            campoSenha.setManaged(true);
        }
    }

    @FXML
    public void fazerLogin(ActionEvent event) {
        String email = campoEmail.getText();
        String senha = getSenhaAtual();

        limparMensagemErro();

        Usuario usuario = new Usuario();
        Cargo cargo = new Cargo();
        usuario.setId_email(email);
        usuario.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        CargoDAO dao2 = new CargoDAO();
        if (!email.trim().isEmpty() && !senha.trim().isEmpty()) {
            try {
                Usuario usuarioLogado = dao.verificar(usuario);

                if (usuarioLogado != null) {
                    cargo.setID_cargo(usuarioLogado.getCargo());
                    Cargo cargoSetado = dao2.consultarCargo(cargo); 

                    if (cargoSetado != null) {
                        // Inicia a sessão
                        Session.iniciar(usuarioLogado);
                        Session.iniciarCargo(cargoSetado); 

                        // Carrega a tela principal
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
                        Parent root = loader.load();

                        Stage stage = (Stage) campoEmail.getScene().getWindow();
                        stage.setScene(new Scene(root));

                        Platform.runLater(() -> {
                            stage.setMaximized(true);
                        });
                        stage.show();
                    } else {
                        setMensagemErro("E-mail ou senha incorretos.");
                    }
                } else {
                    setMensagemErro("E-mail ou senha incorretos.");
                }
            } catch (SQLException | java.io.IOException e) {
                exibirAlerta("Erro no Sistema", "Não foi possível carregar a próxima tela.");
                e.printStackTrace();
            }
        } else {
            setMensagemErro("Digite um e-mail e senha para acessar o sistema.");
        }
    }

    @FXML
    public void abrirGuia(ActionEvent event) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(campoEmail.getScene().getWindow());
        popup.setTitle("Como acessar o sistema");

        
        Label titulo = new Label("Como acessar o sistema");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label subtitulo = new Label("Siga os passos abaixo para fazer seu primeiro acesso.");
        subtitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");

        VBox cabecalho = new VBox(4, titulo, subtitulo);
        cabecalho.setPadding(new Insets(0, 0, 8, 0));

       
        VBox passos = new VBox(0,
            criarPasso("1", "Receba seu acesso",
                "O diretor cadastra sua conta. Você recebe e-mail e senha direto com ele."),
            criarPasso("2", "Digite e-mail e senha",
                "Use exatamente as credenciais que o diretor te passou."),
            criarPasso("3", "Clique em Entrar",
                "Pronto! O sistema abre no painel do seu cargo automaticamente.")
        );

        
        Label dica = new Label("Esqueceu a senha? Solicite uma nova ao diretor da empresa.");
        dica.setStyle(
            "-fx-font-size: 11px; -fx-text-fill: #64748b;" +
            "-fx-background-color: #f1f5f9; -fx-background-radius: 6px; -fx-padding: 10px 14px;"
        );
        dica.setWrapText(true);
        dica.setMaxWidth(Double.MAX_VALUE);

        
        Button btnFechar = new Button("Entendido");
        btnFechar.setStyle(
            "-fx-background-color: #2b59c3; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 13px;" +
            "-fx-background-radius: 7px; -fx-cursor: hand; -fx-padding: 8px 28px;"
        );
        btnFechar.setOnAction(e -> popup.close());

        HBox rodape = new HBox(btnFechar);
        rodape.setAlignment(Pos.CENTER_RIGHT);
        rodape.setPadding(new Insets(12, 0, 0, 0));



       
        VBox layout = new VBox(14, cabecalho, passos, dica, rodape);
        layout.setPadding(new Insets(28));
        layout.setStyle("-fx-background-color: white;");
        layout.setPrefWidth(380);

        Scene scene = new Scene(layout);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    private VBox criarPasso(String numero, String tituloTexto, String descricao) {
        
        Label lblNum = new Label(numero);
        lblNum.setStyle(
            "-fx-background-color: #2b59c3; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-font-size: 12px;" +
            "-fx-background-radius: 50%; -fx-alignment: center;" +
            "-fx-min-width: 26px; -fx-min-height: 26px;" +
            "-fx-max-width: 26px; -fx-max-height: 26px;"
        );
        lblNum.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label(tituloTexto);
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #1e293b;");

        Label lblDesc = new Label(descricao);
        lblDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");
        lblDesc.setWrapText(true);

        VBox texto = new VBox(2, lblTitulo, lblDesc);

        HBox linha = new HBox(12, lblNum, texto);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setPadding(new Insets(10, 0, 10, 0));

        return new VBox(linha);
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private String getSenhaAtual() {
        return senhaVisivel ? campoSenhaVisivel.getText() : campoSenha.getText();
    }

    @FXML
    public void alternarMostrarSenha(ActionEvent event) {
        if (senhaVisivel) {
            esconderSenha();
        } else {
            mostrarSenha();
        }
    }

    private void mostrarSenha() {
        campoSenhaVisivel.setText(campoSenha.getText());
        campoSenhaVisivel.setVisible(true);
        campoSenhaVisivel.setManaged(true);
        campoSenha.setVisible(false);
        campoSenha.setManaged(false);
        if (botaoMostrarSenha != null) botaoMostrarSenha.setGraphic(createEyeGraphic(true));
        senhaVisivel = true;
    }

    private void esconderSenha() {
        campoSenha.setText(campoSenhaVisivel.getText());
        campoSenha.setVisible(true);
        campoSenha.setManaged(true);
        campoSenhaVisivel.setVisible(false);
        campoSenhaVisivel.setManaged(false);
        if (botaoMostrarSenha != null) botaoMostrarSenha.setGraphic(createEyeGraphic(false));
        senhaVisivel = false;
    }

    
    private Group createEyeGraphic(boolean struck) {
        Ellipse outer = new Ellipse(0, 0, 10, 6);
        outer.setFill(Color.TRANSPARENT);
        outer.setStroke(Color.web("#374151"));
        outer.setStrokeWidth(1.5);

        Circle pupil = new Circle(0, 0, 3);
        pupil.setFill(Color.web("#374151"));

        Group g = new Group(outer, pupil);
        if (struck) {
            Line slash = new Line(-12, -6, 12, 6);
            slash.setStroke(Color.web("#ef4444"));
            slash.setStrokeWidth(2);
            g.getChildren().add(slash);
        }
        // small translate to center inside button
        g.setTranslateX(0);
        g.setTranslateY(0);
        return g;
    }

    private void setMensagemErro(String mensagem) {
        if (mensagemErro != null) {
            mensagemErro.setText(mensagem);
            mensagemErro.setVisible(true);
            mensagemErro.setManaged(true);

            PauseTransition pausa = new PauseTransition(Duration.seconds(10));
            pausa.setOnFinished(e -> limparMensagemErro());
            pausa.play();
        }
    }

    private void limparMensagemErro() {
        if (mensagemErro != null) {
            mensagemErro.setText("");
            mensagemErro.setVisible(false);
            mensagemErro.setManaged(false);
        }
    }
}