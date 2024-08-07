import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ResultadoExamenes extends JFrame {
    private JTextField npaciente;
    private JButton buscarButton;
    private JButton salirButton;
    private JPanel panelresultados;
    private JTextArea resultados; // Cambiado a JTextArea si era necesario
    private JFrame ventanaAnterior;

    public ResultadoExamenes(JFrame ventanaAnterior) {
        super("Resultado de Exámenes");
        this.ventanaAnterior = ventanaAnterior;

        // Inicializar y configurar los componentes
        npaciente = new JTextField(20);
        buscarButton = new JButton("Buscar");
        salirButton = new JButton("Salir");
        resultados = new JTextArea(15, 50); // Asegúrate de que esto coincida con el diseño de tu formulario
        resultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultados);
        panelresultados = new JPanel();
        panelresultados.add(new JLabel("Nombre del Paciente:"));
        panelresultados.add(npaciente);
        panelresultados.add(buscarButton);
        panelresultados.add(scrollPane); // Agregar el JScrollPane al panel
        panelresultados.add(salirButton);

        setContentPane(panelresultados);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setVisible(true);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarResultados();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarVentanaAnterior();
            }
        });
    }

    private void buscarResultados() {
        String nombres = npaciente.getText().trim();

        // Verificar si el campo está vacío
        if (nombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del paciente.");
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
            stmt.setString(1, nombres);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int pacienteId = rs.getInt("id");

                // Limpiar el área de texto antes de agregar nuevos resultados
                resultadosTexto.setLength(0);

                // Consulta para obtener los resultados de exámenes del paciente por paciente_id
                String sqlResultados = "SELECT d.nombres AS doctor_nombre, r.fecha AS fecha, r.examen AS examen, r.resultado AS resultado " +
                        "FROM ResultadosExamen r " +
                        "JOIN Medicos d ON r.medico_id = d.id " +
                        "WHERE r.paciente_id = ?";
                stmt = conn.prepareStatement(sqlResultados);
                stmt.setInt(1, pacienteId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String doctorNombre = rs.getString("doctor_nombre");
                    String fecha = rs.getString("fecha");
                    String examen = rs.getString("examen");
                    String resultadoDescripcion = rs.getString("resultado");
                    resultadosTexto.append("Doctor: ").append(doctorNombre).append("\n");
                    resultadosTexto.append("Fecha: ").append(fecha).append("\n");
                    resultadosTexto.append("Examen: ").append(examen).append("\n");
                    resultadosTexto.append("Resultado: ").append(resultadoDescripcion).append("\n\n");
                }

                if (resultadosTexto.length() == 0) {
                    resultadosTexto.append("No se encontraron resultados.");
                }

                // Mostrar los resultados en el área de texto
                resultados.setText(resultadosTexto.toString());

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el paciente con el nombre ingresado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar los resultados de exámenes: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cerrar la conexión: " + ex.getMessage());
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
        // Esta línea es opcional y se usa si quieres ejecutar ResultadoExamenes directamente
        new ResultadoExamenes(null);
    }
}
