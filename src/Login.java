import database.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JLabel togglePasswordLabel;
    private boolean passwordVisible = false;

    private ImageIcon eyeIcon;
    private ImageIcon eyeIconCrossed;
    private ImageIcon userIcon;
    private ImageIcon buildingIcon;

    public Login() {
        loadIcons();
        setTitle("Login / Registro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(panel);

        gbc.insets = new Insets(5, 5, 5, 5);

        // Título con icono de edificio
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("MediCare", buildingIcon, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        // Etiqueta y campo de nombre de usuario con icono
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(userIcon), gbc);

        // Etiqueta y campo de contraseña con icono de ojo para mostrar/ocultar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        togglePasswordLabel = new JLabel(eyeIcon);
        togglePasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(togglePasswordLabel, gbc);

        // Etiqueta y combobox para el rol
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        roleComboBox = new JComboBox<>(new String[]{"Administrador", "Médico"});
        roleComboBox.setPreferredSize(usernameField.getPreferredSize());
        panel.add(roleComboBox, gbc);

        // Botón de login
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Iniciar Sesión");
        panel.add(loginButton, gbc);

        togglePasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                togglePasswordVisibility();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void loadIcons() {
        eyeIcon = loadImage("/img/ojo.png", 24, 24);
        eyeIconCrossed = loadImage("/img/ojo-cruzado.png", 24, 24);
        userIcon = loadImage("/img/usuario.png", 24, 24);
        buildingIcon = loadImage("/img/edificio.png", 24, 24);
    }

    private ImageIcon loadImage(String path, int width, int height) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el archivo: " + path);
            return null;
        }
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            passwordField.setEchoChar((char) 0);
            togglePasswordLabel.setIcon(eyeIconCrossed);
        } else {
            passwordField.setEchoChar('*');
            togglePasswordLabel.setIcon(eyeIcon);
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        try {
            Connection con = ConexionBaseDatos.getConnection();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
