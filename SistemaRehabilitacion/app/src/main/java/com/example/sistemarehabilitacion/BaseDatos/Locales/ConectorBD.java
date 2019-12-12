package com.example.sistemarehabilitacion.BaseDatos.Locales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConectorBD extends SQLiteOpenHelper {


    public ConectorBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //al crear la base de datos
        db.execSQL(IdentificadoresBD.senencia_creacion_paciente);
        db.execSQL(IdentificadoresBD.senencia_creacion_sesion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //si hay una nueva version de base de datos
        db.execSQL(IdentificadoresBD.senencia_drop_sesion);
        db.execSQL(IdentificadoresBD.senencia_drop_paciente);
        onCreate(db);
    }
}
