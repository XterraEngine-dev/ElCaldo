package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.ads.*;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

import java.io.InputStream;

/**
 * Created by Lazaro on 10/9/2015.
 */

public class CamaraActivity extends AppCompatActivity implements View.OnClickListener, InterstitialAdListener {

    private Toolbar toolbar;
    private Button compartirImagen;
    private static final int CAMARA_DATA = 0;
    private ImageView devuelvePic;
    private ImageButton tomarPic;
    private Bitmap bmp;
    public static GoogleAnalytics googleAnalytics;
    public static Tracker tracker;
    private String keyTracker;
    private static final int REQUEST_TO_CAMERA = 1;
    private static final int REQUEST_TO_STORAGE = 2;
    //Facebook vars
    private InterstitialAd interstitialAd;
    private String fbPlace;

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

        //Llamamos al metodo que inicailzia e instancea variables metodos, widgets, etc.
        iniciaVars();

        loadInterstialAd();

        //llamamos metodo que inicia api de Google Analytics
        startAnalytics();

        checkPermision();

        InputStream is = getResources().openRawResource(R.raw.splashcaldo);
        bmp = BitmapFactory.decodeStream(is);
    }

    private void loadInterstialAd() {
        fbPlace = Parametros.FB_PLACEMENT_ID;
        AdSettings.addTestDevice(getString(R.string.facebook_app_id));
        interstitialAd = new InterstitialAd(this, fbPlace);
        interstitialAd.setAdListener(CamaraActivity.this);
        interstitialAd.loadAd();
    }

    private void checkPermision() {

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

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TO_CAMERA: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("CAMERA", "PERMISSION HAS BEEN DENIED BY USER");
                } else {
                    Log.i("CAMERA", "PERMISSION HAS BEEN GRANTED BY USER");
                }
                return;
            }

            case REQUEST_TO_STORAGE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("STORAGE", "PERMISSION HAS BEEN DENIED BY USER");
                } else {
                    Log.i("STORAGE", "PERMISSION HAS BEEN GRANTED BY USER");
                }
                return;
            }
        }
    }

    private void cameraRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_TO_CAMERA);
    }

    private void storageRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_TO_CAMERA);
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
    }

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
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("CameraActivity")
                        .setAction("Share picture")
                        .setLabel("Sharing picture from CameraActivity")
                        .build());
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

    /**
     * Iniciamos las constantes y variables para el
     * manejo de Google Analytics
     */
    private void startAnalytics() {
        //Analytics
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        //Iniciamos y seteamos datos a nuestro tracker
        keyTracker = Parametros.TRACKER_ANALYTICS;
        tracker = googleAnalytics.newTracker(keyTracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }

    @Override
    public void onInterstitialDisplayed(Ad ad) {
        onPause();
    }

    @Override
    public void onInterstitialDismissed(Ad ad) {
        onResume();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Log.d("ERROR APIFACEBOOK", "MESSAGE = " + adError.getErrorMessage() +
                "ERROR CODE = " + adError.getErrorCode());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        interstitialAd.show();
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.d("AD", ad.getPlacementId());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
