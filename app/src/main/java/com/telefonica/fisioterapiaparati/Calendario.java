package com.telefonica.fisioterapiaparati;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

public class Calendario extends AppCompatActivity {
   SeekBar diasTotales;
   SeekBar diasSemanales;
   SeekBar vecesDiarias;
   TextView etiquetaDiasTotales;
   TextView etiquetaDiasSemanales;
   TextView etiquetaVecesDiarias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        diasTotales = (SeekBar)findViewById(R.id.diasTotales);
        diasSemanales = (SeekBar)findViewById(R.id.diasSemana);
        vecesDiarias = (SeekBar)findViewById(R.id.vecesdiarias);
        etiquetaDiasTotales = (TextView)findViewById(R.id.etiquetaDiasTotales);
        etiquetaDiasSemanales = (TextView)findViewById(R.id.etiquetaDiasSemanales);
        etiquetaVecesDiarias = (TextView)findViewById(R.id.etiquetaVecesDiarias);
        diasTotales.setOnSeekBarChangeListener(new diastotalesListener());
        diasSemanales.setOnSeekBarChangeListener(new diassemanalesListener());
        vecesDiarias.setOnSeekBarChangeListener(new vecesdiariasListener());

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
    private class diassemanalesListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
            etiquetaDiasSemanales.setText(""+progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
    private class vecesdiariasListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
            etiquetaVecesDiarias.setText(""+progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
}
