package com.example.sistemarehabilitacion.Vistas.Musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReproductoMusica {






    public String getMRM(int milliseconds){
        int seconds=(int) (milliseconds/1000)%60;
        int minutes=(int)((milliseconds/(1000*60))%60);
        int hours=(int)((milliseconds/(1000*60*60))%24);
        String aux="";
        aux=((hours<10)?"0"+hours:hours)+":"+((minutes<10)?"0"+minutes:minutes)+":"+((seconds<10)?"0"+seconds:seconds);
        return aux;
    }





    public ArrayList<File> EncontrarCanciones(File root){



        ArrayList<File> canciones = new ArrayList<File>();
        File[] archivos=root.listFiles();


        for(File lista : archivos){


            if(lista.isDirectory() && !lista.isHidden()){
                canciones.addAll(EncontrarCanciones(lista));
            }else{
                if(lista.getName().endsWith(".mp3") || lista.getName().endsWith(".vav")){

                    canciones.add(lista);
                }

            }


        }
        return canciones;
    }

    public String[] cargarCanciones(){

        final ArrayList<File> canciones = EncontrarCanciones(Environment.getExternalStorageDirectory());

        String[] items=new String[canciones.size()];

        for (int i =0; i<canciones.size();i++){
            items[i]=canciones.get(i).getName().toString().replace(".mp3","").replace(".vav","").toLowerCase();

        }
        return  items;

    }
}
