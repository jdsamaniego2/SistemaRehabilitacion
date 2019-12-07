package com.example.sistemarehabilitacion.Vistas.GestionPacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.IdentificadoresBD;
import com.example.sistemarehabilitacion.BaseDatos.Modelos.Paciente;
import com.example.sistemarehabilitacion.BaseDatos.ServicioBD;
import com.example.sistemarehabilitacion.R;

import java.util.Calendar;

public class EdicionActivity extends AppCompatActivity {

    TextView txt_cedula;
    TextView txt_nombre;
    TextView txt_apellido;
    TextView txt_nacimiento_dia;
    TextView txt_nacimiento_mes;
    TextView txt_nacimiento_anio;
    TextView txt_tecnico;

    Button btn_editar;

    Paciente paciente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);
        inicializarComponentes();
        inicializarEventos();

        paciente = (Paciente) getIntent().getSerializableExtra("paciente");
        txt_cedula.setText(paciente.getCedula());
        txt_nombre.setText(paciente.getNombre());
        txt_apellido.setText(paciente.getApellido());
        txt_tecnico.setText(paciente.getTecnico());
        txt_nacimiento_dia.setText(paciente.getNacimiento().charAt(0)+""+paciente.getNacimiento().charAt(1));
        txt_nacimiento_mes.setText(paciente.getNacimiento().charAt(3)+""+paciente.getNacimiento().charAt(4));
        txt_nacimiento_anio.setText(paciente.getNacimiento().charAt(6)+""+paciente.getNacimiento().charAt(7)+""+paciente.getNacimiento().charAt(8)+""+paciente.getNacimiento().charAt(9));



    }
    private void inicializarComponentes(){
        txt_cedula = findViewById(R.id.txt_cedula_edicion);
        txt_nombre = findViewById(R.id.txt_nombre_edicion);
        txt_apellido = findViewById(R.id.txt_apellido_edicion);
        txt_tecnico  = findViewById(R.id.txt_tecnico_edicion);
        btn_editar  = findViewById(R.id.btn_editar_edicion);
        txt_nacimiento_dia = findViewById(R.id.txt_dia_edicion);
        txt_nacimiento_mes = findViewById(R.id.txt_mes_edicion);
        txt_nacimiento_anio = findViewById(R.id.txt_ano_edicion);
    }
    private void inicializarEventos(){
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//si está vacio algun campo
                if(txt_cedula.getText().toString().isEmpty()||txt_nombre.getText().toString().isEmpty()||txt_apellido.getText().toString().isEmpty()||txt_tecnico.getText().toString().isEmpty()||txt_nacimiento_dia.getText().toString().isEmpty()||txt_nacimiento_anio.getText().toString().isEmpty()||txt_nacimiento_mes.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Complete todos los campos",Toast.LENGTH_SHORT).show();
                    return;
                }
                //si la fecha no es valida
                try{
                    if((Integer.parseInt(txt_nacimiento_dia.getText().toString())>31)||
                            (Integer.parseInt(txt_nacimiento_mes.getText().toString())>12)||
                            (Integer.parseInt(txt_nacimiento_anio.getText().toString())> Calendar.getInstance().get(Calendar.YEAR))||
                            (Integer.parseInt(txt_nacimiento_dia.getText().toString())<1)||
                            (Integer.parseInt(txt_nacimiento_mes.getText().toString())<1)||
                            (Integer.parseInt(txt_nacimiento_anio.getText().toString())<1)) {
                        Toast.makeText(getApplicationContext(),"La fecha ingresada no es válida",Toast.LENGTH_SHORT).show();
                        return;

                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"La fecha ingresada no es válida",Toast.LENGTH_SHORT).show();
                    return;
                }

                paciente.setNombre(txt_nombre.getText().toString());
                paciente.setApellido(txt_apellido.getText().toString());
                paciente.setTecnico(txt_tecnico.getText().toString());
                paciente.setNacimiento((txt_nacimiento_dia.getText().toString().length()==1?"0"+txt_nacimiento_dia.getText().toString():txt_nacimiento_dia.getText().toString())+"/"+(txt_nacimiento_mes.getText().toString().length()==1?"0"+txt_nacimiento_mes.getText().toString():txt_nacimiento_mes.getText().toString())+"/"+
                        (txt_nacimiento_anio.getText().toString().length()==1?"000"+txt_nacimiento_anio.getText().toString():txt_nacimiento_anio.getText().toString().length()==2?"00"+txt_nacimiento_anio.getText().toString():txt_nacimiento_anio.getText().toString().length()==3?"0"+txt_nacimiento_anio.getText().toString():txt_nacimiento_anio.getText().toString())
                );
                ServicioBD servicio = new ServicioBD(EdicionActivity.this.getApplicationContext(), IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
                servicio.EditarPaciente(paciente.getNombre(),paciente.getApellido(),paciente.getCedula(),paciente.getNacimiento(),paciente.getTecnico());

                Intent intent = new Intent(EdicionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
