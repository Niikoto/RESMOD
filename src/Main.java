import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent; 
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

    //Mostra/chama a tela de login para o usuario
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("NEWE Logística Integrada - RESMOD");

        // Ícone
        java.io.File iconFile = new java.io.File("src/resources/sources/Logo_N.png");
        if (iconFile.exists()) {
            stage.getIcons().add(new Image(iconFile.toURI().toString()));
        }

        // FXML pelo classpath (jeito correto com Maven)
        Parent root = FXMLLoader.load(getClass().getResource("/view/TelaLogin.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Projeto RESMOD funcionando!");
        launch(args);
    }
}