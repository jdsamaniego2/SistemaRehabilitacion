package com.example.sistemarehabilitacion.Vistas.Ejercicios;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.Bluetooth.BluetoothActivity;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;
import com.example.sistemarehabilitacion.Vistas.Musica.ReproductoMusica;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EjercicioCierreActivity extends BluetoothActivity {

    TextView lbl_contador;
    Button btn_finalizar;


    private int repeticiones_totales;
    private int repeticion_actual;

    private int valor_anterior;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ejercicio_cierre);
        inicializarComponentes();
        inicializarEventos();
    }

    private void inicializarComponentes(){
        lbl_contador = findViewById(R.id.txt_contador_sesion_cierre);
        btn_finalizar = findViewById(R.id.btn_finalizar_sesion_cierre);

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
                            if(tipo_ejercicio.equals("CIERRE")){//solo se detecta si es tipo cierre (SOLO MODIFICAR LO DE ESTE IF EN LAS OTRAS VISTAS)

                                if(valor_anterior == 0){//si el valor anterior fue 0
                                    if(valor_ejercicio.equals("1")){
                                        repeticion_actual++;
                                        //*INCREMENTAR EL TIEMPO*/

                                        if(repeticion_actual==repeticiones_totales){

                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                                            Date date = new Date();
                                            final String fecha = dateFormat.format(date);
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
                                            EjercicioCierreActivity.this.lbl_contador.setText( repeticion_actual+"/"+repeticiones_totales);
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
                                Toast.makeText(EjercicioCierreActivity.this.getApplicationContext(),"No valida!"+dataInPrint+"!",Toast.LENGTH_SHORT).show();
                            }
                       }catch (Exception e){
                            Toast.makeText(EjercicioCierreActivity.this.getApplicationContext(),"Excepcion no controlada",Toast.LENGTH_SHORT).show();
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

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EjercicioCierreActivity.this);
                dialogo1.setTitle("Mensaje De Confirmación");
                dialogo1.setMessage("¿Desea Finalizar Esta Sesión?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                        Date date = new Date();
                        final String fecha = dateFormat.format(date);
                        final int repeticiones_realizadas = repeticion_actual;
                        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(EjercicioCierreActivity.this);
                        dialogo2.setTitle("Mensaje De Confirmación");
                        dialogo2.setMessage("¿ Desea Guardad Esta Sesión ?\nTipo: Cierre"+"\nTiempo:"+180+"\nRepeticiones:"+repeticiones_realizadas+"/"+repeticiones_totales+"\nFecha: "+fecha);
                        dialogo2.setCancelable(false);
                        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {

                                ServicioBD sercicio = new ServicioBD(EjercicioCierreActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                                sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,repeticiones_realizadas,"CIERRE",fecha,"Normal");
                                Toast.makeText(EjercicioCierreActivity.this.getApplicationContext(),"Sesión Guardada Satisfactoriamente",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EjercicioCierreActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Toast.makeText(EjercicioCierreActivity.this.getApplicationContext(),"La Sesión No Fué Guardada ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EjercicioCierreActivity.this, MenuActivity.class);
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

}
