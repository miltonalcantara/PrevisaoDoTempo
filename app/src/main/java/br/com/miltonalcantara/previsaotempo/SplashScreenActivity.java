package br.com.miltonalcantara.previsaotempo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * Created by Milton Alcântara on 05/06/2016.
 */
public class SplashScreenActivity extends Activity implements Runnable, OnClickListener {

    private Handler handler = new Handler();
    private boolean started;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout splash = (RelativeLayout) findViewById(R.id.id_splash);
        splash.setOnClickListener(this);
        handler.postDelayed(this, 2000);
    }

    public void run() {
        iniciarPrograma();
    }

    public void onClick(View v) {
        iniciarPrograma();
    }


    private synchronized void iniciarPrograma() {

        if (!started) {
            started = true;
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            setResult(RESULT_OK);
            finish();
        }
    }
}
