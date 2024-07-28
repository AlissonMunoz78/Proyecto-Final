import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public Usuario() {
        // Configuración de la ventana principal
        setTitle("Login - MediCare");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Etiquetas y campos de texto
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Botón de login
        loginButton = new JButton("Login");
        panel.add(loginButton);

        // Etiqueta de estado
        statusLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(statusLabel);

        // Agregar panel al marco
        add(panel);

        // Acción del botón de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    if (login(username, password)) {
                        statusLabel.setText("Login successful!");
                        // Redirigir al usuario a la interfaz correspondiente
                        // Aquí puedes agregar lógica para abrir la ventana de Administrador o Personal Médico
                    } else {
                        statusLabel.setText("Login failed. Please try again.");
                    }
                } catch (Exception ex) {
                    statusLabel.setText("An error occurred. Please try again.");
                    ex.printStackTrace();
                }
            }
        });
    }

    // Método para iniciar sesión
    public boolean login(String username, String password) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isAuthenticated = false;

        try {
            con = getConnection();
            String query = "SELECT * FROM Usuario WHERE username = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                isAuthenticated = true;
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return isAuthenticated;
    }

    // Método para obtener la conexión a la base de datos
    public Connection getConnection() throws Exception {
        String URL = "jdbc:mysql://localhost:3306/proyectofinal";
        String USER = "root";
        String PASSWORD = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de despacho de eventos
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Usuario().setVisible(true);
            }
        });
    }
}
