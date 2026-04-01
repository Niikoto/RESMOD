package gui;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Session;
import modelo.Usuario;
import java.sql.SQLException;

public class TelaLoginController {

    @FXML
    private TextField campoEmail;     //id do FXML
    @FXML
    private PasswordField campoSenha; //id do FXML
    public TextField getCampoEmail() {
        return campoEmail;
    }

    @FXML
    public void fazerLogin(ActionEvent event) {
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        Usuario usuario = new Usuario();
        usuario.setId_email(email);
        usuario.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        try {
            Usuario usuarioLogado = dao.verificar(usuario);

            if (usuarioLogado != null) {

                // Inicia a sessão
                Session.iniciar(usuarioLogado);

                // Carrega a tela principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
                Parent root = loader.load();

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