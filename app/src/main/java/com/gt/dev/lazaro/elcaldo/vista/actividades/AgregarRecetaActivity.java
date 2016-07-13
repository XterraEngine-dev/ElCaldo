package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.modelo.HotSpot;

public class AgregarRecetaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nombre, ingredientes, preparacion;
    private boolean works;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_receta);
        toolbar = (Toolbar) findViewById(R.id.toolbar_agregar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniciaVariables();
    }

    private void iniciaVariables() {
        nombre = (EditText) findViewById(R.id.etNombre);
        ingredientes = (EditText) findViewById(R.id.etIngredientes);
        preparacion = (EditText) findViewById(R.id.etPreparacion);
    }

    public void onClickGuardar(View view) {
        works = true;
        String sNombre = nombre.getText().toString().trim();
        String sIngredientes = ingredientes.getText().toString().trim();
        String sPreparacion = preparacion.getText().toString().trim();

        HotSpot instanciaGuardar = new HotSpot(this);

        try {
            Toast.makeText(getApplicationContext(), "Receta guardada", Toast.LENGTH_SHORT).show();
            instanciaGuardar.abrir();
            instanciaGuardar.almacenadata(sNombre, sIngredientes, sPreparacion);
            instanciaGuardar.cerrar();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AgregarRecetaActivity.this, "la has cagado", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickVerDatos(View view){
        startActivity(new Intent(AgregarRecetaActivity.this, MisRecetasActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
