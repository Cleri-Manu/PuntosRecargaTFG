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
import es.usal.tfg1.model.Puntuacion;
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

    private MutableLiveData<ArrayList<PuntoRecarga>> _PRCompleteList;
    public  LiveData<ArrayList<PuntoRecarga>> PRCompleteList;

    private MutableLiveData<Boolean> _nuevoBOutline1;
    public  LiveData<Boolean> nuevoBOutline1;

    private MutableLiveData<Boolean> _nuevoBOutline2;
    public  LiveData<Boolean> nuevoBOutline2;

    private MutableLiveData<Boolean> _nuevoToastFillFields;
    public  LiveData<Boolean> nuevoToastFillFields;

    private MutableLiveData<Boolean> _nuevoToastPRAlreadyExists;
    public  LiveData<Boolean> nuevoToastPRAlreadyExists;

    private MutableLiveData<Boolean> _nuevoLoadingVisibility;
    public  LiveData<Boolean> nuevoLoadingVisibility;

    private MutableLiveData<Boolean> _infoLoadingVisibility;
    public  LiveData<Boolean> infoLoadingVisibility;

    private MutableLiveData<ArrayList<Puntuacion>> _infoRecyclerListData;
    public  LiveData<ArrayList<Puntuacion>> infoRecyclerListData;

    private MutableLiveData<Boolean> _infoEco;
    public  LiveData<Boolean> infoEco;

    private MutableLiveData<String> _infoTitle;
    public  LiveData<String> infoTitle;

    private MutableLiveData<Float> _infoRating;
    public  LiveData<Float> infoRating;

    private MutableLiveData<String> _infoDist;
    public  LiveData<String> infoDist;

    private MutableLiveData<String> _infoDesc;
    public  LiveData<String> infoDesc;

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

    public LiveData<Boolean> getNuevoBOutline1() {
        return (LiveData<Boolean>) _nuevoBOutline1;
    }

    public LiveData<Boolean> getNuevoBOutline2() {
        return (LiveData<Boolean>)  _nuevoBOutline2;
    }

    public LiveData<Boolean> getNuevoToastFillFields() {
        return (LiveData<Boolean>)  _nuevoToastFillFields;
    }

    public LiveData<ArrayList<PuntoRecarga>> getPRCompleteList() {
        return (LiveData<ArrayList<PuntoRecarga>>)  _PRCompleteList;
    }

    public LiveData<Boolean> getNuevoToastPRAlreadyExists() {
        return (LiveData<Boolean>)  _nuevoToastPRAlreadyExists;
    }

    public LiveData<Boolean> getNuevoLoadingVisibility() {
        return (LiveData<Boolean>)  _nuevoLoadingVisibility;
    }

    public LiveData<Boolean> getInfoLoadingVisibility() {
        return (LiveData<Boolean>)  _infoLoadingVisibility;
    }

    public LiveData<Boolean> getInfoEco() {
        return (LiveData<Boolean>)  _infoEco;
    }

    public LiveData<ArrayList<Puntuacion>> getInfoRecyclerListData() {
        return (LiveData<ArrayList<Puntuacion>>) _infoRecyclerListData;
    }

    public LiveData<String> getInfoTitle() {
        return (LiveData<String>) _infoTitle;
    }

    public LiveData<Float> getInfoRating() {
        return (LiveData<Float>) _infoRating;
    }

    public LiveData<String> getInfoDist() {
        return (LiveData<String>) _infoDist;
    }

    public LiveData<String> getInfoDesc() {
        return (LiveData<String>) _infoDesc;
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
        if(_nuevoBOutline1 == null) {
            _nuevoBOutline1 = new MutableLiveData<Boolean>();
            _nuevoBOutline1.setValue(false);
        }
        if(_nuevoBOutline2 == null) {
            _nuevoBOutline2 = new MutableLiveData<Boolean>();
            _nuevoBOutline2.setValue(false);
        }
        if(_nuevoToastFillFields == null)
            _nuevoToastFillFields = new MutableLiveData<Boolean>();
        if(_PRCompleteList == null)
            _PRCompleteList = new MutableLiveData<ArrayList<PuntoRecarga>>();
        if(_nuevoToastPRAlreadyExists == null)
            _nuevoToastPRAlreadyExists = new MutableLiveData<Boolean>();
        if(_nuevoLoadingVisibility == null) {
            _nuevoLoadingVisibility = new MutableLiveData<Boolean>();
            _nuevoLoadingVisibility.setValue(false);
        }
        if(_infoLoadingVisibility == null) {
            _infoLoadingVisibility = new MutableLiveData<Boolean>();
            _infoLoadingVisibility.setValue(false);
        }
        if(_infoEco == null) {
            _infoEco = new MutableLiveData<Boolean>();
        }
        if(_infoRecyclerListData == null)
            _infoRecyclerListData = new MutableLiveData<ArrayList<Puntuacion>>();
        if(_infoTitle == null)
            _infoTitle = new MutableLiveData<String>();
        if(_infoRating == null)
            _infoRating = new MutableLiveData<Float>();
        if(_infoDist == null)
            _infoDist = new MutableLiveData<String>();
        if(_infoDesc == null)
            _infoDesc = new MutableLiveData<String>();
    }

    public void getPR() {
        //TODO
        /* Trozo de codigo sin acabar, revisar
        *  Quizas no hacen falta parametros, ya se vera
        *  De momento se cargan todos
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

    public void nuevoChargerNClick() {
        if(_nuevoBOutline2.getValue()) {
            _nuevoBOutline2.setValue(false);
            _nuevoBOutline2.setValue(_nuevoBOutline2.getValue());
        }
        if(_nuevoBOutline1.getValue()) {
            _nuevoBOutline1.setValue(false);
            _nuevoBOutline1.setValue(_nuevoBOutline1.getValue());
        } else {
            _nuevoBOutline1.setValue(true);
            _nuevoBOutline1.setValue(_nuevoBOutline1.getValue());
        }
    }

    public void nuevoChargerGClick() {
        if(_nuevoBOutline1.getValue()) {
            _nuevoBOutline1.setValue(false);
            _nuevoBOutline1.setValue(_nuevoBOutline1.getValue());
        }
        if(_nuevoBOutline2.getValue()) {
            _nuevoBOutline2.setValue(false);
            _nuevoBOutline2.setValue(_nuevoBOutline2.getValue());
        } else {
            _nuevoBOutline2.setValue(true);
            _nuevoBOutline2.setValue(_nuevoBOutline2.getValue());
        }
    }

    public void onDestinationChangeResetNuevo(int id, int navigation_nuevo) {
        if(id != navigation_nuevo){
            _nuevoBOutline1.setValue(false);
            _nuevoBOutline2.setValue(false);
            _nuevoToastPRAlreadyExists = new MutableLiveData<Boolean>();
            _nuevoToastFillFields = new MutableLiveData<Boolean>();
        }
    }

    public void addNewPR(String nombre, String lat, String lon, String descripcion) {
        nuevoNewLoadVisibility(true);
        if(nombre.equals("") || lat.equals("") || lon.equals("")) {
            _nuevoToastFillFields.setValue(true);
            nuevoNewLoadVisibility(false);
            return;
        }
        if(_nuevoBOutline1.getValue()) {
            repository.newPR(nombre, lat, lon, descripcion, false);
        } else if(_nuevoBOutline2.getValue()) {
            repository.newPR(nombre, lat, lon, descripcion, true);
        } else {
            _nuevoToastFillFields.setValue(true);
            nuevoNewLoadVisibility(false);
        }
    }

    public void PRAlreadyExists(boolean exists) {
        nuevoNewLoadVisibility(false);
        _nuevoToastPRAlreadyExists.setValue(exists);
    }

    public void changePRCompelteList(ArrayList<PuntoRecarga> prCompleteList) {
        _PRCompleteList.setValue(prCompleteList);
        _PRCompleteList.setValue(_PRCompleteList.getValue());
    }

    public void changePRList(ArrayList<PuntoRecarga> prList) {
        _recyclerListData.setValue(prList);
        _loadRecyclerVisibility.setValue(false);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        //_recyclerListData.setValue(_recyclerListData.getValue());
    }

    public void loadRecyclerList() {
        _loadRecyclerVisibility.setValue(true);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        repository.getPRList(new Parada(1,1));
    }

    public void nuevoNewLoadVisibility(boolean value) {
        _nuevoLoadingVisibility.setValue(value);
        _nuevoLoadingVisibility.setValue(_nuevoLoadingVisibility.getValue());
    }

    public void setSelectedPR(int position) {
        //repository.getPRFromFirestore(_recyclerListData.getValue().get(position).getId());
        setSelectedPR(_recyclerListData.getValue().get(position));
    }
    public void setSelectedPR(PuntoRecarga p) {
        _infoEco.setValue(p.isEco());
        _infoEco.setValue(_infoEco.getValue());
        _infoTitle.setValue(p.getNombre());
        _infoTitle.setValue(_infoTitle.getValue());
        _infoRating.setValue((float)p.getPuntuacion());
        _infoRating.setValue(_infoRating.getValue());
        _infoDist.setValue(p.getDistancia());
        _infoDist.setValue(_infoDist.getValue());
        _infoDesc.setValue(p.getDescripcion());
        _infoDesc.setValue(_infoDesc.getValue());
        _infoRecyclerListData.setValue(p.getPuntuaciones());
        _infoRecyclerListData.setValue(_infoRecyclerListData.getValue());
    }
}
