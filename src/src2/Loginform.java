import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loginform extends JFrame {
    public JTextField campoUsuario;
    public JPasswordField campoContrasena;
    public JButton botonLogin;
    public JPanel panelLogin;

    public Loginform() {
        setTitle("Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLogin);
        setLocationRelativeTo(null);

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String contrasena = new String(campoContrasena.getPassword());

                if (autenticarUsuario(usuario, contrasena)) {
                    // Aquí puedes redirigir a la pantalla correspondiente según el tipo de usuario
                    new Menu().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos");
                }
            }
        });
    }

    private boolean autenticarUsuario(String usuario, String contrasena) {
        // Implementa aquí la autenticación
        return true;
    }

    public static void main(String[] args) {
        new Loginform().setVisible(true);
    }
}
