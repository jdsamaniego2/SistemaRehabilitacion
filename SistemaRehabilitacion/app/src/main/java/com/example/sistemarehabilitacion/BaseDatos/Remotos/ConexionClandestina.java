package com.example.sistemarehabilitacion.BaseDatos.Remotos;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class ConexionClandestina extends AsyncTask<Void,Integer,Boolean> {
    private  Connection conexion = null;
    private Statement comando = null;
    private ResultSet registro;

    private String servidor = "37.59.55.185";
    private String puerto = "3306";
    private String nombre_bd = "ESV1gkyZl8";

    private String usuario = "ESV1gkyZl8";
    private String contrasenia = "zjVgdjcQbu";
    private Context c;

    public ConexionClandestina(Context c){
        this.c = c;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Class.forName("com.mysql.jdbc.Driver");//comprobar version
            //Nombre del servidor. localhost:3306 es la ruta y el puerto de la conexión MySQL
            //panamahitek_text es el nombre que le dimos a la base de datos
            String servidor = "jdbc:mysql://"+this.servidor+"/"+this.nombre_bd;
            //El root es el nombre de usuario por default. No hay contraseña
            //Toast.makeText(c,servidor,Toast.LENGTH_SHORT).show();
            //Se inicia la conexión
            conexion = DriverManager.getConnection(servidor,this.usuario,this.contrasenia);

            if (conexion == null)
            {
                return false;
            }
        } catch (NoClassDefFoundError e){
            Log.e("Definicion de clase",e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Clase no encontrada",e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR Conexion:",e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    protected void onPostExecute(Boolean resultado) {
        if(resultado) {
            Log.e("LOG:", "conectado");
        }else {
            Log.e("LOG:", "no conectado");
        }
    }
}