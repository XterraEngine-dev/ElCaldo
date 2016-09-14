package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gt.dev.lazaro.elcaldo.R;

import java.util.ArrayList;

/**
 * Created by Fernando on 14/09/2016.
 */
public class AgregarRecetaActivityTwo extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_UNO = "arat";
    private Spinner spinner;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    private Button button, buttonB;
    private EditText etNombreReceta, etIngredientes, etPrepacion;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_receta);

        iniciarVars();
        setupSpiner();
    }

    private void iniciarVars() {

        //Adelante y atras
        button = (Button) findViewById(R.id.btn_receta_adelante);
        buttonB = (Button) findViewById(R.id.btn_receta_atras);

        //Campos
        etNombreReceta = (EditText) findViewById(R.id.et_nombre_receta);
        etIngredientes = (EditText) findViewById(R.id.et_ingredientes);
        etPrepacion = (EditText) findViewById(R.id.et_preparacion);
        spinner = (Spinner) findViewById(R.id.gt);


        button.setOnClickListener(this);
        buttonB.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_receta_adelante:
                setupLogicaFormulario();
                break;
            case R.id.btn_receta_atras:
                startActivity(new Intent(this, AgregarRecetaActivityOne.class));
                this.finish();
                break;


        }
    }

    private void setupLogicaFormulario() {

        String nombreReceta = this.etNombreReceta.getText().toString();
        String ingredientes = this.etIngredientes.getText().toString();
        String preparacion = this.etPrepacion.getText().toString();


        if(TextUtils.isEmpty(nombreReceta)) {
            etNombreReceta.setError("Introduce nombre receta ");
            return;
        }
        if(TextUtils.isEmpty(ingredientes)) {
            etIngredientes.setError("Introduce ingredientes");
            return;
        }
        if(TextUtils.isEmpty(preparacion)) {
            etPrepacion.setError("Introduce prepacion");
            return;
        }else {
            Log.i(TAG_UNO, "siguiente");
            startActivity(new Intent(this, AgregarRecetaActivityThree.class));
            this.finish();
        }
    }

    public void setupSpiner() {
        String[] values = getResources().getStringArray(R.array.regiones_gt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }
}