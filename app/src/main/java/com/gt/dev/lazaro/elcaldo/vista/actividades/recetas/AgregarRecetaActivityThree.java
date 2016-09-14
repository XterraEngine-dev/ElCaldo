package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Fernando on 14/09/2016.
 */
public class AgregarRecetaActivityThree extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_AGREGAR_THREE = "agregar";
    private Button enviar;
    private Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envio_imagen);
        setupVars();

    }

    private void setupVars() {
        enviar = (Button) findViewById(R.id.btn_imagen_adelante);
        atras = (Button) findViewById(R.id.btn_imagen_atras);

        enviar.setOnClickListener(this);
        atras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_imagen_adelante:
                enviarFormulario();
                break;
            case R.id.btn_imagen_atras:
                startActivity(new Intent(this, AgregarRecetaActivityTwo.class));
                this.finish();
                break;
        }


    }

    private void enviarFormulario() {

        Log.i(TAG_AGREGAR_THREE, "enviar");

    }
}