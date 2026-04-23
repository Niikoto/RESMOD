package gui;

import dao.CargoDAO;
import dao.UsuarioDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cargo;
import modelo.Session;
import modelo.Usuario;
import java.sql.SQLException;

public class TelaLoginController {

    @FXML
    private TextField campoEmail; // id do FXML
    @FXML
    private PasswordField campoSenha; // id do FXML

    public TextField getCampoEmail() {
        return campoEmail;
    }

    @FXML
    public void fazerLogin(ActionEvent event) {
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        Usuario usuario = new Usuario();
        Cargo cargo = new Cargo();
        usuario.setId_email(email);
        usuario.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        CargoDAO dao2 = new CargoDAO();
        try {
            Usuario usuarioLogado = dao.verificar(usuario);

            cargo.setID_cargo(usuarioLogado.getCargo());
            Cargo cargoSetado = dao2.consultarCargo(cargo);//objeto criado para rodar o conultarcargo

            if (usuarioLogado != null && cargoSetado != null) {

                // Inicia a sessão
                Session.iniciar(usuarioLogado);
                Session.iniciarCargo(cargoSetado);//guardando o cargo na sessão

                // Carrega a tela principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) campoEmail.getScene().getWindow();
                stage.setScene(new Scene(root));

                Platform.runLater(() -> {//coloca isso aqui para executar depois, já que caso não seja feito o proximo comando não sera executado
                    stage.setMaximized(true);//metodo para deixar a tela "cheia"
                });
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