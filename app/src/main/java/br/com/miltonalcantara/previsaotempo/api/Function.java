package br.com.miltonalcantara.previsaotempo.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Milton Alcântara on 02/06/2016.
 */
public class Function {

    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";

    private static final String OPEN_WEATHER_MAP_API = "56fc64fce3ffe3b5347ac09534369fac";

    public static String setWeatherIcon(int idAtual, long solNascente, long sunset) {
        int id = idAtual / 100;
        String icone = "";
        if (idAtual == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= solNascente && currentTime < sunset) {
                icone = "&#xf00d;";
            } else {
                icone = "&#xf02e;";
            }
        } else {
            switch (id) {
                case 2:
                    icone = "&#xf01e;";
                    break;
                case 3:
                    icone = "&#xf01c;";
                    break;
                case 7:
                    icone = "&#xf014;";
                    break;
                case 8:
                    icone = "&#xf013;";
                    break;
                case 6:
                    icone = "&#xf01b;";
                    break;
                case 5:
                    icone = "&#xf019;";
                    break;
            }
        }
        return icone;
    }


    public interface AsyncResponse {

        void processFinish(String output1, String output2, String output3, String output4, String output5, String output6, String output7, String output8);
    }


    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;//interface callback

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse; //Atribuir interface callback através do construtor
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                jsonWeather = getWeatherJSON(params[0], params[1]);
            } catch (Exception e) {
                Log.d("Erro", "Não pode processar resultados JSON", e);
            }

            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();


                    String city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                    String description = details.getString("description").toUpperCase(Locale.US);
                    String temperature = String.format("%.2f", main.getDouble("temp"));
                    String humidity = main.getString("humidity") + "%";
                    String pressure = main.getString("pressure") + " hPa";
                    String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                    String iconText = setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000);

                    delegate.processFinish(city, description, temperature, humidity, pressure, updatedOn, iconText, "" + (json.getJSONObject("sys").getLong("sunrise") * 1000));

                }
            } catch (JSONException e) {
            }


        }
    }


    public static JSONObject getWeatherJSON(String lat, String lon) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // Este valor será 404 se o pedido não foi bem-sucedida
            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch (Exception e) {
            return null;
        }
    }


}
