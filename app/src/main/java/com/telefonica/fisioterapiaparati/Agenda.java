package com.telefonica.fisioterapiaparati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
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
    private String cadenaVideosEjercio;
    private ArrayList<Video> videosRutina = new ArrayList<Video>();
    private Date fechaInicio;
    private Date fechaHoy;
    private Date fechaFin;
    private TextView etiquetaDiasTotales;
    private SeekBar progreso;
    private ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        SharedPreferences sp = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        cadenaVideosEjercio = sp.getString("VideosEjercicio","");
        try {
            anadirVideos(cadenaVideosEjercio);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lista = (ListView)(findViewById(R.id.listaVideosRutina));
        generarLista(videosRutina);
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
        long diff = fechaFin.getTime() - fechaHoy.getTime();
        int days = (int) (diff / (1000*60*60*24));
        System.out.println("Dias que quedan "+days+" fecha de inicio "+fechaInicio+" fecha fin "+fechaFin);
        etiquetaDiasTotales.setText("LLevas "+(diasTotales-days)+" días de rutina, te quedan "+days);
        progreso = (SeekBar)(findViewById(R.id.progreso));
        progreso.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        progreso.setMax(diasTotales);
        progreso.setProgress(diasTotales-days);
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

    public void anadirCalendario(View v){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", fechaInicio.getTime());
        intent.putExtra("allDay", true);
        //intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", fechaFin.getTime()+60*60*1000);
        intent.putExtra("title", "Debes realizar tu rutina de ejercicios - Fisioterapia para Ti");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Las URLs de los vídeos a realizar son: \n\n"+videosURLs(videosRutina));
        intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);
        startActivity(intent);
    }

    public String videosURLs(ArrayList<Video> videosRutina){
        String cadena = "";
        for(int i = 0; i < videosRutina.size(); i++){
            cadena += "https://www.youtube.com/watch?v="+videosRutina.get(i).getVideoID()+"\n";
        }
        return cadena;
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
        CustomList adapter = new CustomList(Agenda.this, titulos, fotos);
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
