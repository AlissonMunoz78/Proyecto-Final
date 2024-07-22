package org.example;

public class Paciente {
    private String nombre;
    private String apellido;
    private String cedula;
    private HistorialMedico historialMedico;

    public Paciente(String nombre, String apellido, String cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.historialMedico = new HistorialMedico();
    }
}