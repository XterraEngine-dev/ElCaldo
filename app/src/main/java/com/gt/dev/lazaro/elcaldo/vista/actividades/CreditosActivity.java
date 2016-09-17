package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;

/**
 * Created by Lazaro on 10/9/2015.
 */

/**
 * Clase CreditosActivity que extiende de la superclase AppCompatActivity
 */
public class CreditosActivity extends AppCompatActivity implements InterstitialAdListener {

    private Toolbar toolbar;
    private InterstitialAd interstitialAd;
    private String fbPlace, url;
    private Button btnEmail;
    private TextView twLazaro;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        toolbar = (Toolbar) findViewById(R.id.toolbar_creditos);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        url = "https://twitter.com/luiscomodo";

        twLazaro = (TextView) findViewById(R.id.tv_twlazaro);
        twLazaro.setText(Html.fromHtml("<a href=" + url + ">@luiscomodo</a>"));
        twLazaro.setMovementMethod(LinkMovementMethod.getInstance());

        btnEmail = (Button) findViewById(R.id.btn_send_mail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamar al metodo para envio de mail
                sendEmail();
            }
        });

        loadInterstialAd();
    }

    private void sendEmail() {
        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setType("message/rfc822");
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@elcaldogt.com"});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "ElCaldo - Android");
        sendEmail.putExtra(Intent.EXTRA_TEXT, "");
        try {
            startActivity(Intent.createChooser(sendEmail, "Sending email..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(CreditosActivity.this, "No hay aplicacion de correo", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInterstialAd() {
        fbPlace = Parametros.FB_PLACEMENT_ID;
        AdSettings.addTestDevice(getString(R.string.facebook_app_id));
        interstitialAd = new InterstitialAd(this, fbPlace);
        interstitialAd.setAdListener(CreditosActivity.this);
        interstitialAd.loadAd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    /**
     * Que hara cada item seleccionado
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_facebook_about:
                openFacebook();
                break;
            case R.id.menu_twitter_about:
                openTwitter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFacebook() {
        String url = "http://www.facebook.com/elcaldoapp";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void openTwitter() {
        String url = "http://www.twitter.com/caldogt";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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
        Log.d("ERROR APIFACEBOOK", "ERROR = " + adError.getErrorMessage() +
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
