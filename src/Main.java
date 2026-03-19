import dao.UsuarioDAO;
import modelo.Usuario;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Projeto RESMOD funcionando!");

        // TEMPORÁRIO!
        // Sistema de login via terminal
        // Pois não há front-end ainda.
        Scanner input = new Scanner(System.in);

        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        System.out.print("Cargo (número): ");
        int cargo = input.nextInt();

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

    }
}