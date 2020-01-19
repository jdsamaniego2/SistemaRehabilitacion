package com.example.sistemarehabilitacion.Vistas.BaseDeDatos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.SincronizadorLocalRemoto;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Errores.ErrorConexionBdRemota;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class SincronizacionActivity extends AppCompatActivity {


    List<Paciente> pacientes;
    ProgressBar pbar;
    ImageView img_satisfactorio;

    TextView lv_sincronizando;
    TextView lv_espere;

    Handler handler;
    Long tiempo;
    Button btn_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sincronizacion);
        inicializarComponentes();




        SincronizadorLocalRemoto sincronizador = new SincronizadorLocalRemoto();
        sincronizador.subir(SincronizacionActivity.this.getApplicationContext(), pacientes);
        sincronizador.bajar(SincronizacionActivity.this.getApplicationContext(), pacientes);






    }
    @Override
    public void onBackPressed(){ }

    private void inicializarComponentes(){
        pbar = findViewById(R.id.pbar_sincronizar);
        btn_aceptar = findViewById(R.id.btn_aceptar_sincronizar);
        lv_espere = findViewById(R.id.txt_espere);
        lv_sincronizando = findViewById(R.id.txt_sincronizado);
        lv_espere.setText("Por favor espere");
        lv_sincronizando.setText("Sincronizando");
        img_satisfactorio = findViewById(R.id.img_satisfactorio);
        img_satisfactorio.setVisibility(View.INVISIBLE);
        ServicioBD sercicio = new ServicioBD(SincronizacionActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        this.pacientes = sercicio.ConsultarPacientes();
        pbar.setMax(this.pacientes.size());
        tiempo = 0l;
        inicializarContadorSegundos();



    }

    private void inicializarContadorSegundos() {
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SincronizacionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                tiempo++;
                //Toast.makeText(SincronizacionActivity.this,""+tiempo,Toast.LENGTH_SHORT).show();
                if(tiempo >= 25){
                    img_satisfactorio.setVisibility(View.VISIBLE);
                    pbar.setVisibility(View.INVISIBLE);
                    lv_espere.setVisibility(View.INVISIBLE);
                    lv_sincronizando.setText("Sincronizado");
                    btn_aceptar.setVisibility(View.VISIBLE);
                }
                else {
                    handler.postDelayed(this, 1000);
                }
            }

        }, 1000);

    }

}