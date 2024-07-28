import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        // Configuración de la ventana principal
        setTitle("Inicio de Sesión - MediCare");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Campos de entrada
        JLabel usernameLabel = new JLabel("Usuario:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();

        // Botón de inicio de sesión
        loginButton = new JButton("Iniciar Sesión");

        // Agregar componentes al panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(loginButton);

        // Agregar panel al marco
        add(panel);

        // Acción del botón de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    // Método para iniciar sesión
    private void iniciarSesion() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Usuarios WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("rol");
                if ("admin".equalsIgnoreCase(role)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido Administrador");
                    // Abrir ventana del administrador
                    new Administrador().setVisible(true);
                } else if ("medico".equalsIgnoreCase(role)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido Personal Médico");
                    // Abrir ventana del personal médico
                    new PersonalMedico().setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }
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
                new Login().setVisible(true);
            }
        });
    }
}
