package com.example.sistemarehabilitacion.Vistas.GestionPacientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    Paciente paciente_seleccionado = null;//para mostrar el menu flotante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//QUITA LA BARRA DE TITULO SUPERIOR DE LA VISTA
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        inicializarEventos();

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
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                }
            });
            dialogo1.show();
            onResume();
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

    private void inicializarComponentes(){
        lv_pacientes = findViewById(R.id.lv_pacientes_main);
        btn_registrar = findViewById(R.id.btnft_registrar_main);
        btn_sincronizar = findViewById(R.id.btnft_sincronizar_main);

        ServicioBD sercicio = new ServicioBD(MainActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        pacientes = sercicio.ConsultarPacientes();
        items_pacientes = new AdaptadorItem(pacientes,this.getApplicationContext());
        lv_pacientes.setAdapter(items_pacientes);
        registerForContextMenu(lv_pacientes);
    }
    private void inicializarEventos(){
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(intent);
            }
        });
        btn_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //INTENT A VISTA PARA SINCRONIZAR
            }
        });
        lv_pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this.getApplicationContext(),"Cedula: "+pacientes.get(i).getCedula()+"\nFecha: "+pacientes.get(i).getNacimiento(),Toast.LENGTH_SHORT).show();
            }
        });
        lv_pacientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                paciente_seleccionado = pacientes.get(i);
                return false;
            }
        });
    }
}