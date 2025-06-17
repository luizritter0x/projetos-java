package minhaempresa;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class Truncate {
    public void Truncate() {
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = null;
        
        try {
            conexao = conexaoBD.conectar();
            if (conexao != null) {
                String sqlDeleteFuncionarios = "DELETE FROM funcionarios;";
                try (PreparedStatement psFuncionarios = conexao.prepareStatement(sqlDeleteFuncionarios)) {
                    int linhasAfetadas = psFuncionarios.executeUpdate();
                    System.out.println(linhasAfetadas + " funcionários e seus atributos foram removidos com sucesso!");
                }
            }
        } catch(SQLException e) {
            System.out.println("Erro ao limpar as tabelas.");
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}