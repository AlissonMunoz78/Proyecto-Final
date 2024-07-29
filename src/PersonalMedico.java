import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalMedico extends JFrame {
    private Usuario medico;

    private JButton registerAppointmentButton;
    private JButton viewMedicalHistoryButton;
    private JButton viewExamResultsButton;

    public PersonalMedico(Usuario medico) {
        this.medico = medico;

        setTitle("Panel de Médico - MediCare");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        registerAppointmentButton = new JButton("Registrar Cita");
        viewMedicalHistoryButton = new JButton("Ver Historial Médico");
        viewExamResultsButton = new JButton("Ver Resultados de Exámenes");

        panel.add(registerAppointmentButton);
        panel.add(viewMedicalHistoryButton);
        panel.add(viewExamResultsButton);

        add(panel);

        registerAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Cita(medico).setVisible(true);
            }
        });

        viewMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistorialMedico(medico).setVisible(true);
            }
        });

        viewExamResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultadoExamen().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario medico = new Usuario(2, "medico", "medico123", "medico");
            new PersonalMedico(medico).setVisible(true);
        });
    }
}
