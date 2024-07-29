import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrador extends JFrame {
    private JButton viewDoctorsButton;
    private JButton viewReportsButton;

    public Administrador() {
        setTitle("Panel de Administrador - MediCare");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        viewDoctorsButton = new JButton("Ver Médicos");
        viewReportsButton = new JButton("Ver Reportes");

        panel.add(viewDoctorsButton);
        panel.add(viewReportsButton);

        add(panel);

        viewDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PersonalMedico(null).setVisible(true);
            }
        });

        viewReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Reporte().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Administrador().setVisible(true));
    }
}
