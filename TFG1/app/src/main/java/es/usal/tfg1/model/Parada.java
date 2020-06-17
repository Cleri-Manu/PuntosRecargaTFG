package es.usal.tfg1.model;

public class Parada {
    private double latitud;
    private double longitud;

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Parada() { }

    public Parada(double longitud, double latitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Parada(Parada parada) {
        this.latitud = parada.latitud;
        this.longitud = parada.longitud;
    }
}
