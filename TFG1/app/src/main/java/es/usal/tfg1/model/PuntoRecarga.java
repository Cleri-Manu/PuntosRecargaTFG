package es.usal.tfg1.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PuntoRecarga {
    private Parada parada;
    private String id;
    private String nombre;
    private String creadorID;
    private boolean verificado;
    private String descripcion;
    private String distancia;
    private float distaciaF;
    private boolean eco;
    private ArrayList<Puntuacion> puntuaciones;
    private float puntuacion;


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

    public String getDescripcion() {
        return descripcion;
    }

    public boolean setDescripción(String descripción) {
        if(descripción.length() <= 300) {
            this.descripcion = descripción;
            return  true;
        } else {
            return false;
        }
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistanciaF(float distancia) {
        DecimalFormat df1 = new DecimalFormat("#");
        df1.setRoundingMode(RoundingMode.CEILING);
        this.distancia = df1.format(distancia/1000) + " km";
        distaciaF = distancia/1000;
    }

    public float getDistaciaF() {
        return distaciaF;
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
        updatePuntuacion();
    }

    public void updatePuntuacion() {
        float count = 0;
        float total = 0;
        if(puntuaciones != null) {
            for (Puntuacion p:puntuaciones) {
                count++;
                total += p.getPuntuacion();
            }
            if(count != 0){
                puntuacion = total/count;
            }
        }
    }

    public void setPuntuacionesNew(ArrayList<Puntuacion> puntuaciones) {
        for (Puntuacion p: puntuaciones) {
            this.puntuaciones.add(new Puntuacion(p));
        }
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PuntoRecarga() { }
    public PuntoRecarga(Parada parada, String id, String nombre, String creadorID, boolean verificado, String descripcion, ArrayList<Puntuacion> puntuaciones) {
        this.parada = parada;
        this.id = id;
        this.nombre = nombre;
        this.creadorID = creadorID;
        this.verificado = verificado;
        this.descripcion = descripcion;
        this.puntuaciones = puntuaciones;
    }

    public PuntoRecarga(Parada parada, String id, String nombre, String creadorID, boolean verificado, String descripcion, String distancia, boolean eco, ArrayList<Puntuacion> puntuaciones) {
        this.parada = parada;
        this.id = id;
        this.nombre = nombre;
        this.creadorID = creadorID;
        this.verificado = verificado;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.eco = eco;
        this.puntuaciones = puntuaciones;
    }

    public PuntoRecarga(Parada parada, String id, String nombre, String creadorID, boolean verificado, String descripcion, boolean eco) {
        this.parada = parada;
        this.id = id;
        this.nombre = nombre;
        this.creadorID = creadorID;
        this.verificado = verificado;
        this.descripcion = descripcion;
        this.distancia = "";
        this.eco = eco;
        puntuacion = 0;
        puntuaciones = new ArrayList<Puntuacion>();
    }

    public PuntoRecarga(PuntoRecarga p) {
        this.parada = p.parada;
        this.id = p.id;
        this.nombre = p.nombre;
        this.creadorID = p.creadorID;
        this.verificado = p.verificado;
        this.descripcion = p.descripcion;
        this.distancia = p.distancia;
        this.eco = p.eco;
        puntuacion = p.puntuacion;
        puntuaciones = new ArrayList<Puntuacion>();
        for (Puntuacion puntos: p.puntuaciones) {
            puntuaciones.add(new Puntuacion(puntos));
        }
    }
}
