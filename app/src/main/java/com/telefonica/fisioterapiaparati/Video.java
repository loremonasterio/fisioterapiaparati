package com.telefonica.fisioterapiaparati;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by telefonica on 05/05/2017.
 */

public class Video {
    private String videoID;
    private String titulo;
    private String descripcion;
    private String url;
    private String imagen;

    public Video() {
        this.videoID = "";
        this.titulo = "";
        this.descripcion = "";
        this.url = "";
        this.imagen = "";
    }

    public Video(String videoID, String titulo, String descripcion, String imagen) {
        this.videoID = videoID;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url = "https://www.youtube.com/watch?v="+videoID;
        this.imagen = imagen;
    }
    public Video(String cadenaVideoCompleto) throws JSONException {
        JSONObject object = new JSONObject("{"+cadenaVideoCompleto+"}");
        this.videoID = object.getString("videoID");
        this.titulo = object.getString("titulo");
        this.descripcion = object.getString("descripcion");
        this.imagen = object.getString("imagen");
    }

    public String getVideoID() {
        return videoID;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() { return descripcion; }

    public String getUrl() {
        return url;
    }

    public String getImagen() {
        return imagen;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return
                "videoID:'" + videoID + '\'' +
                ", titulo:'" + titulo + '\'' +
                ", descripcion:'" + descripcion + '\'' +
                ", url:'" + url + '\'' +
                ", imagen:'" + imagen + '\''+'}';
    }
}
