import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrador2 extends JFrame{
    private JButton registrarPacienteButton;
    private JButton buscarPacienteButton;
    private JButton eliminarPacienteButton;
    private JButton salirButton;
    private JButton actualizarPacienteButton;
    private JPanel menu;

    public Administrador2() {
        setTitle("Menu");
        setSize(650,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
