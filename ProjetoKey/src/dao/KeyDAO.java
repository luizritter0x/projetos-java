package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.KeyRegistro;

public class KeyDAO {
    ConexaoDAO conexao = new ConexaoDAO();

    public boolean validarKey(String chave) {
        try (Connection conn = conexao.conectaBD()) {
            String sql = "SELECT * FROM keys_registro WHERE chave = ? AND usada = false";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, chave);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro validarKey: " + e);
            return false;
        }
    }

    public void marcarKeyComoUsada(String chave) {
        try (Connection conn = conexao.conectaBD()) {
            String sql = "UPDATE keys_registro SET usada = true WHERE chave = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, chave);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro marcarKeyComoUsada: " + e);
        }
    }

    // Método que faltava: inserirKey
    public boolean inserirKey(String chave) {
        try (Connection conn = conexao.conectaBD()) {
            String sql = "INSERT INTO keys_registro(chave, usada) VALUES (?, false)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, chave);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserirKey: " + e);
            return false;
        }
    }

    public List<KeyRegistro> listarKeys() {
        List<KeyRegistro> lista = new ArrayList<>();
        try (Connection conn = conexao.conectaBD()) {
            String sql = "SELECT * FROM keys_registro";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KeyRegistro key = new KeyRegistro();
                key.setId(rs.getInt("id"));
                key.setChave(rs.getString("chave"));
                key.setUsada(rs.getBoolean("usada"));
                lista.add(key);
            }
        } catch (SQLException e) {
            System.out.println("Erro listarKeys: " + e);
        }
        return lista;
    }
}
