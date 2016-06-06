package br.com.miltonalcantara.previsaotempo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.miltonalcantara.previsaotempo.database.DBHelper;

public class EditarLocalActivity extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_local);

        Bundle bundle = getIntent().getExtras();
        final EditText editText_Nome = (EditText) findViewById(R.id.editText_nome);
        final EditText editText_Latitude = (EditText) findViewById(R.id.editText_latitude);
        final EditText editText_Longitude = (EditText) findViewById(R.id.editText_longitude);

        if (bundle != null) {
            id = (int) bundle.get("id");
            editText_Nome.setText((String) bundle.get("nome"));
            editText_Latitude.setText((String) bundle.get("latitude"));
            editText_Longitude.setText((String) bundle.get("longitude"));
        }


        Button button_update = (Button) findViewById(R.id.button_update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocal(id, editText_Nome.getText().toString(), editText_Latitude.getText().toString(), editText_Longitude.getText().toString());
            }
        });
    }

    private void updateLocal(int id, String nome, String latitude, String longitude ) {
        DBHelper myDb = new DBHelper(this);
        myDb.atualizarLocal(id, nome, latitude, longitude);
        Toast.makeText(EditarLocalActivity.this, "Local " + nome + " editado!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
