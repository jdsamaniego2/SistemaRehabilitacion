package com.example.sistemarehabilitacion.Vistas.Musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReproductoMusica extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnplay,btndetener,btnlist;
    SeekBar sb;
    static MediaPlayer np;
    ArrayList<File> canciones;
    Thread actualizarsb;
    int posicion;
    Uri u;
    String aux="";
    TextView txtinicio,txtfin,nombre,txtmodulo;
    Button btnfin;
    String modulo;
    int repeticionesConfig;
    String dificultad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducto_musica);
        inicializarComponentes();
        hiloReproduccion();

        if(np!=null){

            np.stop();
        }

        inciar();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                np.seekTo(seekBar.getProgress());
            }
        });
    }

    private void inicializarComponentes(){

        btnplay=(ImageButton)findViewById(R.id.btnplay);
        btndetener=(ImageButton)findViewById(R.id.btndetener);
        sb=(SeekBar)findViewById(R.id.sbp);
        btnlist=(ImageButton)findViewById(R.id.btnlist);
        txtmodulo=(TextView) findViewById(R.id.txtmodulo);
        btnfin= findViewById(R.id.btnfinalizar);
        btnplay.setOnClickListener(this);
        btndetener.setOnClickListener(this);
        btndetener.setOnClickListener(this);
        btnfin.setOnClickListener(this);

        btnlist.setOnClickListener(this);
        txtinicio=(TextView)findViewById(R.id.txtini);
        txtfin=(TextView)findViewById(R.id.txtfin);
        nombre=(TextView)findViewById(R.id.txtnombre);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        String rep=b.getString("repeticion");

        repeticionesConfig=Integer.parseInt(rep);

        modulo= b.getString("modulo");
        dificultad=b.getString("dificultad");
        txtmodulo.setText(modulo);






    }

    private  void hiloReproduccion(){



        actualizarsb=new Thread(){
            @Override
            public void run() {
                super.run();
                int duracion=np.getDuration();
                sb.setMax(duracion);
                int posicionActual=0;
                int ejecution=0;

                boolean ban=false;

                while(posicionActual< duracion){
                    try{
                        sleep(500);
                        posicionActual=np.getCurrentPosition();
                        sb.setProgress(posicionActual);
                        ejecution=sb.getProgress();
                        aux=getMRM(ejecution);
                        txtfin.setText(aux.toString().trim());



                    }catch (Exception e){
                        //txtfin.setText(aux);
                    }

                }
            }
        };
    }


    private void inciar(){
        try {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            canciones = (ArrayList) b.getParcelableArrayList("canciones");
            posicion = (int) b.getInt("pos", 0);
            u = Uri.parse(canciones.get(posicion).toString());
            nombre.setText(canciones.get(posicion).getName().toString());
            np = MediaPlayer.create(getApplication(), u);
            actualizarsb.start();
            np.start();
            txtinicio.setText(getMRM(np.getDuration()));
            np.start();
        } catch (Exception e) {


        }
    }


    private String getMRM(int milliseconds){
        int seconds=(int) (milliseconds/1000)%60;
        int minutes=(int)((milliseconds/(1000*60))%60);
        int hours=(int)((milliseconds/(1000*60*60))%24);
        String aux="";
        aux=((hours<10)?"0"+hours:hours)+":"+((minutes<10)?"0"+minutes:minutes)+":"+((seconds<10)?"0"+seconds:seconds);
        return aux;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnplay:
                if(np!=null){
                    if(np.isPlaying()){
                        btnplay.setImageResource(R.drawable.play);
                        np.pause();
                    }else{
                        btnplay.setImageResource(R.drawable.pausa);
                        np.start();
                    }
                }else {

                    inciar();
                }
                break;
            case R.id.btnlist:
                startActivity(new Intent(getApplicationContext(), ListaReproduccion.class).putExtra("pos",posicion).putExtra("canciones",canciones));
                break;
            case R.id.btndetener:
                if(np!=null) {
                    np.stop();
                    btnplay.setImageResource(R.drawable.play);
                    np=null;
                }
                break;
            case R.id.btnfinalizar:
                if(PacienteActivo.HayUnaSesion()){
                    int idpaciente=PacienteActivo.ObtenerPasienteSesion().getId();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);


                    ServicioBD sercicio = new ServicioBD(ReproductoMusica.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                    long ids = sercicio.RegistrarSesion(idpaciente,180,repeticionesConfig,modulo,fecha,dificultad);

                    Toast.makeText(ReproductoMusica.this.getApplicationContext(),"SESION REGISTRADA: "+ids,Toast.LENGTH_LONG).show();
                    if(np.isPlaying()) {

                        np.stop();
                    }
                    np=null;
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));


                }else{

                    Toast.makeText(ReproductoMusica.this.getApplicationContext(),"SESION NO REGISTRADA: ",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }
                break;




        }


    }



    public ArrayList<File> EncontrarCanciones(File root){



        ArrayList<File> canciones = new ArrayList<File>();
        File[] archivos=root.listFiles();


        for(File lista : archivos){


            if(lista.isDirectory() && !lista.isHidden()){
                canciones.addAll(EncontrarCanciones(lista));
            }else{
                if(lista.getName().endsWith(".mp3") || lista.getName().endsWith(".vav")){

                    canciones.add(lista);
                }

            }


        }
        return canciones;
    }

    public String[] cargarCanciones(){

        final ArrayList<File> canciones = EncontrarCanciones(Environment.getExternalStorageDirectory());

        String[] items=new String[canciones.size()];

        for (int i =0; i<canciones.size();i++){
            items[i]=canciones.get(i).getName().toString().replace(".mp3","").replace(".vav","").toLowerCase();

        }
        return  items;

    }
}
