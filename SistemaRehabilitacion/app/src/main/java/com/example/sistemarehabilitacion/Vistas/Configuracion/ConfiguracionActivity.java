package com.example.sistemarehabilitacion.Vistas.Configuracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Remotos.IdentificadoresApi;
import com.example.sistemarehabilitacion.Bluetooth.BluetoothActivity;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.ConfiguracionPopup;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;

import org.w3c.dom.Text;

public class ConfiguracionActivity extends AppCompatActivity {

    EditText txt_ip;
    RadioGroup btn_group_modelo;
    RadioButton btn_modelo1;
    RadioButton btn_modelo2;
    Button btn_guardar;
    SharedPreferences prefs ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_configuracion);

        inicializarComponentes();
        inicializarEventos();


    }


    private void inicializarComponentes(){
        prefs = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        btn_modelo1 = findViewById(R.id.rbtn_modelo1);
        btn_modelo2 = findViewById(R.id.rbtn_modelo2);
        btn_guardar = findViewById(R.id.btn_guardar_configuracion);
        txt_ip = findViewById(R.id.txt_ip_configuracion);
        btn_group_modelo = findViewById(R.id.rbtn_group_modelo);

        String ip = prefs.getString("ip", "sin_valor");
        txt_ip.setText(ip);

        String mac = prefs.getString("mac", "sin_valor");

        if(mac.equals("98:D3:33:80:59:0F")){
            btn_modelo1.setChecked(true);
        }else if(mac.equals("20:13:10:16:09:80")){
            btn_modelo2.setChecked(true);
        }


    }
    private void inicializarEventos(){
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_ip.getText().toString().isEmpty()||(!btn_modelo1.isChecked()&&!btn_modelo2.isChecked())){
                    Toast.makeText(ConfiguracionActivity.this.getApplicationContext(),"Complete todos los campos",Toast.LENGTH_SHORT).show();
                }
                else{
                    prefs.edit().putString("ip", txt_ip.getText().toString()).apply();
                    IdentificadoresApi.direcccion = txt_ip.getText().toString();


                    if(btn_modelo1.isChecked()){
                        prefs.edit().putString("mac", "98:D3:33:80:59:0F").apply();
                        BluetoothActivity.address = "98:D3:33:80:59:0F";


                    }
                    else{
                        prefs.edit().putString("mac", "20:13:10:16:09:80").apply();
                        BluetoothActivity.address = "20:13:10:16:09:80";
                    }
                    Intent intent = new Intent(ConfiguracionActivity.this, MainActivity.class);
                    startActivity(intent);

                }

            }
        });
    }

}
