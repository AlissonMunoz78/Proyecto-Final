import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Reporte extends JFrame {
    private JButton viewAvailabilityButton;
    private JTextArea displayArea;

    public Reporte() {
        // Configuración de la ventana principal
        setTitle("Reportes Estadísticos - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        viewAvailabilityButton = new JButton("Ver Disponibilidad de Médicos");

        buttonPanel.add(viewAvailabilityButton);

        // Área de visualización
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Agregar paneles al marco
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Acciones de los botones
        viewAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDisponibilidadMedicos();
            }
        });
    }

    // Método para ver la disponibilidad de los médicos
    private void verDisponibilidadMedicos() {
        try {
            Connection con = getConnection();
            String query = "SELECT m.id AS MedicoID, m.nombre AS NombreMedico, e.especialidad AS Especialidad, " +
                    "(SELECT COUNT(*) FROM Citas c WHERE c.medicoId = m.id AND c.fecha >= CURDATE()) AS CitasPendientes " +
                    "FROM Medicos m " +
                    "JOIN Especialidades e ON m.especialidadId = e.id";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Médico: ").append(rs.getInt("MedicoID")).append("\n");
                sb.append("Nombre: ").append(rs.getString("NombreMedico")).append("\n");
                sb.append("Especialidad: ").append(rs.getString("Especialidad")).append("\n");
                sb.append("Citas Pendientes: ").append(rs.getInt("CitasPendientes")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener la disponibilidad de los médicos.");
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
                new Reporte().setVisible(true);
            }
        });
    }
}
