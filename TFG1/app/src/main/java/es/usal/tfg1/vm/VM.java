package es.usal.tfg1.vm;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.Repository;
import es.usal.tfg1.ViewC.MAPA.dialog.CustomDialog;
import es.usal.tfg1.model.Parada;
import es.usal.tfg1.model.PuntoRecarga;
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

    private MutableLiveData<Boolean> _loadUserVisibility;
    public  LiveData<Boolean> loadUserVisibility;

    private MutableLiveData<Boolean> _loadRecyclerVisibility;
    public  LiveData<Boolean> loadRecyclerVisibility;

    private MutableLiveData<ArrayList<PuntoRecarga>> _recyclerListData;
    public  LiveData<ArrayList<PuntoRecarga>> recyclerListData;

    private Usuario myUser;
    private ArrayList<PuntoRecarga> PRList;

    public LiveData<Boolean> gettoastVisibility() {
        return (LiveData<Boolean>) _toastVisibility;
    }

    public LiveData<Usuario> getUsuario() {
        return (LiveData<Usuario>) _usuario;
    }

    public LiveData<Integer> getVisibility() {
        return (LiveData<Integer>) _visibility;
    }

    public LiveData<Boolean> getLoadUserVisibility() {
        return (LiveData<Boolean>) _loadUserVisibility;
    }

    public LiveData<Boolean> getLoadRecyclerVisibility() {
        return (LiveData<Boolean>) _loadRecyclerVisibility;
    }

    public LiveData<ArrayList<PuntoRecarga>> getRecyclerListData() {
        return (LiveData<ArrayList<PuntoRecarga>>) _recyclerListData;
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
        if(_loadUserVisibility == null){
            _loadUserVisibility = new MutableLiveData<Boolean>();
            _loadUserVisibility.setValue(false);
        }
        if(PRList == null) {
            PRList = new ArrayList<PuntoRecarga>();
        }
        if(_loadRecyclerVisibility == null) {
            _loadRecyclerVisibility = new MutableLiveData<Boolean>();
            _loadRecyclerVisibility.setValue(true);
        }
        if(_recyclerListData == null) {
            _recyclerListData = new MutableLiveData<ArrayList<PuntoRecarga>>();
        }
    }

    public void getPR() {
        //TODO
        /* Trozo de codigo sin acabar, revisar
        *  Quizas no hacen falta parametros, ya se vera
        *
        */
        repository.getPRList(new Parada(1,1));
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
            myUser = repository.getMyUser();
    }


    public void textFocusUser(int id, boolean hasFocus) {
        if(id == R.id.textEmailUser && hasFocus) {
            _usuario.getValue().setEmail("");
            _usuario.setValue(_usuario.getValue());
        }
        else  if (id == R.id.textEmailUser && !hasFocus) {
            _usuario.getValue().setEmail(repository.getMyUser().getEmail());
            _usuario.setValue(_usuario.getValue());
        } else if(id == R.id.textPassUser && hasFocus) {

        } else if (id == R.id.textPassUser && !hasFocus) {

        }
    }

    public  void reLogUserEmail(String pass) {
        userLoadVisibility(true);
        repository.relLogUser(repository.getMyUser(), pass);
    }


    public void reLogError() {
        this._toastVisibility.setValue(false);
        //this._toastVisibility.setValue(_toastVisibility.getValue());
        userLoadVisibility(false);
    }

    public void reLogSucces() {
        userLoadVisibility(false);
        this._toastVisibility.setValue(true);
        //this._toastVisibility.setValue(_toastVisibility.getValue());
    }
    public void changeError(){
        userLoadVisibility(false);
        this._toastVisibility.setValue(false);
        //this._toastVisibility.setValue(_toastVisibility.getValue());
    }
    public void changeSucces(String email) {
        if(repository.getMyUser().getEmail().equals(email)) {
            this._toastVisibility.setValue(false);
        } else {
            _usuario.getValue().setEmail(email);
            _usuario.setValue(_usuario.getValue());
            repository.getMyUser().setEmail(_usuario.getValue().getEmail());
            repository.modUserEmailFirestore();
            userLoadVisibility(false);
            this._toastVisibility.setValue(true);
        }
    }
    public  void changeSucces() {
        this._toastVisibility.setValue(true);
    }

    private void userLoadVisibility(boolean visible) {
            _loadUserVisibility.setValue(visible);
            _loadUserVisibility.setValue(_loadUserVisibility.getValue());
    }

    public void recovery() {
        repository.recovery();
    }

    public void changePass(String pass) {
        repository.modUserpassAuth(pass);
    }

    public void changeEmail(String email) {
        repository.modUserEmailAuth(email);
    }

    public void DelUser() {
        repository.delUser();
    }
}
