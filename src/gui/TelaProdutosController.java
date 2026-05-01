package gui;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import com.mysql.cj.xdevapi.Column;
import dao.CategoriaDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Categoria;
import modelo.Fornecedor;
import modelo.Produto;

import javax.swing.*;

public class TelaProdutosController {

    @FXML
    private TableView<Produto> tabelaProdutos;

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

    @FXML private TableColumn<Produto, Integer>columnId ;
    @FXML private TableColumn<Produto, String>columnNome;
    @FXML private TableColumn<Produto, Integer> columnQuantidade;
    @FXML private TableColumn<Produto, Float> columnPreco;
    @FXML private TableColumn<Produto, String> columnCategoria;
    @FXML private TableColumn<Produto, String> columnFornecedor;

    private CategoriaDAO catDao = new CategoriaDAO();
    private FornecedorDAO forDao = new FornecedorDAO();

    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("ID_produto")); //essa parte vai usar o metodo get nas coisas que estão dentro das aspas no parenteses, a primeira letra ele vai deixar maiuscula
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome_produto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria().getNomeCategoria()));
        columnFornecedor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFornecedor().getNome_fornecedor()));

        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listarProduto();
        tabelaProdutos.setItems(FXCollections.observableArrayList(lista));

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
        nova.setNomeCategoria(nome);
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

    // Botão do produto para gerencia de estoque
    @FXML
    private void botaoEstoque() {
        Produto produto = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produto == null) {
            System.out.println("Selecione um produto!");
            return;
        }

        modalEstoque(produto);
    }

    // modal do estoque
    private void modalEstoque(Produto produto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaEditEstoqueProduto.fxml"));
            Parent root = loader.load();

            TelaEstoqueController controller = loader.getController();
            controller.setProdutos(produto);

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Editar Produto");

            modal.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}