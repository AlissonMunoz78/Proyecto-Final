import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/estudiantes2024a";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public Login() {
        // ...
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public static Usuario autenticar(String idUsuario, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT tipo_usuario FROM usuarios WHERE id_usuario = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idUsuario);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String tipoUsuario = resultSet.getString("tipo_usuario");
                if ("administrador".equalsIgnoreCase(tipoUsuario)) {
                    return new Administrador("1234", "Rodolfo", "Luna");
                } else if ("medico".equalsIgnoreCase(tipoUsuario)) {
                    return new PersonalMedico("1345", "Monica","Loor");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al autenticar: " + e.getMessage());
        }
        return null;
    }

    private void actionPerformed(ActionEvent e) {
        String usuario = userField.getText();
        String contraseña = new String(passField.getPassword());

        Usuario authenticatedUser = autenticar(usuario, contraseña);
        if (authenticatedUser != null) {
            if (authenticatedUser instanceof Administrador) {
                new Administrador().setVisible(true);
            } else {
                new Medico().setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos");
        }
    }
}