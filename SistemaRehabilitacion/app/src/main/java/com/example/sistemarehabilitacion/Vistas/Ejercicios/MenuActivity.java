package com.example.sistemarehabilitacion.Vistas.Ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Sesiones.ReportesActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;
import com.example.sistemarehabilitacion.Vistas.Musica.ListaReproduccion;

public class MenuActivity extends AppCompatActivity {

    //Button btn_malla;
    Button btn_cierre;
    Button btn_laberinto;
    Button btn_timon;
    Button btn_reportes;
    TextView txt_cedula;
    TextView txt_nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        inicializarComponentes();
        inicializarEventos();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void  inicializarComponentes(){
        //btn_malla = findViewById(R.id.btn_malla_menu);
        btn_cierre = findViewById(R.id.btn_cierre_menu);
        btn_laberinto = findViewById(R.id.btn_laberinto_menu);
        btn_timon = findViewById(R.id.btn_timon_menu);
        btn_reportes = findViewById(R.id.btn_reportes_menu);
        txt_cedula = findViewById(R.id.txt_cedula_menu);
        txt_nombre = findViewById(R.id.txt_nombre_menu);

        if(PacienteActivo.HayUnaSesion()){
            txt_cedula.setText(PacienteActivo.ObtenerPasienteSesion().getCedula());
            txt_nombre.setText(PacienteActivo.ObtenerPasienteSesion().getNombre()+" "+ PacienteActivo.ObtenerPasienteSesion().getApellido());
        }

    }
    private  void inicializarEventos(){
        btn_reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportesActivity.class);
                startActivity(intent);
            }
        });

/*
        btn_malla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);

                ServicioBD sercicio = new ServicioBD(MenuActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                long id = sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,5,"Malla",fecha,EncargadoActivity.encargado_actual);
                Toast.makeText(MenuActivity.this.getApplicationContext(),"SESION REGISTRADA: "+id,Toast.LENGTH_LONG).show();
            }
        });
        btn_cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);

                ServicioBD sercicio = new ServicioBD(MenuActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                long id = sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,5,"Cierre",fecha,EncargadoActivity.encargado_actual);
                Toast.makeText(MenuActivity.this.getApplicationContext(),"SESION REGISTRADA: "+id,Toast.LENGTH_LONG).show();
            }
        });
        btn_laberinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);

                ServicioBD sercicio = new ServicioBD(MenuActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                long id = sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,5,"Laberinto",fecha,EncargadoActivity.encargado_actual);
                Toast.makeText(MenuActivity.this.getApplicationContext(),"SESION REGISTRADA: "+id,Toast.LENGTH_LONG).show();
            }
        });
        btn_timon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);
                ServicioBD sercicio = new ServicioBD(MenuActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                long id = sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,5,"Timon",fecha,EncargadoActivity.encargado_actual);
                Toast.makeText(MenuActivity.this.getApplicationContext(),"SESION REGISTRADA: "+id,Toast.LENGTH_LONG).show();
            }
        });*/
        btn_cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ConfiguracionPopup.class).putExtra("modulo","Cierre");
                startActivity(intent);
            }
        });

        btn_timon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ConfiguracionPopup.class).putExtra("modulo","Manijas");
                startActivity(intent);
            }
        });

        btn_laberinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ListaReproduccion.class).putExtra("modulo","Laberinto");
                startActivity(intent);
            }
        });
        /*btn_malla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ConfigMalla.class).putExtra("modulo","Malla");
                startActivity(intent);
            }
        });*/

    }
}
