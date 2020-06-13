package es.usal.tfg1.vm;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.usal.tfg1.R;
import es.usal.tfg1.model.Usuario;

public class VM extends ViewModel {
    private MutableLiveData<Usuario> _usuario;
    public LiveData<Usuario> usuario;

    private MutableLiveData<Integer> _visibility;
    public LiveData<Integer> visibility;

    public void set_usuario() {
        if(_usuario == null) {
            _usuario = new MutableLiveData<Usuario>();
        }
        this._usuario = _usuario;
        _usuario.setValue(new Usuario("1","pepe@usal.es",false));
    }

    public void set_visibility(MutableLiveData<Integer> _visibility) {
        if(_visibility == null) {
            _visibility = new MutableLiveData<Integer>();
        }
        this._visibility = _visibility;
    }

    public LiveData<Usuario> getUsuario() {
        return (LiveData<Usuario>) _usuario;
    }

    public LiveData<Integer> getVisibility() {
        return (LiveData<Integer>) _visibility;
    }

    public void onDestinationChange(int idDest, int idNavUsuario) {
        if(_visibility == null) {
            _visibility = new MutableLiveData<Integer>();
        }
        if(idDest == idNavUsuario) {
            _visibility.setValue(View.INVISIBLE);
        } else {
            _visibility.setValue(View.VISIBLE);
        }
        this.set_usuario();
    }




}
