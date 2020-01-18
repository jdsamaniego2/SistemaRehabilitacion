package com.example.sistemarehabilitacion.Vistas.Ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.Bluetooth.BluetoothActivity;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;
import com.example.sistemarehabilitacion.Vistas.Musica.ReproductoMusica;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EjercicioManijaActivity extends BluetoothActivity implements View.OnClickListener {
    ReproductoMusica reproductor=new ReproductoMusica();

    TextView lbl_contador;
    Button btn_finalizar;

    ImageButton btnplay,btndetener;
    SeekBar sb;
    static MediaPlayer np;
    String aux="";
    Thread actualizarsb;
    TextView txtinicio,txtfin;
    ArrayList<File> canciones;
    int posicion;
    Uri u;

    private int repeticiones_totales=0,segundos_control=10;
    private int repeticion_actual=0;

    private int valor_anterior=0;


    private Date tiempo_inicio;
    private Date tiempo_fin;
    private long tiempo_sesion;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ejercicio_manija);
        inicializarComponentes();
        inicializarEventos();
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
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        np.stop();
        np = null;
        Intent intent = new Intent(EjercicioManijaActivity.this, MenuActivity.class);
        startActivity(intent);
    }
    private void inicializarComponentes(){

        tiempo_inicio = new Date();
        tiempo_sesion = 0;

        lbl_contador = findViewById(R.id.txt_contador_sesion_manija_man);
        btn_finalizar = findViewById(R.id.btn_finalizar_sesion_manija_man);


        btnplay=(ImageButton)findViewById(R.id.btnplay_man);
        btndetener=(ImageButton)findViewById(R.id.btndetener_man);
        sb=(SeekBar)findViewById(R.id.sbp_man);
        btnplay.setOnClickListener(this);
        btndetener.setOnClickListener(this);
        txtinicio=(TextView)findViewById(R.id.txtini_man);
        txtfin=(TextView)findViewById(R.id.txtfin_man);



        repeticiones_totales = Integer.parseInt(getIntent().getExtras().getString("repeticion"));
        repeticion_actual = 0;
        valor_anterior = 1;
        lbl_contador.setText( repeticion_actual+"/"+repeticiones_totales);
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {

                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);
                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        try{
                            dataInPrint = dataInPrint.replaceAll("\n", "");//ES IMPORTANTE QUITAR LOS SALTOS DE LINEA
                            String [] partes = dataInPrint.split(":");
                            String tipo_ejercicio = partes[0];
                            String valor_ejercicio = partes[1];
                            if(tipo_ejercicio.equals("MANIJA")){//solo se detecta si es tipo cierre (SOLO MODIFICAR LO DE ESTE IF EN LAS OTRAS VISTAS)

                                if(valor_anterior == 0){//si el valor anterior fue 0
                                    if(valor_ejercicio.equals("1")&&(repeticion_actual<repeticiones_totales)){
                                        repeticion_actual++;
                                        //*INCREMENTAR EL TIEMPO*/
                                        segundos_control =segundos_control+10;
                                        if (!np.isPlaying()) {
                                            np.start();
                                        }



                                        if(repeticion_actual==repeticiones_totales){
                                            tiempo_fin = new Date();
                                            tiempo_sesion =tiempo_fin.getTime() - tiempo_inicio.getTime();
                                            tiempo_sesion/=1000;
                                            /*
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                                            Date date = new Date();
                                            final String fecha = dateFormat.format(date);*/
                                            /*SUENA LA CANCION COMPLETA*/

                                            /*
                                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EjercicioCierreActivity.this);
                                            dialogo1.setTitle("Mensaje De Confirmación");
                                            dialogo1.setMessage("¿ Desea Guardad Esta Sesión ?\nTipo: Cierre"+"\nTiempo:"+180+"\nRepeticiones:"+repeticion_actual+"/"+repeticiones_totales+"\nFecha: "+fecha);
                                            dialogo1.setCancelable(false);
                                            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialogo1, int id) {

                                                    ServicioBD sercicio = new ServicioBD(EjercicioCierreActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                                                    sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,repeticion_actual,"CIERRE",fecha,"Normal");
                                                    Toast.makeText(EjercicioCierreActivity.this.getApplicationContext(),"Sesión Guardada Satisfactoriamente",Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(EjercicioCierreActivity.this, MenuActivity.class);
                                                    startActivity(intent);


                                                }
                                            });
                                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialogo1, int id) {
                                                    Toast.makeText(EjercicioCierreActivity.this.getApplicationContext(),"La Sesión No Fué Guardada ",Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(EjercicioCierreActivity.this, MenuActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            dialogo1.show();
                                            */


                                        }
                                        else{
                                            EjercicioManijaActivity.this.lbl_contador.setText( repeticion_actual+"/"+repeticiones_totales);
                                        }
                                        valor_anterior = 1;
                                    }
                                }
                                else{//si el valor anterior fue 1
                                    if(valor_ejercicio.equals("0")){
                                        valor_anterior = 0;
                                    }

                                }
                            }
                            else {
                                Toast.makeText(EjercicioManijaActivity.this.getApplicationContext(),"No valida!"+dataInPrint+"!"+"tiempo reproduccion"+segundos_control,Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(EjercicioManijaActivity.this.getApplicationContext(),"Excepcion no controlada",Toast.LENGTH_SHORT).show();
                        }
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };
    }

    private void inicializarEventos(){
        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(( repeticion_actual<repeticiones_totales )){
                    tiempo_fin = new Date();
                    tiempo_sesion =tiempo_fin.getTime() - tiempo_inicio.getTime();
                    tiempo_sesion/=1000;
                }
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EjercicioManijaActivity.this);
                dialogo1.setTitle("Mensaje De Confirmación");
                dialogo1.setMessage("¿Desea Finalizar Esta Sesión?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                        Date date = new Date();
                        final String fecha = dateFormat.format(date);
                        final int repeticiones_realizadas = repeticion_actual;
                        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(EjercicioManijaActivity.this);
                        dialogo2.setTitle("Mensaje De Confirmación");
                        String tiempo = "";
                        if(tiempo_sesion%60!=0){
                            tiempo = (tiempo_sesion/60)+" minutos y "+(tiempo_sesion-(tiempo_sesion/60)*60)+" segundos";
                        }
                        else{
                            tiempo =(tiempo_sesion/60)+" minutos";
                        }
                        dialogo2.setMessage("¿ Desea Guardad Esta Sesión ?\nTipo: Cierre"+"\nTiempo:"+tiempo+"\nRepeticiones:"+repeticiones_realizadas+"/"+repeticiones_totales+"\nFecha: "+fecha);
                        dialogo2.setCancelable(false);
                        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                np.stop();
                                np = null;
                                ServicioBD sercicio = new ServicioBD(EjercicioManijaActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                                sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),(int)tiempo_sesion,repeticiones_realizadas,"CIERRE",fecha,"Normal");
                                Toast.makeText(EjercicioManijaActivity.this.getApplicationContext(),"Sesión Guardada Satisfactoriamente",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EjercicioManijaActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                np.stop();
                                np = null;
                                Toast.makeText(EjercicioManijaActivity.this.getApplicationContext(),"La Sesión No Fué Guardada ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EjercicioManijaActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialogo2.show();

                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) { }
                });
                dialogo1.show();



            }
        });
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
                        aux=reproductor.getMRM(ejecution);
                        txtfin.setText(aux.toString().trim());
                        int seconds=(int) (ejecution/1000)%60;
                        if(repeticion_actual<repeticiones_totales){
                            if (seconds==segundos_control) {

                                np.pause();
                            }
                        }
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

            np = MediaPlayer.create(getApplication(), u);
            actualizarsb.start();
            np.start();
            txtinicio.setText(reproductor.getMRM(np.getDuration()));
            np.start();
        } catch (Exception e) {
        }
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
            case R.id.btndetener:
                if(np!=null) {
                    np.stop();
                    btnplay.setImageResource(R.drawable.play);
                    np=null;
                    inciar();
                }
                break;
        }
    }
}
