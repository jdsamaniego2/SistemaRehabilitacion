package com.example.sistemarehabilitacion.BaseDatos.Remotos;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Request;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi.AdaptadorApi;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi.IServiciosApi;
import com.example.sistemarehabilitacion.Vistas.Errores.ErrorConexionBdRemota;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincronizadorSesion {

    private Context contexto;
    private Sesion sesion;
    private Paciente paciente;
    private long id_sesion;
    private long id_paciente;

    public void sincronizarRemoto(Context c, Sesion s, Paciente p){
        this.sesion = s;
        this.sesion.setId(0);
        this.paciente = p;
        this.paciente.setId(0);
        this.contexto = c;
        this.id_sesion = 0;
        this.id_paciente = 0;




        IServiciosApi api = AdaptadorApi.getApiservice();
        Call<List<Paciente>> call_buscar_paciente = api.getPaciente(SincronizadorSesion.this.paciente.getCedula());
        call_buscar_paciente.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call_buscar_paciente, Response<List<Paciente>> response) {
                if(response.isSuccessful()){
                    try{//si devuelve un paciente
                        SincronizadorSesion.this.id_paciente = response.body().get(0).getId();
                       // Toast.makeText(SincronizadorSesion.this.contexto,"Busqueda de paciente exitosa pacientes id: "+ SincronizadorSesion.this.id_paciente,Toast.LENGTH_LONG).show();


                        IServiciosApi api = AdaptadorApi.getApiservice();
                        Call<List<Sesion>> call_buscar_sesion = api.getSesion(SincronizadorSesion.this.paciente.getCedula(),SincronizadorSesion.this.sesion.getFecha());
                        call_buscar_sesion.enqueue(new Callback<List<Sesion>>() {
                            @Override
                            public void onResponse(Call<List<Sesion>> call_buscar_sesion, Response<List<Sesion>> response) {
                                if(response.isSuccessful()){
                                    try{
                                        SincronizadorSesion.this.id_sesion = response.body().get(0).getId();
                                        //Toast.makeText(SincronizadorSesion.this.contexto,"SESION ENCONTRADA",Toast.LENGTH_LONG).show();
                                        //la sesion si existe (no hacer nada)

                                    }catch (Exception e){
                                        //Toast.makeText(SincronizadorSesion.this.contexto,"NO EXISTE LA SESION",Toast.LENGTH_LONG).show();
                                        //no existe la sesion (la guardo)
                                        IServiciosApi api_guardar_sesion = AdaptadorApi.getApiservice();
                                        SincronizadorSesion.this.sesion.setId_paciente((int)SincronizadorSesion.this.id_paciente);
                                        Call<Request> call_guardar_seion =  api_guardar_sesion.postSesion(SincronizadorSesion.this.sesion);

                                        call_guardar_seion.enqueue(new Callback<Request>() {
                                            @Override
                                            public void onResponse(Call<Request> call_guardar_seion, Response<Request> response) {
                                                if(response.isSuccessful()){
                                                    if(response.body().getCode()==200){
                                                        //Toast.makeText(SincronizadorSesion.this.contexto,"SESION GUARDADA",Toast.LENGTH_LONG).show();
                                                    }
                                                    else{
                                                        //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR AL GUARDAR SESION",Toast.LENGTH_LONG).show();

                                                        //vista de error
                                                        if(!SincronizadorLocalRemoto.error_mostrado){
                                                            Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            SincronizadorSesion.this.contexto.startActivity(intent);
                                                            SincronizadorLocalRemoto.error_mostrado = true;
                                                        }

                                                    }

                                                }
                                                else{
                                                    //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR AL GUARDAR SESION",Toast.LENGTH_LONG).show();
                                                    //vista de error
                                                    if(!SincronizadorLocalRemoto.error_mostrado){
                                                        Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        SincronizadorSesion.this.contexto.startActivity(intent);
                                                        SincronizadorLocalRemoto.error_mostrado = true;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Request> call_guardar_seion, Throwable t) {
                                                //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR EN LA PETICIÓN---------",Toast.LENGTH_LONG).show();
                                                //vista de error
                                                if(!SincronizadorLocalRemoto.error_mostrado){
                                                    Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    SincronizadorSesion.this.contexto.startActivity(intent);
                                                    SincronizadorLocalRemoto.error_mostrado = true;
                                                }
                                            }
                                        });
                                    }

                                }
                                else{
                                    //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR LA RESPUESTA",Toast.LENGTH_LONG).show();
                                    //vista de error
                                    if(!SincronizadorLocalRemoto.error_mostrado){
                                        Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        SincronizadorSesion.this.contexto.startActivity(intent);
                                        SincronizadorLocalRemoto.error_mostrado = true;
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Sesion>> call_buscar_sesion, Throwable t) {
                                //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR EN LA PETICIÓN",Toast.LENGTH_LONG).show();
                                //vista de error
                                if(!SincronizadorLocalRemoto.error_mostrado){
                                    Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    SincronizadorSesion.this.contexto.startActivity(intent);
                                    SincronizadorLocalRemoto.error_mostrado = true;
                                }
                            }
                        });


                    }
                    catch (Exception e){//no se encontró el paciente (devuelve null)
                       // Toast.makeText(SincronizadorSesion.this.contexto,"PACIENTE NO ENCONTRADO",Toast.LENGTH_LONG).show();//esto no debería ocurrir
                        //vista de error
                        if(!SincronizadorLocalRemoto.error_mostrado){
                            Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SincronizadorSesion.this.contexto.startActivity(intent);
                            SincronizadorLocalRemoto.error_mostrado = true;
                        }
                    }
                }
                else{
                    //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR AL BUSCAR PACIENTE",Toast.LENGTH_LONG).show();
                    //vista de error
                    if(!SincronizadorLocalRemoto.error_mostrado){
                        Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        SincronizadorSesion.this.contexto.startActivity(intent);
                        SincronizadorLocalRemoto.error_mostrado = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Paciente>> call_buscar_paciente, Throwable t) {
                //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR EN LA PETICIÓN--",Toast.LENGTH_LONG).show();
                //vista de error
                if(!SincronizadorLocalRemoto.error_mostrado){
                    Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SincronizadorSesion.this.contexto.startActivity(intent);
                    SincronizadorLocalRemoto.error_mostrado = true;
                }
            }
        });








    }
    public void sincronizarLocal(Context c, Paciente p){

        this.paciente = p;
        this.contexto = c;


        IServiciosApi api = AdaptadorApi.getApiservice();

        Call<List<Sesion>> call_buscar_sesion = api.getSesiones(SincronizadorSesion.this.paciente.getCedula());
        call_buscar_sesion.enqueue(new Callback<List<Sesion>>() {
            @Override
            public void onResponse(Call<List<Sesion>> call, Response<List<Sesion>> response) {
               if(response.isSuccessful()){
                    //Toast.makeText(SincronizadorSesion.this.contexto,"LOCAL",Toast.LENGTH_LONG).show();
                    for (Sesion s : response.body()){
                        if(!(SincronizadorSesion.this.existeSesionEnLocal(s,SincronizadorSesion.this.paciente))){
                            ServicioBD sercicio = new ServicioBD(SincronizadorSesion.this.contexto, IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                            sercicio.RegistrarSesion(SincronizadorSesion.this.paciente.getId(), s.getTiempo(),s.getRepeticiones(), s.getTipo(), s.getFecha(), s.getSupervisor());
                        }
                    }
                }
                else {
                    //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR AL BUSCAR LA SESION",Toast.LENGTH_LONG).show();
                    //vista de error
                   if(!SincronizadorLocalRemoto.error_mostrado){
                       Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       SincronizadorSesion.this.contexto.startActivity(intent);
                       SincronizadorLocalRemoto.error_mostrado = true;
                   }
                }
            }

            @Override
            public void onFailure(Call<List<Sesion>> call, Throwable t) {
                //Toast.makeText(SincronizadorSesion.this.contexto,"ERROR EN LA PETICIÓN",Toast.LENGTH_LONG).show();
                //vista de error
                if(!SincronizadorLocalRemoto.error_mostrado){
                    Intent intent = new Intent(SincronizadorSesion.this.contexto, ErrorConexionBdRemota.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SincronizadorSesion.this.contexto.startActivity(intent);
                    SincronizadorLocalRemoto.error_mostrado = true;
                }
            }
        });


    }
    private boolean existeSesionEnLocal(Sesion s, Paciente p){
        ServicioBD sercicio = new ServicioBD(SincronizadorSesion.this.contexto, IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        List<Sesion> sesiones = sercicio.ConsultarSesionesPaciente(p.getId());
        for (Sesion aux:sesiones){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
            Date fecha_sesion_local = null;
            try {
                fecha_sesion_local = dateFormat.parse(aux.getFecha());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date fecha_sesion_remota = null;
            try {
                fecha_sesion_remota = dateFormat.parse(s.getFecha());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(fecha_sesion_local.compareTo(fecha_sesion_remota)==0){
                return  true;
            }
        }
        return  false;
    }


}
