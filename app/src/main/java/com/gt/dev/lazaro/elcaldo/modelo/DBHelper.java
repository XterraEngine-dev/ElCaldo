package com.gt.dev.lazaro.elcaldo.modelo;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String NOMBRE_BD = "datacaldo.sqlite";
    private static final int VERSION_BD = 7;
    private static File ARCHIVO_BASE_DE_DATOS = null;

    private boolean bdInvalida = false;
    private boolean bdActualizada = false;

    private Context miContexto;
    private int conexionesAbiertas = 0;

    // Singleton
    private static DBHelper miInstancia;

    synchronized static public DBHelper getInstancia(Context contexto) {
        if (miInstancia == null) {
            try {
                miInstancia = new DBHelper(contexto.getApplicationContext());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return miInstancia;
    }

    private DBHelper(Context context) throws SQLException {
        super(context, NOMBRE_BD, null, VERSION_BD);
        this.miContexto = context;
        ARCHIVO_BASE_DE_DATOS = context.getDatabasePath(NOMBRE_BD);
        SQLiteDatabase bd = null;
        bd =

                getReadableDatabase();

        if (bd != null)

        {
            bd.close();
        }

        if (bdInvalida)

        {
            copiarBD();
        }

        if (bdActualizada)

        {
            actualizarBD();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        bdInvalida = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionVieja, int versionNueva) {
        bdInvalida = true;
        bdActualizada = true;
    }

    private void actualizarBD() {

    }

    @Override
    public void onOpen(SQLiteDatabase bd) {
        super.onOpen(bd);
        conexionesAbiertas++;
        if (!bd.isReadOnly()) {
            bd.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    @Override
    public synchronized void close() {
        conexionesAbiertas--;
        if (conexionesAbiertas == 0) {
            super.close();
        }
    }

    private void copiarBD() {
        AssetManager assetManager = miContexto.getResources().getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(NOMBRE_BD);
            out = new FileOutputStream(ARCHIVO_BASE_DE_DATOS);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    out.close();
                } catch (IOException e) {

                }
            }
        }
        try {
            setVersionBD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bdInvalida = false;
    }

    private void setVersionBD() throws SQLException {
        SQLiteDatabase bd = null;
        bd = SQLiteDatabase.openDatabase(ARCHIVO_BASE_DE_DATOS.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        bd.execSQL("PRAGMA version_usuario = " + VERSION_BD);
    }

}
