package br.com.miltonalcantara.previsaotempo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.miltonalcantara.previsaotempo.api.Function;

public class DetalhesActivity extends AppCompatActivity {

    ImageView fotoLocal;
    LinearLayout background;

    private int id;
    private TextView nomeLocal;
    private TextView descricaoDoTempo;
    private TextView temperaturaAtual;
    private TextView umidadeDoAr;
    private TextView pressaoDoAr;
    private TextView iconeDoTempo;
    private TextView dataEhora;
    private String nome;
    private String latitude;
    private String longitude;

    private Typeface weatherFonte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        toolbar();
        weatherFonte = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weather.ttf");
        iniciandoViews();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = (int) bundle.get("id");
            nome = (String) bundle.get("nome");
            nomeLocal.setText(nome);
            latitude = (String) bundle.get("latitude");
            longitude = (String) bundle.get("longitude");

            fotoLocal.setImageResource((int) bundle.get("foto"));

            //Pegando a cor mais vibrante da foto e colocando no rodapé de informações das previsões futuras
            Bitmap foto = BitmapFactory.decodeResource(this.getResources(), (int) bundle.get("foto"));

            new Palette.Builder(foto).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch vibrantLightSwatch = palette.getLightVibrantSwatch();
                    assert vibrantLightSwatch != null;
                    float[] vibrant = vibrantLightSwatch.getHsl();

                    background.setBackgroundColor(Color.HSVToColor(vibrant));
                }
            });
        }


        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String cidade, String descricao, String temperatura, String umidade, String pressao, String dataHora, String iconeTempo, String nascerDoSol) {

                dataEhora.setText(dataHora);
                if (descricao.equalsIgnoreCase("clear sky"))
                    descricao = "CÉU LIMPO";
                else if (descricao.equalsIgnoreCase("light rain"))
                    descricao = "CHUVA LEVE";
                else if (descricao.equalsIgnoreCase("broken clouds"))
                    descricao = "PARCIALMENTE NUBLADO";
                else if (descricao.equalsIgnoreCase("overcast clouds"))
                    descricao = "NUBLADO";
                else if (descricao.equalsIgnoreCase("scattered clouds"))
                    descricao = "NUVENS DISPERSAS";
                else if (descricao.equalsIgnoreCase("few clouds"))
                    descricao = "ALGUMAS NUVENS";
                else if (descricao.equalsIgnoreCase("moderate rain"))
                    descricao = "CHUVA MODERADA";
                else if (descricao.equalsIgnoreCase("few clouds"))
                    descricao = "CHUVA COM TROVOADAS";
                else if (descricao.equalsIgnoreCase("fog"))
                    descricao = "NÉVOA";
                else if (descricao.equalsIgnoreCase("heavy intensity rain"))
                    descricao = "NÉVOA";
                descricaoDoTempo.setText(descricao);
                double temp = Double.parseDouble(temperatura.replaceAll(",", "."));
                int res = (int) temp;
                temperatura = res + "°";
                temperaturaAtual.setText(temperatura);
                umidadeDoAr.setText(umidade);
                pressaoDoAr.setText(pressao);
                iconeDoTempo.setText(Html.fromHtml(iconeTempo));

            }
        });

        asyncTask.execute(latitude, longitude);
    }

    private void iniciandoViews() {
        fotoLocal = (ImageView) findViewById(R.id.id_foto_local);
        background = (LinearLayout) findViewById(R.id.id_background);

        nomeLocal = (TextView) findViewById(R.id.id_cidade_pais);
        descricaoDoTempo = (TextView) findViewById(R.id.id_descricao);
        temperaturaAtual = (TextView) findViewById(R.id.id_temperatura);
        umidadeDoAr = (TextView) findViewById(R.id.id_umidade);
        pressaoDoAr = (TextView) findViewById(R.id.id_pressao);
        iconeDoTempo = (TextView) findViewById(R.id.id_desenho_clima);
        dataEhora = (TextView) findViewById(R.id.id_data_hora);
        iconeDoTempo.setTypeface(weatherFonte);
    }

    //Setando a Toolbar
    private void toolbar() {
        // Inicializando a Toolbar e setando com uma actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            params.setMargins(0, getStatusBarHeight(), 0, 0);
            toolbar.setLayoutParams(params);
        }


        //Colocando a seta na cor branca
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Pegar a altura da statusbar para setar a barra em baixo dela
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
