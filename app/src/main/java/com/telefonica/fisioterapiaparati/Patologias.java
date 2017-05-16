package com.telefonica.fisioterapiaparati;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Patologias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologias);
    }

    public void generarListaPatologia(){

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
