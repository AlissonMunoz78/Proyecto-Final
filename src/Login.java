import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton registerButton;
    private JButton togglePasswordButton;
    private boolean passwordVisible = false; // Estado del campo de contraseña

    // Cargar imágenes para mostrar y ocultar la contraseña
    private ImageIcon eyeIcon = new ImageIcon(getClass().getResource("/img/ojo.png"));
    private ImageIcon eyeIconCrossed = new ImageIcon(getClass().getResource("/img/ojo-cruzado.png"));

    public Login() {
        setTitle("Login / Registro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Login / Registro"), gbc);

        // Imagen y campo de texto para el nombre de usuario
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;

        // Cargar y redimensionar la imagen
        ImageIcon userIcon = new ImageIcon(getClass().getResource("/img/usuario.png"));
        Image userImg = userIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // Tamaño ajustado
        userIcon = new ImageIcon(userImg);

        // Panel para la imagen y el campo de texto
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel imageLabel = new JLabel(userIcon);
        usernameField = new JTextField(15); // Ajustar el tamaño del campo de texto

        usernamePanel.add(imageLabel);
        usernamePanel.add(usernameField);

        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(usernamePanel, gbc);

        // Campo de texto para la contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;

        // Panel para la contraseña y el botón de mostrar/ocultar
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        Image eyeImg = eyeIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // Tamaño ajustado
        eyeIcon = new ImageIcon(eyeImg);
        Image eyeCrossedImg = eyeIconCrossed.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // Tamaño ajustado
        eyeIconCrossed = new ImageIcon(eyeCrossedImg);

        togglePasswordButton = new JButton(eyeIcon);
        togglePasswordButton.setBorder(BorderFactory.createEmptyBorder());
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setToolTipText("Mostrar/Ocultar Contraseña");
        togglePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility();
            }
        });

        passwordField = new JPasswordField(15); // Ajustar el tamaño del campo de texto
        passwordPanel.add(togglePasswordButton);
        passwordPanel.add(passwordField);

        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(passwordPanel, gbc);

        // ComboBox para el rol (admin, medico, etc.)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Personal:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        roleComboBox = new JComboBox<>(new String[]{"Administrativo", "Médico"});
        panel.add(roleComboBox, gbc);

        // Contenedor para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30)); // Tamaño más pequeño
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 30)); // Tamaño más pequeño
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        panel.add(buttonPanel, gbc);
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            // Mostrar la contraseña
            passwordField.setEchoChar((char) 0);
            togglePasswordButton.setIcon(eyeIconCrossed); // Cambiar imagen a cruzada
        } else {
            // Ocultar la contraseña
            passwordField.setEchoChar('*');
            togglePasswordButton.setIcon(eyeIcon); // Cambiar imagen a normal
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Usuarios WHERE username = ? AND password = ? AND rol = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (role.equals("Administrador")) {
                    new Administrador().setVisible(true);
                } else if (role.equals("Médico")) {
                    Usuario medico = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("rol"));
                    new PersonalMedico(medico).setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas.");
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "INSERT INTO Usuarios (username, password, rol) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
