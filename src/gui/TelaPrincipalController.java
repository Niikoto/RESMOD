package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import modelo.Session;
import modelo.Usuario;

public class TelaPrincipalController {

    // LIMITADOR DE TELAS
    // TEMPORÁRIO
    public final int limite = 2;
    public static int janela_aberta = 0;

    @FXML
    private Label minhaLabel;

    @FXML
    private Button botaoCriarConta;

    private Usuario usuarioLogado;

    @FXML private Button botaoPedidos;

    @FXML private Button botaoLogout;

    @FXML
    public void initialize() {

        if (minhaLabel != null) {
            minhaLabel.setText("IntelliDog");
        }
    }

    public void iniciarDados(Usuario u) {
        this.usuarioLogado = u;

        if (usuarioLogado.getCargo() != 1) {//Isso daqui vai verificar se a pessoa logada é administração
            botaoCriarConta.setVisible(false);//Caso não seja botão na aparece
            botaoCriarConta.setManaged(false);
        }
    }

    @FXML
    public void fazerLogout(ActionEvent event) {
        Session.encerrar(); // limpa o usuário da memória
        try {
            // criando variáveis para poder inicializar
            // loader = pega o FXML
            // root = transforma em objeto (o FXML)

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLogin.fxml"));
            Parent root = loader.load();

            // propriedades de tela
            Stage stagePrincipal = (Stage) minhaLabel.getScene().getWindow();
            stagePrincipal.setScene(new Scene(root)); // setar a janela
            stagePrincipal.centerOnScreen(); // centralizar na tela
            stagePrincipal.show(); //finalmente, mostrar ela

            fecharJanelas(stagePrincipal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirTelaCadastro(ActionEvent event) {
        janela_aberta += 1;
        if(janela_aberta <= limite) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastro.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED); // Remove tudo: barra, bordas
                stage.setTitle("Cadastrar Usuário");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("ERRO");
        }
    }

    @FXML
    public void abrirTelaPedidos(ActionEvent event) {
        janela_aberta += 1;
        if(janela_aberta <= limite) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("ERRO");
        }
    }

    public void fecharJanelas(Stage janelaPrincipal) {
        // Obtém todas as janelas abertas e fecha uma por uma
        // Cria variável temporária "window"
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage && window != janelaPrincipal) {
                ((Stage) window).close();
            }
        }
    }
}