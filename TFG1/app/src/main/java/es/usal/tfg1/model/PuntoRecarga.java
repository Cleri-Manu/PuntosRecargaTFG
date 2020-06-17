package es.usal.tfg1.model;

import java.util.ArrayList;

public class PuntoRecarga {
    private Parada parada;
    private String id;
    private String nombre;
    private String creadorID;
    private boolean verificado;
    private String descripción;
    private String distancia;
    private boolean eco;
    private ArrayList<Puntuacion> puntuaciones;
    private double puntuacion;

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }

    public void setParada(double longitud, double latitud) {
        this.parada = new Parada(longitud, latitud);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreadorID() {
        return creadorID;
    }

    public void setCreadorID(String id) {
        this.creadorID = id;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getDescripción() {
        return descripción;
    }

    public boolean setDescripción(String descripción) {
        if(descripción.length() <= 300) {
            this.descripción = descripción;
            return  true;
        } else {
            return false;
        }
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public boolean isEco() {
        return eco;
    }

    public void setEco(boolean eco) {
        this.eco = eco;
    }

    public ArrayList<Puntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<Puntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
        double count = 0;
        double total = 0;
        for (Puntuacion p:puntuaciones) {
            count++;
            puntuacion += p.getPuntuacion();
        }
        if(count != 0)
            this.puntuacion = total/count;
    }

    public void setPuntuacionesNew(ArrayList<Puntuacion> puntuaciones) {
        for (Puntuacion p: puntuaciones) {
            this.puntuaciones.add(new Puntuacion(p));
        }
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public PuntoRecarga() { }
    public PuntoRecarga(Parada parada, String id, String nombre, String creadorID, boolean verificado, String descripción, ArrayList<Puntuacion> puntuaciones) {
        this.parada = parada;
        this.id = id;
        this.nombre = nombre;
        this.creadorID = creadorID;
        this.verificado = verificado;
        this.descripción = descripción;
        this.puntuaciones = puntuaciones;
    }

    public PuntoRecarga(Parada parada, String id, String nombre, String creadorID, boolean verificado, String descripción, String distancia, boolean eco, ArrayList<Puntuacion> puntuaciones) {
        this.parada = parada;
        this.id = id;
        this.nombre = nombre;
        this.creadorID = creadorID;
        this.verificado = verificado;
        this.descripción = descripción;
        this.distancia = distancia;
        this.eco = eco;
        this.puntuaciones = puntuaciones;
    }

    public PuntoRecarga(Parada parada, String id, String nombre, String creadorID, boolean verificado, String descripción, String distancia, boolean eco) {
        this.parada = parada;
        this.id = id;
        this.nombre = nombre;
        this.creadorID = creadorID;
        this.verificado = verificado;
        this.descripción = descripción;
        this.distancia = distancia;
        this.eco = eco;
    }
}
