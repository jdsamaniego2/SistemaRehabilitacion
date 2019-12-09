package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.R;


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
        txt_cedula.setText(paciente.getCedula());
        txt_apellido.setText(paciente.getApellido());
        txt_nombre.setText(paciente.getNombre());

        return view;
    }


}
