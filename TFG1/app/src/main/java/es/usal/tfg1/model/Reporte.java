package es.usal.tfg1.model;

public class Reporte {
    /**
     * Tipo String. ID del punto de recarga que se reporta
     */
    private String puntoRecargaID;
    /**
     * Tipo String. ID del usuario que realiza el reporte
     */
    private String usuarioID;

    /**
     * Devuelve el ID del punto de recarga reportado
     * @return
     */
    public String getPuntoRecargaID() {
        return puntoRecargaID;
    }

    /**
     * Asigna el valor del ID del punto de recarga que se ha reportado
     * @param puntoRecargaID
     */
    public void setPuntoRecargaID(String puntoRecargaID) {
        this.puntoRecargaID = puntoRecargaID;
    }

    /**
     * Devuelve el ID del usuario que ha realizado el reporte
     * @return
     */
    public String getUsuarioID() {
        return usuarioID;
    }

    /**
     * Asigna el valor del ID del usuario que ha realizado el reporte
     * @param usuarioID
     */
    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    /**
     * Constructor vacio de la clase Reporte
     */
    public Reporte() { }

    /**
     * Constructor de la Clase Reporte a partir de parametros
     * @param puntoRecargaID
     * @param usuarioID
     */
    public Reporte(String puntoRecargaID, String usuarioID) {
        this.puntoRecargaID = puntoRecargaID;
        this.usuarioID = usuarioID;
    }
}
