import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cita extends JFrame {
    private Usuario medico;
    private JTextField campoEdad;
    private JButton botonRegistrar;
    private JComboBox<String> comboBox1;
    private ImageIcon buildingIcon;

    public Cita(Usuario medico) {
        this.medico = medico;

        loadIcons();
        setTitle("Registrar Cita - MediCare");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Panel de título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel buildingLabel = new JLabel(buildingIcon);
        titlePanel.add(buildingLabel);
        JLabel titleLabel = new JLabel("MediCare - Citas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField pacienteIdField = new JTextField();
        JTextField fechaField = new JTextField();
        JTextField horaField = new JTextField();
        campoEdad = new JTextField();
        JComboBox<String> especialidadComboBox = new JComboBox<>(new String[]{"Fisiatría", "Cardiología", "Otorrinolaringología", "Pediatría"});

        formPanel.add(new JLabel("ID del Paciente:"));
        formPanel.add(pacienteIdField);

        formPanel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        formPanel.add(fechaField);

        formPanel.add(new JLabel("Hora (HH:MM:SS):"));
        formPanel.add(horaField);

        formPanel.add(new JLabel("Especialidad:"));
        formPanel.add(especialidadComboBox);

        formPanel.add(new JLabel("Edad:"));
        formPanel.add(campoEdad);

        JButton registrarButton = new JButton("Registrar");
        formPanel.add(new JLabel(""));
        formPanel.add(registrarButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pacienteId = Integer.parseInt(pacienteIdField.getText());
                String fecha = fechaField.getText();
                String hora = horaField.getText();
                String especialidad = (String) especialidadComboBox.getSelectedItem();
                int edad = Integer.parseInt(campoEdad.getText());

                try (Connection con = DatabaseConnection.getConnection()) {
                    if (especialidad.equals("Pediatría") && edad >= 12) {
                        JOptionPane.showMessageDialog(null, "La especialidad Pediatría solo es válida para pacientes menores de 12 años.");
                        return;
                    }

                    String query = "INSERT INTO Citas (paciente_id, medico_id, fecha, hora, especialidad) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, pacienteId);
                    ps.setInt(2, medico.getId());
                    ps.setString(3, fecha);
                    ps.setString(4, hora);
                    ps.setString(5, especialidad);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cita registrada con éxito.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar la cita.");
                }
            }
        });
    }

    private void loadIcons() {
        buildingIcon = new ImageIcon(getClass().getResource("/img/edificio.png")); // Ajusta la ruta según sea necesario
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario medico = new Usuario(2, "medico", "medico123", "medico");
            new Cita(medico).setVisible(true);
        });
    }
}
