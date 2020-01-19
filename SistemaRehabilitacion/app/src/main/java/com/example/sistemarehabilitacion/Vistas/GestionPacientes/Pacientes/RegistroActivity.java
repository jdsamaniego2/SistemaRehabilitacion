package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.ConfiguracionPopup;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity {


    TextView txt_cedula;
    TextView txt_nombre;
    TextView txt_apellido;
    TextView txt_nacimiento_dia;
    TextView txt_nacimiento_mes;
    TextView txt_nacimiento_anio;
    TextView txt_tecnico;

    Button btn_registrar;
    //Button btn_calendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();
        inicializarEventos();




    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void inicializarComponentes(){
        txt_cedula = findViewById(R.id.txt_cedula_registro);
        txt_nombre = findViewById(R.id.txt_nombre_registro);
        txt_apellido = findViewById(R.id.txt_apellido_registro);
        txt_tecnico  = findViewById(R.id.txt_tecnico_registro);
        btn_registrar  = findViewById(R.id.btn_registrar_registro);
        txt_nacimiento_dia = findViewById(R.id.txt_dia_registro);
        txt_nacimiento_mes = findViewById(R.id.txt_mes_registro);
        txt_nacimiento_anio = findViewById(R.id.txt_ano_registro);

    }
    private void inicializarEventos(){
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si está vacio algun campo
               if(txt_cedula.getText().toString().isEmpty()||txt_nombre.getText().toString().isEmpty()||txt_apellido.getText().toString().isEmpty()||txt_tecnico.getText().toString().isEmpty()||txt_nacimiento_dia.getText().toString().isEmpty()||txt_nacimiento_anio.getText().toString().isEmpty()||txt_nacimiento_mes.getText().toString().isEmpty()){
                   Toast.makeText(getApplicationContext(),"Complete todos los campos",Toast.LENGTH_SHORT).show();
                   return;
               }
               //si la fecha no es valida
                try{
                    if((Integer.parseInt(txt_nacimiento_dia.getText().toString())>31)||
                            (Integer.parseInt(txt_nacimiento_mes.getText().toString())>12)||
                            (Integer.parseInt(txt_nacimiento_anio.getText().toString())>Calendar.getInstance().get(Calendar.YEAR))||
                            (Integer.parseInt(txt_nacimiento_dia.getText().toString())<1)||
                            (Integer.parseInt(txt_nacimiento_mes.getText().toString())<1)||
                            (Integer.parseInt(txt_nacimiento_anio.getText().toString())<1)) {
                        Toast.makeText(getApplicationContext(),"La fecha ingresada no es válida",Toast.LENGTH_SHORT).show();
                        return;

                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"La fecha ingresada no es válida",Toast.LENGTH_SHORT).show();
                    return;
                }

                ServicioBD servicio = new ServicioBD(RegistroActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                servicio.RegistrarPaciente(txt_nombre.getText().toString(),txt_apellido.getText().toString(),txt_cedula.getText().toString(),
                        (txt_nacimiento_dia.getText().toString().length()==1?"0"+txt_nacimiento_dia.getText().toString():txt_nacimiento_dia.getText().toString())+"/"+(txt_nacimiento_mes.getText().toString().length()==1?"0"+txt_nacimiento_mes.getText().toString():txt_nacimiento_mes.getText().toString())+"/"+
                                (txt_nacimiento_anio.getText().toString().length()==1?"000"+txt_nacimiento_anio.getText().toString():txt_nacimiento_anio.getText().toString().length()==2?"00"+txt_nacimiento_anio.getText().toString():txt_nacimiento_anio.getText().toString().length()==3?"0"+txt_nacimiento_anio.getText().toString():txt_nacimiento_anio.getText().toString())
                        ,txt_tecnico.getText().toString());

                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

}
