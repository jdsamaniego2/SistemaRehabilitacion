package com.example.sistemarehabilitacion.BaseDatos.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sesion implements Serializable {

    @SerializedName("ID_SES")
    @Expose
    private int id;

    @SerializedName("ID_PAC")
    @Expose
    private int id_paciente;

    @SerializedName("TIEMPO_SES")
    @Expose
    private int tiempo;

    @SerializedName("REPETICIONES_SES")
    @Expose
    private int repeticiones;

    @SerializedName("TIPO_SES")
    @Expose
    private  String tipo;

    @SerializedName("FECHA_SES")
    @Expose
    private String fecha;

    @SerializedName("DIFICULTAD_SES")
    @Expose
    private  String dificultado;

    public Sesion(int id, int id_paciente, int tiempo, int repeticiones, String tipo, String fecha, String dificultado) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.tiempo = tiempo;
        this.repeticiones = repeticiones;
        this.tipo = tipo;
        this.fecha = fecha;
        this.dificultado = dificultado;
    }

    public Sesion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDificultado() {
        return dificultado;
    }

    public void setDificultado(String dificultado) {
        this.dificultado = dificultado;
    }
}
