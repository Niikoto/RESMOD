package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import dao.CategoriaDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Categoria;
import modelo.Fornecedor;
import modelo.Produto;

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
    private Button buttonExcluir;

    @FXML
    private Label txtNotFound;// caso nada tenha sito selecionado
    @FXML
    private Label txtImgNDefinida;

    @FXML
    private ImageView imgProd;// imagem de estoque
    @FXML
    private ImageView imgNDefinida;

    @FXML
    private ComboBox<Fornecedor> comboFor;
    @FXML
    private ComboBox<Categoria> comboCat;

    @FXML
    VBox vBoxCadProd;
    @FXML
    private VBox vboxNovaCategoria;

    @FXML
    private TextField txtNovaCategoria;

    @FXML
    private TableColumn<Produto, Integer> columnId;
    @FXML
    private TableColumn<Produto, String> columnNome;
    @FXML
    private TableColumn<Produto, Integer> columnQuantidade;
    @FXML
    private TableColumn<Produto, Float> columnPreco;
    @FXML
    private TableColumn<Produto, String> columnCategoria;
    @FXML
    private TableColumn<Produto, String> columnFornecedor;

    @FXML
    private TextField txtNomePes;
    @FXML
    private TextField txtNomeFornecedor1;

    private CategoriaDAO catDao = new CategoriaDAO();
    private FornecedorDAO forDao = new FornecedorDAO();

    private String nomeImg;

    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("ID_produto")); // essa parte vai usar o metodo get nas
                                                                                // coisas que estão dentro das aspas no
                                                                                // parenteses, a primeira letra ele vai
                                                                                // deixar maiuscula
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome_produto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCategoria().getNomeCategoria()));
        columnFornecedor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFornecedor().getNome_fornecedor()));

        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listarProduto("", "");
        tabelaProdutos.setItems(FXCollections.observableArrayList(lista));

        comboCat.setItems(FXCollections.observableArrayList(catDao.listarCategoria()));
        comboFor.setItems(FXCollections.observableArrayList(forDao.listarFornecedor()));
        // Aqui vais adicionar depois o código para carregar os produtos na tabela
        // tabelaProdutos.setPlaceholder(new Label("Nenhum produto encontrado"));
        vBoxCadProd.setVisible(false);
        vBoxCadProd.setManaged(false);

        imgProd.setVisible(false);
        imgProd.setManaged(false);

        txtNotFound.setVisible(true);
        txtNotFound.setManaged(true);

        txtNomePes.setOnAction(e -> {
            List<Produto> list = dao.listarProduto(txtNomePes.getText(), txtNomeFornecedor1.getText());
            tabelaProdutos.setItems(FXCollections.observableArrayList(list));
        });
        txtNomeFornecedor1.setOnAction(e -> {
            List<Produto> list = dao.listarProduto(txtNomePes.getText(), txtNomeFornecedor1.getText());
            tabelaProdutos.setItems(FXCollections.observableArrayList(list));
        });

        // função anonima que faz com que seja identificado um clique em uma linha
        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        imgProd.setVisible(true);
                        imgProd.setManaged(true);
                        imgProd.setPreserveRatio(true);// dimenciona o tamanho da imagem para não dar problema no espaço
                                                       // de exibição

                        Produto prodSelect = tabelaProdutos.getSelectionModel().getSelectedItem();// pega os dados da
                                                                                                  // linha selecionada
                        String nomeImg = prodSelect.getImg_prod();// pega o nome da imagem que está salva no banco de
                                                                  // dados

                        File file = new File("src/resources/sources/" + nomeImg);// caminho para puxar a imagem

                        Image img = new Image(file.toURI().toString());// converte para um caminho de imagem do javafx

                        imgProd.setImage(img);// troca a imagem que está na imagem view

                        txtNotFound.setVisible(false);
                    txtNotFound.setManaged(false);
                }
            });
            
        buttonExcluir.setDisable(true);
        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                buttonExcluir.setDisable(false);
            }
            else{
                buttonExcluir.setDisable(true);
            }
        });

        imgNDefinida.setVisible(false);
        imgNDefinida.setManaged(false);
    }

    @FXML
    public void excluirProduto(ActionEvent event){
        Produto produtoselecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        int guardarID_produto = produtoselecionado.getID_produto();

        ProdutoDAO apagarProduto = new ProdutoDAO();
        apagarProduto.buttonExcluirProduto(guardarID_produto);

        tabelaProdutos.getItems().remove(produtoselecionado);
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

        if (nomeImg != "") {
            p.setImg_prod(nomeImg);
        }

        p.setNome_produto(txtNomeProd.getText());

        try {
            Number number = nf.parse(txtPreco.getText());
            p.setPreco(number.floatValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
            imgNDefinida.setImage(null);
            imgNDefinida.setVisible(false);
            imgNDefinida.setManaged(false);

            txtImgNDefinida.setVisible(true);
            txtImgNDefinida.setManaged(true);

            exibirAlerta("Sucesso", "Produto cadastro");
            vBoxCadProd.setVisible(false);
            vBoxCadProd.setManaged(false);

            List<Produto> lista = prodDao.listarProduto("", "");
            tabelaProdutos.setItems(FXCollections.observableArrayList(lista));
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
        if (visivel)
            txtNovaCategoria.clear();
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
            controller.setAtualizar(this);

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene(root));
            modal.setTitle("Editar Produto");

            modal.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarTela() {
        ProdutoDAO daoProd = new ProdutoDAO();

        List<Produto> list = daoProd.listarProduto("", "");
        tabelaProdutos.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void colocarImg(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Escolha uma imagem para o produto");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File arq = chooser.showOpenDialog(stage);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dataHora = LocalDateTime.now().format(formato);

        nomeImg = dataHora + arq.getName();

        Path destino = Paths.get("src/resources/sources/" + nomeImg);

        try {
            Files.copy(arq.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);// salva o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image img = new Image(destino.toUri().toString());// salva o caminho da imagem para ser setado certo

        imgNDefinida.setPreserveRatio(true);
        imgNDefinida.setImage(img);// salva a nova imagem
        imgNDefinida.setVisible(true);
        imgNDefinida.setManaged(true);

        txtImgNDefinida.setVisible(false);
        txtImgNDefinida.setManaged(false);
    }
}