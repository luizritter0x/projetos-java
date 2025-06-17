package minhaempresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ExtrasFuncionario {

    public void adicionarAtributo(Scanner scanner) {
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = null;

        System.out.println("--- Adicionar Informação Extra a Funcionário ---");
        System.out.print("Digite o NOME do funcionário: ");
        String nomeFuncionario = scanner.nextLine();

        try {
            conexao = conexaoBD.conectar();
            if (conexao == null) {
                System.out.println("Erro na conexão com o banco.");
                return;
            }

            String sqlBuscarId = "SELECT id FROM funcionarios WHERE nome = ?";
            int funcionarioId = -1;

            try (PreparedStatement psBuscarId = conexao.prepareStatement(sqlBuscarId)) {
                psBuscarId.setString(1, nomeFuncionario);
                ResultSet rs = psBuscarId.executeQuery();
                if (rs.next()) {
                    funcionarioId = rs.getInt("id");
                }
            }

            if (funcionarioId == -1) {
                System.out.println("Funcionário '" + nomeFuncionario + "' não encontrado.");
                return;
            }

            conexao.setAutoCommit(false);

            while (true) {
                System.out.print("Adicionar nova informação para '" + nomeFuncionario + "'? (sim/não): ");
                String resposta = scanner.nextLine().trim().toLowerCase();

                if (!resposta.equals("sim")) break;

                System.out.print("Nome do atributo (ex: CPF, Salário): ");
                String nomeAtributo = scanner.nextLine();

                System.out.print("Valor do atributo: ");
                String valorAtributo = scanner.nextLine();

                String sqlInsert = "INSERT INTO funcionario_extras (funcionario_id, nome_atributo, valor_atributo) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conexao.prepareStatement(sqlInsert)) {
                    stmt.setInt(1, funcionarioId);
                    stmt.setString(2, nomeAtributo);
                    stmt.setString(3, valorAtributo);
                    stmt.executeUpdate();
                    System.out.println("Adicionado: " + nomeAtributo + " = " + valorAtributo);
                }
            }

            conexao.commit();
            System.out.println("Finalizado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            try {
                if (conexao != null) conexao.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}
