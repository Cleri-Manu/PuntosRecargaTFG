package es.usal.tfg1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PuntoRecarga {
    /**
     * Tipo Parada que representa la latitud y longitud del punto de recarga en el mapa
     */
    private Parada parada;
    /**
     * Tipo String. ID unica del punto de recarga en Firebase
     */
    private String id;
    /**
     * Tipo String. Nombre del punto de recarga. Se recomienda utilizar la direccion y el codigo postal
     */
    private String nombre;
    /**
     * Tipo String. ID del creador del punto de recarga
     */
    private String creadorID;
    /**
     * Tipo boolean. Indica si el punta de recarga esta o no cerificado
     */
    private boolean verificado;
    /**
     * Tipo String. Descripción del punto de recarga y/o sus servicios
     */
    private String descripcion;
    /**
     * Tipo String. Diastancia del punto de recarga a la posicion actual
     */
    private String distancia;
    /**
     * Tipo float. Diastancia del punto de recarga a la posicion actual
     */
    private float distaciaF;
    /**
     * Tipo boolean. Indica si el punto de recarga utiliza energias renovables
     */
    private boolean eco;
    /**
     * Tipo ArrayList<Puntuaicones>. Lista que contiene todas las puntuaciones asociadas al punto de recarga
     */
    private ArrayList<Puntuacion> puntuaciones;
    /**
     * Tipo float. Media de las putuaciones del punto de recarga
     */
    private float puntuacion;

    /**
     * Devuelve la Parada del punto de recarga
     * @return
     */
    public Parada getParada() {
        return parada;
    }

    /**
     * Asigna un valor a la parada del punto de recarga a partir de otra parada
     * @param parada
     */
    public void setParada(Parada parada) {
        this.parada = parada;
    }

    /**
     * Devuelve el ID del punto de recarga
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna un id al punto de recarga
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del puto de recarga
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre al punto de recarga
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el id del creador del punto de recarga
     * @return
     */
    public String getCreadorID() {
        return creadorID;
    }

    /**
     * Asigna un id de creador del punto de recarga
     * @param id
     */
    public void setCreadorID(String id) {
        this.creadorID = id;
    }

    /**
     * Decuelve el estado de verificacion del punto de recarga
     * @return
     */
    public boolean isVerificado() {
        return verificado;
    }

    /**
     * Asigna un estado de verifiacion al punto de recarga
     * @param verificado
     */
    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    /**
     * Devuelve la descripcion del punto de recarga
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Tipo String. Devuelve la distancia del punto de recarga
     * @return
     */
    public String getDistancia() {
        return distancia;
    }

    /**
     * Tipo float. Asigna la distancia al punto de recarga
     * @param distancia
     */
    public void setDistanciaF(float distancia) {
        DecimalFormat df1 = new DecimalFormat("#");
        df1.setRoundingMode(RoundingMode.CEILING);
        this.distancia = df1.format(distancia/1000) + " km";
        distaciaF = distancia/1000;
    }

    /**
     * Tipo float. Devuelve la distancia al punto de recarga
     * @return
     */
    public float getDistaciaF() {
        return distaciaF;
    }

    /**
     * Tipo String. Asigna la distancia al punto de recarga
     * @param distancia
     */
    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    /**
     * Devuelve verdadero o falso indicando si el punto de recarga utiliza energias renovables o no
     * @return
     */
    public boolean isEco() {
        return eco;
    }

    /**
     * Asigna el estado de eco del punto de recarga
     * @param eco
     */
    public void setEco(boolean eco) {
        this.eco = eco;
    }

    /**
     * Devuelve una ArrayList con las puntuaciones asociadas al punto de recarga
     * @return
     */
    public ArrayList<Puntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    /**
     * Asigna un valor al array de puntuaciones del punto de recarga y actualiza el valor de la puntuacion
     * @param puntuaciones
     */
    public void setPuntuaciones(ArrayList<Puntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
        updatePuntuacion();
    }

    /**
     * Actualiza el valor de la puntuacion del punto de recarga haciendo la media de todas sus putuaciones
     */
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

    /**
     * Devuelve la puntuación del punto de recarga
     * @return
     */
    public float getPuntuacion() {
        return puntuacion;
    }

    /**
     * Asigna una puntuacion a unpunto de recarga
     * @param puntuacion
     */
    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Asigna una descripcion a un punto de recarga
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Constructor vacio de la clase PuntoRecarga
     */
    public PuntoRecarga() { }

    /**
     * Constructor de la clase PuntoRecarga a partir de parametros
     * @param parada
     * @param id
     * @param nombre
     * @param creadorID
     * @param verificado
     * @param descripcion
     * @param eco
     */
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

    /**
     * Constructor de la clase PuntoRecarga a partir de otro punto de recarga
     * @param p
     */
    public PuntoRecarga(PuntoRecarga p) {
        this.parada = p.parada;
        this.id = p.id;
        this.nombre = p.nombre;
        this.creadorID = p.creadorID;
        this.verificado = p.verificado;
        this.descripcion = p.descripcion;
        this.distancia = p.distancia;
        this.distaciaF = p.distaciaF;
        this.eco = p.eco;
        this.puntuacion = p.puntuacion;
        this.puntuaciones = new ArrayList<Puntuacion>();
        if(p.puntuaciones != null) {
            for (Puntuacion puntos: p.puntuaciones) {
                this.puntuaciones.add(new Puntuacion(puntos));
            }
        }
    }

    /**
     * Metodo que convierte una cadena de texto resultante de la lectura de un archivo JSON en puntos de recarga
     * EN concreto se encarga de converitr los JSON con el formato de los datos en abierto de la Junta de Castilla y Leon para los puntos de recarga de vehiculos electricos
     * @param jsonText
     * @return
     * @throws JSONException
     */
    public ArrayList<PuntoRecarga> createPRFromJson(String jsonText) throws JSONException {
        ArrayList<PuntoRecarga> newPRs = new ArrayList<PuntoRecarga>();

        //Posiciones 0 nombre
        //Posiciones 1 coordeandas
        //Posiciones 2 descripcion
        JSONObject root = new JSONObject(jsonText);
        JSONObject document = root.getJSONObject("document");
        JSONArray list = document.getJSONArray("list");
        ArrayList<String> texto = new ArrayList<String>();
        ArrayList<String> desc = new ArrayList<String>();
        for (int i = 0; i< list.length(); i++) {
            root = list.getJSONObject(i);
            JSONObject element = root.getJSONObject("element");
            JSONArray attribute = element.getJSONArray("attribute");
            texto.add(attribute.getJSONObject(5).getString("string") + " - " + attribute.getJSONObject(6).getString("string"));
            texto.add(attribute.getJSONObject(15).getString("string"));
            texto.add(attribute.getJSONObject(1).getString("string"));
        }
        for(int j = 0; j < texto.size(); j+=3) {
            String[] parts = texto.get(j+1).split("#");
            newPRs.add(new PuntoRecarga(texto.get(j), parts[0], parts[1], texto.get(j+2)));
        }
        return newPRs;
    }

    /**
     * Constructor de la clase PuntoRecarga a partir de parametros
     * @param nombre
     * @param lat
     * @param lon
     * @param descripcion
     */
    public PuntoRecarga(String nombre, String lat, String lon, String descripcion) {
        this.nombre = nombre;
        this.parada = new Parada(Double.parseDouble(lon),Double.parseDouble(lat));
        this.verificado = true;
        this.descripcion = descripcion;
        this.eco = false;
        this.creadorID = "OFICIAL";
    }
}
