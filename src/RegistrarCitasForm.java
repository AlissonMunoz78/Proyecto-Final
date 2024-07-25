import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarCitasForm {
    private JFrame frame;
    private JComboBox<String> especialidadComboBox;
    private JComboBox<String> diaComboBox;
    private JComboBox<String> doctorComboBox;

    private Map<String, ArrayList<String>> doctoresPorEspecialidad;

    public RegistrarCitasForm() {
        frame = new JFrame("Registrar Citas");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel especialidadLabel = new JLabel("Especialidad:");
        especialidadLabel.setBounds(10, 20, 100, 25);
        frame.add(especialidadLabel);

        especialidadComboBox = new JComboBox<>(new String[]{"General", "Odontólogo", "Ginecólogo", "Traumatólogo", "Pediatría", "Psicología", "Cardiólogo"});
        especialidadComboBox.setBounds(120, 20, 200, 25);
        frame.add(especialidadComboBox);

        JLabel diaLabel = new JLabel("Día:");
        diaLabel.setBounds(10, 60, 100, 25);
        frame.add(diaLabel);

        diaComboBox = new JComboBox<>(new String[]{"2024-07-25", "2024-07-26", "2024-07-27"});
        diaComboBox.setBounds(120, 60, 200, 25);
        frame.add(diaComboBox);

        JLabel doctorLabel = new JLabel("Doctor:");
        doctorLabel.setBounds(10, 100, 100, 25);
        frame.add(doctorLabel);

        doctorComboBox = new JComboBox<>(new String[]{});
        doctorComboBox.setBounds(120, 100, 200, 25);
        frame.add(doctorComboBox);

        JButton registrarButton = new JButton("Registrar Cita");
        registrarButton.setBounds(120, 140, 150, 30);
        frame.add(registrarButton);

        // Inicializar los doctores por especialidad
        inicializarDoctores();

        // Actualizar doctores al seleccionar especialidad
        especialidadComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String especialidadSeleccionada = (String) especialidadComboBox.getSelectedItem();
                ArrayList<String> doctores = doctoresPorEspecialidad.get(especialidadSeleccionada);
                doctorComboBox.setModel(new DefaultComboBoxModel<>(doctores.toArray(new String[0])));
            }
        });

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String especialidad = (String) especialidadComboBox.getSelectedItem();
                String dia = (String) diaComboBox.getSelectedItem();
                String doctor = (String) doctorComboBox.getSelectedItem();
                // Lógica para registrar la cita
                JOptionPane.showMessageDialog(frame, "Cita registrada para el día " + dia + " con el doctor " + doctor + " en especialidad " + especialidad);
                frame.dispose(); // Cerrar la ventana
            }
        });

        frame.setVisible(true);
    }

    private void inicializarDoctores() {
        doctoresPorEspecialidad = new HashMap<>();
        doctoresPorEspecialidad.put("General", new ArrayList<>(List.of("Dr. Pérez", "Dr. González")));
        doctoresPorEspecialidad.put("Odontólogo", new ArrayList<>(List.of("Dr. Martínez", "Dr. López")));
        doctoresPorEspecialidad.put("Ginecólogo", new ArrayList<>(List.of("Dr. Ruiz", "Dr. Fernández")));
        doctoresPorEspecialidad.put("Traumatólogo", new ArrayList<>(List.of("Dr. Hernández", "Dr. Ramírez")));
        doctoresPorEspecialidad.put("Pediatría", new ArrayList<>(List.of("Dr. Castillo", "Dr. Morales")));
        doctoresPorEspecialidad.put("Psicología", new ArrayList<>(List.of("Dr. Vargas", "Dr. Ortega")));
        doctoresPorEspecialidad.put("Cardiólogo", new ArrayList<>(List.of("Dr. Romero", "Dr. Castro")));
    }
}
