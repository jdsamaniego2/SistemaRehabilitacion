package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Request;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi.AdaptadorApi;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.ClienteApi.IServiciosApi;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.IdentificadoresApi;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.SincronizadorLocalRemoto;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.SincronizadorPaciente;
import com.example.sistemarehabilitacion.BaseDatos.Remotos.SincronizadorSesion;
import com.example.sistemarehabilitacion.Bluetooth.BluetoothActivity;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.BaseDeDatos.SincronizacionActivity;
import com.example.sistemarehabilitacion.Vistas.Configuracion.ConfiguracionActivity;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.ConfiguracionPopup;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;
import com.example.sistemarehabilitacion.Vistas.Errores.ErrorConexionBdRemota;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores.AdaptadorItemPaciente;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111 ;

    AdaptadorItemPaciente items_pacientes;
    List<Paciente> pacientes;


    ListView lv_pacientes;
    FloatingActionButton btn_registrar;
    FloatingActionButton btn_sincronizar;
    FloatingActionButton btn_configuraciones;

    Paciente paciente_seleccionado = null;//para mostrar el menu flotante
    SharedPreferences prefs ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //QUITA LA BARRA DE TITULO SUPERIOR DE LA VISTA
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        setTitle("Mis Pacientes");
        inicializarComponentes();
        inicializarEventos();
        Toast.makeText(MainActivity.this,BluetoothActivity.address,Toast.LENGTH_LONG).show();

        solicitarPermisos();
    }


    private void solicitarPermisos() {

        int permissionCheck = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

    }






    @Override
    protected void onResume() {
        super.onResume();
        inicializarComponentes();
        inicializarEventos();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_paciente,menu);
        menu.setHeaderTitle("Opciones");
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.op_eliminar_menu){

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Advertencia");
            dialogo1.setMessage("¿Está seguro que desea eliminar al paciente con cédula ("+paciente_seleccionado.getCedula()+")?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    ServicioBD sercicio = new ServicioBD(MainActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                    sercicio.EliminarPaciente(paciente_seleccionado.getId());
                    inicializarComponentes();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                }
            });
            dialogo1.show();

        }
        else if(item.getItemId() == R.id.op_editar_menu){
            Intent intent = new Intent(MainActivity.this, EdicionActivity.class);
            intent.putExtra("paciente", paciente_seleccionado);
            paciente_seleccionado = null;
            startActivity(intent);
        }
        else{

            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed(){
        //super.onBackPressed();

    }

    private void inicializarComponentes(){
        prefs = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        lv_pacientes = findViewById(R.id.lv_pacientes_main);
        btn_registrar = findViewById(R.id.btnft_registrar_main);
        btn_sincronizar = findViewById(R.id.btnft_sincronizar_main);
        btn_configuraciones = findViewById(R.id.btn_configuaciones);


        ServicioBD sercicio = new ServicioBD(MainActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        pacientes = sercicio.ConsultarPacientes();
        items_pacientes = new AdaptadorItemPaciente(pacientes,this.getApplicationContext());
        lv_pacientes.setAdapter(items_pacientes);
        registerForContextMenu(lv_pacientes);



        //String ip = prefs.getString("ip", "sin_valor");
/*
        if(ip.equals("sin_valor")){
            prefs.edit().putString("ip", "192.168.1.177").apply();
            ip = "192.168.1.177";
            IdentificadoresApi.direcccion = ip;
        }
        else{
            IdentificadoresApi.direcccion = ip;
        }
*/
        String mac = prefs.getString("mac", "sin_valor");
        if(mac.equals("sin_valor")){
            prefs.edit().putString("mac", "98:D3:33:80:59:0F").apply();
            mac = "98:D3:33:80:59:0F";
            BluetoothActivity.address = mac;
        }
        else{
            BluetoothActivity.address = mac;
        }

    }
    private void inicializarEventos(){
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
        btn_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //INTENT A VISTA PARA SINCRONIZAR

                //ServicioBD sercicio = new ServicioBD(MainActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                //long id = sercicio.RegistrarSesion(13,180,5,"Timon","15/08/2019","Normal");
                //Toast.makeText(MainActivity.this.getApplicationContext(),"SESION REGISTRADA: "+id,Toast.LENGTH_LONG).show();

                /*OBTENER TODOS LOS PACIENTES
                IServiciosApi api = AdaptadorApi.getApiservice();
                Call<List<Paciente>> call = api.getPacientes();
                call.enqueue(new Callback<List<Paciente>>() {
                    @Override
                    public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                        if(response.isSuccessful()){
                            List<Paciente> pacientes = response.body();
                            MainActivity.this.pacientes = pacientes;
                            items_pacientes = new AdaptadorItemPaciente(pacientes,MainActivity.this.getApplicationContext());
                            lv_pacientes.setAdapter(items_pacientes);
                            registerForContextMenu(lv_pacientes);
                            Toast.makeText(MainActivity.this,"Hay "+pacientes.size()+" pacientes",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"ERROR LA RESPUESTA",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Paciente>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"ERROR EN LA PETICIÓN",Toast.LENGTH_LONG).show();
                    }
                });

                */
                /*OBTENER UN PACIENTE
                IServiciosApi api = AdaptadorApi.getApiservice();
                Call<List<Paciente>> call = api.getPaciente(Long.parseLong(""+1));
                call.enqueue(new Callback<List<Paciente>>() {
                    @Override
                    public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                        if(response.isSuccessful()){
                            List<Paciente> pacientes = response.body();
                            MainActivity.this.pacientes = pacientes;
                            items_pacientes = new AdaptadorItemPaciente(pacientes,MainActivity.this.getApplicationContext());
                            lv_pacientes.setAdapter(items_pacientes);
                            registerForContextMenu(lv_pacientes);
                            Toast.makeText(MainActivity.this,"Hay "+pacientes.size()+" pacientes",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"ERROR LA RESPUESTA",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Paciente>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"ERROR EN LA PETICIÓN",Toast.LENGTH_LONG).show();
                    }
                });
                */


/*GUARDAR UN CLIENTE
                IServiciosApi api = AdaptadorApi.getApiservice();
                Call<Request> call =  api.postPaciente( new Paciente("PACIENTE1","APELLIDO1","0601","07-04-98","JAIRO") );

                call.enqueue(new Callback<Request>() {
                    @Override
                    public void onResponse(Call<Request> call, Response<Request> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode()==200){
                                Toast.makeText(MainActivity.this,"Guardado",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"ERROR AL GUARDAR",Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this,"ERROR AL GUARDAR",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Request> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"ERROR EN LA PETICIÓN",Toast.LENGTH_LONG).show();
                    }
                });

*/

/*
                IServiciosApi api = AdaptadorApi.getApiservice();
                Call<Request> call =  api.editPaciente(Long.parseLong( ""+38), new Paciente("PACIENTE1","APELLIDO1","0601","07-04-98","JAIRO") );

                call.enqueue(new Callback<Request>() {
                    @Override
                    public void onResponse(Call<Request> call, Response<Request> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode()==200){
                                Toast.makeText(MainActivity.this,"Editado",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"ERROR AL EDITAR",Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this,"ERROR AL EDITAR",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Request> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"ERROR EN LA EDITAR",Toast.LENGTH_LONG).show();
                    }
                });

*/
                /*ELIMINAR
                IServiciosApi api = AdaptadorApi.getApiservice();
                Call<Request> call =  api.deletePaciente(Long.parseLong( ""+39));

                call.enqueue(new Callback<Request>() {
                    @Override
                    public void onResponse(Call<Request> call, Response<Request> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode()==200){
                                Toast.makeText(MainActivity.this,"Eliminado",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"ERROR AL ELIMINAR",Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this,"ERROR AL ELIMINAR",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Request> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"ERROR EN LA ELIMINAR",Toast.LENGTH_LONG).show();
                    }
                });
                */

                /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);*/

              //  SincronizadorPaciente s = new SincronizadorPaciente();
               // s.SincronizarPaciente(MainActivity.this.getApplicationContext(),new Paciente("JAIRO","SAMANIEGO","0604178542","07-04-98",fecha,"Jairo Daniel S"));
                //Toast.makeText(MainActivity.this.getApplicationContext(),s.obtenerPaciente(MainActivity.this.getApplicationContext()).getNombre(),Toast.LENGTH_LONG).show();
                //SincronizadorSesion s = new SincronizadorSesion();
               // s.SincronizarSesion(MainActivity.this.getApplicationContext(),new Sesion(0,0,0,0,"","05-05-2019",""),new Paciente("JAIRO","SAMANIEGO","06021785411","07-04-98","Jairo Daniel S"));


                Intent intent = new Intent(MainActivity.this, SincronizacionActivity.class);
                startActivity(intent);



            }
        });
        lv_pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PacienteActivo.IniciarSesion(pacientes.get(i));
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this.getApplicationContext(),"ID: "+pacientes.get(i).getId()+"\nCedula: "+pacientes.get(i).getCedula()+"\nFecha: "+pacientes.get(i).getNacimiento(),Toast.LENGTH_SHORT).show();
            }
        });
        lv_pacientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                paciente_seleccionado = pacientes.get(i);
                return false;
            }
        });
        btn_configuraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
            }
        });
    }
}