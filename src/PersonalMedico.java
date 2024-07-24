import java.util.ArrayList;
import java.util.List;

public class PersonalMedico extends Usuario {
    private String especialidad;
    private String horarioDeTrabajo;
    private List<Cita> citas;
    private List<HistorialMedico> historialesMedicos;
    private List<ResultadoExamen> resultadosExamenes;
    private List<Tratamiento> tratamientos;

    public PersonalMedico(int id, String nombre, String apellido, String especialidad, String horarioDeTrabajo) {
        super(id, nombre, apellido, "personal médico");
        this.especialidad = especialidad;
        this.horarioDeTrabajo = horarioDeTrabajo;
        this.citas = new ArrayList<>();
        this.historialesMedicos = new ArrayList<>();
        this.resultadosExamenes = new ArrayList<>();
        this.tratamientos = new ArrayList<>();
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getHorarioDeTrabajo() {
        return horarioDeTrabajo;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public List<HistorialMedico> getHistorialesMedicos() {
        return historialesMedicos;
    }

    public List<ResultadoExamen> getResultadosExamenes() {
        return resultadosExamenes;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    @Override
    public void login() {
        System.out.println("Login exitoso como personal médico");
    }

    @Override
    public void logout() {
        System.out.println("Logout exitoso como personal médico");
    }

    public void registrarCita(Cita cita) {
        citas.add(cita);
        System.out.println("Cita registrada con éxito");
    }

    public void registrarHistorialMedico(HistorialMedico historialMedico) {
        historialesMedicos.add(historialMedico);
        System.out.println("Historial médico registrado con éxito");
    }

    public void registrarResultadoExamen(ResultadoExamen resultadoExamen) {
        resultadosExamenes.add(resultadoExamen);
        System.out.println("Resultado de examen registrado con éxito");
    }

    public void asignarTratamiento(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        System.out.println("Tratamiento asignado con éxito");
    }

    public void verCitas() {
        System.out.println("Citas programadas:");
        for (Cita cita : citas) {
            System.out.println(cita.getFecha() + " " + cita.getHora());
        }
    }

    public void verHistorialesMedicos() {
        System.out.println("Historiales médicos:");
        for (HistorialMedico historialMedico : historialesMedicos) {
            System.out.println(historialMedico.getPaciente().getNombre() + " " + historialMedico.getPaciente().getApellido());
        }
    }

    public void verResultadosExamenes() {
        System.out.println("Resultados de exámenes:");
        for (ResultadoExamen resultadoExamen : resultadosExamenes) {
            System.out.println(resultadoExamen.getPaciente().getNombre() + " " + resultadoExamen.getPaciente().getApellido());
        }
    }

    public void verTratamientos() {
        System.out.println("Tratamientos asignados:");
        for (Tratamiento tratamiento : tratamientos) {
            System.out.println(tratamiento.getPaciente().getNombre() + " " + tratamiento.getPaciente().getApellido());
        }
    }
}