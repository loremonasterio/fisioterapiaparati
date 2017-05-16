package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Agenda extends AppCompatActivity {

    private int diasTotales;
    private int diasSemanales;
    private int vecesDiarias;
    private String cadenaVideosEjercio;
    private ArrayList<Video> videosRutina = new ArrayList<Video>();
    private Date fechaInicio;
    private Date fechaHoy;
    private Date fechaFin;
    private TextView etiquetaDiasTotales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        fechaHoy = new Date();
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        diasTotales = sp.getInt("DiasTotales",0);
        try {
            fechaInicio = df.parse(sp.getString("FechaInicio",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicio);
        c.add(Calendar.DATE, diasTotales);
        fechaFin = c.getTime();
        etiquetaDiasTotales = (TextView)(findViewById(R.id.diasTotales));
        etiquetaDiasTotales.setText("Quedan "+diasTotales+" días de rutina");
        diasSemanales = sp.getInt("DiasSemanales",0);
        vecesDiarias = sp.getInt("VecesDiarias",0);
        cadenaVideosEjercio = sp.getString("VideosEjercicio","");
        long diff = fechaFin.getTime() - fechaInicio.getTime();
        System.out.println(diff);
        try {
            anadirVideos(cadenaVideosEjercio);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayout imagenes = (LinearLayout) findViewById(R.id.imagenes);
        for(int i = 0; i < videosRutina.size(); i++){
            ImageView imagen = new ImageView(getApplicationContext());
            imagen.setMaxWidth(120);
            imagen.setMaxHeight(68);
            imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
            new Agenda.ImageLoadTask(videosRutina.get(i).getImagen(), imagen).execute();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 100);
            params.setMargins(10, 0, 10, 0);
            imagen.setLayoutParams(params);
            imagenes.addView(imagen);
        }

    }

    public void anadirVideos(String cadena) throws JSONException {
        StringTokenizer st = new StringTokenizer(cadena,"|");
        while(st.hasMoreElements()){
            Video video = new Video(st.nextElement().toString());
            videosRutina.add(video);
        }
    }

    public void borrarEjercicio(View v){
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("EjercicioCreado", false);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
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
