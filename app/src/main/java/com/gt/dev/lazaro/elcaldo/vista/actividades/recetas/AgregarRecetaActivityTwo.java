package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gt.dev.lazaro.elcaldo.R;

import java.util.ArrayList;

/**
 * Created by Fernando on 14/09/2016.
 */
public class AgregarRecetaActivityTwo extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    private Button button;
    private Button buttonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_receta);

        iniciarVars();
        setupSpiner();
    }

    private void iniciarVars() {

        button =(Button)findViewById(R.id.btn_receta_adelante);
        buttonB = (Button) findViewById(R.id.btn_receta_atras);
        button.setOnClickListener(this);
        buttonB.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_receta_adelante:
                startActivity(new Intent(this, AgregarRecetaActivityThree.class));
                break;
            case R.id.btn_receta_atras:
                startActivity(new Intent(this, AgregarRecetaActivityOne.class));
                break;


        }
    }
    public void setupSpiner( ) {
        String [] values= getResources().getStringArray(R.array.regiones_gt);
        Spinner spinner = (Spinner) findViewById(R.id.gt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }
}