package com.example.sistemarehabilitacion.Vistas.Musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.EjercicioCierreActivity;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.EjercicioRecorridoActivity;

public class ListaReproduccion extends AppCompatActivity {
    ListView lv;
    String modulo;
    String repeticion;
    String dificultad=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reproduccion);

        lv=(ListView) findViewById(R.id.lstCancion);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        repeticion=b.getString("repeticion","0");
        modulo=b.getString("modulo");
        dificultad=b.getString("dificultad");


        final ReproductoMusica cn= new ReproductoMusica();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.cancion,R.id.itemcancion,cn.cargarCanciones());
       lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(modulo.equals("Cierre")){
                   startActivity(new Intent(getApplicationContext(), EjercicioCierreActivity.class).putExtra("pos",position).putExtra("canciones",cn.EncontrarCanciones(Environment.getExternalStorageDirectory())).putExtra("repeticion",repeticion).putExtra("modulo",modulo).putExtra("dificultad",dificultad));
               }else if(modulo.equals("Timon")){
                   //pendiente
               }else if(modulo.equals("Laberinto")){
                   startActivity(new Intent(getApplicationContext(), EjercicioRecorridoActivity.class).putExtra("pos",position).putExtra("canciones",cn.EncontrarCanciones(Environment.getExternalStorageDirectory())).putExtra("repeticion",repeticion).putExtra("modulo",modulo).putExtra("dificultad",dificultad));

               }else if(modulo.equals("Malla")){
                   //pendiente
               }
          }
        });
    }
}
