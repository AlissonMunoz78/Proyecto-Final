import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Paciente extends JFrame {
    private JButton viewInfoButton;
    private JButton viewAppointmentsButton;
    private JButton viewMedicalHistoryButton;
    private JButton viewExamResultsButton;
    private JButton viewTreatmentsButton;
    private JTextArea displayArea;

    public Paciente() {
        // Configuración de la ventana principal
        setTitle("Paciente - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        viewInfoButton = new JButton("Ver Información");
        viewAppointmentsButton = new JButton("Ver Citas");
        viewMedicalHistoryButton = new JButton("Ver Historial Médico");
        viewExamResultsButton = new JButton("Ver Resultados Examen");
        viewTreatmentsButton = new JButton("Ver Tratamientos");

        buttonPanel.add(viewInfoButton);
        buttonPanel.add(viewAppointmentsButton);
        buttonPanel.add(viewMedicalHistoryButton);
        buttonPanel.add(viewExamResultsButton);
        buttonPanel.add(viewTreatmentsButton);

        // Área de visualización
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Agregar paneles al marco
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Acciones de los botones
        viewInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verInformacion();
            }
        });

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCitas();
            }
        });

        viewMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialMedico();
            }
        });

        viewExamResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verResultadosExamen();
            }
        });

        viewTreatmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verTratamientos();
            }
        });
    }

    // Método para ver la información del paciente
    private void verInformacion() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Pacientes WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId()); // Asumiendo que tienes una forma de obtener el ID del paciente actual
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Nombre: ").append(rs.getString("nombre")).append("\n");
                sb.append("Apellido: ").append(rs.getString("apellido")).append("\n");
                sb.append("Direccion: ").append(rs.getString("direccion")).append("\n");
                sb.append("Telefono: ").append(rs.getString("telefono")).append("\n");
                sb.append("Fecha de Nacimiento: ").append(rs.getString("fechaNacimiento")).append("\n");

                displayArea.setText(sb.toString());
            } else {
                displayArea.setText("No se encontró la información del paciente.");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener la información del paciente.");
        }
    }

    // Método para ver las citas del paciente
    private void verCitas() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Citas WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId()); // Asumiendo que tienes una forma de obtener el ID del paciente actual
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Cita: ").append(rs.getInt("id")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n");
                sb.append("Especialidad: ").append(rs.getString("especialidad")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener las citas del paciente.");
        }
    }

    // Método para ver el historial médico del paciente
    private void verHistorialMedico() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM HistorialesMedicos WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId()); // Asumiendo que tienes una forma de obtener el ID del paciente actual
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Historial: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener el historial médico del paciente.");
        }
    }

    // Método para ver los resultados de exámenes del paciente
    private void verResultadosExamen() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM ResultadosExamenes WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId()); // Asumiendo que tienes una forma de obtener el ID del paciente actual
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Resultado: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Documento: ").append(rs.getString("documento")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los resultados de exámenes del paciente.");
        }
    }

    // Método para ver los tratamientos del paciente
    private void verTratamientos() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Tratamientos WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId()); // Asumiendo que tienes una forma de obtener el ID del paciente actual
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Tratamiento: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Medicamento: ").append(rs.getString("medicamento")).append("\n");
                sb.append("Fecha Inicio: ").append(rs.getString("fechaInicio")).append("\n");
                sb.append("Fecha Fin: ").append(rs.getString("fechaFin")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los tratamientos del paciente.");
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

    // Método para obtener el ID del paciente (este es solo un ejemplo)
    private int getPacienteId() {
        // Aquí deberías implementar la lógica para obtener el ID del paciente actual
        // Por ejemplo, podrías solicitarlo al inicio de sesión o mantenerlo en una variable global
        return 1; // Asumiendo que el ID del paciente es 1 para este ejemplo
    }

    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de despacho de eventos
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Paciente().setVisible(true);
            }
        });
    }
}
