package modelo;

public class Session {

    // Estático para não ser instanciado, pode chamar só pelo nome da classe + método
    private static Usuario usuarioLogado;

    // Construtor vazio
    // Para não ser instanciado
    private Session() {}

    // Método que inicializa a sessão, apontado para usuário.
    public static void iniciar(Usuario usuario) {
        usuarioLogado = usuario;
    }

    // Método que finaliza a sessão, apontando para null.
    public static void encerrar() {
        usuarioLogado = null;
    }

    // GETTER
    // Para retornar usuário
    public static Usuario getUsuario() {
        return usuarioLogado;
    }

    // Verifica se ele está ativo, se login permanece
    public static boolean estaAtiva() {
        return usuarioLogado != null;
    }
}