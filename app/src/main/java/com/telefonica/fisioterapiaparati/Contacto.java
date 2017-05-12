package com.telefonica.fisioterapiaparati;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Contacto extends AppCompatActivity {
    private TextView nombre;
    private TextView telefono;
    private TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactolayout);
        nombre = (TextView)findViewById(R.id.inputnombre);
        telefono = (TextView)findViewById(R.id.inputtelefono);
        mensaje = (TextView)findViewById(R.id.inputmensaje);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void enviar(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","lore.monasterio.mtnz@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email enviado desde app Fisioterapia para TI");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nombre: "+nombre.getText()+"\n"+" Teléfono: "+telefono.getText()+"\n"+"\n"+mensaje.getText());
        startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
    }

    public void borrar(View v){
        nombre.setText("");
        telefono.setText("");
        mensaje.setText("");
    }

    public void lanzarTwitter(View v){
        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=631135762"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Fisioparati"));
        }
        this.startActivity(intent);
    }

    public void lanzarFacebook(View v) {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/466054160120444"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Fisioterapia-para-TI-466054160120444"));
        }
        this.startActivity(intent);
    }

    public void llamar(View v){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "686855186", null)));
    }

    public void lanzarYoutube(View v) {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.google.android.youtube", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/FisioterapiaparaTI"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/FisioterapiaparaTI"));
        }
        this.startActivity(intent);
    }

    public void lanzarWeb(View v){
        Intent intent = null;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fisioterapiaparati.com/"));
        this.startActivity(intent);
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
