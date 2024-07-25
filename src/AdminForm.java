import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminForm {
    private JFrame frame;

    public AdminForm() {
        frame = new JFrame("Administrador");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton verMedicosButton = new JButton("Ver Médicos");
        verMedicosButton.setBounds(50, 50, 150, 30);
        frame.add(verMedicosButton);

        JButton verReportesButton = new JButton("Ver Reportes");
        verReportesButton.setBounds(200, 50, 150, 30);
        frame.add(verReportesButton);

        JButton asignarPacientesButton = new JButton("Asignar Pacientes");
        asignarPacientesButton.setBounds(50, 100, 150, 30);
        frame.add(asignarPacientesButton);

        // Aquí se pueden agregar ActionListeners para manejar los eventos de los botones
        verMedicosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para ver médicos
            }
        });

        verReportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para ver reportes
            }
        });

        asignarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para asignar pacientes
            }
        });

        frame.setVisible(true);
    }
}
