package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.ConexionVerify;

public class TopRecipesActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_recipes);
        toolbar = (Toolbar) findViewById(R.id.tool_bar_toprecipes);
        setSupportActionBar(toolbar);
        verifyConnection();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(String title, String message, boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(TopRecipesActivity.this).create();
        alertDialog.setTitle("No tiene conexión a internet");
        alertDialog.setMessage("Conectar a internet");
        alertDialog.show();
    }

    private void verifyConnection() {
        if (!ConexionVerify.isNetworkAvailable(this)) {
            showAlertDialog("No tiene conexión a internet", "Conectar a internet", true);
            onStop();
        }
    }

}
