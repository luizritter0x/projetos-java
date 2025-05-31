package dao;

import model.funcionario;
import util.DataBase; // Certifique-se de que esta importação está correta

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class funcionarioDAO {

    public void adicionar(funcionario f) throws SQLException {
        String sql = "INSERT INTO funcionarios (nome, cargo, telefone) VALUES (?, ?, ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCargo());
            stmt.setString(3, f.getTelefone());
            stmt.executeUpdate();
        }
    }

    public List<funcionario> listar() throws SQLException {
        List<funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, cargo, telefone FROM funcionarios";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                funcionario f = new funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cargo"),
                    rs.getString("telefone")
                );
                funcionarios.add(f);
            }
        }
        return funcionarios;
    }

    // --- Este é o método crucial que precisa estar correto ---
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}