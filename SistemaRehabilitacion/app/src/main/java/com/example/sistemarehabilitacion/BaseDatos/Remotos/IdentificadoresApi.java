package com.example.sistemarehabilitacion.BaseDatos.Remotos;

import android.content.SharedPreferences;

public class IdentificadoresApi {

    public static String direcccion = "192.168.1.5";
    public final static String ruta_base = "http://192.168.1.5/api_rehabilitacion/index.php/";//debería cambiarse la ip por un dns
    public final static String subruta_paciente = "pacientes/";
    public final static String subruta_sesion = "sesiones/";
    public final static String subruta_probar_conexion = "conexion/";

}