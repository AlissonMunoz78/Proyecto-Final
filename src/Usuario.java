public abstract class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String rol;

    public Usuario(int id, String nombre, String apellido, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getRol() {
        return rol;
    }

    public abstract void login();

    public abstract void logout();
}