package com.example.sistemarehabilitacion.BaseDatos.Remotos;

import android.content.Context;
import android.os.Handler;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;


import java.util.List;

public class SincronizadorLocalRemoto {
    private Context contexto;
    private  List<Paciente> pacientes;


    public void subir(Context contexto,List<Paciente> pacientes){//sincroniza cada sesion de cada paciente

        this.pacientes = pacientes;
        this.contexto = contexto;

        //sincronizar pacientes
        for (Paciente p : this.pacientes){
            SincronizadorPaciente sincronizador = new SincronizadorPaciente();
            sincronizador.sincronizarRemoto(this.contexto,p);
        }
        //sincronizar sesiones
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                for (Paciente p : SincronizadorLocalRemoto.this.pacientes){
                    ServicioBD sercicio = new ServicioBD(SincronizadorLocalRemoto.this.contexto, IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                    List<Sesion>  sesiones = sercicio.ConsultarSesionesPaciente(p.getId());
                    for(Sesion s : sesiones){
                        SincronizadorSesion sincronizador = new SincronizadorSesion();
                        sincronizador.sincronizarRemoto(SincronizadorLocalRemoto.this.contexto,s,p);
                    }
                }
            }
        }, 5000);
    }
    public void bajar(Context contexto,List<Paciente> pacientes){//sincroniza cada sesion de cada paciente

        this.pacientes = pacientes;
        this.contexto = contexto;

//sincronizar pacientes
        for(Paciente p: pacientes){
            SincronizadorPaciente sincronizador = new SincronizadorPaciente();
            sincronizador.sincronizarLocal(this.contexto,p);

            SincronizadorSesion sincronizador_sesiones = new SincronizadorSesion();
            sincronizador_sesiones.sincronizarLocal(this.contexto,p);
        }

    }



}
