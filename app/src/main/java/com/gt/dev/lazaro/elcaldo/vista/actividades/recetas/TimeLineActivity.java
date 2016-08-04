package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.gt.dev.lazaro.elcaldo.R;

public class TimeLineActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.glass);
        getSupportActionBar().setSubtitle("Comunidad ElCaldo");
        fab = (FloatingActionButton) findViewById(R.id.fab_timeline);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_timeline1:
                Toast.makeText(TimeLineActivity.this, "start activity to add a recipe", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
