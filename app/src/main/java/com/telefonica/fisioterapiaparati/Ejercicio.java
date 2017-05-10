package com.telefonica.fisioterapiaparati;

import java.util.ArrayList;

/**
 * Created by telefonica on 10/05/2017.
 */

public class Ejercicio {
    private int diasTotales;
    private int diasSemanales;
    private int vecesDiarias;
    private ArrayList<Video> videos;

    public Ejercicio(int diasTotales, int diasSemanales, int vecesDiarias, ArrayList<Video> videos) {
        this.diasTotales = diasTotales;
        this.diasSemanales = diasSemanales;
        this.vecesDiarias = vecesDiarias;
        this.videos = videos;
    }

    public int getDiasTotales() {
        return diasTotales;
    }

    public void setDiasTotales(int diasTotales) {
        this.diasTotales = diasTotales;
    }

    public int getDiasSemanales() {
        return diasSemanales;
    }

    public void setDiasSemanales(int diasSemanales) {
        this.diasSemanales = diasSemanales;
    }

    public int getVecesDiarias() {
        return vecesDiarias;
    }

    public void setVecesDiarias(int vecesDiarias) {
        this.vecesDiarias = vecesDiarias;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "diasTotales=" + diasTotales +
                ", diasSemanales=" + diasSemanales +
                ", vecesDiarias=" + vecesDiarias +
                ", videos=" + videos +
                '}';
    }
}
