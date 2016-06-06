package br.com.miltonalcantara.previsaotempo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.miltonalcantara.previsaotempo.database.DBHelper;
import br.com.miltonalcantara.previsaotempo.modelo.Cidade;

public class MainActivity extends AppCompatActivity {

    ListView listView_contact;
    ArrayList<Cidade> arrayList_cidades = new ArrayList<Cidade>();
    Cidade c;
    int enderecoImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cidade que será detalhada na próxima tela
        listView_contact = (ListView) findViewById(R.id.listView_contacts);
        listView_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cidadeEscolhida(c, position, DetalhesActivity.class);
            }
        });

        //Editar o nome e localização da Cidade Cadastrada
        listView_contact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cidadeEscolhida(c, position, EditarCidadeActivity.class);

                return true;
            }
        });

    }

    private void cidadeEscolhida(Cidade c, int position, Class activity) {
        c = arrayList_cidades.get(position);
        Intent my_intent = new Intent(MainActivity.this, activity);

        my_intent.putExtra("id", c.getId());
        my_intent.putExtra("nome", c.getNome());
        my_intent.putExtra("latitude", c.getLatitude());
        my_intent.putExtra("longitude", c.getLongitude());
        fotoCidade(c.getNome());
        my_intent.putExtra("foto", enderecoImagem);

        startActivity(my_intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_nova_cidade:
                startActivity(new Intent(MainActivity.this, NovaCidadeActivity.class));
                break;

            case R.id.menu_item_sobre:
                startActivity(new Intent(MainActivity.this, SobreActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList_cidades.clear();

        DBHelper myDb = new DBHelper(this);
        ArrayList<String> arrayNomes = new ArrayList<String>();

        int x = myDb.getTodasCidades().size();

        if (myDb.getTodasCidades().size() > 0) {
            atualizarLista(myDb, arrayNomes);
        } else { //Usando o app pela primeira vez, add as cidades Bases
            addPrevisaoCidade("Aracaju, SE", "-11.0013704", "-37.2435545");
            addPrevisaoCidade("Belém, PA", "-1.3444569", "-48.5191217");
            addPrevisaoCidade("Belo Horizonte, MG", "-19.9178164", "-44.1003978");
            addPrevisaoCidade("Boa Vista, RR", "3.0169942", "-61.204985");
            addPrevisaoCidade("Brasília, DF", "-15.7215843", "-48.2177339");
            addPrevisaoCidade("Campo Grande, MS", "-20.8745433", "-54.8223597");
            addPrevisaoCidade("Cuiabá, MT", "-15.6143632", "-56.1815475");
            addPrevisaoCidade("Curitiba,PR", "-25.6272978", "-49.4369474");
            addPrevisaoCidade("Fortaleza, CE", "-3.7905171", "-38.5886789");
            addPrevisaoCidade("Florianópolis, SC", "-27.6138612", "-48.6229076");
            addPrevisaoCidade("Goiânia, GO", "-16.6426313", "-49.5419642");
            addPrevisaoCidade("João Pessoa, PB", "-7.149646", "-35.0231863");
            addPrevisaoCidade("Macapá, AP", "0.1019439", "-51.1670723");
            addPrevisaoCidade("Maceió, AL", "-9.5350006", "-35.8273656");
            addPrevisaoCidade("Manaus, AM", "-2.5717632", "-60.543204");
            addPrevisaoCidade("Natal, RN", "-5.8019356", "-35.3620617");
            addPrevisaoCidade("Palmas, TO", "-10.2600298", "-48.4869751");
            addPrevisaoCidade("Porto Alegre, RS", "-30.1019259", "-51.4354133");
            addPrevisaoCidade("Porto Velho, RO", "-8.75652", "-63.9946476");
            addPrevisaoCidade("Recife, PE", "-8.0464256", "-35.072229");
            addPrevisaoCidade("Rio Branco, AC", "-9.9861541", "-67.9013082");
            addPrevisaoCidade("Rio de Janeiro, RJ", "-22.9102555", "-44.0073913");
            addPrevisaoCidade("Salvador, BA", "-12.8807582", "-38.4877968");
            addPrevisaoCidade("São Luís, MA", "-2.5606246", "-44.3978628");
            addPrevisaoCidade("São Paulo, SP", "-23.6815315", "-46.8754808");
            addPrevisaoCidade("Teresina, PI", "-5.1863825", "-43.0809775");
            addPrevisaoCidade("Vitória, ES", "-20.2821361", "-40.4259873");
            atualizarLista(myDb, arrayNomes);
            Toast.makeText(MainActivity.this, "Banco de dados criado", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayNomes);
        listView_contact.setAdapter(myAdapter);
    }

    private void atualizarLista(DBHelper myDb, ArrayList<String> arrayNomes) {
        for (int i = 0; i < myDb.getTodasCidades().size(); i++) {
            arrayNomes.add(myDb.getTodasCidades().get(i).getNome());
            arrayList_cidades.add(myDb.getTodasCidades().get(i));
        }
    }

    private void addPrevisaoCidade(String nome, String latitude, String longitude) {
        DBHelper myDb = new DBHelper(this);
        myDb.inserirCidade(nome, latitude, longitude);
    }

    private int fotoCidade(String nomeCidade) {
        if (nomeCidade.equals("Aracaju, SE"))
            enderecoImagem = R.drawable.img_aracaju;
        else if (nomeCidade.equals("Belém, PA"))
            enderecoImagem = R.drawable.img_belem;
        else if (nomeCidade.equals("Belo Horizonte, MG"))
            enderecoImagem = R.drawable.img_bh;
        else if (nomeCidade.equals("Boa Vista, RR"))
            enderecoImagem = R.drawable.img_boavista;
        else if (nomeCidade.equals("Brasília, DF"))
            enderecoImagem = R.drawable.img_brasilia;
        else if (nomeCidade.equals("Campo Grande, MS"))
            enderecoImagem = R.drawable.img_campogrande;
        else if (nomeCidade.equals("Cuiabá, MT"))
            enderecoImagem = R.drawable.img_cuiaba;
        else if (nomeCidade.equals("Curitiba,PR"))
            enderecoImagem = R.drawable.img_curitiba;
        else if (nomeCidade.equals("Fortaleza, CE"))
            enderecoImagem = R.drawable.img_fortaleza;
        else if (nomeCidade.equals("Florianópolis, SC"))
            enderecoImagem = R.drawable.img_floripa;
        else if (nomeCidade.equals("Goiânia, GO"))
            enderecoImagem = R.drawable.img_goiania;
        else if (nomeCidade.equals("João Pessoa, PB"))
            enderecoImagem = R.drawable.img_joaopessoa;
        else if (nomeCidade.equals("Macapá, AP"))
            enderecoImagem = R.drawable.img_macapa;
        else if (nomeCidade.equals("Maceió, AL"))
            enderecoImagem = R.drawable.img_maceio;
        else if (nomeCidade.equals("Manaus, AM"))
            enderecoImagem = R.drawable.img_manaus;
        else if (nomeCidade.equals("Natal, RN"))
            enderecoImagem = R.drawable.img_natal;
        else if (nomeCidade.equals("Palmas, TO"))
            enderecoImagem = R.drawable.img_palmas;
        else if (nomeCidade.equals("Porto Alegre, RS"))
            enderecoImagem = R.drawable.img_portoalegre;
        else if (nomeCidade.equals("Porto Velho, RO"))
            enderecoImagem = R.drawable.img_portovelho;
        else if (nomeCidade.equals("Recife, PE"))
            enderecoImagem = R.drawable.img_recife;
        else if (nomeCidade.equals("Rio Branco, AC"))
            enderecoImagem = R.drawable.img_acre;
        else if (nomeCidade.equals("Rio de Janeiro, RJ"))
            enderecoImagem = R.drawable.img_rj;
        else if (nomeCidade.equals("Salvador, BA"))
            enderecoImagem = R.drawable.img_salvador;
        else if (nomeCidade.equals("São Luís, MA"))
            enderecoImagem = R.drawable.img_saoluiz;
        else if (nomeCidade.equals("São Paulo, SP"))
            enderecoImagem = R.drawable.img_sp;
        else if (nomeCidade.equals("Teresina, PI"))
            enderecoImagem = R.drawable.img_teresina;
        else if (nomeCidade.equals("Vitória, ES"))
            enderecoImagem = R.drawable.img_vitoria;
        else
            enderecoImagem = R.drawable.img_custom;


        return enderecoImagem;
    }
}
