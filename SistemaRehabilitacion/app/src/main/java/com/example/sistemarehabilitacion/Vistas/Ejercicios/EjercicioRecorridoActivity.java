package com.example.sistemarehabilitacion.Vistas.Ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.Bluetooth.BluetoothActivity;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EjercicioRecorridoActivity extends BluetoothActivity {

    Button btn_finalizar;
    TextView lbl_contador;
    ProgressBar pb_estado_recorrido;


    private int total_puntos_recorrido = 6;
    private int punto_actual = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ejercicio_recorrido);
        inicializarComponentes();
        inicializarEventos();
        lbl_contador.setText( punto_actual+"/"+total_puntos_recorrido);
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
                            if(tipo_ejercicio.equals("CAMINO")){//solo se detecta si es tipo CAMINO (SOLO MODIFICAR LO DE ESTE IF EN LAS OTRAS VISTAS)
                                if(Integer.parseInt(valor_ejercicio) == punto_actual+1){
                                    pb_estado_recorrido.setProgress(punto_actual);
                                    punto_actual = Integer.parseInt(valor_ejercicio);
                                    lbl_contador.setText( punto_actual+"/"+total_puntos_recorrido);



                                    if(punto_actual==total_puntos_recorrido){
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                                        Date date = new Date();
                                        final String fecha = dateFormat.format(date);

                                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EjercicioRecorridoActivity.this);
                                        dialogo1.setTitle("Mensaje De Confirmación");
                                        dialogo1.setMessage("¿ Desea Guardad Esta Sesión ?\nTipo: Recorrido"+"\nTiempo:"+180+"\nRepeticiones:"+punto_actual+"/"+total_puntos_recorrido+"\nFecha: "+fecha);
                                        dialogo1.setCancelable(false);
                                        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogo1, int id) {

                                                ServicioBD sercicio = new ServicioBD(EjercicioRecorridoActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                                                sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,punto_actual,"RECORRIDO",fecha,"Normal");
                                                Toast.makeText(EjercicioRecorridoActivity.this.getApplicationContext(),"Sesión Guardada Satisfactoriamente",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(EjercicioRecorridoActivity.this, MenuActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogo1, int id) {
                                                Toast.makeText(EjercicioRecorridoActivity.this.getApplicationContext(),"La Sesión No Fué Guardada ",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(EjercicioRecorridoActivity.this, MenuActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                        dialogo1.show();

                                    }
                                }
                            }
                            else {
                                Toast.makeText(EjercicioRecorridoActivity.this.getApplicationContext(),"No valida!"+dataInPrint+"!",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(EjercicioRecorridoActivity.this.getApplicationContext(),"Excepcion no controlada",Toast.LENGTH_SHORT).show();
                        }
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

    }


    private void inicializarComponentes(){
        btn_finalizar = findViewById(R.id.btn_finalizar_sesion_recorrido);
        lbl_contador = findViewById(R.id.txt_contador_sesion_recorrido);
        pb_estado_recorrido = findViewById(R.id.pb_sesion_recorrido);
        pb_estado_recorrido.setMax(total_puntos_recorrido);
    }
    private void inicializarEventos(){
        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EjercicioRecorridoActivity.this);
                dialogo1.setTitle("Mensaje De Confirmación");
                dialogo1.setMessage("¿Desea Finalizar Esta Sesión?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                        Date date = new Date();
                        final String fecha = dateFormat.format(date);
                        final int repeticiones_realizadas = punto_actual;
                        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(EjercicioRecorridoActivity.this);
                        dialogo2.setTitle("Mensaje De Confirmación");
                        dialogo2.setMessage("¿ Desea Guardad Esta Sesión ?\nTipo: Recorrido"+"\nTiempo:"+180+"\nRepeticiones:"+repeticiones_realizadas+"/"+total_puntos_recorrido+"\nFecha: "+fecha);
                        dialogo2.setCancelable(false);
                        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {

                                ServicioBD sercicio = new ServicioBD(EjercicioRecorridoActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                                sercicio.RegistrarSesion(PacienteActivo.ObtenerPasienteSesion().getId(),180,repeticiones_realizadas,"RECORRIDO",fecha,"Normal");
                                Toast.makeText(EjercicioRecorridoActivity.this.getApplicationContext(),"Sesión Guardada Satisfactoriamente",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EjercicioRecorridoActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Toast.makeText(EjercicioRecorridoActivity.this.getApplicationContext(),"La Sesión No Fué Guardada ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EjercicioRecorridoActivity.this, MenuActivity.class);
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
