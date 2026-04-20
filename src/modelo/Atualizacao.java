package modelo;

public class Atualizacao {
    private int id;
    private String titulo;
    private String mensagem;
    private String dataHora;
    private boolean lida; // Define se vai ficar laranja ou não

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public boolean isLida() { return lida; }
    public void setLida(boolean lida) { this.lida = lida; }
}