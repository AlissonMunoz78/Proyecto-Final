import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultadoExamen extends JFrame {
    private JButton registerExamResultButton;
    private JButton viewExamResultsButton;
    private JTextArea displayArea;

    public ResultadoExamen() {
        // Configuración de la ventana principal
        setTitle("Gestión de Resultados de Exámenes - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        registerExamResultButton = new JButton("Registrar Resultado de Examen");
        viewExamResultsButton = new JButton("Ver Resultados de Examen");

        buttonPanel.add(registerExamResultButton);
        buttonPanel.add(viewExamResultsButton);

        // Área de visualización
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Agregar paneles al marco
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Acciones de los botones
        registerExamResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarResultadoExamen();
            }
        });

        viewExamResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verResultadosExamen();
            }
        });
    }

    // Método para registrar un nuevo resultado de examen
    private void registrarResultadoExamen() {
        JTextField patientIdField = new JTextField();
        JTextField examNameField = new JTextField();
        JTextField resultField = new JTextField();
        JTextField doctorIdField = new JTextField();

        Object[] fields = {
                "ID del Paciente:", patientIdField,
                "Nombre del Examen:", examNameField,
                "Resultado:", resultField,
                "ID del Médico:", doctorIdField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Registrar Resultado de Examen", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int patientId = Integer.parseInt(patientIdField.getText());
            String examName = examNameField.getText();
            String result = resultField.getText();
            int doctorId = Integer.parseInt(doctorIdField.getText());

            try {
                Connection con = getConnection();
                String query = "INSERT INTO ResultadosExamenes (pacienteId, nombreExamen, resultado, medicoId, fecha) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, patientId);
                ps.setString(2, examName);
                ps.setString(3, result);
                ps.setInt(4, doctorId);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Resultado de examen registrado con éxito.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar el resultado de examen.");
            }
        }
    }

    // Método para ver los resultados de exámenes
    private void verResultadosExamen() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM ResultadosExamenes";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Resultado: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Nombre del Examen: ").append(rs.getString("nombreExamen")).append("\n");
                sb.append("Resultado: ").append(rs.getString("resultado")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los resultados de exámenes.");
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
                new ResultadoExamen().setVisible(true);
            }
        });
    }
}
