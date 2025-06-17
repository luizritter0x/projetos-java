package minhaempresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InserirFuncionario {
    public void inserir(String nome, String cargo) {
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = conexaoBD.conectar();
        if (conexao != null) {
            String sql = "INSERT INTO funcionarios (nome, cargo) VALUES (?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, nome);
                stmt.setString(2, cargo);
                stmt.executeUpdate();
                System.out.println("Funcionário inserido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao inserir funcionário");
                e.printStackTrace();
            } finally {
                try {
                    if (conexao != null) {
                        conexao.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}