package gui;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import dao.CategoriaDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import modelo.Categoria;
import modelo.Fornecedor;
import modelo.Produto;

public class TelaProdutosController {

    @FXML
    private TableView<?> tabelaProdutos;

    @FXML
    private TextField txtNomeProd;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtQuant;
    @FXML
    private TextField txtMinEs;

    @FXML
    private ComboBox<Fornecedor> comboFor;
    @FXML
    private ComboBox<Categoria> comboCat;

    @FXML
    VBox vBoxCadProd;
    @FXML private VBox vboxNovaCategoria;

    @FXML private TextField txtNovaCategoria;

    private CategoriaDAO catDao = new CategoriaDAO();
    private FornecedorDAO forDao = new FornecedorDAO();

    @FXML
    public void initialize() {
        comboCat.setItems(FXCollections.observableArrayList(catDao.listarCategoria()));
        comboFor.setItems(FXCollections.observableArrayList(forDao.listarFornecedores()));
        // Aqui vais adicionar depois o código para carregar os produtos na tabela
        // tabelaProdutos.setPlaceholder(new Label("Nenhum produto encontrado"));
        vBoxCadProd.setVisible(false);
        vBoxCadProd.setManaged(false);

    }

    @FXML
    public void alterarVisibilidade(ActionEvent event) {
        boolean visivel = vBoxCadProd.isVisible();
        if (!visivel) {
            vBoxCadProd.setVisible(true);
            vBoxCadProd.setManaged(true);
        } else {
            vBoxCadProd.setVisible(false);
            vBoxCadProd.setManaged(false);
        }
    }

    @FXML
    public void enviarProd(ActionEvent event) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        ProdutoDAO prodDao = new ProdutoDAO();
        Produto p = new Produto();
        p.setNome_produto(txtNomeProd.getText());

        try {
            Number number = nf.parse(txtPreco.getText());
            p.setPreco(number.floatValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ARRUMAR AQUI
        // PERDÃO FAMILIA
        // :(
        p.setQuantidade(Integer.parseInt(txtQuant.getText()));
        p.setMinimo(Integer.parseInt(txtMinEs.getText()));
        Categoria catEscolhida = comboCat.getValue();
        Fornecedor forEscolhido = comboFor.getValue();

        if (catEscolhida != null && forEscolhido != null) {
            p.setCOD_categoria(catEscolhida.getID_categoria());
            p.setCOD_CNPJ(forEscolhido.getCNPJ());
        } else {
            return;
        }

        try {
            prodDao.cadastrarProduto(p);

            txtNomeProd.clear();
            txtPreco.clear();
            txtQuant.clear();
            txtMinEs.clear();
            comboCat.setValue(null);
            comboFor.setValue(null);

            exibirAlerta("Sucesso", "Produto cadastro");
            vBoxCadProd.setVisible(false);
            vBoxCadProd.setManaged(false);
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro", "Não foi possivel cadastrar o produto");
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void alternarNovaCategoria() {
        boolean visivel = vboxNovaCategoria.isVisible();
        vboxNovaCategoria.setVisible(!visivel);
        vboxNovaCategoria.setManaged(!visivel);
        if (visivel) txtNovaCategoria.clear();
    }

    @FXML
    public void salvarNovaCategoria() {
        String nome = txtNovaCategoria.getText().trim();
        if (nome.isEmpty()) {
            exibirAlerta("Aviso", "Digite um nome para a categoria.");
            return;
        }

        Categoria nova = new Categoria();
        nova.setNomeCatetegoria(nome);
        catDao = new CategoriaDAO(); // nova conexão
        catDao.cadastrarCategoria(nova);

        alternarNovaCategoria();
        recarregarCategorias();
        exibirAlerta("Sucesso", "Categoria \"" + nome + "\" cadastrada!");
    }

    @FXML
    public void removerCategoria() {
        Categoria selecionada = comboCat.getValue();
        if (selecionada == null) {
            exibirAlerta("Aviso", "Selecione uma categoria para remover.");
            return;
        }

        catDao = new CategoriaDAO();
        catDao.deletarCategoria(selecionada.getID_categoria());
        comboCat.setValue(null);
        recarregarCategorias();
        exibirAlerta("Sucesso", "Categoria removida!");
    }

    private void recarregarCategorias() {
        catDao = new CategoriaDAO();
        comboCat.setItems(FXCollections.observableArrayList(catDao.listarCategoria()));
    }
}