import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class PersonalMedico extends JFrame {
    private Usuario medico;

    private JButton registerAppointmentButton;
    private JButton viewMedicalHistoryButton;
    private JButton viewExamResultsButton;
    private JButton tratamientoButton;
    private JButton salirButton;

    private ImageIcon appointmentIcon;
    private ImageIcon historyIcon;
    private ImageIcon resultsIcon;
    private ImageIcon treatmentIcon;
    private ImageIcon exitIcon;
    private ImageIcon buildingIcon;

    public PersonalMedico(Usuario medico) {
        this.medico = medico;

        loadIcons();
        setTitle("MediCare - Médico - " + medico.getNombre());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        add(panel);

        // Título con imagen de edificio a la izquierda
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel buildingLabel = new JLabel(buildingIcon);
        titlePanel.add(buildingLabel);
        JLabel titleLabel = new JLabel("MediCare - Médico");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        panel.add(titlePanel, gbc);

        // Registrar Cita
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        registerAppointmentButton = new JButton("Registrar Cita", appointmentIcon);
        registerAppointmentButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        setButtonSize(registerAppointmentButton);
        panel.add(registerAppointmentButton, gbc);

        // Historial Médico
        gbc.gridy = 2;
        viewMedicalHistoryButton = new JButton("Historial Médico", historyIcon);
        viewMedicalHistoryButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        setButtonSize(viewMedicalHistoryButton);
        panel.add(viewMedicalHistoryButton, gbc);

        // Resultados de Exámenes
        gbc.gridy = 3;
        viewExamResultsButton = new JButton("Resultados de Exámenes", resultsIcon);
        viewExamResultsButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        setButtonSize(viewExamResultsButton);
        panel.add(viewExamResultsButton, gbc);

        // Tratamiento
        gbc.gridy = 4;
        tratamientoButton = new JButton("Tratamiento", treatmentIcon);
        tratamientoButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        setButtonSize(tratamientoButton);
        panel.add(tratamientoButton, gbc);

        // Salir
        gbc.gridy = 5;
        salirButton = new JButton("Salir", exitIcon);
        salirButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        setButtonSize(salirButton);
        panel.add(salirButton, gbc);

        // Configuración de los botones
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

        tratamientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón de tratamiento
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
            }
        });
    }

    private void loadIcons() {
        appointmentIcon = loadImage("/img/cita.png", 24, 24);
        historyIcon = loadImage("/img/historia.png", 24, 24);
        resultsIcon = loadImage("/img/examenes.png", 24, 24);
        treatmentIcon = loadImage("/img/capsulas.png", 24, 24);
        exitIcon = loadImage("/img/exit.png", 24, 24);
        buildingIcon = loadImage("/img/edificio.png", 32, 32); // Ajusta el tamaño según sea necesario
    }

    private ImageIcon loadImage(String path, int width, int height) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el archivo: " + path);
            return null;
        }
    }

    private void setButtonSize(JButton button) {
        button.setPreferredSize(new Dimension(200, 40)); // Tamaño estándar para todos los botones
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario medico = new Usuario(2, "medico", "medico123", "medico");
            new PersonalMedico(medico).setVisible(true);
        });
    }
}
