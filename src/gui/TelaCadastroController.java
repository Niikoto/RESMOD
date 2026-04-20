package gui;

import dao.CargoDAO;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Cargo;
import modelo.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaCadastroController {
    @FXML private TextField campoNomeCadastro;
    @FXML private TextField campoEmailCadastro;
    @FXML private PasswordField campoSenhaCadastro;
    @FXML private ComboBox<Cargo> campoCargo;
    @FXML private TextField campoPesquisa; // A barra de pesquisa

    @FXML private FlowPane painelFuncionarios; // Onde os cards serão desenhados
    @FXML private VBox vboxFormulario; // O formulário de cadastro

    private CargoDAO car = new CargoDAO();
    private UsuarioDAO dao = new UsuarioDAO();

    // Lista mestra para guardar todos os utilizadores carregados do banco
    private List<Usuario> todosUsuarios = new ArrayList<>();

    @FXML
    public void initialize() {
        campoCargo.setItems(FXCollections.observableArrayList(car.listarCargo()));

        vboxFormulario.setVisible(false);
        vboxFormulario.setManaged(false);

        carregarListaFuncionarios();

        // Adiciona um "ouvinte" à barra de pesquisa. Sempre que o texto muda, ele filtra.
        campoPesquisa.textProperty().addListener((observavel, valorAntigo, valorNovo) -> {
            filtrarFuncionarios(valorNovo);
        });
    }

    public void carregarListaFuncionarios() {
        try {
            // Guarda todos os usuários do banco nesta lista mestra
            todosUsuarios = dao.listar();
            // Inicia mostrando toda a gente (texto vazio)
            filtrarFuncionarios("");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // A MÁGICA DA PESQUISA
    private void filtrarFuncionarios(String termoBusca) {
        painelFuncionarios.getChildren().clear(); // Limpa a tela

        String termoLowerCase = termoBusca.toLowerCase().trim();

        for (Usuario u : todosUsuarios) {
            // Verifica se o nome ou o email contêm o texto que foi digitado
            boolean bateComNome = u.getNome() != null && u.getNome().toLowerCase().contains(termoLowerCase);
            boolean bateComEmail = u.getId_Email() != null && u.getId_Email().toLowerCase().contains(termoLowerCase);

            // Se o texto estiver vazio OU encontrar correspondência, adiciona o cartão
            if (termoLowerCase.isEmpty() || bateComNome || bateComEmail) {
                painelFuncionarios.getChildren().add(criarCard(u));
            }
        }
    }

    private VBox criarCard(Usuario u) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-padding: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 10, 0, 0, 0);");
        card.setPrefWidth(280);

        Label nome = new Label(u.getNome() != null ? u.getNome() : "Sem nome");
        nome.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1e293b;");

        Label email = new Label("✉ " + u.getId_Email());
        email.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");

        card.getChildren().addAll(nome, email);
        return card;
    }

    @FXML
    public void alternarVisibilidadeFormulario() {
        boolean visivel = !vboxFormulario.isVisible();
        vboxFormulario.setVisible(visivel);
        vboxFormulario.setManaged(visivel);
    }

    @FXML
    public void cadastrarUsuario(ActionEvent event) {
        Usuario u = new Usuario();
        u.setId_email(campoEmailCadastro.getText());
        u.setNome(campoNomeCadastro.getText());
        u.setSenha(campoSenhaCadastro.getText());
        Cargo cargoEscolhido = campoCargo.getValue(); //Vai guardar o que o usuario escolheu no CampoBox

        if (cargoEscolhido != null) { //Quando ele tiver escolhido esse comando vai rodar guardando o ID do cargo escolhido
            u.setCargo(cargoEscolhido.getID_cargo());
        } else {
            exibirAlerta("Aviso", "Por favor, selecione um cargo na lista.");
            return;
        }

        try {
            dao.cadastrar(u);
            exibirAlerta("Sucesso", "Usuário cadastrado!");

            campoNomeCadastro.clear();
            campoEmailCadastro.clear();
            campoSenhaCadastro.clear();
            campoCargo.setValue(null);
            alternarVisibilidadeFormulario();

            // Recarrega a lista do banco de dados para incluir o novo funcionário
            carregarListaFuncionarios();

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