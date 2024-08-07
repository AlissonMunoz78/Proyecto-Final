import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Tratamiento extends JFrame {
    private JTextField busquedanombre;
    private JButton salirButton;
    private JButton buscarButton;
    private JPanel paneltratamiento;
    private JLabel npaciente;
    private JLabel tratamiento;
    private JPanel receta;
    private JFrame ventanaAnterior;

    public Tratamiento(JFrame ventanaAnterior) {
        super("Tratamiento");
        this.ventanaAnterior = ventanaAnterior;

        // Configuración de la ventana
        setContentPane(paneltratamiento);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setVisible(true);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTratamientos();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarVentanaAnterior();
            }
        });
    }

    private void buscarTratamientos() {
        String nombrePaciente = busquedanombre.getText().trim();

        // Verificar si el campo está vacío
        if (nombrePaciente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del paciente.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder resultadosTexto = new StringBuilder();

        try {
            conn = conexion_base();
            // Consulta para obtener el paciente_id
            String sqlPaciente = "SELECT id FROM Pacientes WHERE nombres = ?";
            stmt = conn.prepareStatement(sqlPaciente);
            stmt.setString(1, nombrePaciente);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int pacienteId = rs.getInt("id");

                // Limpiar el área de texto antes de agregar nuevos resultados
                resultadosTexto.setLength(0);

                // Consulta para obtener los tratamientos del paciente por paciente_id
                String sqlTratamientos = "SELECT fecha, tratamiento, medicamento " +
                        "FROM Tratamientos " +
                        "WHERE paciente_id = ?";
                stmt = conn.prepareStatement(sqlTratamientos);
                stmt.setInt(1, pacienteId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String fecha = rs.getString("fecha");
                    String tratamientoDescripcion = rs.getString("tratamiento");
                    String medicamento = rs.getString("medicamento");
                    resultadosTexto.append("Fecha: ").append(fecha).append("\n");
                    resultadosTexto.append("Tratamiento: ").append(tratamientoDescripcion).append("\n");
                    resultadosTexto.append("Medicamento: ").append(medicamento).append("\n\n");
                }

                if (resultadosTexto.length() == 0) {
                    resultadosTexto.append("No se encontraron tratamientos.");
                }

                // Mostrar los resultados en un área de texto o etiqueta
                JTextArea resultadosArea = new JTextArea();
                resultadosArea.setText(resultadosTexto.toString());
                resultadosArea.setEditable(false); // No editable
                JScrollPane scrollPane = new JScrollPane(resultadosArea);
                receta.removeAll(); // Limpiar panel de resultados
                receta.setLayout(new BoxLayout(receta, BoxLayout.Y_AXIS)); // Establecer el layout
                receta.add(scrollPane); // Agregar nuevo contenido
                receta.revalidate(); // Revalidar panel
                receta.repaint(); // Repintar panel

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el paciente con el nombre ingresado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar los tratamientos: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    private void regresarVentanaAnterior() {
        if (ventanaAnterior != null) {
            ventanaAnterior.setVisible(true); // Mostrar la ventana anterior
        }
        dispose(); // Cierra esta ventana
    }

    public static void main(String[] args) {
        // Esta línea es opcional y se usa si quieres ejecutar Tratamiento directamente
        new Tratamiento(null);
    }
}
