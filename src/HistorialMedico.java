import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private Paciente paciente;
    private ArrayList<String> historial;
    private static final ArrayList<String> especialidades = new ArrayList<>(List.of("General", "Odontólogo", "Ginecólogo", "Traumatólogo", "Pediatría", "Psicología", "Cardiólogo"));

    public HistorialMedico(Paciente paciente) {
        this.paciente = paciente;
        this.historial = new ArrayList<>();
    }

    public void agregarRegistro(String especialidad, String registro) {
        if (!especialidades.contains(especialidad)) {
            throw new IllegalArgumentException("Especialidad no válida.");
        }

        if (especialidad.equals("Pediatría") && paciente.getEdad() >= 12) {
            throw new IllegalArgumentException("Pediatría solo apta para niños menores de 12 años.");
        }

        historial.add("Especialidad: " + especialidad + " - " + registro);
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public ArrayList<String> getHistorial() {
        return historial;
    }
}
