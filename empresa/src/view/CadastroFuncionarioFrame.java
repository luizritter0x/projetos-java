package view;

import dao.funcionarioDAO;
import model.funcionario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroFuncionarioFrame extends JFrame {
    private JTextField nomeField;
    private JTextField cargoField;
    private JTextField telefoneField;

    public CadastroFuncionarioFrame() {
        setTitle("Cadastro de Funcionário");
        setSize(480, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(contentPane);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIManager.getColor("Component.borderColor"), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        formPanel.setBackground(UIManager.getColor("Panel.background"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel formTitle = new JLabel("Novo Registro", SwingConstants.CENTER);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setBorder(new EmptyBorder(0,0,15,0));
        gbc.gridy = 0;
        formPanel.add(formTitle, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridy++;
        nomeField = new JTextField();
        formPanel.add(nomeField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Cargo:"), gbc);
        gbc.gridy++;
        cargoField = new JTextField();
        formPanel.add(cargoField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridy++;
        telefoneField = new JTextField();
        formPanel.add(telefoneField, gbc);

        JButton btnSalvar = new JButton("Salvar");
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        formPanel.add(btnSalvar, gbc);
        btnSalvar.addActionListener(e -> salvarFuncionario());

        contentPane.add(formPanel, BorderLayout.CENTER);
    }

    private void salvarFuncionario() {
        String nome = nomeField.getText().trim();
        String cargo = cargoField.getText().trim();
        String telefone = telefoneField.getText().trim();

        if (nome.isEmpty() || cargo.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nome.length() > 50) {
            JOptionPane.showMessageDialog(this, "O nome deve ter no máximo 50 caracteres.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cargoUpper = cargo.toUpperCase();
        if (!cargoUpper.equals("GERENTE") && !cargoUpper.equals("ATENDENTE") && !cargoUpper.equals("DONO")) {
            JOptionPane.showMessageDialog(this, "O cargo deve ser GERENTE, ATENDENTE ou DONO.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String telefoneRegex = "\\d{2} \\d{4}-\\d{4}";
        Pattern pattern = Pattern.compile(telefoneRegex);
        Matcher matcher = pattern.matcher(telefone);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "O telefone deve estar no formato DDD XXXX-XXXX.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        funcionario f = new funcionario(nome, cargo, telefone);
        funcionarioDAO dao = new funcionarioDAO();

        try {
            dao.adicionar(f);
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            nomeField.setText("");
            cargoField.setText("");
            telefoneField.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}