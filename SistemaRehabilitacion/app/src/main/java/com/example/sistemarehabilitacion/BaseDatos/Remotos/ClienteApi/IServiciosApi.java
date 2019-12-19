package com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Request;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.IdentificadoresApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IServiciosApi {

    @GET(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_paciente)
    Call<List<Paciente>> getPacientes();

    @GET(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_paciente+"{id}")
    Call<List<Paciente>> getPaciente(@Path("id") String id);


    @Headers("Content-Type: application/json")
    @POST(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_paciente)
    Call<Request> postPaciente(@Body Paciente paciente);

    @PUT(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_paciente+"{id}")
    Call<Request> editPaciente(@Path("id") Long id,@Body Paciente paciente);

    @DELETE(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_paciente+"{id}")
    Call<Request> deletePaciente(@Path("id") Long id);




    @GET(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_sesion)
    Call<List<Sesion>> getSesiones();

    @GET(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_sesion+"{cedula}")
    Call<List<Sesion>> getSesiones(@Path("cedula") String cedula);

    @GET(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_sesion+"{cedula}/"+"{fecha}")
    Call<List<Sesion>> getSesion(@Path("cedula") String cedula,@Path("fecha") String fecha);


    @Headers("Content-Type: application/json")
    @POST(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_sesion)
    Call<Request> postSesion(@Body Sesion sesion);

    @PUT(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_sesion+"{id}")
    Call<Request> editSesion(@Path("id") Long id,@Body Sesion sesion);

    @DELETE(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_sesion+"{id}")
    Call<Request> deleteSesion(@Path("id") Long id);


    /*

     @GET(IdentificadoresApi.ruta_base+IdentificadoresApi.subruta_paciente+"{id}")
     Call<Paciente> getPacientes();

     */


}


