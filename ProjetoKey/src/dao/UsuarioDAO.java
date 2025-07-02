package dao;

import model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    ConexaoDAO conexao = new ConexaoDAO();

    public boolean autenticarUsuario(String usuario, String senha) {
        try (Connection conn = conexao.conectaBD()) {
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro autenticarUsuario: " + e);
            return false;
        }
    }

    public boolean inserirUsuario(Usuario u) {
        try (Connection conn = conexao.conectaBD()) {
            String sql = "INSERT INTO usuarios(nome, usuario, senha, is_admin) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getUsuario());
            stmt.setString(3, u.getSenha());
            stmt.setBoolean(4, u.isAdmin());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserirUsuario: " + e);
            return false;
        }
    }

    public boolean verificarAdmin(String usuario, String senha) {
        try (Connection conn = conexao.conectaBD()) {
            String sql = "SELECT is_admin FROM usuarios WHERE usuario = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_admin");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Erro verificarAdmin: " + e);
            return false;
        }
    }
}
