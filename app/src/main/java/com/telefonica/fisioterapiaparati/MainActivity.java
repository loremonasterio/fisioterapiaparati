package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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


public class MainActivity extends AppCompatActivity {

    ArrayList<Video> videos = new ArrayList<Video>();
    String[] tituloSinInternet = {"Se necesita conexión a internet para acceder a los vídeos"};
    String[] fotoSinInternet = {"https://static.parastorage.com/services/santa/1.2207.10/static/images/video/not-found.png"};
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_content);
        lista=(ListView)findViewById(R.id.listaTitulos);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        if(isNetworkAvailable()) {
            cargarVideos();
        }else{
            CustomList adapter = new CustomList(MainActivity.this, tituloSinInternet, fotoSinInternet);
            lista.setAdapter(adapter);
        }
    }

    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cargarVideos() {
        //iniciar tarea en segundo plano
        ComunicacionTask com = new ComunicacionTask();
        //le pasa como parámetro la dirección
        //de la página
        com.execute("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&channelId=UCYALMdLMd75Q7BTyRikYz5g&part=snippet,id&order=date&maxResults=50");


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
                    }
                }
                generarLista(videos);
            } catch (JSONException e) {
                e.printStackTrace();
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

    public void generarLista(ArrayList<Video> videos){
        String[] titulos = new String[videos.size()];
        String[] fotos = new String[videos.size()];
        final String[] videoIDS = new String[videos.size()];
        for (int i=0; i<videos.size(); i++){
            titulos[i] = videos.get(i).getTitulo();
            fotos[i] = videos.get(i).getImagen();
            videoIDS[i] = videos.get(i).getVideoID();
        }
        CustomList adapter = new CustomList(MainActivity.this, titulos, fotos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoIDS[position]));
                        startActivity(intent);
                    }
                });
    }

}
