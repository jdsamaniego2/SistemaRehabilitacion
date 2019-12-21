package com.example.sistemarehabilitacion.Vistas.BaseDeDatos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.SincronizadorLocalRemoto;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;

import java.util.List;
import java.util.Map;

public class SincronizacionActivity extends AppCompatActivity {


    List<Paciente> pacientes;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sincronizacion);
        inicializarComponentes();




        SincronizadorLocalRemoto sincronizador = new SincronizadorLocalRemoto();
        sincronizador.subir(SincronizacionActivity.this.getApplicationContext(), pacientes);
        sincronizador.bajar(SincronizacionActivity.this.getApplicationContext(), pacientes);






    }

    private void inicializarComponentes(){
        pbar = findViewById(R.id.pbar_sincronizar);
        ServicioBD sercicio = new ServicioBD(SincronizacionActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        this.pacientes = sercicio.ConsultarPacientes();
        pbar.setMax(this.pacientes.size());

    }


}