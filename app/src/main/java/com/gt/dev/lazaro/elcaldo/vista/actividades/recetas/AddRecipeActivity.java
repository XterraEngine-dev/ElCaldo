package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gt.dev.lazaro.elcaldo.R;

public class AddRecipeActivity extends AppCompatActivity {

    private Button btnUploadPicture, btnTakePicture, btnAddrecipe;
    private ImageView ivPictureRecipe;
    private ImageButton avatar1, avatar2, avatar3, avatar4;
    private EditText etNickname, etRecipename, etIngredients, etPreparation;

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
    }

}
