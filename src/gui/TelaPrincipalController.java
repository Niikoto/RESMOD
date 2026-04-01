package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import modelo.Session;
import modelo.Usuario;

public class TelaPrincipalController {

    @FXML
    private ImageView minhaImagem;

    @FXML
    private Button botaoCriarConta;

    private Usuario usuarioLogado;

    @FXML
    private Button botaoPedidos;

    @FXML
    private Button botaoLogout;

    public void iniciarDados(Usuario u) {
        this.usuarioLogado = u;

        if (usuarioLogado.getCargo() != 1) {// Isso daqui vai verificar se a pessoa logada é administração
            botaoCriarConta.setVisible(false);// Caso não seja botão na aparece
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
            Stage stagePrincipal = (Stage) minhaImagem.getScene().getWindow();
            stagePrincipal.setScene(new Scene(root)); // setar a janela
            stagePrincipal.centerOnScreen(); // centralizar na tela
            stagePrincipal.show(); // finalmente, mostrar ela

            fecharJanelas(stagePrincipal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirTelaCadastro(ActionEvent event) {

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
    }

    @FXML
    public void abrirTelaPedidos(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPedidos.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
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