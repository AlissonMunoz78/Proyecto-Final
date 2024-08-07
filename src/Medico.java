import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Medico extends JFrame {
    private JButton rCita;
    private JButton hMedico;
    private JButton tratamiento;
    private JButton salir;
    private JButton rExamenes;
    private JPanel panelmedico;

    public Medico() {
        super("Medico");
        setContentPane(panelmedico);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        rCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana RegistrarCita y cierra la ventana actual
                new RegistrarCita(Medico.this);
            }
        });

        hMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana HistorialMedico
                new HistorialMedico();
            }
        });

        rExamenes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultadoExamenes(Medico.this);
            }
        });

        tratamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana Tratamiento y pasa la referencia de la ventana actual
                new Tratamiento(Medico.this);
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra la ventana actual
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new Medico();
    }
}
