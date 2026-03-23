import java.sql.SQLException;

import dao.UsuarioDAO;
import gui.TelaPrincipalController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;


public class Main extends Application{

    //Mostra/chama a tela de login para o usuario
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/view/TelaLogin.fxml")
        );
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Projeto RESMOD funcionando!");
        launch(args);
    }

    public Usuario user = new Usuario();

    @FXML
    private TextField campoEmail;

    @FXML
    private PasswordField campoSenha;

    @FXML
    public void fazerLogin(ActionEvent event) {
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        user.setId_email(email);
        user.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        try {
            Usuario usuarioLogado = dao.verificar(user);

            if (usuarioLogado != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
                Parent root = loader.load();

                TelaPrincipalController controllerPrincipal = loader.getController();
                controllerPrincipal.iniciarDados(usuarioLogado);

                Stage stage = (Stage) campoEmail.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.centerOnScreen();
                stage.show();

            } else {
                exibirAlerta("Erro", "E-mail ou senha incorretos.");
            }
        } catch (SQLException | java.io.IOException e) {
            exibirAlerta("Erro no Sistema", "Não foi possível carregar a próxima tela.");
            e.printStackTrace();
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}