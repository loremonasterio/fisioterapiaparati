package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Video> videos = new ArrayList<Video>();
    private String[] tituloSinInternet = {"Se necesita conexión a internet para acceder a los vídeos"};
    private String[] fotoSinInternet = {"https://static.parastorage.com/services/santa/1.2207.10/static/images/video/not-found.png"};
    private ListView lista;
    private String cadenaVideos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_content);
        lista=(ListView)findViewById(R.id.listaTitulos);
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        cadenaVideos = sp.getString("CadenaVideos","");
        try {
            anadirVideos(cadenaVideos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        generarLista(videos);

        if(!isNetworkAvailable()) {
            CustomList adapter = new CustomList(MainActivity.this, tituloSinInternet, fotoSinInternet);
            lista.setAdapter(adapter);
        }

    }

    public void onResume(){
        super.onResume();
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
    public void anadirVideos(String cadena) throws JSONException {
        StringTokenizer st = new StringTokenizer(cadena,"|");
        while(st.hasMoreElements()){
            Video video = new Video(st.nextElement().toString());
            videos.add(video);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void menuVideos(View v){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
    public void menuPatologias(View v){
        Intent intent = new Intent(this, Patologias.class);
        this.startActivity(intent);
        this.finish();
    }
    public void menuCalendario(View v){
        Intent intent = new Intent(this, Calendario.class);
        this.startActivity(intent);
        this.finish();
    }
    public void menuContacto(View v){
        Intent intent = new Intent(this, Contacto.class);
        this.startActivity(intent);
        this.finish();
    }
}
