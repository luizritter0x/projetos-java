package minhaempresa;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

class DeletarFuncionario {
    public void deletar(String nome){
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = conexaoBD.conectar();
        
        if(conexao != null){
            String sql = "DELETE FROM funcionarios WHERE nome = ?;";
            try(PreparedStatement ps = conexao.prepareStatement(sql)){
                ps.setString(1, nome);
                int linhasAfetadas = ps.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Usuário deletado com sucesso!");
                } else {
                    System.out.println("Usuário '" + nome + "' não encontrado para deleção.");
                }
            }catch(SQLException e){
                System.out.println("Erro ao deletar usuário");
                e.printStackTrace();
            }finally{
                try{
                    if (conexao != null) {
                        conexao.close();
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}