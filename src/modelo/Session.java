package modelo;

public class Session {

    // Estático para não ser instanciado, pode chamar só pelo nome da classe + método
    private static Usuario usuarioLogado;
    private static Cargo cargoSetado;

    // Construtor vazio
    // Para não ser instanciado
    private Session() {}

    // Método que inicializa a sessão, apontado para usuário.
    public static void iniciar(Usuario usuario) {
        usuarioLogado = usuario;
    }

    // Guarda o objeto cargo enviado em cargoSetado
    public static void iniciarCargo(Cargo cargo){
        cargoSetado = cargo;
    }

    // Método que finaliza a sessão, apontando para null.
    public static void encerrar() {
        usuarioLogado = null;
        cargoSetado = null;
    }

    // GETTER
    // Para retornar usuário
    public static Usuario getUsuario() {
        return usuarioLogado;
    }

    //Retorna o cargo setado
    public static Cargo getCargo(){
        return cargoSetado;
    }

    // Verifica se ele está ativo, se login permanece
    public static boolean estaAtiva() {
        return usuarioLogado != null && cargoSetado != null;
    }
}