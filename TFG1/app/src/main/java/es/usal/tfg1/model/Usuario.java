package es.usal.tfg1.model;

import com.google.firebase.auth.FirebaseUser;

public class Usuario {
    private String id;
    private String email;
    private boolean rol = false;    //True=admin, false=normal User
    private float autonomiaF;
    private String autonomia;
    public Usuario() {

    }
    public Usuario(Usuario user) {
        this.id = user.id;
        this.email = user.email;
        this.rol = user.rol;
        this.autonomia = user.autonomia;
        this.autonomiaF = user.autonomiaF;
    }

    public Usuario(String id, String email) {
        this.id = id;
        this.email = email;
        this.rol = false;
        autonomiaF = 200f;
        autonomia = "200";
    }

    public Usuario(String id, String email, boolean rol) {
        this.id = id;
        this.email = email;
        this.rol = false;
        autonomiaF = 200f;
        autonomia = "200";
    }

    public Usuario(String id, String email, String autonomia) {
        this.id = id;
        this.email = email;
        this.rol = false;
        this.autonomiaF = Float.parseFloat(autonomia);
        this.autonomia = autonomia;
    }

    public Usuario(String id, String email, float autonomia) {
        this.id = id;
        this.email = email;
        this.rol = false;
        this.autonomiaF = autonomia;
        this.autonomia = Float.toString(autonomia);
    }

    public float getAutonomiaF() {
        return autonomiaF;
    }

    public void setAutonomiaF(float autonomiaF) {
        this.autonomiaF = autonomiaF;
    }

    public String getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
        this.autonomiaF = Float.parseFloat(autonomia);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }
}
