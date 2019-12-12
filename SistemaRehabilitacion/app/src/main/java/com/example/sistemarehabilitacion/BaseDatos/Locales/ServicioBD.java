package com.example.sistemarehabilitacion.BaseDatos.Locales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;

import java.util.ArrayList;
import java.util.List;

public class ServicioBD {
    private  Context contexto;
    private  String bd;
    private int version;



    public ServicioBD (Context context,String bd,int version){
        this.contexto = context;
        this.bd = bd;
        this.version = version;
    }

    //RECIBE LA FECHA EN FORMATO DD/MM/YYYY

    public long RegistrarPaciente(String nombre, String apellido, String cedula, String nacimiento, String tecnico) {
        if(ConsultarPaciente(cedula)!=null){
            return -1;//NO ALMACENA EL PACIENTE PORQUE YA EXISTE UNO CON ESA CEDULA
        }
        try {
            ConectorBD conn = new ConectorBD(contexto, bd, null, version);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            //values.put(IdentificadoresBD.campo_paciente_id,"NULL");// no es necesario porque el campo es autoincremental
            values.put(IdentificadoresBD.campo_paciente_cedula, cedula);
            values.put(IdentificadoresBD.campo_paciente_apellido, apellido);
            values.put(IdentificadoresBD.campo_paciente_nombre, nombre);
            values.put(IdentificadoresBD.campo_paciente_nacimiento, nacimiento);
            values.put(IdentificadoresBD.campo_paciente_tecnico, tecnico);
            Long idResultante = db.insert(IdentificadoresBD.tabla_paciente, IdentificadoresBD.campo_paciente_id, values);

            db.close();
            return idResultante;//si no se guarda retorna -1
        }catch(Exception e){
            return -1;
        }

    }


    public Paciente ConsultarPaciente(long id) {
        ConectorBD conn = new ConectorBD(contexto,bd,null,version);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] parametrosConsulta = {id+""};
        String [] camposConsulta = {IdentificadoresBD.campo_paciente_id,IdentificadoresBD.campo_paciente_nombre,IdentificadoresBD.campo_paciente_apellido,IdentificadoresBD.campo_paciente_cedula,IdentificadoresBD.campo_paciente_nacimiento,IdentificadoresBD.campo_paciente_tecnico};
        Paciente paciente = null;
        try{
            Cursor cursor = db.query(IdentificadoresBD.tabla_paciente,camposConsulta,IdentificadoresBD.campo_paciente_id+"=?",parametrosConsulta,null,null,null);
            cursor.moveToFirst();
            paciente = new Paciente();
            paciente.setId(Integer.parseInt(cursor.getString(0)));
            paciente.setNombre(cursor.getString(1));
            paciente.setApellido(cursor.getString(2));
            paciente.setCedula(cursor.getString(3));
            paciente.setNacimiento(cursor.getString(4));
            paciente.setTecnico(cursor.getString(5));
            db.close();
        }catch(Exception e){
            db.close();
            paciente = null;
        }
        finally {
            return paciente;
        }

    }
    public Paciente ConsultarPaciente(String cedula) {
        ConectorBD conn = new ConectorBD(contexto,bd,null,version);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] parametrosConsulta = {cedula+""};
        String [] camposConsulta = {IdentificadoresBD.campo_paciente_id,IdentificadoresBD.campo_paciente_nombre,IdentificadoresBD.campo_paciente_apellido,IdentificadoresBD.campo_paciente_cedula,IdentificadoresBD.campo_paciente_nacimiento,IdentificadoresBD.campo_paciente_tecnico};
        Paciente paciente = null;
        try{
            Cursor cursor = db.query(IdentificadoresBD.tabla_paciente,camposConsulta,IdentificadoresBD.campo_paciente_cedula+"=?",parametrosConsulta,null,null,null);
            cursor.moveToFirst();
            paciente = new Paciente();
            paciente.setId(Integer.parseInt(cursor.getString(0)));
            paciente.setNombre(cursor.getString(1));
            paciente.setApellido(cursor.getString(2));
            paciente.setCedula(cursor.getString(3));
            paciente.setNacimiento(cursor.getString(4));
            paciente.setTecnico(cursor.getString(5));
            db.close();
        }catch(Exception e){
            db.close();
            paciente = null;
        }
        finally {
            return paciente;
        }


    }

    public List<Paciente> ConsultarPacientes(){
        List<Paciente> lista = new ArrayList<>();
        ConectorBD conn = new ConectorBD(contexto,bd,null,version);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] camposConsulta = {IdentificadoresBD.campo_paciente_id,IdentificadoresBD.campo_paciente_nombre,IdentificadoresBD.campo_paciente_apellido,IdentificadoresBD.campo_paciente_cedula,IdentificadoresBD.campo_paciente_nacimiento,IdentificadoresBD.campo_paciente_tecnico};
        try{
            Cursor cursor = db.query(IdentificadoresBD.tabla_paciente, camposConsulta, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Paciente paciente = new Paciente();
                    paciente.setId(Integer.parseInt(cursor.getString(0)));
                    paciente.setNombre(cursor.getString(1));
                    paciente.setApellido(cursor.getString(2));
                    paciente.setCedula(cursor.getString(3));
                    paciente.setNacimiento(cursor.getString(4));
                    paciente.setTecnico(cursor.getString(5));
                    lista.add(paciente);
                } while(cursor.moveToNext());
            }
            db.close();
            return lista;
        }catch(Exception e){
            db.close();
            return null;
        }


    }

    public long EditarPaciente(long id, String nombre, String apellido, String cedula, String nacimiento, String tecnico){
        if(ConsultarPaciente(id)==null){
            return -1;//SI EL PACIENTE NO EXISTE RETORNA -1
        }
        try {


            ConectorBD conn = new ConectorBD(contexto, bd, null, version);
            SQLiteDatabase db = conn.getWritableDatabase();

            String[] parametrosConsulta = {id + ""};
            ContentValues values = new ContentValues();
            values.put(IdentificadoresBD.campo_paciente_cedula, cedula);
            values.put(IdentificadoresBD.campo_paciente_apellido, apellido);
            values.put(IdentificadoresBD.campo_paciente_nombre, nombre);
            values.put(IdentificadoresBD.campo_paciente_nacimiento, nacimiento);
            values.put(IdentificadoresBD.campo_paciente_tecnico, tecnico);

            int idResultante = db.update(IdentificadoresBD.tabla_paciente, values, IdentificadoresBD.campo_sesion_idpaciente + "=?", parametrosConsulta);
            db.close();
            return idResultante;//si no se guarda retorna -1
        }catch (Exception e){
            return -1;
        }

    }

    public long EditarPaciente(String nombre, String apellido, String cedula, String nacimiento, String tecnico){
        if(ConsultarPaciente(cedula)==null){
            return -1;//SI EL PACIENTE NO EXISTE RETORNA -1
        }
        try {


            ConectorBD conn = new ConectorBD(contexto, bd, null, version);
            SQLiteDatabase db = conn.getWritableDatabase();

            String[] parametrosConsulta = {cedula + ""};
            ContentValues values = new ContentValues();
            values.put(IdentificadoresBD.campo_paciente_cedula, cedula);
            values.put(IdentificadoresBD.campo_paciente_apellido, apellido);
            values.put(IdentificadoresBD.campo_paciente_nombre, nombre);
            values.put(IdentificadoresBD.campo_paciente_nacimiento, nacimiento);
            values.put(IdentificadoresBD.campo_paciente_tecnico, tecnico);

            int idResultante = db.update(IdentificadoresBD.tabla_paciente, values, IdentificadoresBD.campo_paciente_cedula + "=?", parametrosConsulta);

            db.close();
            return idResultante;//si no se guarda retorna -1
        }catch (Exception e){
            return -1;
        }
    }
    public long EliminarPaciente(long id){
        try{
            ConectorBD conn = new ConectorBD(contexto,bd,null,version);
            SQLiteDatabase db = conn.getWritableDatabase();

            String [] parametrosConsulta = {id+""};

            int elementos_eliminados = db.delete(IdentificadoresBD.tabla_paciente,IdentificadoresBD.campo_paciente_id+"=?",parametrosConsulta);

            return elementos_eliminados;//CANTIDAD DE ELEMENTOS ELIMINADOS
        }catch (Exception e){
            return -1;
        }

    }
    public long EliminarPaciente(String cedula){
        try{
            ConectorBD conn = new ConectorBD(contexto,bd,null,version);
            SQLiteDatabase db = conn.getWritableDatabase();

            String [] parametrosConsulta = {cedula+""};

            int elementos_eliminados = db.delete(IdentificadoresBD.tabla_paciente,IdentificadoresBD.campo_paciente_cedula+"=?",parametrosConsulta);

            return elementos_eliminados;//CANTIDAD DE ELEMENTOS ELIMINADOS
        }catch (Exception e){
            return -1;
        }
    }

    public long RegistrarSesion(int id_paciente, int tiempo, int repeticiones, String tipo, String fecha, String dificultad){
        try {
            ConectorBD conn = new ConectorBD(contexto, bd, null, version);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(IdentificadoresBD.campo_sesion_idpaciente, id_paciente);
            values.put(IdentificadoresBD.campo_sesion_tiempo, tiempo);
            values.put(IdentificadoresBD.campo_sesion_repeticiones, repeticiones);
            values.put(IdentificadoresBD.campo_sesion_tipo, tipo);
            values.put(IdentificadoresBD.campo_sesion_fecha, fecha);
            values.put(IdentificadoresBD.campo_sesion_dificultad, dificultad);

            Long idResultante = db.insert(IdentificadoresBD.tabla_sesion, IdentificadoresBD.campo_sesion_id, values);
            db.close();
            return idResultante;//si no se guarda retorna -1
        }catch(Exception e){
            return -1;
        }
    }
    public Sesion ConsultarSesion(long id) {

        ConectorBD conn = new ConectorBD(contexto,bd,null,version);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] parametrosConsulta = {id+""};
        String [] camposConsulta = {IdentificadoresBD.campo_sesion_id,IdentificadoresBD.campo_sesion_idpaciente,IdentificadoresBD.campo_sesion_tiempo,IdentificadoresBD.campo_sesion_repeticiones,IdentificadoresBD.campo_sesion_tipo,IdentificadoresBD.campo_sesion_fecha,IdentificadoresBD.campo_sesion_dificultad};
        Sesion sesion = null;
        try{
            Cursor cursor = db.query(IdentificadoresBD.tabla_sesion,camposConsulta,IdentificadoresBD.campo_sesion_id+"=?",parametrosConsulta,null,null,null);
            cursor.moveToFirst();
            sesion = new Sesion();
            sesion.setId(Integer.parseInt(cursor.getString(0)));
            sesion.setId_paciente(Integer.parseInt(cursor.getString(1)));
            sesion.setTiempo(Integer.parseInt(cursor.getString(2)));
            sesion.setRepeticiones(Integer.parseInt(cursor.getString(3)));
            sesion.setTipo(cursor.getString(4));
            sesion.setFecha(cursor.getString(5));
            sesion.setDificultado(cursor.getString(6));
            db.close();
        }catch(Exception e){
            db.close();
            sesion = null;
        }
        finally {
            return sesion;
        }
    }
    public List<Sesion> ConsultarSesionesPaciente(long id_paciente) {
        List<Sesion> lista = new ArrayList<>();
        ConectorBD conn = new ConectorBD(contexto,bd,null,version);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] parametrosConsulta = {id_paciente+""};
        String [] camposConsulta = {IdentificadoresBD.campo_sesion_id,IdentificadoresBD.campo_sesion_idpaciente,IdentificadoresBD.campo_sesion_tiempo,IdentificadoresBD.campo_sesion_repeticiones,IdentificadoresBD.campo_sesion_tipo,IdentificadoresBD.campo_sesion_fecha,IdentificadoresBD.campo_sesion_dificultad};
        try{

            Cursor cursor =  db.query(IdentificadoresBD.tabla_sesion,camposConsulta,IdentificadoresBD.campo_paciente_id+"=?",parametrosConsulta,null,null,null);
            if (cursor.moveToFirst()) {
                do {
                    Sesion sesion = new Sesion();
                    sesion.setId(Integer.parseInt(cursor.getString(0)));
                    sesion.setId_paciente(Integer.parseInt(cursor.getString(1)));
                    sesion.setTiempo(Integer.parseInt(cursor.getString(2)));
                    sesion.setRepeticiones(Integer.parseInt(cursor.getString(3)));
                    sesion.setTipo(cursor.getString(4));
                    sesion.setFecha(cursor.getString(5));
                    sesion.setDificultado(cursor.getString(6));

                    lista.add(sesion);
                } while(cursor.moveToNext());
            }
            db.close();
            return lista;
        }catch(Exception e){
            db.close();
            return null;
        }
    }
    public List<Sesion> ConsultarSesiones(){
        List<Sesion> lista = new ArrayList<>();
        ConectorBD conn = new ConectorBD(contexto,bd,null,version);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] camposConsulta = {IdentificadoresBD.campo_sesion_id,IdentificadoresBD.campo_sesion_idpaciente,IdentificadoresBD.campo_sesion_tiempo,IdentificadoresBD.campo_sesion_repeticiones,IdentificadoresBD.campo_sesion_tipo,IdentificadoresBD.campo_sesion_fecha,IdentificadoresBD.campo_sesion_dificultad};
        try{
            Cursor cursor = db.query(IdentificadoresBD.tabla_sesion, camposConsulta, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Sesion sesion = new Sesion();
                    sesion.setId(Integer.parseInt(cursor.getString(0)));
                    sesion.setId_paciente(Integer.parseInt(cursor.getString(1)));
                    sesion.setTiempo(Integer.parseInt(cursor.getString(2)));
                    sesion.setRepeticiones(Integer.parseInt(cursor.getString(3)));
                    sesion.setTipo(cursor.getString(4));
                    sesion.setFecha(cursor.getString(5));
                    sesion.setDificultado(cursor.getString(6));

                    lista.add(sesion);
                } while(cursor.moveToNext());
            }
            db.close();
            return lista;
        }catch(Exception e){
            db.close();
            return null;
        }


    }
    public long EliminarSesion(int id){
        try{
            ConectorBD conn = new ConectorBD(contexto,bd,null,version);
            SQLiteDatabase db = conn.getWritableDatabase();

            String [] parametrosConsulta = {id+""};

            int elementos_eliminados = db.delete(IdentificadoresBD.tabla_sesion,IdentificadoresBD.campo_sesion_id+"=?",parametrosConsulta);

            return elementos_eliminados;//CANTIDAD DE ELEMENTOS ELIMINADOS
        }catch (Exception e){
            return -1;
        }
    }



}
