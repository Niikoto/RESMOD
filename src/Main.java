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
        //Nome da janela
        stage.setTitle("NEWE Logística Integrada - RESMOD");
        //ícone da janela ("N" do NeweLog)
        stage.getIcons().add(new Image(getClass().getResourceAsStream("sources/Logo_N.png")));
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