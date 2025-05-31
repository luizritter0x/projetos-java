package view;

import controller.usuariocontroller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class loginframe extends JFrame {
    private usuariocontroller userController;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public loginframe() {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        userController = new usuariocontroller();

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(contentPane);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIManager.getColor("Component.borderColor"), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));
        loginPanel.setBackground(UIManager.getColor("Panel.background"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0,0,0,0));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0;
        loginPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0;
        loginPanel.add(new JLabel("Usuário:"), gbc);

        gbc.gridy++;
        userField = new JTextField();
        loginPanel.add(userField, gbc);

        gbc.gridy++;
        loginPanel.add(new JLabel("Senha:"), gbc);

        gbc.gridy++;
        passField = new JPasswordField();
        loginPanel.add(passField, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(Box.createVerticalGlue(), gbc);

        loginButton = new JButton("Entrar");
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        loginPanel.add(loginButton, gbc);
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (userController.autenticar(user, pass)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                dashboardframe dashboard = new dashboardframe();
                dashboard.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
            }
        });

        contentPane.add(loginPanel, BorderLayout.CENTER);
    }
}