package es.usal.tfg1.model;

public class Usuario {
    private String id;
    private String email;
    private boolean rol = false;    //True=admin, false=normal User

    public Usuario() { }

    public Usuario(String id, String email) {
        this.id = id;
        this.email = email;
        this.rol = false;
    }

    public Usuario(String id, String email, boolean rol) {
        this.id = id;
        this.email = email;
        this.rol = rol;
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
