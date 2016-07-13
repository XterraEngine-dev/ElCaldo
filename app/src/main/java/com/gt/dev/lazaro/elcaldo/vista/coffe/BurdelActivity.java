package com.gt.dev.lazaro.elcaldo.vista.coffe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gt.dev.lazaro.elcaldo.R;

public class BurdelActivity extends AppCompatActivity {

    private String titulo, texto;
    private int picture;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView tvTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burdel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.cafe_claro));
        setSupportActionBar(toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tvTexto = (TextView) findViewById(R.id.tv_texto_coffe);
        //toolbarLayout.setTitle(getTitle());
        getBundle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_burdel_share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCoffee();
            }
        });
    }

    private void shareCoffee() {

        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        intentCompartir.setType("text/plain");
        intentCompartir.putExtra(Intent.EXTRA_SUBJECT, "Compartir en redes sociales");
        intentCompartir.putExtra(Intent.EXTRA_TEXT, titulo + "\n" +
                texto + "\n" +
                getString(R.string.via) + " https://play.google.com/store/apps/details?id=com.gt.dev.lazaro.elcaldo");

        startActivity(Intent.createChooser(intentCompartir, getString(R.string.share_pic_coffe)));
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();

        titulo = bundle.getString("titulo");
        texto = bundle.getString("texto");
        picture = bundle.getInt("imagen");

        toolbarLayout.setBackgroundResource(picture);
        toolbarLayout.setTitle(titulo);
        tvTexto.setText(texto);
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
}
