package com.example.sistemarehabilitacion.BaseDatos.Locales;

public class IdentificadoresBD {


    public static final String nombre_bd= "bd_rehabilitacion";
    public static final int version_bd= 8;


    //TABLAS
    public static final String tabla_paciente = "PACIENTE";
    public static final String tabla_sesion = "SESION";


    //CAMPOS PACIENTE
    public static final String campo_paciente_id = "ID_PAC";
    public static final String campo_paciente_nombre = "NOMBRE_PAC";
    public static final String campo_paciente_apellido = "APELLIDO_PAC";
    public static final String campo_paciente_cedula = "CEDULA_PAC";
    public static final String campo_paciente_nacimiento = "NACIMIENTO_PAC";
    public static final String campo_paciente_ultima_modificacion = "ULTIMAMODIFICACION_PAC";
    public static final String campo_paciente_enfermedad = "ENFERMEDADES_PAC";

    //CAMPOS SESION
    public static final String campo_sesion_id = "ID_SES";
    public static final String campo_sesion_idpaciente = "ID_PAC";
    public static final String campo_sesion_tiempo = "TIEMPO_SES";
    public static final String campo_sesion_repeticiones = "REPETICIONES_SES";
    public static final String campo_sesion_tipo = "TIPO_SES";
    public static final String campo_sesion_fecha = "FECHA_SES";
    public static final String campo_sesion_supervisor = "SUPERVISOR_SES";



    //CONSTANTES DE CREACION
    public static final String senencia_creacion_paciente = "" +
            "create table PACIENTE (\n" +
            "   ID_PAC               INTEGER       PRIMARY KEY       AUTOINCREMENT,\n" +
            "   NOMBRE_PAC           TEXT          ,\n" +
            "   APELLIDO_PAC         TEXT          ,\n" +
            "   CEDULA_PAC           TEXT          ,\n" +
            "   NACIMIENTO_PAC       TEXT          ,\n" +
            "   ENFERMEDADES_PAC          TEXT          ,\n" +
            "   ULTIMAMODIFICACION_PAC TEXT        \n" +
            ")";
    public static final String senencia_creacion_sesion =  ""+
            "create table SESION (\n" +
            "   ID_SES               INTEGER      PRIMARY KEY        AUTOINCREMENT,\n" +
            "   ID_PAC               INTEGER       ,\n" +
            "   TIEMPO_SES           TEXT          ,\n" +
            "   REPETICIONES_SES     TEXT          ,\n" +
            "   TIPO_SES             TEXT          ,\n" +
            "   FECHA_SES            TEXT          ,\n" +
            "   SUPERVISOR_SES       TEXT          ,\n" +
            "   FOREIGN KEY(ID_PAC) REFERENCES PACIENTE(ID_PAC)\n" +
            ")";
    //CONSTANTES DE ELIMINACION TABLAS

    public static final String senencia_drop_sesion = "DROP TABLE SESION";
    public static final String senencia_drop_paciente = "DROP TABLE PACIENTE";
}
