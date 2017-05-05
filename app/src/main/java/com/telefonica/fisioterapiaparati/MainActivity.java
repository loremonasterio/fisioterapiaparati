package com.telefonica.fisioterapiaparati;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Video> videos = new ArrayList<Video>();
    ArrayList<String> titulos = new ArrayList<String>();
    ArrayList<String> imagenes = new ArrayList<String>();
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_content);
        lista=(ListView)findViewById(R.id.listaTitulos);
        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(Ranking.this, android.R.layout.simple_list_item_1,titulos);
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
        cargarVideos();
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
        com.execute("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&channelId=UCYALMdLMd75Q7BTyRikYz5g&part=snippet,id&order=date&maxResults=30");


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
                String[] videosIds = new String[items.length()];
                String[] videosTitle = new String[items.length()];
                String[] videosDescription = new String[items.length()];
                String[] videosImage = new String[items.length()];
                String type;
                for (int i = 0; i < items.length(); i++) {
                    JSONObject job = items.getJSONObject(i);
                    JSONObject ids = job.getJSONObject("id");
                    type = ids.getString("kind");
                    if (type.equals("youtube#video")) {
                        videosIds[i] = ids.getString("videoId");
                    }
                    JSONObject snippet = job.getJSONObject("snippet");
                    videosTitle[i] = snippet.getString("title");
                    videosDescription[i] = snippet.getString("description");
                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                    JSONObject medium = thumbnails.getJSONObject("medium");
                    videosImage[i] = medium.getString("url");
                    Video video = new Video(videosIds[i], videosTitle[i], videosDescription[i], videosImage[i]);
                    videos.add(video);
                }
                for (int j=0; j<videos.size();j++){
                    titulos.add(videos.get(j).getTitulo());
                    imagenes.add(videos.get(j).getImagen());
                }
                //ArrayAdapter<String> adapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,titulos);
               CustomList adapter = new CustomList(MainActivity.this, videosTitle, videosImage);
                //Onadapter adapter = new Onadapter(MainActivity.this, videosTitle, videosImage);
                lista.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
