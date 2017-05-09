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

}
