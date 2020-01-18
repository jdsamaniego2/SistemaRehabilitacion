package com.example.sistemarehabilitacion.Vistas.Ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Musica.ListaReproduccion;


public class ConfigMalla extends AppCompatActivity {
    EditText repeticiones;
    Button btncomenzar;
    String modulo;
    TextView txtmodulo;
    RadioGroup grupDificultad;
    String dificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config_malla);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        repeticiones = (EditText) findViewById(R.id.txtrepeticiones);
        btncomenzar = (Button) findViewById(R.id.btniniciar);
        txtmodulo = (TextView) findViewById(R.id.txtmodulo);
        grupDificultad=(RadioGroup) findViewById(R.id.rgdificultad);
        modulo = (String) b.getString("modulo");
        txtmodulo.setText(modulo);
        DisplayMetrics medidasVentana = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int) (ancho * 0.85), (int) (alto * 0.85));

        grupDificultad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==R.id.rbalta){
                  dificultad="Alta";

               }else if(checkedId==R.id.rbmedia){
                   dificultad="Media";

               }else if(checkedId==R.id.rbbaja){
                   dificultad="Baja";
               }
            }
        });
        btncomenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ConfigMalla.this, ListaReproduccion.class).putExtra("repeticion", repeticiones.getText().toString()).putExtra("modulo", modulo).putExtra("dificultad",dificultad);
                startActivity(intent);
            }
        });

    }
}

