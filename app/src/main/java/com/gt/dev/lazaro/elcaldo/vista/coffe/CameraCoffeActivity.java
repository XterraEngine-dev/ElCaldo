package com.gt.dev.lazaro.elcaldo.vista.coffe;

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

import com.gt.dev.lazaro.elcaldo.R;

import java.io.InputStream;

public class CameraCoffeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sharePic;
    private static final int CAMERA_DATA = 0;
    private ImageView returnPic;
    private ImageButton shotPic;
    private Bitmap bmp;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_coffe);

        startVars();
        InputStream is = getResources().openRawResource(R.raw.splashcaldo);
        bmp = BitmapFactory.decodeStream(is);
    }

    private void startVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_coffe_camera);
        shotPic = (ImageButton) findViewById(R.id.btn_shot_pic);
        returnPic = (ImageView) findViewById(R.id.iv_return_pic);
        sharePic = (Button) findViewById(R.id.btn_share_picture);
        sharePic.setOnClickListener(this);
        shotPic.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shot_pic:
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePic, CAMERA_DATA);
                break;
            case R.id.btn_share_picture:
                if (bmp != null) {
                    String elBit = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "yes", null);
                    Uri bmpUri = Uri.parse(elBit);

                    Intent sharePicture = new Intent(android.content.Intent.ACTION_SEND);
                    sharePicture.setType("image/*");
                    sharePicture.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sharePicture.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    sharePicture.putExtra(Intent.EXTRA_SUBJECT, "ElCaldo");
                    sharePicture.putExtra(Intent.EXTRA_TEXT, getText(R.string.message_coffe_camera) + getString(R.string.message_coffe_camera) + "\n" + "#ElCaldoApp" + "\n");
                    startActivity(Intent.createChooser(sharePicture, getString(R.string.share_pic_coffe)));
                } else {
                    Toast.makeText(CameraCoffeActivity.this, getString(R.string.toast_camara), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle cesta = data.getExtras();
            bmp = (Bitmap) cesta.get("data");
            returnPic.setImageBitmap(bmp);
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
