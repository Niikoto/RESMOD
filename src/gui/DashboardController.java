package gui;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label minhaLabel;

    @FXML
    public void inicializar() {
        minhaLabel.setText("IntelliDog");
    }
}
