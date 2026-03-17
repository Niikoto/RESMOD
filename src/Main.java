import dao.Usuario;

public class Main {
    public static void main(String[] args) {
        System.out.println("Projeto RESMOD funcionando!");

        Usuario user1 = new Usuario("jejo@gmail.com","marcao","123", "FUNCIONARIO");
        user1.consultarDados();
        System.out.println("-=-=-");
    }
}