package es.usal.tfg1.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DAO {
    private Usuario usuarioAct;
    private MutableLiveData<Usuario> LDUsuario = new MutableLiveData<Usuario>();

    public DAO () {
        this.LDUsuario.setValue(usuarioAct);
    }

    public LiveData<Usuario> getLDUsuario() {
        return (LiveData<Usuario>) LDUsuario;
    }
}
