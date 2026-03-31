import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    //Mostra/chama a tela de login para o usuario
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/view/TelaLogin.fxml")
        );
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Projeto RESMOD funcionando!");
        launch(args);
    }
}