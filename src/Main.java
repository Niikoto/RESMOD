//import dao.UsuarioDAO;
//import modelo.Usuario;

//import java.sql.SQLException;
//import java.util.Scanner; <-- apague os barras da desses imports para que todo o resto funcione


import java.io.IOException;

import gui.TelaPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXML;
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
        // TEMPORÁRIO!
        // Sistema de login via terminal
        // Pois não há front-end ainda.
        
        //Scanner input = new Scanner(System.in);<-- apague os barras para liberar o input

        /*
        Inputs para criar conta TESTES
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        System.out.print("Cargo (número): ");
        int cargo = input.nextInt();
        
        input.close();

        Usuario u = new Usuario();
        u.setId_email(email);
        u.setNome(nome);
        u.setSenha(senha);
        u.setCargo(cargo);

        UsuarioDAO dao = new UsuarioDAO();
        try{
            dao.cadastrar(u);
            System.out.println("Usuário " + u.getNome() + " Cadastrado!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */

        /* 
        Verificar conta TESTE
        System.out.print("Digite seu email: ");
        String ID_email = input.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = input.nextLine();

        input.close();

        Usuario u = new Usuario();
        u.setId_email(ID_email);
        u.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        try{
            dao.verificar(u);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
            */
    }
}