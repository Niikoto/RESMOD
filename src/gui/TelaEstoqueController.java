package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Produto;

public class TelaEstoqueController {

    @FXML private Label nomeField;
    @FXML private TextField quantidadeField;
    @FXML private ComboBox<String> tipoOperacaoBox;

    private Produto produtoSelecionado;

    public void setProdutos(Produto produto) {
        this.produtoSelecionado = produto;

        // preencher campos
        nomeField.setText(produto.getNome_produto());
        quantidadeField.setText(String.valueOf(produto.getQuantidade()));
    }

    @FXML
    private void enviar() {

        String quantidade = quantidadeField.getText();
        String tipo = tipoOperacaoBox.getValue();

        if (quantidade.isEmpty() || tipo == null) {
            System.out.println("Adicione um valor!");
            return;
        }

        int qtd = Integer.parseInt(quantidade);

        if (tipo.equals("Entrada")) {
            System.out.println("Somando: " + qtd);
        } else {
            System.out.println("Subtraindo: " + qtd);
        }

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
