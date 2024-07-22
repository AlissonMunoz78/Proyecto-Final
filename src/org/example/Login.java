package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public Login() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        userField = new JTextField();
        passField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(new JLabel("Usuario:"));
        panel.add(userField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passField);
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = userField.getText();
                String contraseña = new String(passField.getPassword());

                // Lógica de autenticación
                if (autenticar(usuario, contraseña)) {
                    if (esAdministrador(usuario)) {
                        new Admin().setVisible(true);
                    } else {
                        new MedicoFrame().setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos");
                }
            }
        });
    }

    private boolean autenticar(String usuario, String contraseña) {
        // Implementar la lógica de autenticación
        return true; // Placeholder
    }

    private boolean esAdministrador(String usuario) {
        // Lógica para determinar si el usuario es administrador
        return usuario.equals("admin"); // Placeholder
    }
}
