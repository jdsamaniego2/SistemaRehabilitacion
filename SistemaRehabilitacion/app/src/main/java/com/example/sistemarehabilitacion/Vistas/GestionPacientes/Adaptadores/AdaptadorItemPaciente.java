package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdaptadorItemPaciente extends BaseAdapter {
    private List<Paciente> pacientes;
    private Context contexto;

    public AdaptadorItemPaciente(List<Paciente> pacientes, Context contexto) {
        this.pacientes = pacientes;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return pacientes.size();
    }

    @Override
    public Object getItem(int i) {
        return pacientes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Paciente paciente = (Paciente) getItem(i);
        view = LayoutInflater.from(contexto).inflate(R.layout.activity_main_item,null);
        TextView txt_cedula = (TextView) view.findViewById(R.id.lbl_cedula_item);
        TextView txt_apellido = (TextView) view.findViewById(R.id.lbl_apellido_item);
        TextView txt_nombre = (TextView) view.findViewById(R.id.lbl_nombre_item);
        TextView txt_edad = (TextView) view.findViewById(R.id.lbl_edad_item);
        txt_cedula.setText(paciente.getCedula());
        txt_apellido.setText("Apellidos:"+paciente.getApellido());
        txt_nombre.setText("Nombres:"+paciente.getNombre());
        txt_edad.setText("Edad:"+calcularEdad(paciente.getNacimiento()));

        return view;
    }

    public int calcularEdad(String fecnacimiento){
        Date fechaNac=null;
        int año= 0;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
             que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
             en este caso es día mes año*/
            fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fecnacimiento);
            Calendar fechaNacimiento = Calendar.getInstance();
            //Se crea un objeto con la fecha actual
            Calendar fechaActual = Calendar.getInstance();
            //Se asigna la fecha recibida a la fecha de nacimiento.
            fechaNacimiento.setTime(fechaNac);
            //Se restan la fecha actual y la fecha de nacimiento
            año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
            int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
            int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
            //Se ajusta el año dependiendo el mes y el día
            if(mes<0 || (mes==0 && dia<0)){
                año--;
            }

        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        return año;

    }



}
