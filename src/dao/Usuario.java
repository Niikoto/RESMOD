package dao;

public class Usuario {
    private String id_email;
    private String nome;
    private String senha;
    private String cargo;

    public Usuario(String id_email, String nome, String senha, String cargo){
        this.id_email = id_email;
        this.nome = nome;
        this.senha = senha;
        this.cargo = cargo;
    }

    // GETTERS
    public String getId_Email() {return id_email;}
    public String getNome() {return nome;}
    public String getSenha() {return senha;}
    public String getCargo() {return cargo;}

    //  SETTERS
    public void setId_email(String id_email) {this.id_email = id_email;}
    public void setNome(String nome) {this.nome = nome;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setCargo(String cargo) {this.cargo = cargo;}

    //CONSULTAR DADOS
    public void consultarDados(){
        System.out.println(getId_Email());
        System.out.println(getNome());
        System.out.println(getSenha());
        System.out.println(getCargo());
    }
}
