package view;

import dao.funcionarioDAO;
import model.funcionario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class dashboardframe extends JFrame {

    private JTable tableFuncionarios;
    private DefaultTableModel tableModel;
    private funcionarioDAO funcionarioDAO;
    private JPopupMenu popupMenu;
    private JMenuItem deleteItem;

    public dashboardframe() {
        setTitle("Dashboard - Sistema");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        funcionarioDAO = new funcionarioDAO();

        JPanel contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(contentPane);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIManager.getColor("Component.borderColor"), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));
        headerPanel.setBackground(UIManager.getColor("Panel.background"));

        JButton btnCadastrar = new JButton("Cadastrar Novo Funcionário");
        btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCadastrar.addActionListener(e -> abrirCadastro());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        headerPanel.add(btnCadastrar, gbc);

        contentPane.add(headerPanel, BorderLayout.NORTH);

        JPanel tableContainerPanel = new JPanel(new BorderLayout(0, 10));
        tableContainerPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIManager.getColor("Component.borderColor"), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        tableContainerPanel.setBackground(UIManager.getColor("Panel.background"));

        JLabel tableTitle = new JLabel("Lista de Funcionários", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableContainerPanel.add(tableTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Cargo", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableFuncionarios = new JTable(tableModel);
        tableFuncionarios.setRowHeight(25);
        tableFuncionarios.getTableHeader().setReorderingAllowed(false);
        tableFuncionarios.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tableFuncionarios);
        tableContainerPanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(tableContainerPanel, BorderLayout.CENTER);

        // --- Configuração do JPopupMenu para Exclusão ---
        popupMenu = new JPopupMenu();
        deleteItem = new JMenuItem("Excluir");
        popupMenu.add(deleteItem);

        deleteItem.addActionListener(e -> excluirFuncionarioSelecionado());

        // Adiciona o MouseListener à tabela para exibir o pop-up menu
        tableFuncionarios.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tableFuncionarios.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        tableFuncionarios.setRowSelectionInterval(row, row); // Seleciona a linha clicada
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        carregarFuncionarios();
    }

    private void abrirCadastro() {
        CadastroFuncionarioFrame cadastro = new CadastroFuncionarioFrame();
        cadastro.setVisible(true);

        cadastro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                carregarFuncionarios();
            }
        });
    }

    private void carregarFuncionarios() {
        tableModel.setRowCount(0);
        try {
            List<funcionario> funcionarios = funcionarioDAO.listar();
            for (funcionario f : funcionarios) {
                tableModel.addRow(new Object[]{f.getId(), f.getNome(), f.getCargo(), f.getTelefone()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void excluirFuncionarioSelecionado() {
        int selectedRow = tableFuncionarios.getSelectedRow();
        if (selectedRow != -1) {
            // Converte a linha da view para a linha do model, caso a tabela esteja ordenada
            int modelRow = tableFuncionarios.convertRowIndexToModel(selectedRow);
            
            Object idObj = tableModel.getValueAt(modelRow, 0); // O ID está na primeira coluna
            if (idObj == null) {
                JOptionPane.showMessageDialog(this, "Não foi possível obter o ID do funcionário para exclusão.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int funcionarioId = (int) idObj;
            String funcionarioNome = (String) tableModel.getValueAt(modelRow, 1); // Nome para exibição

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o funcionário: " + funcionarioNome + " (ID: " + funcionarioId + ")?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    funcionarioDAO.excluir(funcionarioId);
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarFuncionarios(); // Recarrega a tabela após a exclusão
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum funcionário selecionado para exclusão.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}