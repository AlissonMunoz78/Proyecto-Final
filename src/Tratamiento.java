public class Tratamiento {
    private int id;
    private Paciente paciente;
    private String tratamiento;

    public Tratamiento(int id, Paciente paciente, String tratamiento) {
        this.id = id;
        this.paciente = paciente;
        this.tratamiento = tratamiento;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void asignarTratamiento() {
        // Implementar lógica para asignar un tratamiento
    }

    public void verTratamiento() {
        // Implementar lógica para ver un tratamiento
    }
}