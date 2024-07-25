import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicoForm {
    private JFrame frame;

    public MedicoForm() {
        frame = new JFrame("Personal Médico");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton registrarCitasButton = new JButton("Registrar Citas");
        registrarCitasButton.setBounds(50, 50, 150, 30);
        frame.add(registrarCitasButton);

        JButton historialMedicoButton = new JButton("Historial Médico");
        historialMedicoButton.setBounds(200, 50, 150, 30);
        frame.add(historialMedicoButton);

        JButton resultadosExamenesButton = new JButton("Resultados Exámenes");
        resultadosExamenesButton.setBounds(50, 100, 150, 30);
        frame.add(resultadosExamenesButton);

        JButton tratamientosButton = new JButton("Tratamientos y Medicamentos");
        tratamientosButton.setBounds(200, 100, 150, 30);
        frame.add(tratamientosButton);

        registrarCitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrarCitasForm();
            }
        });

        historialMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para historial médico
            }
        });

        resultadosExamenesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para resultados de exámenes
            }
        });

        tratamientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para tratamientos y medicamentos
            }
        });

        frame.setVisible(true);
    }
}
