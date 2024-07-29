import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicoPanelFrame extends JFrame {
    private Usuario medico;

    public MedicoPanelFrame(Usuario medico) {
        this.medico = medico;

        setTitle("Medico Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        add(panel);

        JButton registrarCitaButton = new JButton("Registrar Cita");
        registrarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrarCitaFrame(medico).setVisible(true);
            }
        });
        panel.add(registrarCitaButton);

        JButton historialMedicoButton = new JButton("Historial Médico");
        historialMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistorialMedicoFrame(medico).setVisible(true);
            }
        });
        panel.add(historialMedicoButton);

        JButton resultadoExamenButton = new JButton("Resultado de Exámenes");
        resultadoExamenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultadoExamenFrame(medico).setVisible(true);
            }
        });
        panel.add(resultadoExamenButton);

        JButton tratamientoButton = new JButton("Tratamiento");
        tratamientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TratamientoFrame(medico).setVisible(true);
            }
        });
        panel.add(tratamientoButton);
    }
}
