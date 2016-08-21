package com.gt.dev.lazaro.elcaldo.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Lazaro on 10/9/2015.
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase bd;
    private static final String TABLA_PLATO = "platocomida";
    private static final String COLUMNA_NOMBRE = "nombre";
    private static final String COLUMNA_INGREDIENTES = "ingredientes";
    private static final String COLUMNA_PREPARACION = "preparacion";
    private static final String COLUMNA_IMAGEN = "imagen";

    public DBManager(Context context) {
        helper = DBHelper.getInstancia(context);
        bd = helper.getWritableDatabase();
    }

    public Plato selectPlato(String nombre) {
        Plato plato = null;

        try {
            Cursor c = null;
            c = bd.rawQuery("SELECT * FROM " + TABLA_PLATO + " WHERE " + COLUMNA_NOMBRE + " like \"" + nombre + "\"", null);
            c.moveToFirst();

            String nomb = c.getString(c.getColumnIndex(COLUMNA_NOMBRE));
            String ingredientes = c.getString(c.getColumnIndex(COLUMNA_INGREDIENTES));
            String preparacion = c.getString(c.getColumnIndex(COLUMNA_PREPARACION));

            plato = new Plato(nomb, ingredientes, preparacion);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plato;
    }

    /*public ArrayList<Persona> obtenerPersonas() {
        ArrayList<Persona> arreglo = new ArrayList<Persona>();
        Cursor c = bd.query(NOMBRE_TABLA_PERSONA, null, null, null, null, null, null);
        if (c != null) {
            if(c.moveToFirst()){
                do{
                    Persona persona = new Persona();
                    persona.setId(c.getInt(0));
                    persona.setNombre(c.getString(1));
                    persona.setTelefono(c.getInt(2));
                    persona.setCorreo(c.getString(3));
                    arreglo.add(persona);
                }while(c.moveToNext());
            }
        }
        c.close();
        return arreglo;
    }*/
}

