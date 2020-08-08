package es.usal.tfg1.model;

public class Parada {
    /**
     * Tipo double que representa la latitud de un punto de recaga en el mapa
     */
    private double latitud;
    /**
     * Tipo double que representa la longitud de un punto de recarga en el mapa
     */
    private double longitud;

    /** Devulve la latitud de la Parada actual. Tipo double
     *
     * @return
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Asigna un valor a la latitud de la Parada. Tipo double
     * @param latitud
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /** Devulve la longitud de la Parada actual. Tipo double
     *
     * @return
     */
    public double getLongitud() {
        return longitud;
    }
    /**
     * Asigna un valor a la longitud de la Parada. Tipo double
     * @param longitud
     */

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Dadas la longitud y latitud comprueban si se encuentran dentro de valores correctos para su representacion en un mapa que utilice la API de GoogleMaps
     * @param longitud
     * @param latitud
     * @return
     */
    public boolean checkInvCoords(float longitud, float latitud) {
        if(latitud < -85 ||latitud > 85) {
            return true;
        }
        if(longitud < -180 ||longitud > 180) {
            return true;
        }
        return false;
    }

    /**
     * Constructor vacío para Parada
     */
    public Parada() { }

    /**
     * Constructor para Parada a partir de su latitud y longitud
     * @param longitud
     * @param latitud
     */
    public Parada(double longitud, double latitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Constructor para Parada a partir de otra
     * @param parada
     */
    public Parada(Parada parada) {
        this.latitud = parada.latitud;
        this.longitud = parada.longitud;
    }

}
