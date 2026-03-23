package gui;

import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.Usuario;
import java.sql.SQLException;

public class TelaCadastroController {

    @FXML private TextField campoEmailCadastro;
    @FXML private PasswordField campoSenhaCadastro;
    @FXML private ComboBox<String> campoCargo;

    @FXML
    public void initialize() {
        // caixa de seleçao
        campoCargo.setItems(FXCollections.observableArrayList("Admin", "Gerente", "Comum"));
    }

    @FXML
    public void cadastrarUsuario(ActionEvent event) {
        Usuario u = new Usuario();

        String email = campoEmailCadastro.getText();
        u.setId_email(email);
        u.setNome(email);
        u.setSenha(campoSenhaCadastro.getText());
        String cargoEscolhido = campoCargo.getValue();
        int cargoNumero = 0;

        if (cargoEscolhido != null) {

            if (cargoEscolhido.equals("Admin")) cargoNumero = 1;
            else if (cargoEscolhido.equals("Gerente")) cargoNumero = 2;
            else if (cargoEscolhido.equals("Comum")) cargoNumero = 3;

            u.setCargo(cargoNumero);
        } else {
            exibirAlerta("Aviso", "Por favor, selecione um cargo na lista.");
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.cadastrar(u);

            exibirAlerta("Sucesso", "Usuário cadastrado com sucesso!");

            campoEmailCadastro.clear();
            campoSenhaCadastro.clear();
            campoCargo.setValue(null);

        } catch (SQLException e) {
            exibirAlerta("Erro", "Falha ao salvar no banco de dados.");
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