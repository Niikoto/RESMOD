package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;



public class MainController {
    @FXML
    private Label minhaLabel;
    @FXML
    public void abrirDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.DashboardController"));
        Parent root = loader.load();
        Stage stage = (Stage) minhaLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
