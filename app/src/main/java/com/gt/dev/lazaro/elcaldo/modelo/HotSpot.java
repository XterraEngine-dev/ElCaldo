package com.gt.dev.lazaro.elcaldo.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 7/11/15.
 */
public class HotSpot {

    private static final String KEY_ROWID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_PREPARATION = "preparation";

    private static final String DATABASE_NAME = "agregarrecetas";
    private static final String DATABASE_TABLE = "nuevoplato";
    private static final int DATABASE_VERSION = 1;

    private SuperHelper miHelper;
    private final Context miContexto;
    private SQLiteDatabase miDatabase;


    public HotSpot(Context c) {
        miContexto = c;
    }

    public HotSpot abrir() {
        miHelper = new SuperHelper(miContexto);
        miDatabase = miHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        miHelper.close();
    }

    private static class SuperHelper extends SQLiteOpenHelper {


        public SuperHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NAME + " TEXT NOT NULL,"
                    + KEY_INGREDIENTS + " TEXT NOT NULL,"
                    + KEY_PREPARATION + " TEXT NOT NULL"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public long almacenadata(String nombre, String ingredientes, String preparacion) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, nombre);
        cv.put(KEY_INGREDIENTS, ingredientes);
        cv.put(KEY_PREPARATION, preparacion);
        return miDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getDataInfo() throws Exception {
        String columna[] = new String[]{KEY_ROWID, KEY_NAME, KEY_INGREDIENTS, KEY_PREPARATION};
        Cursor cursor = miDatabase.query(DATABASE_TABLE, columna, null, null, null, null, null);
        String resultado = "";
        int iFila = cursor.getColumnIndex(KEY_ROWID);
        int iNombre = cursor.getColumnIndex(KEY_NAME);
        int iIngredientes = cursor.getColumnIndex(KEY_INGREDIENTS);
        int iPreparacion = cursor.getColumnIndex(KEY_PREPARATION);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            resultado = resultado //+ cursor.getString(iFila) + " "
                    + cursor.getString(iNombre) + "\n";
                    //+ cursor.getString(iIngredientes) + " "
                    //+ cursor.getString(iPreparacion) + "\n";
        }

        return resultado;
    }

    public String getNombre(long l) throws Exception {
        String columna[] = new String[]{KEY_ROWID, KEY_NAME, KEY_INGREDIENTS, KEY_PREPARATION};
        Cursor cursor = miDatabase.query(DATABASE_TABLE, columna, KEY_ROWID + "=" + l, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String nombre = cursor.getString(1);
            return nombre;
        }
        return null;
    }

    public String getIngredientes(long l) throws Exception {
        String columna[] = new String[]{KEY_ROWID, KEY_NAME, KEY_INGREDIENTS, KEY_PREPARATION};
        Cursor cursor = miDatabase.query(DATABASE_TABLE, columna, KEY_ROWID + "=" + l, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String ingredientes = cursor.getString(2);
            return ingredientes;
        }
        return null;
    }

    public String getPreparacion(long l) throws Exception {
        String columna[] = new String[]{KEY_ROWID, KEY_NAME, KEY_INGREDIENTS, KEY_PREPARATION};
        Cursor cursor = miDatabase.query(DATABASE_TABLE, columna, KEY_ROWID + "=" + l, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String preparacion = cursor.getString(3);
            return preparacion;
        }
        return null;
    }

    public void actualizaData(String nombre, String ingredientes, String preparacion, long registro) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, nombre);
        cv.put(KEY_INGREDIENTS, ingredientes);
        cv.put(KEY_PREPARATION, preparacion);

        miDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + registro, null);
    }

    public void eliminaRegistro(long registro) throws Exception {
        miDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + registro, null);
    }

}
