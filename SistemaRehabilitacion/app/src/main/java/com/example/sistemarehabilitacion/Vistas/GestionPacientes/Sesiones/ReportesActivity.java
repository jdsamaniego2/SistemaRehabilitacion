package com.example.sistemarehabilitacion.Vistas.GestionPacientes.Sesiones;

 
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sistemarehabilitacion.BaseDatos.Locales.IdentificadoresBD;

import com.example.sistemarehabilitacion.BaseDatos.Modelos.Sesion;
import com.example.sistemarehabilitacion.BaseDatos.Locales.ServicioBD;
import com.example.sistemarehabilitacion.R;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.ConfiguracionPopup;
import com.example.sistemarehabilitacion.Vistas.Ejercicios.MenuActivity;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores.AdaptadorItemPaciente;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Adaptadores.AdaptadorItemSesion;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.PacienteActivo;
import com.example.sistemarehabilitacion.Vistas.GestionPacientes.Pacientes.MainActivity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportesActivity extends AppCompatActivity {

    AdaptadorItemSesion items_sesiones;
    List<Sesion> sesions;
    ListView lv_sesiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//QUITA LA BARRA DE TITULO SUPERIOR DE LA VISTA
        setTitle("Sesiones Realizadas");
        setContentView(R.layout.activity_reportes);
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
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(ReportesActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void inicializarComponentes(){
        lv_sesiones = findViewById(R.id.lv_sesiones_reportes);
        ServicioBD sercicio = new ServicioBD(ReportesActivity.this.getApplicationContext(),IdentificadoresBD.nombre_bd,IdentificadoresBD.version_bd);
        sesions = sercicio.ConsultarSesionesPaciente(PacienteActivo.ObtenerPasienteSesion().getId());
        items_sesiones = new AdaptadorItemSesion(sesions,this.getApplicationContext());
        lv_sesiones.setAdapter(items_sesiones);
    }
    private void inicializarEventos(){

        lv_sesiones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ReportesActivity.this.getApplicationContext(),"ID: "+sesions.get(i).getId()+"\nFecha: "+sesions.get(i).getFecha()+"\nTipo: "+sesions.get(i).getTipo(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.pdf:
                File directorio = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "ReportesRehabilitacion");
                if (!directorio.exists()) {
                    directorio.mkdir();
                }
                Date fecha_documento = new Date() ;
                String fecha_documento_str = new SimpleDateFormat("yyyyMMdd_HHmmss").format(fecha_documento);
                File archivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Reporte_Local_Sesiones"+ fecha_documento_str + ".pdf");
                try {
                    archivo.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OutputStream outputstrem = null;
                try {
                    outputstrem = new FileOutputStream(archivo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Rectangle pagesize = new Rectangle(1440f, 720f);

                Document documento = new Document(pagesize, 36f, 72f, 108f, 180f);
                try {
                    PdfWriter.getInstance(documento, outputstrem);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                documento.open();
                Paragraph titulo = new Paragraph("Listado de sesiones del paciente "+PacienteActivo.ObtenerPasienteSesion().getNombre()+" "+PacienteActivo.ObtenerPasienteSesion().getApellido()+" con cédula "+PacienteActivo.ObtenerPasienteSesion().getCedula()+" almacenados localmente \n\n",
                        FontFactory.getFont("arial",22, Font.BOLD, BaseColor.BLUE)
                );
                try {
                    documento.add(titulo);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                PdfPTable tabla = new PdfPTable(6);
                tabla.addCell("#");
                tabla.addCell("TIEMPO EMPLEADO");
                tabla.addCell("REPETICIONES");
                tabla.addCell("TIPO");
                tabla.addCell("FECHA");
                tabla.addCell("SUPERVISOR");

                for(int i = 0 ; i < sesions.size() ; i++) {
                    tabla.addCell("" + i);
                    tabla.addCell((sesions.get(i).getTiempo()/60)+" minutos y "+(sesions.get(i).getTiempo()-(sesions.get(i).getTiempo()/60)*60)+" segundos");
                    tabla.addCell(sesions.get(i).getRepeticiones()+"");
                    tabla.addCell(sesions.get(i).getTipo()+"");
                    tabla.addCell(sesions.get(i).getFecha());
                    tabla.addCell(sesions.get(i).getSupervisor());
                }
                try {
                    documento.add(tabla);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                documento.close();


                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
