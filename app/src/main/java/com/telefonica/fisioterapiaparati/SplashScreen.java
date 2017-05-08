package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by telefonica on 05/05/2017.
 */

public class SplashScreen extends AppCompatActivity {

    private GifImageView gif;
    ArrayList<Video> videos = new ArrayList<Video>();
    private String nextToken = "";
    private String cadenaSP = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        if(isNetworkAvailable()) {
            cargarVideos("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&channelId=UCYALMdLMd75Q7BTyRikYz5g&part=snippet,id&order=date");
        }
        gif = (GifImageView) findViewById(R.id.gifSplash);


        try{
            InputStream inputStream = getAssets().open("Gif2.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gif.setBytes(bytes);
            gif.startAnimation();
        }
        catch (IOException ex){
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this,MainActivity.class));
                SplashScreen.this.finish();
            }
        },10000);
    }

    public void cargarVideos(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask com = new ComunicacionTask();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask extends AsyncTask<String, Void, String> {

        //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {

            String cadenaJson = "";
            try {
                //monta la url con la dirección y parámetro
                //de envío
                URL url = new URL(params[0]);
                URLConnection con = url.openConnection();
                //recuperacion de la respuesta JSON
                String s;
                InputStream is = con.getInputStream();
                //utilizamos UTF-8 para que interprete
                //correctamente las ñ y acentos
                BufferedReader bf = new BufferedReader(
                        new InputStreamReader(is, Charset.forName("UTF-8")));
                while ((s = bf.readLine()) != null) {
                    cadenaJson += s;
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return cadenaJson;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                nextToken = object.optString("nextPageToken");
                JSONArray items = object.getJSONArray("items");
                String videoId;
                String videoTitle;
                String videoDescription;
                String videoImage;
                String type;
                for (int i = 0; i < items.length(); i++) {
                    JSONObject job = items.getJSONObject(i);
                    JSONObject ids = job.getJSONObject("id");
                    type = ids.getString("kind");
                    if (type.equals("youtube#video")) {
                        videoId = ids.getString("videoId");
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videos.add(video);
                        cadenaSP=video+"|";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideos("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&channelId=UCYALMdLMd75Q7BTyRikYz5g&part=snippet,id&order=date&pageToken=" + nextToken);
            }else{
                pasarSP();
            }
        }


    }

    public String cortarTitulos(String titulo) {
        String[] tokens = titulo.split("-");
        return tokens[0];
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void pasarSP(){
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("CadenaVideos", cadenaSP);
        editor.commit();
    }
}
