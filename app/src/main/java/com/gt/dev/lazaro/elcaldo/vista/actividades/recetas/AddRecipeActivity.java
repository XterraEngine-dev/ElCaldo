package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.uploaders.UploadingHelper;

import java.util.HashMap;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables de Cifu
    private static String nombreImagenUrl = null;
    private static final int CAMARA_DATA = 0;
    private Button btnUploadPicture, btnTakePicture, btnAddrecipe;
    private ImageView mImageView;
    private ImageButton avatar1, avatar2, avatar3;
    private EditText etNickname, etRecipename, etIngredients, etPreparation;
    private String usuario, nombre, region;
    private ProgressDialog pDialog;
    private TextView valor;

    private String TAG = AddRecipeActivity.class.getSimpleName();
    private String KEY_IAMGE = "imagen";
    private String KEY_NAME = "nombre";
    private String KEY_INGREDIENTES = "ingredientes";
    private String KEY_PREPARACION = "preparacion";
    private String KEY_REGION = "region";
    private String KEY_IMAGEN = "imagen";
    private String KEY_SUBIR = "upload";
    private String KEY_LIKE = "like";
    private String KEY_AVATAR = "avatar";
    private String UPLOAD_URL = "http://elcaldo.justiciayagt.com/timeline";

    RelativeLayout linearLayout;

    UploadingHelper uploadingHelper;

    private int PICK_UP_IMAGE = 1;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //InputStream is = getResources().openRawResource(R.raw.splashcaldo);
        //bmp = BitmapFactory.decodeStream(is);
        startVars();
    }

    /**
     * Obtiene valor de edit text para almacenar en base de datos
     *
     * @return
     */
    public static String getNombreImagenUrl() {
        return nombreImagenUrl;
    }

    private void startVars() {

        valor = (TextView) findViewById(R.id.valor);
        //Button´s
        btnUploadPicture = (Button) findViewById(R.id.btn_uploadpicture_addrecipe);
        btnAddrecipe = (Button) findViewById(R.id.btn_add_recipe);
        btnTakePicture = (Button) findViewById(R.id.btn_takerecipe_addrecipe);
        //ImageView´s
        mImageView = (ImageView) findViewById(R.id.iv_recipe_addrecipe);
        //ImageButton´s
        avatar1 = (ImageButton) findViewById(R.id.avatar1_addrecipe);
        avatar2 = (ImageButton) findViewById(R.id.avatar2_addrecipe);
        avatar3 = (ImageButton) findViewById(R.id.avatar3_addrecipe);

        //EditText´s
        etNickname = (EditText) findViewById(R.id.et_nickname_addrecipe);
        etRecipename = (EditText) findViewById(R.id.et_recipename_addrecipe);
        etIngredients = (EditText) findViewById(R.id.et_ingredients_addrecip);
        etPreparation = (EditText) findViewById(R.id.et_preparation_addrecipe);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(false);

        btnUploadPicture.setOnClickListener(this);
        btnAddrecipe.setOnClickListener(this);
        btnTakePicture.setOnClickListener(this);

        avatar1.setOnClickListener(this);
        avatar2.setOnClickListener(this);
        avatar3.setOnClickListener(this);
    }

    private void enviarImagen() {
        /**
         * Obtiene el string de EditText
         */
        EditText traerImagen = (EditText) findViewById(R.id.et_recipename_addrecipe);

        nombreImagenUrl = traerImagen.getText().toString();

        /**
         * Dispara a API Subir.php donde revise el post de la immagen
         */
        String url = "http://elcaldo.justiciayagt.com/Subir.php";
        uploadingHelper = new UploadingHelper(this, url);
        uploadingHelper.startActivityForImagePick();
        uploadingHelper.getClass();
    }

    private void enviarForulario() {
        StringRequest uploadRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                Toast.makeText(AddRecipeActivity.this, "this" + response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR RESPONSE", "MESSAGE: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String name = etRecipename.getText().toString().trim();
                String ingredientes = etIngredients.getText().toString().trim();
                String preparacion = etPreparation.getText().toString().trim();
                String region = etNickname.getText().toString().trim();
                String like = etRecipename.getText().toString().trim();
                String avatar = valor.getText().toString().trim();
                String url = "http://elcaldo.justiciayagt.com/uploads/" + name;

                Map<String, String> params = new HashMap<>();
                params.put(KEY_NAME, name);
                params.put(KEY_INGREDIENTES, ingredientes);
                params.put(KEY_PREPARACION, preparacion);
                params.put(KEY_REGION, region);
                params.put(KEY_IMAGEN, url);
                params.put(KEY_LIKE, like);
                params.put(KEY_AVATAR, avatar);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString(("cifuentes_estrada@hotmail.com" + ":" + "azazelxd").getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }
        };


        AppController.getInstance().addToRequestQueue(uploadRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == PICK_UP_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from the Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the bitmap to the imageview
                ivPictureRecipe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMARA_DATA && resultCode == RESULT_OK) {
            Bundle cesta = data.getExtras();
            bmp = (Bitmap) cesta.get("data");
            ivPictureRecipe.setImageBitmap(bmp);
        }*/




        /**
         * SWICH PARA USO DE CAMARA O GALERIA
         */

        switch (requestCode){

            case 1:

                /**
                 * Activity for result CAMARA  -devCifuentes
                 */
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mImageView.setImageBitmap(imageBitmap);


                break;


            case 2:


                /**
                 * Activity for result GALERIA -devCifuentes
                 */

                uploadingHelper.setResult(requestCode, resultCode, data);
                linearLayout.removeAllViews();
                linearLayout.addView(uploadingHelper.getLayout());
                break;

        }



    }

    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_uploadpicture_addrecipe:
                //fileChooser();
                enviarImagen();
                break;
            case R.id.btn_takerecipe_addrecipe:
                tomarFoto();
                break;
            case R.id.btn_add_recipe:
                enviarForulario();
                break;
            case R.id.avatar1_addrecipe:
                valor.setText("1");
                Log.d(TAG, "AVATAR = " + valor.getText().toString());
                avatar1.setBackgroundResource(R.drawable.algodones);
                avatar2.setBackgroundResource(R.drawable.alboroto);
                avatar3.setBackgroundResource(R.drawable.alboroto);
                break;
            case R.id.avatar2_addrecipe:
                valor.setText("2");
                Log.d(TAG, "AVARTAR = " + valor.getText().toString());
                avatar2.setBackgroundResource(R.drawable.algodones);
                avatar1.setBackgroundResource(R.drawable.alboroto);
                avatar3.setBackgroundResource(R.drawable.alboroto);
                break;
            case R.id.avatar3_addrecipe:
                valor.setText("3");
                Log.d(TAG, "VALOR = " + valor.getText().toString());
                avatar3.setBackgroundResource(R.drawable.algodones);
                avatar1.setBackgroundResource(R.drawable.alboroto);
                avatar2.setBackgroundResource(R.drawable.alboroto);
                break;
        }
    }
}
