package model;

public class usuario {
    private int id;
    private String usuario;
    private String senha;

    public usuario(int id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }
}