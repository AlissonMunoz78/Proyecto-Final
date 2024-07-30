import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class Administrador extends JFrame{
    private JButton registrarPacienteButton;
    private JButton buscarPacienteButton;
    private JButton eliminarPacienteButton;
    private JButton salirButton;
    private JButton actualizarPacienteButton;
    private JPanel menu;

    public Administrador() {
        setTitle("Menu");
        setSize(650,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu = new JPanel(new GridLayout(4, 3, 20, 20));
        menu.setBackground(Color.GRAY);
        menu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Iconos
        ImageIcon hospitalIcon = new ImageIcon("img/edificio.png"); // Reemplaza con la ruta de tu imagen
        ImageIcon registrarIcon = new ImageIcon("img/agregar.png"); // Reemplaza con la ruta de tu imagen
        ImageIcon buscarIcon = new ImageIcon("img/busqueda.png"); // Reemplaza con la ruta de tu imagen
        ImageIcon eliminarIcon = new ImageIcon("img/medico.png"); // Reemplaza con la ruta de tu imagen
        ImageIcon reportesIcon = new ImageIcon("img/reportes.png"); // Reemplaza con la ruta de tu imagen

        // Botones
        registrarPacienteButton = new JButton("Registrar Paciente", registrarIcon);
        registrarPacienteButton.setHorizontalTextPosition(JButton.RIGHT);
        registrarPacienteButton.setVerticalTextPosition(JButton.CENTER);
        buscarPacienteButton = new JButton("Buscar Paciente", buscarIcon);
        buscarPacienteButton.setHorizontalTextPosition(JButton.RIGHT);
        buscarPacienteButton.setVerticalTextPosition(JButton.CENTER);
        eliminarPacienteButton = new JButton("Reportes", reportesIcon);
        eliminarPacienteButton.setHorizontalTextPosition(JButton.RIGHT);
        eliminarPacienteButton.setVerticalTextPosition(JButton.CENTER);
        actualizarPacienteButton = new JButton("Buscar Médico");
        salirButton = new JButton("Salir");

        // Agregar botones al panel
        menu.add(new JLabel(hospitalIcon));
        menu.add(new JLabel());
        menu.add(new JLabel());

        menu.add(registrarPacienteButton);
        menu.add(new JLabel());
        menu.add(buscarPacienteButton);

        menu.add(actualizarPacienteButton);
        menu.add(new JLabel());
        menu.add(eliminarPacienteButton);

        menu.add(new JLabel());
        menu.add(new JLabel());
        menu.add(salirButton);

        setContentPane(menu);

        registrarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registrarPaciente().setVisible(true);
                dispose();
            }
        });
        actualizarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buscarmedico().setVisible(true);
            }
        });
        buscarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buscarpaciente().setVisible(true);
            }
        });
        eliminarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Reportes2().setVisible(true);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}