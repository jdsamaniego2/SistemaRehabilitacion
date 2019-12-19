package com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi;

import com.example.sistemarehabilitacion.BaseDatos.Remotos.IdentificadoresApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdaptadorApi {

    private static IServiciosApi apiservice;//se implementará un singleton

    public static  IServiciosApi getApiservice(){
        //---LAS SIGUIENTES 4 LINEAS SOLO SIRVEN PARA MOSTRAR EN CONSOLA EL RESULTADO DE LAS CONSULTAS
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httClient = new OkHttpClient.Builder();
        httClient.addInterceptor(logging);
        //---FIN
        if(apiservice == null){//singleton implementado para IServiciosApi
            Retrofit retrofit = new Retrofit.Builder()//patron builder de retrofit
                    .baseUrl(IdentificadoresApi.ruta_base)//estableciento ruta base de la api en el objeto retrofit
                    .addConverterFactory(GsonConverterFactory.create())//estableciendo decodificador retrofit en el objeto retrofit
                    .client(httClient.build())//estableciendo el interceptor para mostrar en consola los mensajes de respuesta (no es muy necesario)
                    .build();

            apiservice = retrofit.create(IServiciosApi.class);//finalizando construccion con el patron builder de Retrofit. Se debe indicar la interfaz de servicios para que genere el objeto en base a ella
        }
        return  apiservice;
    }
}
