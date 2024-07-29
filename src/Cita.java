import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Cita extends JFrame {
    private Usuario medico;

    public Cita(Usuario medico) {
        this.medico = medico;

        setTitle("Registrar Cita - MediCare");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField pacienteIdField = new JTextField();
        JTextField fechaField = new JTextField();
        JTextField horaField = new JTextField();
        JTextField especialidadField = new JTextField();

        panel.add(new JLabel("ID del Paciente:"));
        panel.add(pacienteIdField);

        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(fechaField);

        panel.add(new JLabel("Hora (HH:MM:SS):"));
        panel.add(horaField);

        panel.add(new JLabel("Especialidad:"));
        panel.add(especialidadField);

        JButton registrarButton = new JButton("Registrar");
        panel.add(new JLabel(""));
        panel.add(registrarButton);

        add(panel);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pacienteId = Integer.parseInt(pacienteIdField.getText());
                String fecha = fechaField.getText();
                String hora = horaField.getText();
                String especialidad = especialidadField.getText();

                try (Connection con = DatabaseConnection.getConnection()) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario medico = new Usuario(2, "medico", "medico123", "medico");
            new Cita(medico).setVisible(true);
        });
    }
}
