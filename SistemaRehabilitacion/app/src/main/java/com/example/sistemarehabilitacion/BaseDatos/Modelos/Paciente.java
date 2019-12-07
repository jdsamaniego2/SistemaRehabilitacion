package com.example.sistemarehabilitacion.BaseDatos.Modelos;

import java.io.Serializable;

public class Paciente implements Serializable {
    private  int id;
    private  String nombre;
    private  String apellido;
    private  String cedula;
    private  String nacimiento;
    private  String tecnico;

    public Paciente() {
    }

    public Paciente(int id, String nombre, String apellido, String cedula, String nacimiento, String tecnico) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.nacimiento = nacimiento;
        this.tecnico = tecnico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }
}
