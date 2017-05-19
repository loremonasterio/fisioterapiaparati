package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Calendario extends AppCompatActivity {

    private ArrayList<Video> videos = new ArrayList<Video>();
    private ArrayList<Video> videosRutina = new ArrayList<Video>();
    private ArrayList<String> videosAnadidos = new ArrayList<String>();
    private SeekBar diasTotales;
    private SeekBar diasSemanales;
    private SeekBar vecesDiarias;
    private TextView etiquetaDiasTotales;
    private TextView etiquetaDiasSemanales;
    private TextView etiquetaVecesDiarias;
    private ListView lista;
    private ImageView imagen1;
    private ImageView imagen2;
    private ImageView imagen3;
    private ImageView imagen4;
    private ImageView imagen5;
    private String cadenaVideos = "";
    private Boolean ejercicioCreado = false;
    String cadenaVideosEjercicio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        ejercicioCreado = sp.getBoolean("EjercicioCreado",false);
        if (ejercicioCreado) {
            Intent intent = new Intent(this, Agenda.class);
            this.startActivity(intent);
            this.finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        diasTotales = (SeekBar)findViewById(R.id.diasTotales);
        etiquetaDiasTotales = (TextView)findViewById(R.id.etiquetaDiasTotales);
        lista = (ListView)findViewById(R.id.listaVideos);
        imagen1 = (ImageView)findViewById(R.id.imagen1);
        imagen2 = (ImageView)findViewById(R.id.imagen2);
        imagen3 = (ImageView)findViewById(R.id.imagen3);
        imagen4 = (ImageView)findViewById(R.id.imagen4);
        imagen5 = (ImageView)findViewById(R.id.imagen5);
        imagen1.setDrawingCacheEnabled(true);
        imagen2.setDrawingCacheEnabled(true);
        imagen3.setDrawingCacheEnabled(true);
        imagen4.setDrawingCacheEnabled(true);
        imagen5.setDrawingCacheEnabled(true);
        cadenaVideos = sp.getString("CadenaVideos","");
        try {
            anadirVideos(cadenaVideos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        generarLista(videos);
        diasTotales.setOnSeekBarChangeListener(new diastotalesListener());
    }

    private class diastotalesListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
            etiquetaDiasTotales.setText(""+progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
    public void anadirVideos(String cadena) throws JSONException {
        StringTokenizer st = new StringTokenizer(cadena,"|");
        while(st.hasMoreElements()){
            Video video = new Video(st.nextElement().toString());
            videos.add(video);
        }
    }

    public void generarLista(ArrayList<Video> videos){
        final String[] titulos = new String[videos.size()];
        final String[] fotos = new String[videos.size()];
        final String[] videoIDS = new String[videos.size()];
        final String[] descripciones = new String[videos.size()];
        for (int i=0; i<videos.size(); i++){
            titulos[i] = videos.get(i).getTitulo();
            fotos[i] = videos.get(i).getImagen();
            videoIDS[i] = videos.get(i).getVideoID();
            descripciones[i] = videos.get(i).getDescripcion();
        }
        final CustomList adapter = new CustomList(Calendario.this, titulos, fotos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(imagen1.getTag(R.id.tag_video)!="ocupado0" && !videosAnadidos.contains(videoIDS[position])){
                            videosAnadidos.add(videoIDS[position]);
                            new Calendario.ImageLoadTask(fotos[position], imagen1).execute();
                            imagen1.setTag(R.id.tag_video,"ocupado0");
                            Video video = new Video(videoIDS[position], titulos[position], descripciones[position], fotos[position]);
                            imagen1.setTag(R.id.tag_video_id,video.getVideoID());
                            videosRutina.add(video);
                            cadenaVideosEjercicio +=video+"|";
                            view.setTag(R.id.tag_video,"ocupado0");
                            view.setTag(R.id.tag_video_id,position);
                            Toast.makeText(Calendario.this, "Video añadido, para eliminarlo pincha sobre su miniatura en la parte superior",Toast.LENGTH_SHORT).show();
                        }else if(imagen2.getTag(R.id.tag_video)!="ocupado1" && !videosAnadidos.contains(videoIDS[position])){
                            videosAnadidos.add(videoIDS[position]);
                            new Calendario.ImageLoadTask(fotos[position], imagen2).execute();
                            imagen2.setTag(R.id.tag_video,"ocupado1");
                            Video video = new Video(videoIDS[position], titulos[position], descripciones[position], fotos[position]);
                            imagen2.setTag(R.id.tag_video_id,video.getVideoID());
                            videosRutina.add(video);
                            cadenaVideosEjercicio +=video+"|";
                            view.setTag(R.id.tag_video,"ocupado1");
                            view.setTag(R.id.tag_video_id,position);
                            Toast.makeText(Calendario.this, "Video añadido, para eliminarlo pincha sobre su miniatura en la parte superior",Toast.LENGTH_SHORT).show();
                        }else if(imagen3.getTag(R.id.tag_video)!="ocupado2" && !videosAnadidos.contains(videoIDS[position])){
                            videosAnadidos.add(videoIDS[position]);
                            new Calendario.ImageLoadTask(fotos[position], imagen3).execute();
                            imagen3.setTag(R.id.tag_video,"ocupado2");
                            Video video = new Video(videoIDS[position], titulos[position], descripciones[position], fotos[position]);
                            imagen3.setTag(R.id.tag_video_id,video.getVideoID());
                            videosRutina.add(video);
                            cadenaVideosEjercicio +=video+"|";
                            view.setTag(R.id.tag_video,"ocupado2");
                            view.setTag(R.id.tag_video_id,position);
                            Toast.makeText(Calendario.this, "Video añadido, para eliminarlo pincha sobre su miniatura en la parte superior",Toast.LENGTH_SHORT).show();
                        }else if(imagen4.getTag(R.id.tag_video)!="ocupado3" && !videosAnadidos.contains(videoIDS[position])){
                            videosAnadidos.add(videoIDS[position]);
                            new Calendario.ImageLoadTask(fotos[position], imagen4).execute();
                            imagen4.setTag(R.id.tag_video,"ocupado3");
                            Video video = new Video(videoIDS[position], titulos[position], descripciones[position], fotos[position]);
                            imagen4.setTag(R.id.tag_video_id,video.getVideoID());
                            videosRutina.add(video);
                            cadenaVideosEjercicio +=video+"|";
                            view.setTag(R.id.tag_video,"ocupado3");
                            view.setTag(R.id.tag_video_id,position);
                            Toast.makeText(Calendario.this, "Video añadido, para eliminarlo pincha sobre su miniatura en la parte superior",Toast.LENGTH_SHORT).show();
                        }else if(imagen5.getTag(R.id.tag_video)!="ocupado4" && !videosAnadidos.contains(videoIDS[position])){
                            videosAnadidos.add(videoIDS[position]);
                            new Calendario.ImageLoadTask(fotos[position], imagen5).execute();
                            imagen5.setTag(R.id.tag_video,"ocupado4");
                            Video video = new Video(videoIDS[position], titulos[position], descripciones[position], fotos[position]);
                            imagen5.setTag(R.id.tag_video_id,video.getVideoID());
                            videosRutina.add(video);
                            cadenaVideosEjercicio +=video+"|";
                            view.setTag(R.id.tag_video,"ocupado4");
                            view.setTag(R.id.tag_video_id,position);
                            Toast.makeText(Calendario.this, "Video añadido, para eliminarlo pincha sobre su miniatura en la parte superior",Toast.LENGTH_SHORT).show();
                        }else{
                            if(videosAnadidos.size()==5){
                                AlertDialog alertDialog = new AlertDialog.Builder(Calendario.this,R.style.MyAlertDialogStyle).create();
                                alertDialog.setTitle("No se pueden añadir más vídeos");
                                alertDialog.setMessage("El máximo de vídeos para añadir es 5");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    }
                });
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    public void crearRutina(View v){
        Date fechaInicio = new Date();
        //Ejercicio ejercicio = new Ejercicio(diasTotales.getProgress(), diasSemanales.getProgress(), vecesDiarias.getProgress(), videosRutina, fechaInicio);
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("EjercicioCreado", true);
        editor.putInt("DiasTotales",diasTotales.getProgress());
        editor.putString("VideosEjercicio", cadenaVideosEjercicio);
        editor.putString("FechaInicio",fechaInicio.toString());
        //editor.putString("EjercicioResumen", ejercicio.toString());
        editor.commit();
        Intent intent = new Intent(this, Agenda.class);
        this.startActivity(intent);
        //System.out.println(ejercicio);

    }

    public void borrarVideo(View v){
        switch (v.getTag(R.id.tag_video).toString()){
            case "ocupado0":
                imagen1.setImageResource(R.drawable.no_image);
                for (int i = 0; i < videosRutina.size(); i++){
                    if(videosRutina.get(i).getVideoID().equals(v.getTag(R.id.tag_video_id))){
                        videosRutina.remove(i);
                    }
                }
                for (int i = 0; i < videosAnadidos.size(); i++){
                    if(videosAnadidos.get(i).equals(v.getTag(R.id.tag_video_id))){
                        videosAnadidos.remove(i);
                    }
                }
                v.setTag(R.id.tag_video,"");
                break;
            case "ocupado1":
                imagen2.setImageResource(R.drawable.no_image);
                for (int i = 0; i < videosRutina.size(); i++){
                    if(videosRutina.get(i).getVideoID().equals(v.getTag(R.id.tag_video_id))){
                        videosRutina.remove(i);
                    }
                }
                for (int i = 0; i < videosAnadidos.size(); i++){
                    if(videosAnadidos.get(i).equals(v.getTag(R.id.tag_video_id))){
                        videosAnadidos.remove(i);
                    }
                }
                v.setTag(R.id.tag_video,"");
                break;
            case "ocupado2":
                imagen3.setImageResource(R.drawable.no_image);
                for (int i = 0; i < videosRutina.size(); i++){
                    if(videosRutina.get(i).getVideoID().equals(v.getTag(R.id.tag_video_id))){
                        videosRutina.remove(i);
                    }
                }
                for (int i = 0; i < videosAnadidos.size(); i++){
                    if(videosAnadidos.get(i).equals(v.getTag(R.id.tag_video_id))){
                        videosAnadidos.remove(i);
                    }
                }
                v.setTag(R.id.tag_video,"");
                break;
            case "ocupado3":
                imagen4.setImageResource(R.drawable.no_image);
                for (int i = 0; i < videosRutina.size(); i++){
                    if(videosRutina.get(i).getVideoID().equals(v.getTag(R.id.tag_video_id))){
                        videosRutina.remove(i);
                    }
                }
                for (int i = 0; i < videosAnadidos.size(); i++){
                    if(videosAnadidos.get(i).equals(v.getTag(R.id.tag_video_id))){
                        videosAnadidos.remove(i);
                    }
                }
                v.setTag(R.id.tag_video,"");
                break;
            case "ocupado4":
                imagen5.setImageResource(R.drawable.no_image);
                for (int i = 0; i < videosRutina.size(); i++){
                    if(videosRutina.get(i).getVideoID().equals(v.getTag(R.id.tag_video_id))){
                        videosRutina.remove(i);
                    }
                }
                for (int i = 0; i < videosAnadidos.size(); i++){
                    if(videosAnadidos.get(i).equals(v.getTag(R.id.tag_video_id))){
                        videosAnadidos.remove(i);
                    }
                }
                v.setTag(R.id.tag_video,"");
                break;
        }
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