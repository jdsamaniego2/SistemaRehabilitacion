package com.example.sistemarehabilitacion.Vistas.Errores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;

public class ErrorConexionBdRemota extends AppCompatActivity {

    Button btn_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_conexion_bd_remota);
        inicializarComponentes();
        inicializarEventos();
    }

    private void inicializarComponentes(){
        btn_aceptar = findViewById(R.id.btn_aceptar_err_sinc);
    }
    private void inicializarEventos(){
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ErrorConexionBdRemota.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
