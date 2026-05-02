package gui;

import dao.Entrada_SaidaDAO;
import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Entrada_saida;
import modelo.Produto;

public class TelaEstoqueController {
    @FXML
    private Label nomeField;
    @FXML
    private TextField quantidadeField;
    @FXML
    private ComboBox<String> tipoOperacaoBox;

    private int idProd;
    private int qtn;

    private Produto produtoSelecionado;

    private TelaProdutosController telaProdutosController;

    public void setAtualizar(TelaProdutosController telaProdutosController){
        this.telaProdutosController = telaProdutosController;
    }

    public void setProdutos(Produto produto) {
        this.produtoSelecionado = produto;

        // preencher campos
        nomeField.setText(produto.getNome_produto());
        idProd = produto.getID_produto();
        qtn = produto.getQuantidade();
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
            }
            else{
                produto.setQuantidade(entSai.getQuantidade() - qtn);
            }
        }

        daoES.inserirEouS(entSai);

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

}
