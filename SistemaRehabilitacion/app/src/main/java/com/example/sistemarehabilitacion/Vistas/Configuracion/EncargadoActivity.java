package com.example.sistemarehabilitacion.Vistas.Configuracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.EdicionActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;

public class EncargadoActivity extends AppCompatActivity {
    static  String encargado_actual = null;
    Button btn_iniciar_encargado;
    TextView txt_iniciar_encargado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_encargado);

        this.inicializarComponentes();
        this.inicializarEventos();


    }

    private void inicializarComponentes(){
        btn_iniciar_encargado = findViewById(R.id.btn_iniciar_encargado);
        txt_iniciar_encargado = findViewById(R.id.txt_iniciar_encargado);
    }

    private void inicializarEventos(){
        btn_iniciar_encargado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_encargado = txt_iniciar_encargado.getText().toString();
                nombre_encargado = nombre_encargado.trim();//quitando espacios al inicio y al final
                if(nombre_encargado.isEmpty()){
                    return;
                }

                nombre_encargado = nombre_encargado.toLowerCase();//todos a minusculas
                nombre_encargado = nombre_encargado.replaceAll("\\s{2,}", " ").trim();//para q solo haya 1 espacio entre palabras

                //Poniendo en mayuscula cada palabra
                String nombre_mayusculas = "";
                for (String palabra : nombre_encargado.split(" "))
                {
                    nombre_mayusculas += palabra.substring(0, 1).toUpperCase() + palabra.substring(1, palabra.length()).toLowerCase() + " ";
                }
                nombre_mayusculas = nombre_mayusculas.trim();
                EncargadoActivity.encargado_actual = nombre_mayusculas;

                Intent intent = new Intent(EncargadoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}