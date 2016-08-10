package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AddRecipeActivity extends AppCompatActivity {

    private Button btnUploadPicture, btnTakePicture, btnAddrecipe;
    private ImageView ivPictureRecipe;
    private ImageButton avatar1, avatar2, avatar3, avatar4;
    private EditText etNickname, etRecipename, etIngredients, etPreparation;
    private String usuario, nombre, region;
    private ProgressDialog pDialog;
    private String TAG = AddRecipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
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
    }

    private void addRecipe(){
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

}
