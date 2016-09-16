package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.uploaders.UploadingHelper;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.utilidades.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fernando on 14/09/2016.
 */
public class AgregarRecetaActivityThree extends AppCompatActivity implements View.OnClickListener {

    /**
     * LOG
     */
    private static final String TAG_AGREGAR_THREE = "tag_agregar_three";
    /**
     * Variables Bundle
     */
    private Button enviar,galeria;
    String usuario, avatarB, recetaB, ingredienteB, preparacionB;

    /**
     * Variables para ingresar a API
     */

    private String Key_UNAME = "unombre";
    private String KEY_NAME = "nombre";
    private String KEY_INGREDIENTES = "ingredientes";
    private String KEY_PREPARACION = "preparacion";
    private String KEY_REGION = "region";
    private String KEY_IMAGEN = "imagen";
    private String KEY_LIKE = "like";
    private String KEY_AVATAR = "avatar";
    private static String nombreImagenUrl = null;
    RelativeLayout linearLayout;
    UploadingHelper uploadingHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envio_imagen);
        VolleySingleton.getInstance().init(getApplicationContext());
        setupVars();
        bundleFormulario();
    }

    private void setupVars() {


        enviar = (Button) findViewById(R.id.enviar);

        galeria =(Button)findViewById(R.id.btn_galeria);
        linearLayout=(RelativeLayout)findViewById(R.id.relative_image);

        enviar.setOnClickListener(this);
        galeria.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.enviar:
                enviarFormulario();
                this.finish();
                break;

            case R.id.btn_galeria:
                enviarGaleria();
                break;
        }

    }

    private void enviarGaleria() {

        String url = "http://dev.elcaldogt.com/Subir.php";
        uploadingHelper = new UploadingHelper(this, url);
        uploadingHelper.startActivityForImagePick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uploadingHelper.setResult(resultCode, requestCode, data);
        linearLayout.removeAllViews();
        linearLayout.addView(uploadingHelper.getLayout());
    }

    /**
     * Obtiene valores de bundle e Inicia variables
     */
    private void bundleFormulario() {
        Bundle btwo = getIntent().getExtras();
        String nombreUsuario = btwo.getString("usuario");
        String avatar = btwo.getString("avatar");
        String receta = btwo.getString("receta");
        String ingrediente = btwo.getString("ingredientes");
        String preparacion = btwo.getString("preparacion");
        String region = btwo.getString("region");

        Log.i(TAG_AGREGAR_THREE, "" + nombreUsuario);
        Log.i(TAG_AGREGAR_THREE, "" + avatar);
        Log.i(TAG_AGREGAR_THREE, "" + receta);
        Log.i(TAG_AGREGAR_THREE, "" + ingrediente);
        Log.i(TAG_AGREGAR_THREE, "" + preparacion);
        Log.i(TAG_AGREGAR_THREE, "" + region);

        usuario = nombreUsuario;
        avatarB = avatar;
        recetaB = receta;
        ingredienteB = ingrediente;
        preparacionB = preparacion;
        nombreImagenUrl = recetaB;


    }


    public static String getNombreImagenUrl() {
        return nombreImagenUrl;
    }

    /**
     * Envio de formulario a API
     */

    private void enviarFormulario() {

        String url = Parametros.URL_SHOW_TIMELINE;

        StringRequest uploadRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(AgregarRecetaActivityThree.this, "this" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR RESPONSE", "MESSAGE: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String urlImagen = "http://dev.elcaldogt.com/uploads/" + recetaB;
                String like = "0";

                Map<String, String> params = new HashMap<>();
                params.put(Key_UNAME, usuario);
                params.put(KEY_NAME, recetaB);
                params.put(KEY_INGREDIENTES, ingredienteB);
                params.put(KEY_PREPARACION, preparacionB);
                params.put(KEY_REGION, recetaB);
                params.put(KEY_IMAGEN, urlImagen);
                params.put(KEY_LIKE, like);
                params.put(KEY_AVATAR, avatarB);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString(("dev@elcaldogt.com" + ":" + "azazelxd").getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(uploadRequest);
    }


}