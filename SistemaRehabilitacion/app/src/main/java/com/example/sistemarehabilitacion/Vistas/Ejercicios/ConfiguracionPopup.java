package com.example.sistemarehabilitacion.Vistas.Ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Musica.ListaReproduccion;

public class ConfiguracionPopup extends AppCompatActivity {
     EditText repeticiones;
     Button btncomenzar;
     String modulo;
     TextView txtmodulo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_popup);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        repeticiones=(EditText) findViewById(R.id.txtrepeticiones);
        btncomenzar=(Button) findViewById(R.id.btniniciar);
        txtmodulo=(TextView) findViewById(R.id.txtmodulo);

        modulo=(String) b.getString("modulo");
        txtmodulo.setText(modulo);
        DisplayMetrics medidasVentana= new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho= medidasVentana.widthPixels;
        int alto= medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho*0.85),(int)(alto*0.85));

        btncomenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(Integer.parseInt(repeticiones.getText().toString())>0){
                        Intent intent = null;
                        intent = new Intent(ConfiguracionPopup.this, ListaReproduccion.class).putExtra("repeticion",repeticiones.getText().toString()).putExtra("modulo",modulo);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ConfiguracionPopup.this.getApplicationContext(),"Cantidad no válida",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ConfiguracionPopup.this.getApplicationContext(),"Incique una cantidad de repeticiones",Toast.LENGTH_SHORT).show();
                }


            }
        });




    }


}
