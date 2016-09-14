package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gt.dev.lazaro.elcaldo.R;

/**
 * Created by Fernando on 13/09/2016.
 */
public class AgregarRecetaActivityOne extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG_UNO = "arao";
    private Button buttonA;
    private ImageButton avatarUno, avatarDos, avatarTres;
    private EditText etUsuario;

    public static String nombreUsuario = null;
    public static String avatarValor = null;

    private ImageView checkOne, checkTwo, cheakThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_usuario);

        iniciarVars();
    }

    private void iniciarVars() {
        //Actividades
        buttonA = (Button) findViewById(R.id.btn_siguiente);

        //Formulario
        etUsuario = (EditText) findViewById(R.id.et_usuario);

        //Avatars
        avatarUno = (ImageButton) findViewById(R.id.avatar_uno);
        avatarDos = (ImageButton) findViewById(R.id.avatar_dos);
        avatarTres = (ImageButton) findViewById(R.id.avatar_tres);

        //cheack imagenes
        checkOne = (ImageView) findViewById(R.id.cheak_uno);
        checkTwo = (ImageView) findViewById(R.id.cheak_dos);
        cheakThree = (ImageView) findViewById(R.id.cheak_tres);


        buttonA.setOnClickListener(this);
        avatarUno.setOnClickListener(this);
        avatarDos.setOnClickListener(this);
        avatarTres.setOnClickListener(this);


    }


    /**
     * Obtener valores de variables en otras clases
     */
    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static String getAvatarValor() {
        return avatarValor;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_siguiente:

                if (etUsuario.getText().toString().isEmpty()) {
                    Log.i(TAG_UNO, "ingrese nombre usuario");
                    Toast.makeText(getBaseContext(), "ingrese nombre usuario", Toast.LENGTH_LONG).show();
                    if (avatarValor == null) {
                        Log.i(TAG_UNO, "seleccione avatar");
                        Toast.makeText(getBaseContext(), "Seleccione Avatar", Toast.LENGTH_LONG).show();
                    }

                } else {

                    etUsuario.getText().toString();
                    etUsuario.setText(nombreUsuario);
                    Log.i(TAG_UNO, "Obtubo datos" + nombreUsuario);
                    startActivity(new Intent(this, AgregarRecetaActivityTwo.class));

                }

                break;

            case R.id.avatar_uno:
                avatarValor = "1";
                selecOne();
                Log.i(TAG_UNO, "" + avatarValor);
                break;

            case R.id.avatar_dos:
                avatarValor = "2";
                selecTwo();
                Log.i(TAG_UNO, "" + avatarValor);
                break;

            case R.id.avatar_tres:
                avatarValor = "3";
                selectThree();
                Log.i(TAG_UNO, "" + avatarValor);
                break;


        }


    }


    private void selecOne() {

        checkOne.setVisibility(View.VISIBLE);
        checkTwo.setVisibility(View.INVISIBLE);
        cheakThree.setVisibility(View.INVISIBLE);
    }

    private void selecTwo() {
        checkOne.setVisibility(View.INVISIBLE);
        checkTwo.setVisibility(View.VISIBLE);
        cheakThree.setVisibility(View.INVISIBLE);
    }

    private void selectThree() {

        checkOne.setVisibility(View.INVISIBLE);
        checkTwo.setVisibility(View.INVISIBLE);
        cheakThree.setVisibility(View.VISIBLE);

    }


}