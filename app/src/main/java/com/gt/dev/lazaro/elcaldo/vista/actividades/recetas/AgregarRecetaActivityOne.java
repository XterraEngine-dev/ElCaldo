package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Fernando on 13/09/2016.
 */
public class AgregarRecetaActivityOne extends AppCompatActivity implements View.OnClickListener {


    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_usuario);

        iniciarVars();
    }

    private void iniciarVars() {
        button = (Button)findViewById(R.id.btn_siguiente);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, AgregarRecetaActivityTwo.class));
    }
}