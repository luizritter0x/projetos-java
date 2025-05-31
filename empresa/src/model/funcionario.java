package model;

public class funcionario {
    private int id;
    private String nome;
    private String cargo;
    private String telefone;

    public funcionario(int id, String nome, String cargo, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.telefone = telefone;
    }

    public funcionario(String nome, String cargo, String telefone) {
        this(-1, nome, cargo, telefone);
    }

    public funcionario(String nome, String cargo) {
        this(-1, nome, cargo, ""); // Telefone vazio por padrão
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCargo() { return cargo; }
    public String getTelefone() { return telefone; }
}