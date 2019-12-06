package com.example.sistemarehabilitacion.BaseDatos.Modelos;

public class Sesion {

    private int id;
    private int id_paciente;
    private int tiempo;
    private int repeticiones;
    private  String tipo;
    private String fecha;
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
