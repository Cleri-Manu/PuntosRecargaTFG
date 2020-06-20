package es.usal.tfg1.model;

public class Reporte {
    private String puntoRecargaID;
    private String usuarioID;
    private String comentarioID;

    public String getPuntoRecargaID() {
        return puntoRecargaID;
    }

    public void setPuntoRecargaID(String puntoRecargaID) {
        this.puntoRecargaID = puntoRecargaID;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Reporte() { }

    public Reporte(String puntoRecargaID, String usuarioID) {
        this.puntoRecargaID = puntoRecargaID;
        this.usuarioID = usuarioID;
        this.comentarioID = "";
    }

    public Reporte(String puntoRecargaID, String usuarioID, String comentarioID) {
        this.puntoRecargaID = puntoRecargaID;
        this.usuarioID = usuarioID;
        this.comentarioID = comentarioID;
    }
}
