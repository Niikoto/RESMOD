package gui;

import java.sql.SQLException;

import dao.FornecedorDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import modelo.Fornecedor;

public class TelaFornecedoresController {
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtMunicipio;
    @FXML
    private TextField txtTelefone;

    @FXML
    private Button buttonEnviar;
    @FXML
    private Button buttonAdicionar;

    @FXML
    private HBox hboxCad;

    @FXML
    private TableView<?> tabelaFornecedores;

    @FXML
    public void initialize() {
        hboxCad.setVisible(false);
        hboxCad.setManaged(false);
        // Inicialização futura da tabela de fornecedores
    }

    @FXML
    public void visivel(ActionEvent event){
        boolean ver = hboxCad.isVisible();
        if(ver){
            hboxCad.setVisible(false);
            hboxCad.setManaged(false);
        }
        else{
            hboxCad.setVisible(true);
            hboxCad.setManaged(true);
        }
    }

    @FXML
    public void enviarFornecedor(ActionEvent event){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCNPJ(txtCnpj.getText());
        fornecedor.setNome_fornecedor(txtNome.getText());
        fornecedor.setDescricao(txtDescricao.getText());
        fornecedor.setEstado(txtEstado.getText());
        fornecedor.setMunicipio(txtMunicipio.getText());
        fornecedor.setTelefone(txtTelefone.getText());

        FornecedorDAO dao = new FornecedorDAO();
        try {
            dao.cadastrarFornecedores(fornecedor);
            txtCnpj.clear();
            txtNome.clear();
            txtDescricao.clear();
            txtEstado.clear();
            txtMunicipio.clear();
            txtTelefone.clear();

            hboxCad.setVisible(false);
            hboxCad.setManaged(false);

            exibirAlerta("Sucesso", "Fornecedor Cadastrado com sucesso");
        }catch(SQLException e){
            e.printStackTrace();
            exibirAlerta("Erro", "Não foi possivel cadastrar o fornecedordie");
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