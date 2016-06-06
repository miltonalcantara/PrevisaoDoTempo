package br.com.miltonalcantara.previsaotempo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Milton Alcântaraon 05/06/2016.
 */
public class Utils {

    private static Context context;

    public Utils(Context context) {
        this.context = context;
    }

    /**
     * @param email - E-mail
     */
    public void mandarEmail(String email) {
        String[] TO = { email };
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        context.startActivity(emailIntent);
    }

    /**
     * @param paginaFacebookID - Colocar o ID do facebook
     * @param paginaFacebookLink - Colocar o Link Html
     */
    public static void getOpenFacebookIntent(String paginaFacebookID, String paginaFacebookLink) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checar se o FB está instalado.
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(paginaFacebookID)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(paginaFacebookLink))); //Caso não esteja, abrir em um navagador
        }
    }
}
