package es.usal.tfg1.model;

public class Puntuacion {
    private String id;
    private String comentario;
    private float puntuacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public boolean setComentario(String comentario) {
        if(comentario.length() <= 300) {
            this.comentario = comentario;
            return  true;
        } else {
            return false;
        }
    }

    public float getPuntuacion() {
        return (float)puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Puntuacion() { }

    public Puntuacion(String id, String comentario, float puntuacion) {
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }
    public Puntuacion(String id, float puntuacion) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.comentario = "";
    }

    public Puntuacion(Puntuacion p) {
        this.id = p.id;
        this.puntuacion = p.puntuacion;
        this.comentario = p.comentario;
    }

}
