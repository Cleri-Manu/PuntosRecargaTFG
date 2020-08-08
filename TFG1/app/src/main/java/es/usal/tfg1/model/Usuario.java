package es.usal.tfg1.model;

public class Usuario {
    /**
     * Tipo String. ID del usuario en Firebase
     */
    private String id;
    /**
     * Tipo String. Email del usuario en Firebase
     */
    private String email;
    /**
     * Tipo boolean. Rol del usuario
     * True=admin, False=normal user
     */
    private boolean rol = false;
    /**
     * Tipo float. Autonomia del vehiculo del usuario
     */
    private float autonomiaF;
    /**
     * Tipo String. Autonomia del vehiculo del usuario
     */
    private String autonomia;

    /**
     * Constructor vacio de la clase Usuario
     */
    public Usuario() { }

    /**
     * Constructor de la clase Usuario a partir de otro Usuario
     * @param user
     */
    public Usuario(Usuario user) {
        this.id = user.id;
        this.email = user.email;
        this.rol = user.rol;
        this.autonomia = user.autonomia;
        this.autonomiaF = user.autonomiaF;
    }

    /**
     * Constructor de la clase Usuario a partir de parametros
     * @param id
     * @param email
     */
    public Usuario(String id, String email) {
        this.id = id;
        this.email = email;
        this.rol = false;
        autonomiaF = 200f;
        autonomia = "200";
    }

    /**
     * Tipo float. Devuelve la autonomia del vehiculo del usuario
     * @return
     */
    public float getAutonomiaF() {
        return autonomiaF;
    }

    /**
     * Tipo float. Asigna un valor la autonomia del vehiculo del usuario
     * @param autonomiaF
     */
    public void setAutonomiaF(float autonomiaF) {
        this.autonomiaF = autonomiaF;
    }

    /**
     * Tipo String. Devuelve la autonomia del vehiculo del usuario
     * @return
     */
    public String getAutonomia() {
        return autonomia;
    }

    /**
     * Tipo String. Asigna un valor la autonomia del vehiculo del usuario
     * @param autonomia
     */
    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
        this.autonomiaF = Float.parseFloat(autonomia);
    }

    /**
     * Devuelve el id del usuario
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna un id al usuario
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el email del usuario
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asigna un valor al email del usuario
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el rol del usuario
     * @return
     */
    public boolean getRol() {
        return rol;
    }

    /**
     * Asigna un rol al usuario
     * @param rol
     */
    public void setRol(boolean rol) {
        this.rol = rol;
    }
}
