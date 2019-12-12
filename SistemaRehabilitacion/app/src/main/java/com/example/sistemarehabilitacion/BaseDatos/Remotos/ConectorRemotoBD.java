package com.example.sistemarehabilitacion.BaseDatos.Remotos;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;


public class ConectorRemotoBD {
    private  Connection conexion = null;
    private  Statement comando = null;
    private  ResultSet registro;

    private String servidor = "remotemysql.com";
    private String puerto = "3306";
    private String nombre_bd = "ESV1gkyZl8";

    private String usuario = "ESV1gkyZl8";
    private String contrasenia = "zjVgdjcQbu";


    public boolean Conectar(Context c){
        try {
            //Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            //Nombre del servidor. localhost:3306 es la ruta y el puerto de la conexión MySQL
            //panamahitek_text es el nombre que le dimos a la base de datos
            String servidor = "jdbc:mysql://"+this.servidor+"/"+this.nombre_bd;
            //El root es el nombre de usuario por default. No hay contraseña
            Toast.makeText(c,servidor,Toast.LENGTH_SHORT).show();
            //Se inicia la conexión
            conexion = DriverManager.getConnection(servidor,this.usuario,this.contrasenia);

        } catch (Exception ex) {
            Toast.makeText(c,ex.toString(),Toast.LENGTH_LONG).show();
            conexion = null;
        } finally {

            return conexion != null;
        }
    }
    public  boolean Desconectar(){
        try {
            conexion.close();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }
}
