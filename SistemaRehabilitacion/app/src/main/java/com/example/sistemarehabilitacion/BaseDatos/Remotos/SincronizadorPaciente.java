package com.example.sistemarehabilitacion.BaseDatos.Remotos;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Request;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi.AdaptadorApi;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi.IServiciosApi;
import com.example.sistemarehabilitacion.Vistas.Errores.ErrorConexionBdRemota;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincronizadorPaciente {

 
    private Context contexto;
    private Paciente paciente;
    private long id;
 

    public void sincronizarRemoto(Context c,Paciente p){
        this.paciente = p;
        this.contexto = c;
        this.id = 0;
        IServiciosApi api = AdaptadorApi.getApiservice();
        Call<List<Paciente>> call = api.getPaciente(p.getCedula());
        call.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call_consulta, Response<List<Paciente>> response) {
                if(response.isSuccessful()){
                    try{//si devuelve un paciente
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                        Date fecha_persona_local = dateFormat.parse(SincronizadorPaciente.this.paciente.getUltima_modificacion());
                        Date fecha_persona_remota = dateFormat.parse(response.body().get(0).getUltima_modificacion());
                        Toast.makeText(SincronizadorPaciente.this.contexto,"LOCAL: "+ fecha_persona_local+" REMOTA: "+ fecha_persona_remota,Toast.LENGTH_LONG).show();
                        if(fecha_persona_local.compareTo(fecha_persona_remota)!=0){//si la fecha del paciente remoto es menor q la del paciente local
                            if(fecha_persona_local.after(fecha_persona_remota)){
                                Toast.makeText(SincronizadorPaciente.this.contexto,"ES NECESARIO ACTUALIZAR"+ fecha_persona_remota,Toast.LENGTH_LONG).show();
                                SincronizadorPaciente.this.id = response.body().get(0).getId();
                                //lo modifico
                                Toast.makeText(SincronizadorPaciente.this.contexto,"Busqueda de paciente exitosa pacientesss id: "+ SincronizadorPaciente.this.id,Toast.LENGTH_LONG).show();
                                IServiciosApi api = AdaptadorApi.getApiservice();
                                Call<Request> call_editar =  api.editPaciente(Long.parseLong( ""+ SincronizadorPaciente.this.id), SincronizadorPaciente.this.paciente);

                                call_editar.enqueue(new Callback<Request>() {
                                    @Override
                                    public void onResponse(Call<Request> call_editar, Response<Request> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getCode()==200){
                                                Toast.makeText(SincronizadorPaciente.this.contexto,"PACIENTE EDITADO",Toast.LENGTH_LONG).show();
                                            }
                                            else{

                                                Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR AL GUARDAR PACIENTE (SERVIDOR)",Toast.LENGTH_LONG).show();
                                                //vista de error
                                                if(!SincronizadorLocalRemoto.error_mostrado){
                                                    Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                                                    SincronizadorPaciente.this.contexto.startActivity(intent);
                                                    SincronizadorLocalRemoto.error_mostrado = true;
                                                }

                                            }

                                        }
                                        else{
                                            Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR AL EDITAR PACIENTE",Toast.LENGTH_LONG).show();
                                            //vista de error
                                            if(!SincronizadorLocalRemoto.error_mostrado){
                                                Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                                                SincronizadorPaciente.this.contexto.startActivity(intent);
                                                SincronizadorLocalRemoto.error_mostrado = true;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Request> call_editar, Throwable t) {
                                        Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR EN LA PETICION  AL EDITAR PACIENTE",Toast.LENGTH_LONG).show();
                                        //vista de error
                                        if(!SincronizadorLocalRemoto.error_mostrado){
                                            Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                                            SincronizadorPaciente.this.contexto.startActivity(intent);
                                            SincronizadorLocalRemoto.error_mostrado = true;
                                        }
                                    }
                                });
                            }
                        }

                    }
                    catch (Exception e){//no se encontró el paciente (devuelve null)
                        Toast.makeText(SincronizadorPaciente.this.contexto,"PACIENTE NO ENCONTRADO",Toast.LENGTH_LONG).show();
                        //lo registro
                        IServiciosApi api = AdaptadorApi.getApiservice();
                        Call<Request> call_insertar =  api.postPaciente( SincronizadorPaciente.this.paciente );

                        call_insertar.enqueue(new Callback<Request>() {
                            @Override
                            public void onResponse(Call<Request> call_insertar, Response<Request> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getCode()==200){
                                        Toast.makeText(SincronizadorPaciente.this.contexto,"PACIENTE GUARDADO",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(SincronizadorPaciente.this.contexto, "ERROR AL GUARDAR PACIENTE (SERVIDOR)",Toast.LENGTH_LONG).show();
                                        //vista de error
                                        if(!SincronizadorLocalRemoto.error_mostrado){
                                            Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                                            SincronizadorPaciente.this.contexto.startActivity(intent);
                                            SincronizadorLocalRemoto.error_mostrado = true;
                                        }
                                    }

                                }
                                else{
                                    Toast.makeText(SincronizadorPaciente.this.contexto ,"ERROR AL GUARDAR PACIENTE",Toast.LENGTH_LONG).show();
                                    //vista de error
                                    if(!SincronizadorLocalRemoto.error_mostrado){
                                        Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                                        SincronizadorPaciente.this.contexto.startActivity(intent);
                                        SincronizadorLocalRemoto.error_mostrado = true;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Request> call_insertar, Throwable t) {
                                Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR EN LA PETICIÓN DE GUARDAR PACIENTE",Toast.LENGTH_LONG).show();
                                //vista de error
                                if(!SincronizadorLocalRemoto.error_mostrado){
                                    Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                                    SincronizadorPaciente.this.contexto.startActivity(intent);
                                    SincronizadorLocalRemoto.error_mostrado = true;
                                }
                            }
                        });


                    }

                }
                else{
                    Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR AL BUSCAR PACIENTE",Toast.LENGTH_LONG).show();
                    //vista de error
                    if(!SincronizadorLocalRemoto.error_mostrado){
                        Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                        SincronizadorPaciente.this.contexto.startActivity(intent);
                        SincronizadorLocalRemoto.error_mostrado = true;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Paciente>> call_consulra, Throwable t) {
                Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR EN LA PETICIÓN AL BUSCAR PACIENTE",Toast.LENGTH_LONG).show();
                //vista de error
                if(!SincronizadorLocalRemoto.error_mostrado){
                    Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                    SincronizadorPaciente.this.contexto.startActivity(intent);
                    SincronizadorLocalRemoto.error_mostrado = true;
                }
            }
        });


    }


    public void sincronizarLocal(Context c,Paciente p){
        this.paciente = p;
        this.contexto = c;
        this.id = 0;
        IServiciosApi api = AdaptadorApi.getApiservice();
        Call<List<Paciente>> call = api.getPaciente(p.getCedula());
        call.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                if(response.isSuccessful()){
                    try{//si devuelve un paciente
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                        Date fecha_persona_local = dateFormat.parse(SincronizadorPaciente.this.paciente.getUltima_modificacion());
                        Date fecha_persona_remota = dateFormat.parse(response.body().get(0).getUltima_modificacion());

                        if(fecha_persona_local.compareTo(fecha_persona_remota)!=0){//si la fecha del paciente es diferente
                            if(fecha_persona_local.before(fecha_persona_remota)){
                                //actualizar el local
                                Toast.makeText(SincronizadorPaciente.this.contexto,"ES NECESARIO ACTUALIZAR LA BD LOCAL" ,Toast.LENGTH_LONG).show();
                                ServicioBD sercicio = new ServicioBD(SincronizadorPaciente.this.contexto, IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                                sercicio.EditarPacienteConFechaEspecifica(response.body().get(0).getNombre(), response.body().get(0).getApellido(), response.body().get(0).getCedula(), response.body().get(0).getNacimiento(), response.body().get(0).getTecnico(),response.body().get(0).getUltima_modificacion());
                            }
                        }

                    }
                    catch (Exception e){//no se encontró el paciente (devuelve null)
                        Toast.makeText(SincronizadorPaciente.this.contexto,"NO SE ENCONTRÓ EL PACIENTE",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR AL BUSCAR PACIENTE",Toast.LENGTH_LONG).show();
                    //vista de error
                    if(!SincronizadorLocalRemoto.error_mostrado){
                        Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                        SincronizadorPaciente.this.contexto.startActivity(intent);
                        SincronizadorLocalRemoto.error_mostrado = true;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {
                Toast.makeText(SincronizadorPaciente.this.contexto,"ERROR EN LA PETICIÓN AL BUSCAR PACIENTE",Toast.LENGTH_LONG).show();
                //vista de error
                if(!SincronizadorLocalRemoto.error_mostrado){
                    Intent intent = new Intent(SincronizadorPaciente.this.contexto, ErrorConexionBdRemota.class);
                    SincronizadorPaciente.this.contexto.startActivity(intent);
                    SincronizadorLocalRemoto.error_mostrado = true;
                }
            }
        });


    }








}
