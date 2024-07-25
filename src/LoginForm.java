import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    // Mapa para almacenar los usuarios
    private Map<String, Usuario> usuarios;

    public LoginForm() {
        // Inicializar los usuarios
        usuarios = new HashMap<>();
        usuarios.put("admin", new Administrador("admin", "admin123"));
        usuarios.put("medico1", new PersonalMedico("medico1", "medico123"));

        frame = new JFrame("Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setBounds(10, 20, 80, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 165, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        frame.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usernameField.getText();
                String contraseña = new String(passwordField.getPassword());
                // Lógica para verificar las credenciales y redirigir al usuario adecuado
                Usuario user = usuarios.get(usuario);
                if (user != null && user.getContraseña().equals(contraseña)) {
                    JOptionPane.showMessageDialog(frame, "Login exitoso");
                    frame.dispose(); // Cerrar la ventana de login
                    user.mostrarOpciones();
                    if (user instanceof Administrador) {
                        new AdminForm();
                    } else if (user instanceof PersonalMedico) {
                        new MedicoForm();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
