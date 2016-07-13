package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gt.dev.lazaro.elcaldo.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class TweetComposerActivity extends AppCompatActivity {

    private String nombrePlato;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_composer);
        btnOk = (Button) findViewById(R.id.btn_ok);

        Bundle bundle = getIntent().getExtras();
        nombrePlato = bundle.getString("key");


        TwitterAuthConfig authConfig = new TwitterAuthConfig("6RoCIWLE55HeXRwxIzOKn3uCQ", "a88r5t04oPUjhwBGHaVGAnt5M6X7Dm1GrbccomAN0jRjhybkAP");
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());

        //File imageFile = new File("imagenPlato");
        //Uri imageUri = Uri.fromFile(imageFile);

        TweetComposer.Builder builder = new TweetComposer.Builder(this).text(nombrePlato + "\n" + "https://play.google.com/store/apps/details?id=com.gt.dev.lazaro.elcaldo");
        builder.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
