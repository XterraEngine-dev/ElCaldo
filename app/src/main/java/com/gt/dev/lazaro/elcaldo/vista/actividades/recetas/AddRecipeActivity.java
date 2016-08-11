package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.CustomRequest;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMARA_DATA = 0;
    private Button btnUploadPicture, btnTakePicture, btnAddrecipe;
    private ImageView ivPictureRecipe;
    private ImageButton avatar1, avatar2, avatar3, avatar4;
    private EditText etNickname, etRecipename, etIngredients, etPreparation;
    private String usuario, nombre, region;
    private ProgressDialog pDialog;
    private String TAG = AddRecipeActivity.class.getSimpleName();

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    private Bitmap bitmap, bmp;

    private int PICK_UP_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        InputStream is = getResources().openRawResource(R.raw.splashcaldo);
        bmp = BitmapFactory.decodeStream(is);
        startVars();
    }

    private void startVars() {
        //Button´s
        btnUploadPicture = (Button) findViewById(R.id.btn_uploadpicture_addrecipe);
        btnAddrecipe = (Button) findViewById(R.id.btn_add_recipe);
        btnTakePicture = (Button) findViewById(R.id.btn_takerecipe_addrecipe);
        //ImageView´s
        ivPictureRecipe = (ImageView) findViewById(R.id.iv_recipe_addrecipe);
        //ImageButton´s
        avatar1 = (ImageButton) findViewById(R.id.avatar1_addrecipe);
        avatar2 = (ImageButton) findViewById(R.id.avatar2_addrecipe);
        avatar3 = (ImageButton) findViewById(R.id.avatar3_addrecipe);
        avatar4 = (ImageButton) findViewById(R.id.avatar4_addrecipe);
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
    }

    private void addRecipe() {
        String url = Parametros.URL_SHOW_TMELINE;

        CustomRequest recipeRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, response.toString());
                //if ()
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void fileChooser() {
        Intent iChooser = new Intent();
        iChooser.setType("image/*");
        iChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(iChooser, "Select your picture"), PICK_UP_IMAGE);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_UP_IMAGE && resultCode == RESULT_OK
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
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_takerecipe_addrecipe:
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePic, CAMARA_DATA);
                break;
            case R.id.btn_uploadpicture_addrecipe:
                fileChooser();
                break;
            case R.id.btn_add_recipe:
                addRecipe();
                break;
        }
    }
}
