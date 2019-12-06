package com.example.sistemarehabilitacion.Vistas.GestionPacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.example.sistemarehabilitacion.BaseDatos.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.ServicioBD;

import com.example.sistemarehabilitacion.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    AdaptadorItem items_pacientes;
    List<Paciente> pacientes;


    ListView lv_pacientes;
    FloatingActionButton btn_registrar;
    FloatingActionButton btn_sincronizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//QUITA LA BARRA DE TITULO SUPERIOR DE LA VISTA
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        inicializarEventos();

    }

    private void inicializarComponentes(){
        lv_pacientes = findViewById(R.id.lv_pacientes_main);
        btn_registrar = findViewById(R.id.btnft_registrar_main);
        btn_sincronizar = findViewById(R.id.btnft_sincronizar_main);

        ServicioBD sercicio = new ServicioBD(MainActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        pacientes = sercicio.ConsultarPacientes();
        items_pacientes = new AdaptadorItem(pacientes,this.getApplicationContext());
        lv_pacientes.setAdapter(items_pacientes);
    }
    private void inicializarEventos(){
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

}
