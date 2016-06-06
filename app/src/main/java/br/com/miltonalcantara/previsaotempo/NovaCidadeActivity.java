package br.com.miltonalcantara.previsaotempo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.miltonalcantara.previsaotempo.database.DBHelper;

public class NovaCidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_cidade);

        final EditText editText_Nome = (EditText) findViewById(R.id.editText_nome);
        final EditText editText_Latitude = (EditText) findViewById(R.id.editText_latitude);
        final EditText editText_Longitude = (EditText) findViewById(R.id.editText_longitude);
        Button button_add = (Button) findViewById(R.id.button_add);

        assert button_add != null;
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(editText_Nome.getText().toString(), editText_Latitude.getText().toString(), editText_Longitude.getText().toString());
            }
        });
    }

    private void addContact(String nome, String latitude, String longitude) {
        DBHelper myDb = new DBHelper(this);
        myDb.inserirCidade(nome, latitude, longitude);
        Toast.makeText(NovaCidadeActivity.this, "Cidade " + nome + " added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
