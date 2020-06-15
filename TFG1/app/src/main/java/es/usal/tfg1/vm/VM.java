package es.usal.tfg1.vm;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import es.usal.tfg1.R;
import es.usal.tfg1.Repository;
import es.usal.tfg1.ViewC.MAPA.dialog.CustomDialog;
import es.usal.tfg1.model.Usuario;

public class VM extends ViewModel {
    private Repository repository = new Repository(this);
    private MutableLiveData<Usuario> _usuario;
    public LiveData<Usuario> usuario;

    private MutableLiveData<Integer> _visibility;
    public LiveData<Integer> visibility;

    private MutableLiveData<Integer> _userEmBVisibility;
    public LiveData<Integer> userEmBVisibility;

    private MutableLiveData<Integer> _userPassBVisibility;
    public LiveData<Integer> userPassBVisibility;

    private MutableLiveData<Boolean> _toastVisibility;
    public  LiveData<Boolean> toastVisibility;

    private Usuario myUser;

    public LiveData<Boolean> gettoastVisibility() {
        return (LiveData<Boolean>) _toastVisibility;
    }

    public LiveData<Usuario> getUsuario() {
        return (LiveData<Usuario>) _usuario;
    }

    public LiveData<Integer> getVisibility() {
        return (LiveData<Integer>) _visibility;
    }

    public LiveData<Integer> getUserEmBVisibility() {
        return (LiveData<Integer>) _userEmBVisibility;
    }

    public LiveData<Integer> getUserPassBVisibility() {
        return (LiveData<Integer>) _userPassBVisibility;
    }
    private CustomDialog myDialog;

    public void initializeValues() {
        if(_visibility == null)
            _visibility = new MutableLiveData<Integer>();
        if(_usuario == null)
            _usuario = new MutableLiveData<Usuario>();
        if(_userEmBVisibility == null){
            _userEmBVisibility = new MutableLiveData<Integer>();
            _userEmBVisibility.setValue(View.VISIBLE);
        }
        if(_userPassBVisibility == null){
            _userPassBVisibility = new MutableLiveData<Integer>();
            _userPassBVisibility.setValue(View.VISIBLE);
        }
        if(_toastVisibility == null)
            _toastVisibility = new MutableLiveData<Boolean>();
    }

    public void onDestinationChangeUsuario(int idDest, int idNavUsuario) {
        if(idDest == idNavUsuario) {
            _visibility.setValue(View.INVISIBLE);
        } else {
            _visibility.setValue(View.VISIBLE);
        }
    }

    public void onTextChange(int textId) {
        if(textId == R.id.textEmailUser && _userEmBVisibility.getValue() != View.VISIBLE){
            _userEmBVisibility.setValue(View.VISIBLE);
        } else if (textId == R.id.textPassUser && _userPassBVisibility.getValue() != View.VISIBLE) {
            _userPassBVisibility.setValue(View.VISIBLE);
        }
    }

    public void onUserChangeButton(int buttonId) {
        if(buttonId == R.id.buttonEmailUser){
            _userEmBVisibility.setValue(View.INVISIBLE);
        } else if (buttonId == 1 /*R.id.buttonPassUser*/) {
            _userPassBVisibility.setValue(View.INVISIBLE);
        }
    }

    public void checkNewUser(FirebaseUser currentUser, LifecycleOwner own) {
        repository.checkNewUser(currentUser, _usuario);
        final Observer<Usuario> userObs = new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario user) {
                //Inicializar el usuario porprimera vez
                addUser();
            }
        };
        this.getUsuario().observe(own, userObs);

    }


    public void addUser() {
        if(myUser == null)
            myUser = new Usuario(this._usuario.getValue());
    }


    public void textFocusUser(View v, boolean hasFocus) {
        if(v.getId() == R.id.textEmailUser && hasFocus) {
            _usuario.getValue().setEmail("");
            _usuario.setValue(_usuario.getValue());
        }
        else  if (v.getId() == R.id.textEmailUser && !hasFocus) {
            _usuario.getValue().setEmail(myUser.getEmail());
            _usuario.setValue(_usuario.getValue());
        } else if(v.getId() == R.id.textPassUser && hasFocus) {

        } else if (v.getId() == R.id.textPassUser && !hasFocus) {

        }
    }

    public void changeEmail(String email) {
        // TODO
        /* Comprobar que es un email correcto
        *  Volver a verificar autenticidad del usuario -------- HECHO
        *  Modificar el correo del usuario real con FirebaseAuthUser (o lo que sea, vamos, modificar también el valor del Usuario real fuera de firestore) ---- en proceso
        *  Comprobar que no hay nadie con ese correo?
        */

        repository.modUserEmailAuth(email);
    }

    public  void reLogUser(String pass) {
        repository.relLogUser(myUser, pass);
    }

    public void reLogError() {
        this._toastVisibility.setValue(false);
        //this._toastVisibility.setValue(_toastVisibility.getValue());
    }

    public void reLog() {
        this._toastVisibility.setValue(true);
        //this._toastVisibility.setValue(_toastVisibility.getValue());
    }
    public void changeError(){
        this._toastVisibility.setValue(false);
        //this._toastVisibility.setValue(_toastVisibility.getValue());
    }
    public void changeSucces(String email) {
        if(myUser.getEmail().equals(email)) {
            this._toastVisibility.setValue(false);
        } else {
            _usuario.getValue().setEmail(email);
            _usuario.setValue(_usuario.getValue());
            myUser.setEmail(_usuario.getValue().getEmail());
            repository.modUserEmailFirestore();
            this._toastVisibility.setValue(true);
        }

        //this._toastVisibility.setValue(_toastVisibility.getValue());
    }


}
