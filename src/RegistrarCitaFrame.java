import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrarCitaFrame extends JFrame {
    private Usuario medico;

    private JTextField pacienteIdField;
    private JTextField fechaField;
    private JTextField horaField;
    private JComboBox<String> especialidadComboBox;

    public RegistrarCitaFrame(Usuario medico) {
        this.medico = medico;

        setTitle("Registrar Cita");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        add(panel);

        panel.add(new JLabel("Paciente ID:"));
        pacienteIdField = new JTextField();
        panel.add(pacienteIdField);

        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        fechaField = new JTextField();
        panel.add(fechaField);

        panel.add(new JLabel("Hora (HH:MM:SS):"));
        horaField = new JTextField();
        panel.add(horaField);

        panel.add(new JLabel("Especialidad:"));
        especialidadComboBox = new JComboBox<>(new String[]{"Cardiología", "Pediatría", "Dermatología", "Fisiatría"});
        panel.add(especialidadComboBox);

        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int pacienteId = Integer.parseInt(pacienteIdField.getText());
                    String fecha = fechaField.getText();
                    String hora = horaField.getText();
                    String especialidad = (String) especialidadComboBox.getSelectedItem();

                    Connection con = DatabaseConnection.getConnection();
                    String query = "INSERT INTO Citas (paciente_id, medico_id, fecha, hora, especialidad) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, pacienteId);
                    ps.setInt(2, medico.getId());
                    ps.setString(3, fecha);
                    ps.setString(4, hora);
                    ps.setString(5, especialidad);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cita registrada exitosamente.");
                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar la cita.");
                }
            }
        });
        panel.add(registrarButton);
    }
}
