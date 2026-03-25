package gui;

import dao.CargoDAO;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cargo;
import modelo.Usuario;
import java.sql.SQLException;

public class TelaCadastroController {
    @FXML private Button buttonFechar;
    @FXML private TextField campoEmailCadastro;
    @FXML private PasswordField campoSenhaCadastro;
    @FXML private ComboBox<Cargo> campoCargo; //puxou o comboBox do FXML e atribui a lista Cargo criada no CargoDAO

    CargoDAO car = new CargoDAO(); //Cria um objeto do CargoDAO para puxar a função
    
    @FXML
    public void initialize() {
        // caixa de seleçao
        campoCargo.setItems(FXCollections.observableArrayList(car.listarCargo()));//Guarda todos cargos em forma de Strig, por conta do ToString em Cargo.java
    }

    @FXML
    public void cadastrarUsuario(ActionEvent event) { //Metodo de cadastro do usuario
        Usuario u = new Usuario(); //Cria objeto usario para guardar os dados digitato pelo usuario

        String email = campoEmailCadastro.getText();
        u.setId_email(email);
        u.setNome(email);
        u.setSenha(campoSenhaCadastro.getText());
        Cargo cargoEscolhido = campoCargo.getValue(); //Vai guardar o que o usuario escolheu no CampoBox

        if (cargoEscolhido != null) { //Quando ele tiver escolhido esse comando vai rodar guardando o ID do cargo escolhido
            u.setCargo(cargoEscolhido.getID_cargo());
        } else {
            exibirAlerta("Aviso", "Por favor, selecione um cargo na lista.");
            return;
        }

        try { // após tudo isso vai pegar as informações digitadas e usar o metodo cadastrar que está em UsuarioDAO
            UsuarioDAO dao = new UsuarioDAO();
            dao.cadastrar(u);

            exibirAlerta("Sucesso", "Usuário cadastrado com sucesso!");

            //Depois de cadastrado vai ser limpo os inputs
            campoEmailCadastro.clear();
            campoSenhaCadastro.clear();
            campoCargo.setValue(null);

        } catch (SQLException e) { //Caso de erro
            exibirAlerta("Erro", "Falha ao salvar no banco de dados.");
            e.printStackTrace();
        }
    }

    private void exibirAlerta(String titulo, String mensagem) { //Molde da mensagem se der tudo certo ou erro
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void fecharTela(ActionEvent event){ //Metodo para fechar a tela
        TelaPrincipalController.janela_aberta = 0;
        Stage stage = (Stage) buttonFechar.getScene().getWindow(); 
        stage.close();   
    }
}