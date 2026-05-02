package gui;

import dao.FornecedorDAO;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import modelo.Fornecedor;

public class TelaFornecedoresController {
    @FXML private TextField txtCnpj;
    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtMunicipio;
    @FXML private TextField txtRua;
    @FXML private TextField txtLocal;
    @FXML private TextField txtTelefone;

    @FXML private TextField txtCnpjPes;
    @FXML private TextField txtNomePes;

    @FXML private ComboBox<String> txtEstado;
    @FXML private Button buttonEnviar;
    @FXML private Button buttonAdicionar;
    @FXML private HBox hboxCad;
    @FXML private TableView<?> tabelaFornecedores;
    @FXML private ComboBox<String> comboPesquisaEstado;

    @FXML private TableView<Fornecedor> tableFornecedor;

    @FXML private TableColumn<Fornecedor, String> colCnpj;
    @FXML private TableColumn<Fornecedor, String> colNome;
    @FXML private TableColumn<Fornecedor, String> colEstado;
    @FXML private TableColumn<Fornecedor, String> colMunicipio;
    @FXML private TableColumn<Fornecedor, String> colRua;
    @FXML private TableColumn<Fornecedor, String> colNumero;
    @FXML private TableColumn<Fornecedor, String> colTel;

    private static final List<String> ESTADOS = List.of(
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
            "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    );

    @FXML
    public void initialize() {
        hboxCad.setVisible(false);
        hboxCad.setManaged(false);
        txtEstado.setItems(FXCollections.observableArrayList(ESTADOS));
        comboPesquisaEstado.setItems(FXCollections.observableArrayList(ESTADOS));

        colCnpj.setCellValueFactory(new PropertyValueFactory<>("CNPJ"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome_fornecedor"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));        
        colMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        colRua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    @FXML
    public void visivel(ActionEvent event) {
        boolean ver = hboxCad.isVisible();
        hboxCad.setVisible(!ver);
        hboxCad.setManaged(!ver);
    }

    @FXML
    public void enviarFornecedor(ActionEvent event) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setCNPJ(txtCnpj.getText());
        fornecedor.setNome_fornecedor(txtNome.getText());
        fornecedor.setDescricao(txtDescricao.getText());
        fornecedor.setEstado(txtEstado.getValue());
        fornecedor.setTelefone(txtTelefone.getText());
        fornecedor.setMunicipio(txtMunicipio.getText());
        fornecedor.setRua(txtRua.getText());
        fornecedor.setNumero(Integer.parseInt(txtLocal.getText()));

        FornecedorDAO dao = new FornecedorDAO();

        try {
            dao.cadastrarFornecedores(fornecedor);
            txtCnpj.clear();
            txtNome.clear();
            txtDescricao.clear();
            txtEstado.setValue(null);
            txtTelefone.clear();
            hboxCad.setVisible(false);
            hboxCad.setManaged(false);
            exibirAlerta("Sucesso", "Fornecedor cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro", "Detalhes: " + e.getMessage());
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void btnPesquisar(ActionEvent event){
        Fornecedor fornecedor = new Fornecedor();
        FornecedorDAO dao = new FornecedorDAO();

        fornecedor.setCNPJ(txtCnpjPes.getText());
        fornecedor.setNome_fornecedor(txtNomePes.getText());
        if(comboPesquisaEstado.getValue() != null){
            fornecedor.setEstado(comboPesquisaEstado.getValue());
        }else{
            fornecedor.setEstado("");
        }

        List<Fornecedor> list = dao.listarFornecedores(fornecedor);
        tableFornecedor.setItems(FXCollections.observableArrayList(list));
    }
}