import java.util.ArrayList;

public class PersonalMedico extends Usuario {
    private ArrayList<Cita> citas;
    private ArrayList<HistorialMedico> historialesMedicos;
    private ArrayList<ResultadoExamen> resultadosExamenes;
    private ArrayList<Tratamiento> tratamientos;

    public PersonalMedico(String nombreUsuario, String contraseña) {
        super(nombreUsuario, contraseña);
        this.citas = new ArrayList<>();
        this.historialesMedicos = new ArrayList<>();
        this.resultadosExamenes = new ArrayList<>();
        this.tratamientos = new ArrayList<>();
    }

    public void registrarCita(Cita cita) {
        citas.add(cita);
        System.out.println("Cita registrada: " + cita.getPaciente().getNombre() + " con " + cita.getPersonalMedico().getNombreUsuario());
    }

    public void registrarHistorialMedico(HistorialMedico historial) {
        historialesMedicos.add(historial);
        System.out.println("Historial médico registrado para: " + historial.getPaciente().getNombre());
    }

    public void registrarResultadoExamen(ResultadoExamen resultado) {
        resultadosExamenes.add(resultado);
        System.out.println("Resultado de examen registrado para: " + resultado.getPaciente().getNombre());
    }

    public void asignarTratamiento(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        System.out.println("Tratamiento asignado para: " + tratamiento.getPaciente().getNombre());
    }

    @Override
    public void mostrarOpciones() {
        System.out.println("Opciones del Personal Médico:");
        System.out.println("1. Registrar cita");
        System.out.println("2. Registrar historial médico");
        System.out.println("3. Registrar resultado de examen");
        System.out.println("4. Asignar tratamiento");
    }

    // Métodos adicionales para acceder a las listas si es necesario
    public ArrayList<Cita> getCitas() {
        return citas;
    }

    public ArrayList<HistorialMedico> getHistorialesMedicos() {
        return historialesMedicos;
    }

    public ArrayList<ResultadoExamen> getResultadosExamenes() {
        return resultadosExamenes;
    }

    public ArrayList<Tratamiento> getTratamientos() {
        return tratamientos;
    }
}
