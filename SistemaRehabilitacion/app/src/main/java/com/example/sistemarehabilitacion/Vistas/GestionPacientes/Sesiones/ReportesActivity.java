package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Sesiones;

 
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.ConfiguracionPopup;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores.AdaptadorItemSesion;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;


import java.util.List;

public class ReportesActivity extends AppCompatActivity {

    AdaptadorItemSesion items_sesiones;
    List<Sesion> sesions;
    ListView lv_sesiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//QUITA LA BARRA DE TITULO SUPERIOR DE LA VISTA
        setTitle("Sesiones Realizadas");
        setContentView(R.layout.activity_reportes);
        inicializarComponentes();
        inicializarEventos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inicializarComponentes();
        inicializarEventos();

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(ReportesActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void inicializarComponentes(){
        lv_sesiones = findViewById(R.id.lv_sesiones_reportes);
        ServicioBD sercicio = new ServicioBD(ReportesActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        sesions = sercicio.ConsultarSesionesPaciente(PacienteActivo.ObtenerPasienteSesion().getId());
        items_sesiones = new AdaptadorItemSesion(sesions,this.getApplicationContext());
        lv_sesiones.setAdapter(items_sesiones);
    }
    private void inicializarEventos(){

        lv_sesiones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ReportesActivity.this.getApplicationContext(),"ID: "+sesions.get(i).getId()+"\nFecha: "+sesions.get(i).getFecha()+"\nTipo: "+sesions.get(i).getTipo(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
