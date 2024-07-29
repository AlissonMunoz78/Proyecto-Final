import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Administrador extends JFrame {
    private JButton addPatientButton;
    private JButton viewPatientsButton;
    private JButton viewReportsButton;
    private JButton manageStaffButton;
    private JTextArea displayArea;

    public Administrador() {
        // Configuración de la ventana principal
        setTitle("Administrador - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        addPatientButton = new JButton("Agregar Paciente");
        viewPatientsButton = new JButton("Ver Pacientes");
        viewReportsButton = new JButton("Ver Reportes");
        manageStaffButton = new JButton("Gestionar Personal");

        buttonPanel.add(addPatientButton);
        buttonPanel.add(viewPatientsButton);
        buttonPanel.add(viewReportsButton);
        buttonPanel.add(manageStaffButton);

        // Área de visualización
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Agregar paneles al marco
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Acciones de los botones
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPaciente();
            }
        });

        viewPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verPacientes();
            }
        });

        viewReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verReportes();
            }
        });

        manageStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarPersonal();
            }
        });
    }

    // Método para agregar un paciente
    private void agregarPaciente() {
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField fechaNacimientoField = new JTextField();

        Object[] message = {
                "Nombre:", nombreField,
                "Apellido:", apellidoField,
                "Direccion:", direccionField,
                "Telefono:", telefonoField,
                "Fecha de Nacimiento (YYYY-MM-DD):", fechaNacimientoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            String direccion = direccionField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String fechaNacimiento = fechaNacimientoField.getText().trim();

            if (!nombre.isEmpty() && !apellido.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty() && !fechaNacimiento.isEmpty()) {
                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement("INSERT INTO Pacientes (nombre, apellido, direccion, telefono, fechaNacimiento) VALUES (?, ?, ?, ?, ?)")) {
                    ps.setString(1, nombre);
                    ps.setString(2, apellido);
                    ps.setString(3, direccion);
                    ps.setString(4, telefono);
                    ps.setString(5, fechaNacimiento);
                    ps.executeUpdate();
                    displayArea.setText("Paciente agregado exitosamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    displayArea.setText("Error al agregar paciente.");
                }
            } else {
                displayArea.setText("Por favor, complete todos los campos.");
            }
        }
    }

    // Método para ver los pacientes
    private void verPacientes() {
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Pacientes");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Nombre: ").append(rs.getString("nombre")).append("\n");
                sb.append("Apellido: ").append(rs.getString("apellido")).append("\n");
                sb.append("Direccion: ").append(rs.getString("direccion")).append("\n");
                sb.append("Telefono: ").append(rs.getString("telefono")).append("\n");
                sb.append("Fecha de Nacimiento: ").append(rs.getString("fechaNacimiento")).append("\n\n");
            }
            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener pacientes.");
        }
    }

    // Método para ver reportes
    private void verReportes() {
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Reportes");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Tipo: ").append(rs.getString("tipo")).append("\n");
                sb.append("Fecha de Generacion: ").append(rs.getString("fechaGeneracion")).append("\n");
                sb.append("Descripcion: ").append(rs.getString("descripcion")).append("\n\n");
            }
            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener reportes.");
        }
    }

    // Método para gestionar personal médico
    private void gestionarPersonal() {
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM PersonalMedico");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Nombre: ").append(rs.getString("nombre")).append("\n");
                sb.append("Apellido: ").append(rs.getString("apellido")).append("\n");
                sb.append("Especialidad: ").append(rs.getString("especialidad")).append("\n\n");
            }
            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener personal médico.");
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
        SwingUtilities.invokeLater(() -> new Administrador().setVisible(true));
    }
}
