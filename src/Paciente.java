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
        setTitle("Paciente - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

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

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

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

    private void verInformacion() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Pacientes WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId());
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

    private void verCitas() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Citas WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId());
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

    private void verHistorialMedico() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM HistorialesMedicos WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId());
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

    private void verResultadosExamen() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM ResultadosExamenes WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId());
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

    private void verTratamientos() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Tratamientos WHERE pacienteId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getPacienteId());
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

    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    private int getPacienteId() {
        // Debería devolver el ID del paciente autenticado
        // Esto es un ejemplo, cambiar según la implementación
        return 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Paciente().setVisible(true);
        });
    }
}
