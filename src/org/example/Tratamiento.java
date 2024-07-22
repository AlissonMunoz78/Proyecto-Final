package org.example;

public class Tratamiento {
    private String nombreTratamiento;
    private String descripcion;

    public Tratamiento(String nombreTratamiento, String descripcion) {
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}