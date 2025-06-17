package minhaempresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AtualizarFuncionario {
    
    public static void atualizar(String nome, String novoCargo) {
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = conexaoBD.conectar();
        
        if (conexao != null) {
            String sql = "update funcionarios set cargo = ? where nome = ?;";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, novoCargo);
                ps.setString(2, nome);
                
                int linhasAfetadas = ps.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Funcionario atualizado com sucesso");
                } else {
                    System.out.println("Funcionario '" + nome + "' não encontrado para atualização.");
                }
            } catch(SQLException e) {
                System.out.println("Erro ao atualizar o funcionario");
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