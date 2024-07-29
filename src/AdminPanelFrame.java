import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanelFrame extends JFrame {
    public AdminPanelFrame() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        add(panel);

        JButton gestionarMedicosButton = new JButton("Gestionar Médicos");
        gestionarMedicosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionarMedicosFrame().setVisible(true);
            }
        });
        panel.add(gestionarMedicosButton);

        JButton reportesButton = new JButton("Reportes Estadísticos");
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportesEstadisticosFrame().setVisible(true);
            }
        });
        panel.add(reportesButton);

        JButton agregarPacienteButton = new JButton("Agregar Paciente");
        agregarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarPacienteFrame().setVisible(true);
            }
        });
        panel.add(agregarPacienteButton);

        JButton gestionarPacientesButton = new JButton("Gestionar Pacientes");
        gestionarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionarPacientesFrame().setVisible(true);
            }
        });
        panel.add(gestionarPacientesButton);
    }
}
