package gui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import dao.Entrada_SaidaDAO;
import dao.ProdutoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Entrada_saida;
import modelo.Produto;
import modelo.Session;

public class TelaEstoqueController {
    @FXML
    private TextField quantidadeField;

    @FXML
    private ComboBox<String> tipoOperacaoBox;

    @FXML
    private Label txtNomeProd;

    @FXML
    private ImageView imgFoto;

    @FXML
    private Button btnFoto;

    @FXML
    private Label txtNotFound;

    private int idProd;
    private int qtn;
    private String nomeImg;

    private Produto produtoSelecionado;

    private TelaProdutosController telaProdutosController;

    public void setAtualizar(TelaProdutosController telaProdutosController) {
        this.telaProdutosController = telaProdutosController;
    }

    public void setProdutos(Produto produto) {
        this.produtoSelecionado = produto;

        // preencher campos
        txtNomeProd.setText(produto.getNome_produto());
        idProd = produto.getID_produto();
        qtn = produto.getQuantidade();

        nomeImg = produto.getImg_prod();

        if (nomeImg != "" && nomeImg != null) {
            txtNotFound.setVisible(false);
            txtNotFound.setManaged(false);

            imgFoto.setPreserveRatio(true);

            File file = new File("src/resources/sources/" + nomeImg);

            Image image = new Image(file.toURI().toString());

            imgFoto.setImage(image);
        } else {
            imgFoto.setVisible(false);
            imgFoto.setManaged(false);
        }
    }

    @FXML
    private void enviar() {
        Produto produto = new Produto();
        produto.setID_produto(idProd);

        Entrada_saida entSai = new Entrada_saida();
        entSai.setCOD_produto(idProd);

        ProdutoDAO daoPro = new ProdutoDAO();
        Entrada_SaidaDAO daoES = new Entrada_SaidaDAO();

        entSai.setQuantidade(Integer.parseInt(quantidadeField.getText()));

        String tipo = tipoOperacaoBox.getValue();

        if (quantidadeField.getText() == null || tipo == null) {
            System.out.println("Adicione um valor!");
            return;
        }

        if (tipo.equals("Entrada")) {
            entSai.setTipo(true);
            produto.setQuantidade(qtn + entSai.getQuantidade());
        } else {
            entSai.setTipo(false);
            if (qtn > entSai.getQuantidade()) {
                produto.setQuantidade(qtn - entSai.getQuantidade());
            } else {
                produto.setQuantidade(entSai.getQuantidade() - qtn);
            }
        }

        daoES.inserirEouS(entSai, Session.getUsuario().getId_Email());

        daoPro.upDateEstoque(produto);

        telaProdutosController.atualizarTela();
        fechar();
    }

    @FXML
    private void cancelar() {
        fechar();
    }

    private void fechar() {
        ((Stage) quantidadeField.getScene().getWindow()).close();
    }

    @FXML
    public void trocarFoto(ActionEvent event) {

        FileChooser chooser = new FileChooser();//criar um atributo para salvar o arquivo novo

        chooser.setTitle("Escolha um arquivo");//isso e para tela de escolha do arquivo, vai aparacer como titulo

        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens","*.png","*.jpg","*.jpeg"));//extenções aceitas no sistema

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();//cria a tela para seleção do arquivo

        File arquivo = chooser.showOpenDialog(stage);//guarda o arquivo escolhido na tela

        Path destino = Paths.get("src/resources/sources/"+arquivo.getName());//Onde vai ser salvo a imagem

        try {            
            Files.copy(arquivo.toPath(),destino,StandardCopyOption.REPLACE_EXISTING);//salva o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProdutoDAO proDao = new ProdutoDAO();
        proDao.upDateImg(arquivo.getName(), idProd);//atualiza o nome da imagem no banco

        Image img = new Image(destino.toUri().toString());//salva o caminho da imagem para ser setado certo

        imgFoto.setImage(img);//salva a nova imagem

        File file = new File("src/resources/sources/"+nomeImg);// salva o caminho da imagem anterior para apagar ela

        file.delete();//apaga a imagem anterior

        telaProdutosController.atualizarTela();
    }
}
