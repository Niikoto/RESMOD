package gui;

import dao.FornecedorDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Fornecedor;

import java.util.List;

public class TelaEditarFornecedorController {

    @FXML private TextField campoCnpj;
    @FXML private TextField campoNome;
    @FXML private TextField campoDescricao;
    @FXML private ComboBox<String> campoEstado;
    @FXML private TextField campoMunicipio;
    @FXML private TextField campoRua;
    @FXML private TextField campoNumero;
    @FXML private TextField campoTelefone;

    private Fornecedor fornecedorAtual;
    private TelaFornecedoresController paiController;

    private static final List<String> ESTADOS = List.of(
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
            "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    );

    public void setDados(Fornecedor f, TelaFornecedoresController pai) {
        this.fornecedorAtual = f;
        this.paiController = pai;

        campoEstado.setItems(FXCollections.observableArrayList(ESTADOS));

        campoCnpj.setText(f.getCNPJ());
        campoNome.setText(f.getNome_fornecedor());
        campoDescricao.setText(f.getDescricao());
        campoEstado.setValue(f.getEstado());
        campoMunicipio.setText(f.getMunicipio());
        campoRua.setText(f.getRua());
        campoNumero.setText(String.valueOf(f.getNumero()));
        campoTelefone.setText(f.getTelefone());
    }

    @FXML
    private void salvar() {
        if (campoNome.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Preencha o nome.").showAndWait();
            return;
        }

        fornecedorAtual.setNome_fornecedor(campoNome.getText().trim());
        fornecedorAtual.setDescricao(campoDescricao.getText().trim());
        fornecedorAtual.setEstado(campoEstado.getValue());
        fornecedorAtual.setMunicipio(campoMunicipio.getText().trim());
        fornecedorAtual.setRua(campoRua.getText().trim());
        fornecedorAtual.setTelefone(campoTelefone.getText().trim());

        try {
            fornecedorAtual.setNumero(Integer.parseInt(campoNumero.getText().trim()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Número inválido.").showAndWait();
            return;
        }

        try {
            new FornecedorDAO().atualizarFornecedor(fornecedorAtual);
            paiController.iniciarPesquisa();
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