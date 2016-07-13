package com.gt.dev.lazaro.elcaldo.vista.actividades;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.dev.lazaro.elcaldo.R;
import com.gt.dev.lazaro.elcaldo.adaptadores.Categoria;

/**
 * Created by Lazaro on 10/9/2015.
 */
public class BuscarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText auto;
    private Button voz;
    private ListView lista;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    /**
     * Arreglo de Strings para el autocompletado
     */
    String[] platos = new String[]{"Caldo de gallina india", "Caldo de res"
            , "Caldo de pata", "Caldo de mariscos", "Sopa de tortuga", "Kaq ik"
            , "Jocón", "Pepián", "Pepian Indio", "Pollo en amarillo", "Hilachas de res"
            , "Revolcado de cerdo", "Pulique", "Gallo en chicha", "Tamal colorado"
            , "Tamal negro", "Tamales de chipilin", "Tamales de cambray"
            , "Chepes y tayuyos", "Tamalitos de elote", "Tamales de pache",
            "Chuchitos", "Rellenitos de platano", "Camote en dulce", "Pan de manteca",
            "Champurradas", "Chiquiadores", "Barquillos", "Quesadillas",
            "Platos en mole", "Platanos en gloria", "Durazno en miel", "Ayote en dulce"
            , "Nances en miel", "Bueñuelos", "Nuegados", "Torrejas", "Molletes",
            "Bocadillos de coco", "Alborotos", "Algodones de azucar", "Chilacayote en dulce"
            , "Mazapán", "Tortillas de maíz", "Churrasco", "Chirmol", "Chiles rellenos",
            "Chancletas", "Frijoles colorados", "Yuca con chicharron"
            , "Verduras en escabeche", "Enchiladas", "Frijoles volteados", "Frijoles negros colados"
            , "Mojarra frita", "Ceviche", "Guacamol", "Pan de banano", "Pan de elote"};

    private ArrayList<String> arreglo = new ArrayList<String>();
    int textlength = 0;

    /**
     * onCreateS
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        voz = (Button) findViewById(R.id.bVoz);
        auto = (EditText) findViewById(R.id.tvAuto);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        lista = (ListView) findViewById(R.id.lvBuscar);
        setSupportActionBar(toolbar);

        lista.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, platos));

        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = auto.getText().length();
                arreglo.clear();
                for (int i = 0; i < platos.length; i++) {
                    if (textlength <= platos[i].length()) {
                        if (auto.getText().toString().equalsIgnoreCase((String) platos[i].subSequence(0, textlength))) {
                            arreglo.add(platos[i]);
                        }
                    }
                }
                lista.setAdapter(new ArrayAdapter<String>(BuscarActivity.this, android.R.layout.simple_list_item_1, arreglo));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Toast.makeText(getApplicationContext(), R.string.toast_buscar, Toast.LENGTH_LONG).show();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ArrayList<String>

                //ArrayList<String> arreglo = (ArrayList) parent.getItemAtPosition(position);
                String strNombre = (String) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(DetalleComidaActivity.NOMBRE_PLATO, strNombre);
                bundle.putInt("llave", R.drawable.splashlandscapelarge);

                Intent intent = new Intent(BuscarActivity.this, DetalleComidaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        voz.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }

    /**
     * Metodo para escuchar el comando por voz
     */
    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * La activiadad que espera el resultado
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    auto.setText(result.get(0));
                }
                break;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        // if (id == android.R.id.home)
        //   NavUtils.navigateUpFromSameTask(this);

        return super.onOptionsItemSelected(item);
    }


}
