package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Usuario;

public class TelaPrincipalController {

    @FXML
    private Label minhaLabel;

    @FXML
    private Button botaoCriarConta;

    private Usuario usuarioLogado;

    @FXML
    public void initialize() {

        if (minhaLabel != null) {
            minhaLabel.setText("IntelliDog");
        }
    }

    public void iniciarDados(Usuario u) {
        this.usuarioLogado = u;

        if (usuarioLogado.getCargo() != 1) {
            botaoCriarConta.setVisible(false);
            botaoCriarConta.setManaged(false);
        }
    }

    @FXML
    public void abrirTelaCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastro.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Cadastrar Usuário");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}