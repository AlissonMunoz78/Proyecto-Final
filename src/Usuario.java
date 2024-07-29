import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario extends JFrame {
    private JButton registerUserButton;
    private JButton viewUsersButton;
    private JTextArea displayArea;

    public Usuario() {
        setTitle("Gestión de Usuarios - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        registerUserButton = new JButton("Registrar Usuario");
        viewUsersButton = new JButton("Ver Usuarios");

        buttonPanel.add(registerUserButton);
        buttonPanel.add(viewUsersButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        registerUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarios();
            }
        });
    }

    private void registrarUsuario() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField roleField = new JTextField();

        Object[] fields = {
                "Nombre de Usuario:", usernameField,
                "Contraseña:", passwordField,
                "Rol:", roleField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Registrar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleField.getText();

            try {
                Connection con = getConnection();
                String query = "INSERT INTO Usuarios (username, password, rol) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, role);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Usuario registrado con éxito.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar el usuario.");
            }
        }
    }

    private void verUsuarios() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Usuarios";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Usuario: ").append(rs.getInt("id")).append("\n");
                sb.append("Nombre de Usuario: ").append(rs.getString("username")).append("\n");
                sb.append("Rol: ").append(rs.getString("rol")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los usuarios.");
        }
    }

    public Connection getConnection() throws Exception {
        String URL = "jdbc:mysql://localhost:3306/proyectofinal";
        String USER = "root";
        String PASSWORD = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Usuario().setVisible(true);
            }
        });
    }
}
