package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

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

public class Patologias extends AppCompatActivity {

    private ArrayList<Video> videosLumbalgia = new ArrayList<Video>();
    private ArrayList<Video> videosCervicalgia = new ArrayList<Video>();
    private ArrayList<Video> videosFascitisPlantar = new ArrayList<Video>();
    private ArrayList<Video> videosDolorHombro = new ArrayList<Video>();
    private ArrayList<Video> videosEpicondilitis = new ArrayList<Video>();
    private ArrayList<Video> videosEsguinceTobillo = new ArrayList<Video>();
    private ArrayList<Video> videosSindromePiramidal = new ArrayList<Video>();
    private ArrayList<Video> videosSinCargar = new ArrayList<Video>();
    private String nextToken = "";
    private ListView lista;
    private Video videoSinCargar;
    private LinearLayout layoutLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologias);
        lista = (ListView)(findViewById(R.id.lista));
        layoutLista = (LinearLayout)(findViewById(R.id.layoutLista));
        videoSinCargar = new Video("aaaa", "No has elegido ninguna lista", "aa", "https://yt3.ggpht.com/etFgHSdWHoZXJjiniPqQQotCkgOUQbA7Z7ETFVAYk2JR7pQOfwtQG2S5D8ABeJRBy4l90riDyg=w2120-fcrop64=1,00005a57ffffa5a8-nd-c0xffffffff-rj-k-no");
        videosSinCargar.add(videoSinCargar);
        generarListaSinCargar(videosSinCargar);
        cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-abfmZ8WlryXzMKPZUNL8XZ&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        cargarVideosCervicalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-ZTzpXD2fT1NatzYOJj2YYA&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        cargarVideosFascitisPlantar("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-bvCWJTSfxpQS816NSuyBG5&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        cargarVideosDolorHombro("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-bFfhFAnPQHyleEINOlANo7&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        cargarVideosEpiconditilis("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-ZiGGLC4lALSUgcHVvt_huH&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        cargarVideosEsguinceTobillo("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-Z6lpGDnuk4tw7eTmwwiJH9&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        cargarVideosSindromePiramidal("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-ZypxsyzsLhyly3lQSeNlpl&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30");
        final ToggleButton toggleLumbalgia = (ToggleButton) findViewById(R.id.botonLumbalgia);
        final ToggleButton toggleCervicalgia = (ToggleButton) findViewById(R.id.botonCervicalgia);
        final ToggleButton toggleFascitisPlantar = (ToggleButton) findViewById(R.id.botonFascitisPlantar);
        final ToggleButton toggleDolorHombro = (ToggleButton) findViewById(R.id.botonDolorHombro);
        final ToggleButton toggleEpiconditilis = (ToggleButton) findViewById(R.id.botonEpiconditilis);
        final ToggleButton toggleEsguinceTobillo = (ToggleButton) findViewById(R.id.botonEsguinceTobillo);
        final ToggleButton toggleSindromePiramidal = (ToggleButton) findViewById(R.id.botonSindromePiramidal);
        toggleLumbalgia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleLumbalgia.setTextOn("Ocultar lumbalgía");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleCervicalgia.setChecked(false);
                    toggleCervicalgia.setVisibility(View.GONE);
                    toggleFascitisPlantar.setChecked(false);
                    toggleFascitisPlantar.setVisibility(View.GONE);
                    toggleDolorHombro.setChecked(false);
                    toggleDolorHombro.setVisibility(View.GONE);
                    toggleEpiconditilis.setChecked(false);
                    toggleEpiconditilis.setVisibility(View.GONE);
                    toggleEsguinceTobillo.setChecked(false);
                    toggleEsguinceTobillo.setVisibility(View.GONE);
                    toggleSindromePiramidal.setChecked(false);
                    toggleSindromePiramidal.setVisibility(View.GONE);
                    generarLista(videosLumbalgia);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleCervicalgia.setVisibility(View.VISIBLE);
                    toggleFascitisPlantar.setVisibility(View.VISIBLE);
                    toggleDolorHombro.setVisibility(View.VISIBLE);
                    toggleEpiconditilis.setVisibility(View.VISIBLE);
                    toggleEsguinceTobillo.setVisibility(View.VISIBLE);
                    toggleSindromePiramidal.setVisibility(View.VISIBLE);

                }
            }
        });
        toggleCervicalgia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleCervicalgia.setTextOn("Ocultar Cervicalgía");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleLumbalgia.setChecked(false);
                    toggleLumbalgia.setVisibility(View.GONE);
                    toggleFascitisPlantar.setChecked(false);
                    toggleFascitisPlantar.setVisibility(View.GONE);
                    toggleDolorHombro.setChecked(false);
                    toggleDolorHombro.setVisibility(View.GONE);
                    toggleEpiconditilis.setChecked(false);
                    toggleEpiconditilis.setVisibility(View.GONE);
                    toggleEsguinceTobillo.setChecked(false);
                    toggleEsguinceTobillo.setVisibility(View.GONE);
                    toggleSindromePiramidal.setChecked(false);
                    toggleSindromePiramidal.setVisibility(View.GONE);
                    generarLista(videosCervicalgia);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleLumbalgia.setVisibility(View.VISIBLE);
                    toggleFascitisPlantar.setVisibility(View.VISIBLE);
                    toggleDolorHombro.setVisibility(View.VISIBLE);
                    toggleEpiconditilis.setVisibility(View.VISIBLE);
                    toggleEsguinceTobillo.setVisibility(View.VISIBLE);
                    toggleSindromePiramidal.setVisibility(View.VISIBLE);
                }
            }
        });
        toggleFascitisPlantar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleFascitisPlantar.setTextOn("Ocultar Fascitis Plantar");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleLumbalgia.setChecked(false);
                    toggleLumbalgia.setVisibility(View.GONE);
                    toggleCervicalgia.setChecked(false);
                    toggleCervicalgia.setVisibility(View.GONE);
                    toggleDolorHombro.setChecked(false);
                    toggleDolorHombro.setVisibility(View.GONE);
                    toggleEpiconditilis.setChecked(false);
                    toggleEpiconditilis.setVisibility(View.GONE);
                    toggleEsguinceTobillo.setChecked(false);
                    toggleEsguinceTobillo.setVisibility(View.GONE);
                    toggleSindromePiramidal.setChecked(false);
                    toggleSindromePiramidal.setVisibility(View.GONE);
                    generarLista(videosFascitisPlantar);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleLumbalgia.setVisibility(View.VISIBLE);
                    toggleCervicalgia.setVisibility(View.VISIBLE);
                    toggleDolorHombro.setVisibility(View.VISIBLE);
                    toggleEpiconditilis.setVisibility(View.VISIBLE);
                    toggleEsguinceTobillo.setVisibility(View.VISIBLE);
                    toggleSindromePiramidal.setVisibility(View.VISIBLE);
                }
            }
        });
        toggleDolorHombro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleDolorHombro.setTextOn("Ocultar Dolor de Hombro");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleLumbalgia.setChecked(false);
                    toggleLumbalgia.setVisibility(View.GONE);
                    toggleCervicalgia.setChecked(false);
                    toggleCervicalgia.setVisibility(View.GONE);
                    toggleFascitisPlantar.setChecked(false);
                    toggleFascitisPlantar.setVisibility(View.GONE);
                    toggleEpiconditilis.setChecked(false);
                    toggleEpiconditilis.setVisibility(View.GONE);
                    toggleEsguinceTobillo.setChecked(false);
                    toggleEsguinceTobillo.setVisibility(View.GONE);
                    toggleSindromePiramidal.setChecked(false);
                    toggleSindromePiramidal.setVisibility(View.GONE);
                    generarLista(videosDolorHombro);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleLumbalgia.setVisibility(View.VISIBLE);
                    toggleCervicalgia.setVisibility(View.VISIBLE);
                    toggleFascitisPlantar.setVisibility(View.VISIBLE);
                    toggleEpiconditilis.setVisibility(View.VISIBLE);
                    toggleEsguinceTobillo.setVisibility(View.VISIBLE);
                    toggleSindromePiramidal.setVisibility(View.VISIBLE);
                }
            }
        });
        toggleEpiconditilis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleEpiconditilis.setTextOn("Ocultar Epicondilitis y epitrocleítis");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleLumbalgia.setChecked(false);
                    toggleLumbalgia.setVisibility(View.GONE);
                    toggleCervicalgia.setChecked(false);
                    toggleCervicalgia.setVisibility(View.GONE);
                    toggleFascitisPlantar.setChecked(false);
                    toggleFascitisPlantar.setVisibility(View.GONE);
                    toggleDolorHombro.setChecked(false);
                    toggleDolorHombro.setVisibility(View.GONE);
                    toggleEsguinceTobillo.setChecked(false);
                    toggleEsguinceTobillo.setVisibility(View.GONE);
                    toggleSindromePiramidal.setChecked(false);
                    toggleSindromePiramidal.setVisibility(View.GONE);
                    generarLista(videosEpicondilitis);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleLumbalgia.setVisibility(View.VISIBLE);
                    toggleCervicalgia.setVisibility(View.VISIBLE);
                    toggleFascitisPlantar.setVisibility(View.VISIBLE);
                    toggleDolorHombro.setVisibility(View.VISIBLE);
                    toggleEsguinceTobillo.setVisibility(View.VISIBLE);
                    toggleSindromePiramidal.setVisibility(View.VISIBLE);
                }
            }
        });
        toggleEsguinceTobillo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleEsguinceTobillo.setTextOn("Ocultar Esguince de Tobillo");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleLumbalgia.setChecked(false);
                    toggleLumbalgia.setVisibility(View.GONE);
                    toggleCervicalgia.setChecked(false);
                    toggleCervicalgia.setVisibility(View.GONE);
                    toggleFascitisPlantar.setChecked(false);
                    toggleFascitisPlantar.setVisibility(View.GONE);
                    toggleDolorHombro.setChecked(false);
                    toggleDolorHombro.setVisibility(View.GONE);
                    toggleEpiconditilis.setChecked(false);
                    toggleEpiconditilis.setVisibility(View.GONE);
                    toggleSindromePiramidal.setChecked(false);
                    toggleSindromePiramidal.setVisibility(View.GONE);
                    generarLista(videosEsguinceTobillo);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleLumbalgia.setVisibility(View.VISIBLE);
                    toggleCervicalgia.setVisibility(View.VISIBLE);
                    toggleFascitisPlantar.setVisibility(View.VISIBLE);
                    toggleDolorHombro.setVisibility(View.VISIBLE);
                    toggleEpiconditilis.setVisibility(View.VISIBLE);
                    toggleSindromePiramidal.setVisibility(View.VISIBLE);
                }
            }
        });
        toggleSindromePiramidal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleSindromePiramidal.setTextOn("Ocultar Sindrome Piramidal");
                    layoutLista.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                    toggleLumbalgia.setChecked(false);
                    toggleLumbalgia.setVisibility(View.GONE);
                    toggleCervicalgia.setChecked(false);
                    toggleCervicalgia.setVisibility(View.GONE);
                    toggleFascitisPlantar.setChecked(false);
                    toggleFascitisPlantar.setVisibility(View.GONE);
                    toggleDolorHombro.setChecked(false);
                    toggleDolorHombro.setVisibility(View.GONE);
                    toggleEpiconditilis.setChecked(false);
                    toggleEpiconditilis.setVisibility(View.GONE);
                    toggleEsguinceTobillo.setChecked(false);
                    toggleEsguinceTobillo.setVisibility(View.GONE);
                    generarLista(videosSindromePiramidal);
                    lista.setVisibility(View.VISIBLE);
                } else {
                    lista.setVisibility(View.INVISIBLE);
                    toggleLumbalgia.setVisibility(View.VISIBLE);
                    toggleCervicalgia.setVisibility(View.VISIBLE);
                    toggleFascitisPlantar.setVisibility(View.VISIBLE);
                    toggleDolorHombro.setVisibility(View.VISIBLE);
                    toggleEpiconditilis.setVisibility(View.VISIBLE);
                    toggleEsguinceTobillo.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public void generarListaSinCargar(ArrayList<Video> videos){
        String[] titulos = new String[videos.size()];
        String[] fotos = new String[videos.size()];
        final String[] videoIDS = new String[videos.size()];
        for (int i=0; i<videos.size(); i++){
            titulos[i] = videos.get(i).getTitulo();
            fotos[i] = videos.get(i).getImagen();
            videoIDS[i] = videos.get(i).getVideoID();
        }
        CustomList adapter = new CustomList(Patologias.this, titulos, fotos);
        lista.setAdapter(adapter);
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
        CustomList adapter = new CustomList(Patologias.this, titulos, fotos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Intent intent = null;
                        try {
                            Context context = getApplicationContext();
                            context.getPackageManager().getPackageInfo("com.google.android.youtube", 0);
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoIDS[position]));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } catch (Exception e) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoIDS[position]));
                        }
                        startActivity(intent);
                    }
                });
    }

    public void cargarVideosLumbalgia(String url) {
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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosLumbalgia.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-abfmZ8WlryXzMKPZUNL8XZ&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }

    public void cargarVideosCervicalgia(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask2 com = new ComunicacionTask2();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask2 extends AsyncTask<String, Void, String> {

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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosCervicalgia.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-ZTzpXD2fT1NatzYOJj2YYA&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }

    public void cargarVideosFascitisPlantar(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask3 com = new ComunicacionTask3();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask3 extends AsyncTask<String, Void, String> {

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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosFascitisPlantar.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-bvCWJTSfxpQS816NSuyBG5&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }

    public void cargarVideosDolorHombro(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask4 com = new ComunicacionTask4();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask4 extends AsyncTask<String, Void, String> {

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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosDolorHombro.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-bFfhFAnPQHyleEINOlANo7&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }
    public void cargarVideosEpiconditilis(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask5 com = new ComunicacionTask5();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask5 extends AsyncTask<String, Void, String> {

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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosEpicondilitis.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-ZiGGLC4lALSUgcHVvt_huH&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }

    public void cargarVideosEsguinceTobillo(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask6 com = new ComunicacionTask6();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask6 extends AsyncTask<String, Void, String> {

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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosEsguinceTobillo.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-Z6lpGDnuk4tw7eTmwwiJH9&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }

    public void cargarVideosSindromePiramidal(String url) {
        //iniciar tarea en segundo plano
        ComunicacionTask7 com = new ComunicacionTask7();
        //le pasa como parámetro la dirección
        //de la página
        com.execute(url);


    }

    private class ComunicacionTask7 extends AsyncTask<String, Void, String> {

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
                    type = job.getString("kind");
                    if (type.equals("youtube#playlistItem")) {
                        JSONObject snippet = job.getJSONObject("snippet");
                        videoTitle = cortarTitulos(snippet.getString("title"));
                        videoDescription = snippet.getString("description");
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject medium = thumbnails.getJSONObject("medium");
                        videoImage = medium.getString("url");
                        JSONObject ids = snippet.getJSONObject("resourceId");
                        videoId = ids.getString("videoId");
                        Video video = new Video(videoId, videoTitle, videoDescription, videoImage);
                        videosSindromePiramidal.add(video);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(nextToken!="") {
                cargarVideosLumbalgia("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&type=video&playlistId=PLdDOMYhbAZ-ZypxsyzsLhyly3lQSeNlpl&key=AIzaSyArBI9PaihSf2ShUV3zeQLby9ItDDNvJgE&maxResults=30&pageToken=" + nextToken);
            }
        }

    }


    public String cortarTitulos(String titulo) {
        String[] tokens = titulo.split("-");
        return tokens[0];
    }

    public void menuVideos(View v){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.finish();
    }
    public void menuPatologias(View v){
        Intent intent = new Intent(this, Patologias.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.finish();
    }
    public void menuCalendario(View v){
        Intent intent = new Intent(this, Calendario.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.finish();
    }
    public void menuContacto(View v){
        Intent intent = new Intent(this, Contacto.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.finish();
    }

}
