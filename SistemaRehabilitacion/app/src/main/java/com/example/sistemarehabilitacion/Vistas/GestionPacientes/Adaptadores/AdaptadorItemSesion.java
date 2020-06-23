package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;
import com.example.sistemarehabilitacion.R;

import java.util.List;

public class AdaptadorItemSesion extends BaseAdapter {
    private List<Sesion> sesiones;
    private Context contexto;

    public AdaptadorItemSesion(List<Sesion> sesiones, Context contexto) {
        this.sesiones = sesiones;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return sesiones.size();
    }

    @Override
    public Object getItem(int i) {
        return sesiones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Sesion sesion = (Sesion) getItem(i);
        view = LayoutInflater.from(contexto).inflate(R.layout.activity_reportes_item,null);
        TextView txt_fecha = (TextView) view.findViewById(R.id.lbl_fecha_item_sesion);
        TextView txt_tipo = (TextView) view.findViewById(R.id.lbl_tipo_item_sesion);
        TextView txt_repeticiones = (TextView) view.findViewById(R.id.lbl_repeticiones_item_sesion);
        TextView txt_tiempo = (TextView) view.findViewById(R.id.lbl_tiempo_item_sesion);
        TextView txt_dificultad = (TextView) view.findViewById(R.id.lbl_dificultad_item_sesion);

        txt_fecha.setText("Fecha: "+sesion.getFecha());
        txt_tipo.setText("Tipo: "+sesion.getTipo());
        txt_repeticiones.setText("Repeticiones: "+sesion.getRepeticiones()+"");

        if(sesion.getTiempo()%60!=0){
            txt_tiempo.setText("Tiempo: "+(sesion.getTiempo()/60)+" minutos y "+(sesion.getTiempo()-(sesion.getTiempo()/60)*60)+" segundos");
        }
        else{
            txt_tiempo.setText("Tiempo: "+(sesion.getTiempo()/60)+" minutos");
        }
        txt_dificultad.setText("Supervisado Por: "+sesion.getSupervisor());
        return view;
    }


}


