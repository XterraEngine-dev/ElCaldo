package com.gt.dev.lazaro.elcaldo.vista.actividades.recetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.controlador.AppController;
import com.gt.dev.lazaro.elcaldo.utilidades.Parametros;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline.ComentarioTimeLine;
import com.gt.dev.lazaro.elcaldo.vista.fragmentos.timeline.RecetaTimeLine;

import java.util.HashMap;
import java.util.Map;

public class AddCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNickName, etComment;
    private Button btnComment, btnCancel;
    private String user, comment, idTimeline;
    private ProgressDialog pDialog;
    private Request.Priority priority = Request.Priority.IMMEDIATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        startVars();
    }

    private void startVars() {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage(getString(R.string.comment_sending));

        etNickName = (EditText) findViewById(R.id.et_nickname_addcoment);
        etComment = (EditText) findViewById(R.id.et_comentario_addcoment);

        btnComment = (Button) findViewById(R.id.btn_comentar_addcoment);
        btnCancel = (Button) findViewById(R.id.btn_cancel_addcoment);

        btnComment.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void addComent() {

        showProgressDialog();

        String url = Parametros.URL_SHOW_COMENT;

        StringRequest addComentRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                hideProgressDialog();

                Log.d("COMENT RESPONSE", response.toString());

                if (response.equals("{\"comentario\":\"comentario insertado\"}")) {
                    Toast.makeText(AddCommentActivity.this, R.string.comment_done, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddCommentActivity.this, TimeLineActivity.class));
                    finish();
                } else {
                    Toast.makeText(AddCommentActivity.this, R.string.comment_error, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                Log.d("COMENT ERROR", "MESSAGE = " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String user = Parametros.USER;
                String pass = Parametros.PASS;
                HashMap<String, String> headers = new HashMap<>();
                String credentials = Base64.encodeToString((user + ":" + pass).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + credentials);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                user = etNickName.getText().toString().trim();
                comment = etComment.getText().toString().trim();
                idTimeline = RecetaTimeLine.getIdComentariosReceta();
                Log.d("RESPONSE ID TIMELINE", idTimeline);

                Map<String, String> map = new HashMap<>();
                map.put("usuario", user);
                map.put("comentario", comment);
                map.put("id_timeline", idTimeline);
                return map;
            }

            @Override
            public Priority getPriority() {
                return priority;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        addComentRequest.setRetryPolicy(policy);
        AppController.getInstance().setPriority(priority);
        AppController.getInstance().addToRequestQueue(addComentRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comentar_addcoment:
                if (etComment.getText().toString().equals("") & etNickName.getText().toString().equals("")) {
                    Toast.makeText(AddCommentActivity.this, "Debes llenar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    addComent();
                }
                break;
            case R.id.btn_cancel_addcoment:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
