package com.example.sistemarehabilitacion.BaseDatos.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Paciente implements Serializable {
    @SerializedName("ID_PAC")
    @Expose
    private  int id;

    @SerializedName("NOMBRE_PAC")
    @Expose
    private  String nombre;

    @SerializedName("APELLIDO_PAC")
    @Expose
    private  String apellido;

    @SerializedName("CEDULA_PAC")
    @Expose
    private  String cedula;

    @SerializedName("NACIMIENTO_PAC")
    @Expose
    private  String nacimiento;

    @SerializedName("ULTIMAMODIFICACION_PAC")
    @Expose
    private  String ultima_modificacion;


    @SerializedName("TECNICO_PAC")
    @Expose
    private  String tecnico;

    public Paciente() {
    }

    public Paciente(String nombre, String apellido, String cedula, String nacimiento, String ultima_modificacion, String tecnico) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.nacimiento = nacimiento;
        this.ultima_modificacion = ultima_modificacion;
        this.tecnico = tecnico;
    }

    public String getUltima_modificacion() {
        return ultima_modificacion;
    }

    public void setUltima_modificacion(String ultima_modificacion) {
        this.ultima_modificacion = ultima_modificacion;
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
