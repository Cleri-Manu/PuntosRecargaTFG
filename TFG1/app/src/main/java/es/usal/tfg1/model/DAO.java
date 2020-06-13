package es.usal.tfg1.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public class DAO {
    private Usuario usuarioAct;
    private MutableLiveData<Usuario> LDUsuario = new MutableLiveData<Usuario>();

    public DAO () {
        this.LDUsuario.setValue(usuarioAct);
    }

    public LiveData<Usuario> getLDUsuario() {
        return (LiveData<Usuario>) LDUsuario;
    }

    public void setUsuarioAct(String uid) {
        this.usuarioAct = new Usuario(uid);
    }
}
