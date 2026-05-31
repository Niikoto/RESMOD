package gui;

import dao.CargoDAO;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Cargo;
import modelo.Usuario;

import java.util.List;

public class TelaEditarUsuarioController {

    @FXML private TextField campoNome;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private ComboBox<Cargo> campoCargo;

    private Usuario usuarioAtual;
    private TelaCadastroController paiController;

    public void setDados(Usuario u, TelaCadastroController pai) {
        this.usuarioAtual = u;
        this.paiController = pai;

        campoNome.setText(u.getNome());
        campoEmail.setText(u.getId_Email());

        List<Cargo> cargos = new CargoDAO().listarCargo();
        campoCargo.setItems(FXCollections.observableArrayList(cargos));

        // Seleciona o cargo atual
        cargos.stream()
                .filter(c -> c.getID_cargo() == u.getCargo())
                .findFirst()
                .ifPresent(campoCargo::setValue);
    }

    @FXML
    private void salvar() {
        String novoNome = campoNome.getText().trim();
        String novaSenha = campoSenha.getText().trim();
        Cargo cargoEscolhido = campoCargo.getValue();

        if (novoNome.isEmpty() || cargoEscolhido == null) {
            new Alert(Alert.AlertType.WARNING, "Preencha nome e cargo.").showAndWait();
            return;
        }

        usuarioAtual.setNome(novoNome);
        usuarioAtual.setCargo(cargoEscolhido.getID_cargo());

        if (!novaSenha.isEmpty()) {
            usuarioAtual.setSenha(novaSenha);
        }
        // se vazio, usuarioAtual.getSenha() já tem a senha original
        // que foi carregada no setDados — só não sobrescreve

        try {
            new UsuarioDAO().atualizarUsuario(usuarioAtual);
            paiController.carregarListaFuncionarios();
            fechar();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar.").showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
        fechar();
    }

    private void fechar() {
        ((Stage) campoNome.getScene().getWindow()).close();
    }
}