public class Administrador extends Usuario {
    public Administrador(String nombreUsuario, String contraseña) {
        super(nombreUsuario, contraseña);
    }

    public void gestionarPersonalMedico(PersonalMedico personal) {
        // Implementación para gestionar personal médico
    }

    public void verReportesEstadisticos() {
        // Implementación para ver reportes estadísticos
    }

    public void agregarPaciente(Paciente paciente) {
        // Implementación para agregar pacientes
    }

    public void gestionarPacientes(Paciente paciente) {
        // Implementación para gestionar pacientes
    }

    @Override
    public void mostrarOpciones() {
        System.out.println("Opciones del Administrador:");
        System.out.println("1. Gestionar personal médico");
        System.out.println("2. Ver reportes estadísticos");
        System.out.println("3. Agregar paciente");
        System.out.println("4. Gestionar pacientes");
    }
}
