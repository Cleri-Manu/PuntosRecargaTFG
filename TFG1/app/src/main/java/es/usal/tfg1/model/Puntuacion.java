package es.usal.tfg1.model;

public class Puntuacion {
    /**
     * Tipo String. ID del creador de la puntuacion
     */
    private String id;
    /**
     * Tipo String. Opinion o comentario asociado a la puntuacion
     */
    private String comentario;
    /**
     * Tipo float. Valor de la puntuacion dada
     */
    private float puntuacion;

    /**
     * Devuelve la id del creador de la puntuacion
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna un valor al id creador de la puntuacion
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el comentario u opinion de la puntuacion
     * @return
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Asigna un valor al comentario u opinion de la puntuacion
     * @param comentario
     * @return
     */
    public boolean setComentario(String comentario) {
        if(comentario.length() <= 300) {
            this.comentario = comentario;
            return  true;
        } else {
            return false;
        }
    }

    /**
     * Devuelve la puntuacion asociada
     * @return
     */
    public float getPuntuacion() {
        return (float)puntuacion;
    }

    /**
     * Asigna un valor numerico a la puntuacion
     * @param puntuacion
     */
    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Constructor vacio
     */
    public Puntuacion() { }

    /**
     * Constructor de la clse Puntuacion a partir de parametros
     * @param id
     * @param comentario
     * @param puntuacion
     */
    public Puntuacion(String id, String comentario, float puntuacion) {
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }

    /**
     *      * Constructor de la clse Puntuacion a partir de otra Puntuacion
     * @param p
     */
    public Puntuacion(Puntuacion p) {
        this.id = p.id;
        this.puntuacion = p.puntuacion;
        this.comentario = p.comentario;
    }

}
