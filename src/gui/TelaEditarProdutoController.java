package gui;

import dao.CategoriaDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Categoria;
import modelo.Fornecedor;
import modelo.Produto;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class TelaEditarProdutoController {

    @FXML private TextField campoNome;
    @FXML private TextField campoPreco;
    @FXML private TextField campoQuantidade;
    @FXML private TextField campoMinimo;
    @FXML private ComboBox<Categoria> campoCat;
    @FXML private ComboBox<Fornecedor> campoFor;

    private Produto produtoAtual;
    private TelaProdutosController paiController;

    public void setDados(Produto p, TelaProdutosController pai) {
        this.produtoAtual = p;
        this.paiController = pai;

        List<Categoria> cats = new CategoriaDAO().listarCategoria();
        List<Fornecedor> fors = new FornecedorDAO().listarFornecedor();

        campoCat.setItems(FXCollections.observableArrayList(cats));
        campoFor.setItems(FXCollections.observableArrayList(fors));

        campoNome.setText(p.getNome_produto());
        campoPreco.setText(String.format("%.2f", p.getPreco()));
        campoQuantidade.setText(String.valueOf(p.getQuantidade()));
        campoMinimo.setText(String.valueOf(p.getMinimo()));

        cats.stream()
                .filter(c -> c.getNomeCategoria().equals(p.getCategoria().getNomeCategoria()))
                .findFirst()
                .ifPresent(campoCat::setValue);

        fors.stream()
                .filter(f -> f.getNome_fornecedor().equals(p.getFornecedor().getNome_fornecedor()))
                .findFirst()
                .ifPresent(campoFor::setValue);
    }

    @FXML
    private void salvar() {
        if (campoNome.getText().trim().isEmpty() || campoCat.getValue() == null || campoFor.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
            return;
        }

        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
            produtoAtual.setNome_produto(campoNome.getText().trim());
            produtoAtual.setPreco(nf.parse(campoPreco.getText()).floatValue());
            produtoAtual.setQuantidade(Integer.parseInt(campoQuantidade.getText().trim()));
            produtoAtual.setMinimo(Integer.parseInt(campoMinimo.getText().trim()));
            produtoAtual.setCOD_categoria(campoCat.getValue().getID_categoria());
            produtoAtual.setCOD_CNPJ(campoFor.getValue().getCNPJ());

            new ProdutoDAO().atualizarProduto(produtoAtual);
            paiController.atualizarTela();
            fechar();
        } catch (ParseException e) {
            new Alert(Alert.AlertType.WARNING, "Preço inválido.").showAndWait();
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