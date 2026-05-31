package gui;

import dao.CompraDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Compra;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.text.NumberFormat;
import java.util.Locale;

public class TelaEditarCompraController {

    @FXML private TextArea campoObs;
    @FXML private TextField campoValor;
    @FXML private TextField campoAnexo;
    @FXML private Label imgNotFound;
    @FXML private Button btnSelecionar;

    private String caminhoAnexo;

    private Compra compraAtual;
    private TelaComprasController paiController;

    public void setDados(Compra c, TelaComprasController pai) {
        this.compraAtual = c;
        this.paiController = pai;

    }

    @FXML
    public void initialize() {
    }

    public void preencherDados() {
        if (compraAtual.getAnexo_fiscal() != null && !compraAtual.getAnexo_fiscal().isEmpty()) {
            imgNotFound.setText(compraAtual.getAnexo_fiscal());
            caminhoAnexo = compraAtual.getAnexo_fiscal();
        } else {
            imgNotFound.setText("Nenhum Arquivo Selecionado");
        }
    }

    @FXML
    private void salvar() {
        try {
            if (!campoObs.getText().trim().isEmpty())
                compraAtual.setObs_compra(campoObs.getText().trim());

            if (!campoValor.getText().trim().isEmpty())
                compraAtual.setValor_da_compra(NumberFormat.getInstance(new Locale("pt", "BR")).parse(campoValor.getText()).floatValue());

            if (caminhoAnexo != null && !caminhoAnexo.isEmpty())
                compraAtual.setAnexo_fiscal(caminhoAnexo);

            new CompraDAO().atualizarCompra(compraAtual);
            paiController.recarregarTabela();
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

    @FXML
    private void selecArqu(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Escolha um arquivo como nota fiscal");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos", "*.png", "*.jpg", "*.jpeg", "*.pdf", "*.docx", "*.doc"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File arquivo = chooser.showOpenDialog(stage);

        if (arquivo == null) return;

        Path destino = Paths.get("src/resources/sources/" + arquivo.getName());

        try {
            Files.copy(arquivo.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        caminhoAnexo = arquivo.getName();
        imgNotFound.setText(arquivo.getName());
    }

    private void fechar() {
        ((Stage) campoObs.getScene().getWindow()).close();
    }
}