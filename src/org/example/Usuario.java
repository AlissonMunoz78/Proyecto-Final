package org.example;

public abstract class Usuario {
    protected String nombre;
    protected String apellido;
    protected String idUsuario;
    protected String password;

    public Usuario(String nombre, String apellido, String idUsuario, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public abstract void mostrarMenu();
}