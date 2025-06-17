package minhaempresa;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        
        CriarTabela CriarTabela = new CriarTabela();
        InserirFuncionario inserir = new InserirFuncionario();
        AtualizarFuncionario atualizar = new AtualizarFuncionario();
        DeletarFuncionario deletar = new DeletarFuncionario();
        ConsultarFuncionario consultar = new ConsultarFuncionario();
        Truncate Truncate = new Truncate();
        ExtrasFuncionario gerenciarAtributos = new ExtrasFuncionario();

        CriarTabela.criar();

        int op = -1;
        while (op != 0) {
            System.out.println("\n ## MENU CRUD FUNCIONARIOS ##");
            System.out.println("1. Inserir novo funcionario (Nome e Cargo)");
            System.out.println("2. Atualizar funcionario");
            System.out.println("3. Deletar funcionario");
            System.out.println("4. Limpar tabelas (Funcionarios e Atributos)");
            System.out.println("5. Consultar funcionario");
            System.out.println("6. Adicionar informação extra a funcionario existente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();
            
            switch (op) {
                case 1:
                    System.out.print("Nome do novo funcionario: ");
                    String nomeInserir = sc.nextLine();
                    System.out.print("Cargo do novo funcionario: ");
                    String cargoInserir = sc.nextLine();
                    inserir.inserir(nomeInserir, cargoInserir);
                    break;
                case 2:
                    System.out.print("Nome do funcionario para atualizar o cargo: ");
                    String nomeAtualizar = sc.nextLine();
                    System.out.print("Novo cargo: ");
                    String novoCargo = sc.nextLine();
                    atualizar.atualizar(nomeAtualizar, novoCargo);
                    break;
                case 3:
                    System.out.print("Nome do funcionario para deletar: ");
                    String nomeDeletar = sc.nextLine();
                    deletar.deletar(nomeDeletar);
                    break;
                case 4:
                    Truncate.Truncate();
                    break;
                case 5:
                    consultar.consultar();
                    break;
                case 6:
                    gerenciarAtributos.adicionarAtributo(sc);
                    break;
                case 0:
                    System.out.println("Encerrando o programa ...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
        sc.close();
    }
}