package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.dev.lazaro.elcaldo.R;

import java.io.InputStream;

/**
 * Created by Lazaro on 10/9/2015.
 */

public class CamaraActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button compartirImagen;
    private static final int CAMARA_DATA = 0;
    private ImageView devuelvePic;
    private ImageButton tomarPic;
    private Bitmap bmp;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        Toast.makeText(CamaraActivity.this, R.string.toast_shot, Toast.LENGTH_LONG).show();

        iniciaVars();

        InputStream is = getResources().openRawResource(R.raw.splashcaldo);
        bmp = BitmapFactory.decodeStream(is);
    }

    /**
     * Metodo que inicializa las variables
     */
    private void iniciaVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_camara);
        tomarPic = (ImageButton) findViewById(R.id.btnTomarPic);
        devuelvePic = (ImageView) findViewById(R.id.ivDevuelvePic);
        compartirImagen = (Button) findViewById(R.id.btnImagen);
        compartirImagen.setOnClickListener(this);
        tomarPic.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

/*
    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/jpg");

        // Make sure you put example png image named myImage.png in your
        // directory
        String imagePath = Environment.getExternalStorageDirectory()
                + "/myImage.png";

        File imageFileToShare = new File(imagePath);

        //Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, bmp);

        startActivity(Intent.createChooser(share, "Share Image!"));
    }*/

    /**
     * Metodo para escuchar el evento click
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTomarPic:
                Intent tomartPic = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(tomartPic, CAMARA_DATA);
                break;
            case R.id.btnImagen:
                if (bmp != null) {

                    String elBitmap = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "zoquelo", null);
                    Uri bmpUri = Uri.parse(elBitmap);

                    Intent compartirImagen = new Intent(android.content.Intent.ACTION_SEND);
                    compartirImagen.setType("image/*");
                    compartirImagen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    compartirImagen.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    compartirImagen.putExtra(Intent.EXTRA_SUBJECT, "ElCaldo");
                    compartirImagen.putExtra(Intent.EXTRA_TEXT, getString(R.string.comparte_camara) + "\n" +
                            getString(R.string.via) + "\n" + "#ElCaldoApp" + "\n");
                    startActivity(Intent.createChooser(compartirImagen, getString(R.string.comparte_imagen)));
                } else {
                    Toast.makeText(CamaraActivity.this, R.string.toast_camara, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * Espera resutlado de la acitividad
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle cesta = data.getExtras();
            bmp = (Bitmap) cesta.get("data");
            devuelvePic.setImageBitmap(bmp);
        }
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
