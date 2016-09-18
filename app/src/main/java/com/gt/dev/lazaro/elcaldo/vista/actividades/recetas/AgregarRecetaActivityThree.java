package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.TimeLine;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.utilidades.VolleySingleton;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.sql.Time;
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
    private static final String TAG = "proceso";


    private static final int REQUEST_TO_CAMERA = 1;
    private static final int REQUEST_TO_STORAGE = 2;
    /**
     * Variables Bundle
     */
    private Button enviar;
    String usuario, avatarB, recetaB, ingredienteB, preparacionB;

    private Request.Priority priority = Request.Priority.IMMEDIATE;

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

    /**
     * Variables para subir imagen
     */


    ImageView camara, gallery, upload, imagen;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    String selectedPhoto;
    String selectedGallery;

    final int CAMERA_REQUEST = 1100;
    final int GALLERY_REQUEST = 2200;

    String nombreUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envio_imagen);
        VolleySingleton.getInstance().init(getApplicationContext());
        setupVars();
        checkPermission();
        bundleFormulario();
    }

    private void checkPermission() {

        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraPermission != PackageManager.PERMISSION_GRANTED && storagePermission != PackageManager.PERMISSION_GRANTED) {
            Log.i("INTERNET", "PERMISSION GRANTED");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to acces the camera is requiered to get recipes").setTitle("Permission required");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("CAMERA", "CLICKED");
                        cameraRequest();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                cameraRequest();
            }
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Permission to acces write in external storage is required to take picture").setTitle("Permission required");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("STORAGE", "CLICKED");
                    storageRequest();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            storageRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_TO_CAMERA:
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("CAMERA", "PERMISSION HAS BEEN DENIED BY USER");
                } else {
                    Log.i("CAMERA", "PERMISSION HAS BEEN GRANTED BY USER");
                }
                return;
            case REQUEST_TO_STORAGE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("STORAGE", "PERMISSION HAS BEEN DENIED BY USER");
                } else {
                    Log.i("STORAGE", "PERMISSION HAS BEEN GRANTED BY USER");
                }
                return;
            }
        }
        return;
    }

    private void cameraRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_TO_CAMERA);
    }

    private void storageRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_TO_STORAGE);
    }


    private void setupVars() {

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        enviar = (Button) findViewById(R.id.enviar);
        camara = (ImageView) findViewById(R.id.imv_camara);
        gallery = (ImageView) findViewById(R.id.imv_gallery);
        imagen = (ImageView) findViewById(R.id.imv_image);


        enviar.setOnClickListener(this);
        camara.setOnClickListener(this);
        gallery.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enviar:
                subirProceso();
                break;
            case R.id.imv_camara:
                intentoCamara();
                break;
            case R.id.imv_gallery:
                intentoGallery();
                break;
        }

    }

    private void subirProceso() {

        if (selectedPhoto == null || selectedPhoto.equals("")) {

            //Toast.makeText(getApplicationContext(), "No selecciono imagen", Toast.LENGTH_SHORT).show();
            Log.i("SUBI RECETAS","exito");
            return;
        }

        try {
            Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
            String encodedImage = ImageBase64.encode(bitmap);
            Log.d("subir", encodedImage);

            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("image", encodedImage);
            postData.put("nombre", nombreUrl);
            PostResponseAsyncTask task = new PostResponseAsyncTask(AgregarRecetaActivityThree.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    if (s.contains("uploaded_success")) {
                        Toast.makeText(getApplicationContext(), getString(R.string.recipe_done), Toast.LENGTH_SHORT).show();
                        enviarFormulario();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.recipe_fail), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            task.execute("http://dev.elcaldogt.com/upload.php");
            task.setEachExceptionsHandler(new EachExceptionsHandler() {
                @Override
                public void handleIOException(IOException e) {
                    Log.d(TAG, "handleIOException");
                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {
                    Log.d(TAG, "handleMalformedURLException");
                }

                @Override
                public void handleProtocolException(ProtocolException e) {
                    Log.d(TAG, "handleProtocolException");
                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                    Log.d(TAG, "handleUnsupportedEncodingException");
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void intentoGallery() {
        startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
    }

    private void intentoCamara() {
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
            cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "PROBLEMA CARGAR FOTO", Toast.LENGTH_SHORT).show();
        }

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
        nombreUrl = receta;


    }


    public static String getNombreImagenUrl() {
        return nombreImagenUrl;
    }

    /**
     * Envio de formulario a API
     */

    private void enviarFormulario() {

        String url = Parametros.URL_SHOW_TIMELINE;

        final StringRequest uploadRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(AgregarRecetaActivityThree.this, "this" + response, Toast.LENGTH_SHORT).show();
                AgregarRecetaActivityThree.this.finish();
                startActivity(new Intent(AgregarRecetaActivityThree.this, TimeLineActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR RESPONSE", "MESSAGE: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String urlImagen = "http://dev.elcaldogt.com/upload/" + nombreUrl + ".jpeg";
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
                String user = Parametros.USER;
                String pass = Parametros.PASS;
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString((user + ":" + pass).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }

            @Override
            public Priority getPriority() {
                return priority;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        uploadRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(uploadRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                Bitmap bitmap = null;
                try {
                    bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    imagen.setImageBitmap((bitmap));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    imagen.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

    }



}