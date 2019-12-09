package com.example.sistemarehabilitacion.Vistas.GestionPacientes;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;

public  class PacienteActivo {
    private static Paciente paciente_actual = null;



    public  static  boolean HayUnaSesion(){
        return paciente_actual == null?false:true;
    }
    public static void IniciarSesion(Paciente p){
        paciente_actual = p;
    }
    public   static void FinalizarSesion(){
        paciente_actual = null;
    }

    public  static Paciente ObtenerPasienteSesion(){
        return  paciente_actual;
    }
}
