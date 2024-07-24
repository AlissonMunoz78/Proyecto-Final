
public class Administrador extends Usuario {
    private String departamento;

    public Administrador(int id, String nombre, String apellido) {
        super(id, nombre, apellido, "administrador");
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public void login() {
        // Implementar lógica de login para administrador
    }
}