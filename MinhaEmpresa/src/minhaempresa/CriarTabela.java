package minhaempresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CriarTabela {
    public void criar() {
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = conexaoBD.conectar();
        if (conexao != null) {
            try {
                String sqlFuncionarios = "CREATE TABLE IF NOT EXISTS funcionarios (" +
                                         "id INT AUTO_INCREMENT PRIMARY KEY," +
                                         "nome VARCHAR(100)," +
                                         "cargo VARCHAR(100)" +
                                         ")";
                try (PreparedStatement stmtFuncionarios = conexao.prepareStatement(sqlFuncionarios)) {
                    stmtFuncionarios.execute();
                    System.out.println("Tabela 'funcionarios' criada com sucesso!");
                }

                String sqlAtributos = "CREATE TABLE IF NOT EXISTS funcionario_extras (" +
                                      "id INT AUTO_INCREMENT PRIMARY KEY," +
                                      "funcionario_id INT NOT NULL," +
                                      "nome_atributo VARCHAR(255) NOT NULL," +
                                      "valor_atributo VARCHAR(255) NOT NULL," +
                                      "FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE" +
                                      ")";
                try (PreparedStatement stmtAtributos = conexao.prepareStatement(sqlAtributos)) {
                    stmtAtributos.execute();
                    System.out.println("Tabela 'funcionario_extras' criada com sucesso!");
                }

            } catch (SQLException e) {
                System.out.println("Erro ao criar tabelas");
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