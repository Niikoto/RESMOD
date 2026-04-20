package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class TelaProdutosController {

    @FXML
    private TableView<?> tabelaProdutos;

    @FXML
    public void initialize() {
        // Aqui vais adicionar depois o código para carregar os produtos na tabela
        // tabelaProdutos.setPlaceholder(new Label("Nenhum produto encontrado"));
    }
}