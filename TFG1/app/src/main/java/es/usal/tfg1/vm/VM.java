package es.usal.tfg1.vm;

import android.location.Location;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.Repository;
import es.usal.tfg1.ViewC.fragmentos.dialog.CustomDialog;
import es.usal.tfg1.model.Parada;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.model.Puntuacion;
import es.usal.tfg1.model.Usuario;

public class VM extends ViewModel {
    private Repository repository = new Repository(this);
    private boolean _modSelected = false;
    public boolean modSelected;
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

    private MutableLiveData<ArrayList<PuntoRecarga>> _recyclerListCompleteData;
    public  LiveData<ArrayList<PuntoRecarga>> recyclerListCompleteData;

    private MutableLiveData<ArrayList<PuntoRecarga>> _recyclerlistFilteredData;
    public  LiveData<ArrayList<PuntoRecarga>> recyclerlistFilteredData;

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

    private MutableLiveData<Boolean> _searchPR;
    public  LiveData<Boolean> searchPR;

    private MutableLiveData<Boolean> _nuevoLoadingVisibility;
    public  LiveData<Boolean> nuevoLoadingVisibility;

    private MutableLiveData<Boolean> _infoLoadingVisibility;
    public  LiveData<Boolean> infoLoadingVisibility;

    private MutableLiveData<ArrayList<Puntuacion>> _infoRecyclerListData;
    public  LiveData<ArrayList<Puntuacion>> infoRecyclerListData;

    private MutableLiveData<PuntoRecarga> _infoPR;
    public  LiveData<PuntoRecarga> infoPR;

    private MutableLiveData<Boolean> _puntuacionLoadingVisibility;
    public  LiveData<Boolean> puntuacionLoadingVisibility;

    private MutableLiveData<Float> _puntuacionUserPunt;
    public  LiveData<Float> puntuacionUserPunt;

    private MutableLiveData<Boolean> _puntuacionToast;
    public  LiveData<Boolean> puntuacionToast;

    private MutableLiveData<Boolean> _puntuacionDelVisibility;
    public  LiveData<Boolean> puntuacionDelVisibility;

    private MutableLiveData<Boolean> _infoUserCanDel;
    public  LiveData<Boolean> infoUserCanDel;

    private MutableLiveData<Boolean> _infoReportToast;
    public  LiveData<Boolean> infoReportToast;

    private MutableLiveData<Boolean> _errorToast;
    public  LiveData<Boolean> errorToast;

    private MutableLiveData<Boolean> _coordError;
    public  LiveData<Boolean> coordError;

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

    public LiveData<ArrayList<PuntoRecarga>> getRecyclerListCompleteData() {
        return (LiveData<ArrayList<PuntoRecarga>>) _recyclerListCompleteData;
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

    public LiveData<Boolean> getSearchPR() {
        return (LiveData<Boolean>)  _searchPR;
    }

    public LiveData<Boolean> getNuevoLoadingVisibility() {
        return (LiveData<Boolean>)  _nuevoLoadingVisibility;
    }

    public LiveData<Boolean> getInfoLoadingVisibility() {
        return (LiveData<Boolean>)  _infoLoadingVisibility;
    }

    public LiveData<ArrayList<Puntuacion>> getInfoRecyclerListData() {
        return (LiveData<ArrayList<Puntuacion>>) _infoRecyclerListData;
    }

    public LiveData<Boolean> getPuntuacionLoadingVisibility() {
        return (LiveData<Boolean>)  _puntuacionLoadingVisibility;
    }

    public LiveData<PuntoRecarga> getInfoPR() {
        return (LiveData<PuntoRecarga>) _infoPR;
    }

    public LiveData<Float> getPuntuacionUserPunt() {
        return (LiveData<Float>) _puntuacionUserPunt;
    }

    public LiveData<Boolean> getPuntuacionToast() {
        return (LiveData<Boolean>)  _puntuacionToast;
    }

    public LiveData<Boolean> getPuntuacionDelVisibility() {
        return (LiveData<Boolean>)  _puntuacionDelVisibility;
    }

    public LiveData<Boolean> getInfoUserCanDel() {
        return (LiveData<Boolean>)  _infoUserCanDel;
    }

    public LiveData<Boolean> getInfoReportToast () {
        return (LiveData<Boolean>) _infoReportToast;
    }

    public boolean getModSelected() {
        boolean temp = _modSelected;
        _modSelected = false;
        return temp;
    }

    public LiveData<Boolean> getErrorToast () {
        return (LiveData<Boolean>) _errorToast;
    }

    public LiveData<Boolean> getCoordError () {
        return (LiveData<Boolean>) _coordError;
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
        if(_searchPR == null) {
            _searchPR = new MutableLiveData<Boolean>();
            _searchPR.setValue(false);
        }
        if(_nuevoLoadingVisibility == null) {
            _nuevoLoadingVisibility = new MutableLiveData<Boolean>();
            _nuevoLoadingVisibility.setValue(false);
        }
        if(_infoLoadingVisibility == null) {
            _infoLoadingVisibility = new MutableLiveData<Boolean>();
            _infoLoadingVisibility.setValue(false);
        }
        if(_infoPR == null) {
            _infoPR = new MutableLiveData<PuntoRecarga>();
        }
        if(_infoRecyclerListData == null) {
            _infoRecyclerListData = new MutableLiveData<ArrayList<Puntuacion>>();
        }
        if(_puntuacionLoadingVisibility == null) {
            _puntuacionLoadingVisibility = new MutableLiveData<Boolean>();
            _puntuacionLoadingVisibility.setValue(false);
        }
        if(_puntuacionToast == null) {
            _puntuacionToast = new MutableLiveData<Boolean>();
        }
        if(_puntuacionDelVisibility == null) {
            _puntuacionDelVisibility = new MutableLiveData<Boolean>();
            _puntuacionDelVisibility.setValue(false);
        }
        if(_infoUserCanDel == null) {
            _infoUserCanDel = new MutableLiveData<Boolean>();
            _infoUserCanDel.setValue(false);
        }
        if(_infoReportToast == null) {
            _infoReportToast = new MutableLiveData<Boolean>();
        }
        if(_errorToast == null){
            _errorToast = new MutableLiveData<Boolean>();
            _errorToast.setValue(false);
        }
        if(_coordError == null) {
            _coordError = new MutableLiveData<Boolean>();
            _coordError.setValue(false);
        }
        if(_recyclerListCompleteData == null) {
            _recyclerListCompleteData = new MutableLiveData<ArrayList<PuntoRecarga>>();
        }
        if(_recyclerlistFilteredData == null) {
            _recyclerlistFilteredData = new MutableLiveData<ArrayList<PuntoRecarga>>();
        }
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
        userLoadVisibility(false);
    }

    public void reLogSucces() {
        userLoadVisibility(false);
        this._toastVisibility.setValue(true);
    }
    public void changeError(){
        userLoadVisibility(false);
        this._toastVisibility.setValue(false);
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

    public void changeAut(String aut) {
        _usuario.getValue().setAutonomia(aut);
        repository.moduserAut();
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
            _coordError = new MutableLiveData<Boolean>();
        }
    }

    public void addNewPR(String nombre, String lat, String lon, String descripcion) {
        if(checkInvCoords(Float.parseFloat(lon), Float.parseFloat(lat))) {
            return;
        }

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

    public void changePRCompleteList(ArrayList<PuntoRecarga> prCompleteList) {
        _PRCompleteList.setValue(prCompleteList);
        _PRCompleteList.setValue(_PRCompleteList.getValue());
        ArrayList<PuntoRecarga> temp = new ArrayList<PuntoRecarga>();
        for (PuntoRecarga p: prCompleteList) {
            temp.add(new PuntoRecarga(p));
        }
        _recyclerlistFilteredData.setValue(temp);
    }

    public void changePRList(ArrayList<PuntoRecarga> prList) {
        _recyclerListData.setValue(prList);
        _loadRecyclerVisibility.setValue(false);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
    }

    public void changePRListComplete(ArrayList<PuntoRecarga> prCompleteList) {
        _recyclerListCompleteData.setValue(prCompleteList);
        _loadRecyclerVisibility.setValue(false);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        ArrayList<PuntoRecarga> temp = new ArrayList<PuntoRecarga>();
        for (PuntoRecarga p: prCompleteList) {
            temp.add(new PuntoRecarga(p));
        }
        _recyclerlistFilteredData.setValue(temp);
    }

    public void loadRecyclerList(Location result) {
        _loadRecyclerVisibility.setValue(true);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        repository.getPRList(new Parada(result.getLongitude(),result.getLatitude()));
    }

    public void loadRecyclerListComplete(Location result) {
        _loadRecyclerVisibility.setValue(true);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        repository.getPRListComplete(new Parada(result.getLongitude(),result.getLatitude()));
    }

    public void nuevoNewLoadVisibility(boolean value) {
        _nuevoLoadingVisibility.setValue(value);
        _nuevoLoadingVisibility.setValue(_nuevoLoadingVisibility.getValue());
    }

    public void setSelectedPR(int position) {
        if(_searchPR.getValue()) {
            setSelectedPR(_recyclerlistFilteredData.getValue().get(position));
        } else {
            setSelectedPR(_recyclerListData.getValue().get(position));
        }
    }
    public void setSelectedPR(PuntoRecarga p) {
        _infoPR = new MutableLiveData<PuntoRecarga>();
        _infoPR.setValue(p);
        _infoPR.setValue(_infoPR.getValue());
        /*
        _infoEco.setValue(p.isEco());
        _infoEco.setValue(_infoEco.getValue());
        _infoTitle.setValue(p.getNombre());
        _infoTitle.setValue(_infoTitle.getValue());
        _infoRating.setValue((float)p.getPuntuacion());
        _infoRating.setValue(_infoRating.getValue());
        _infoDist.setValue(p.getDistancia());
        _infoDist.setValue(_infoDist.getValue());
        _infoDesc.setValue(p.getDescripcion());
        _infoDesc.setValue(_infoDesc.getValue());*/

        _infoRecyclerListData.setValue(p.getPuntuaciones());
        _infoRecyclerListData.setValue(_infoRecyclerListData.getValue());
    }

    public Puntuacion checkUserPunt(){
        if(_infoPR.getValue().getPuntuaciones() != null) {
            for (Puntuacion p: _infoPR.getValue().getPuntuaciones()) {
                if(p.getId().equals(_usuario.getValue().getId())) {
                    return p;
                }
            }
        }
        return null;
    }

    public void newPuntuacion(String op, float rating) {
        changePuntuacionLoadingVisibility(true);
        repository.NewPuntuacion(op, rating, _infoPR.getValue());
    }

    public void changePuntuacionLoadingVisibility(boolean visibility){
        _puntuacionLoadingVisibility.setValue(visibility);
        _puntuacionLoadingVisibility.setValue(_puntuacionLoadingVisibility.getValue());
    }

    public void changePuntuacion() {
        for (PuntoRecarga puntoRecarga: _recyclerListData.getValue()) {
            if(puntoRecarga.getId().equals(_infoPR.getValue().getId())) {
                setSelectedPR(puntoRecarga);
                changePuntuacionLoadingVisibility(false);
                //Cambiar el valor del toast para mostrar operacion exitosa
                changePuntuacionToast(true);
                return;
            }
        }
        for (PuntoRecarga puntoRecarga: _recyclerListCompleteData.getValue()) {
            if(puntoRecarga.getId().equals(_infoPR.getValue().getId())) {
                setSelectedPR(puntoRecarga);
                changePuntuacionLoadingVisibility(false);
                //Cambiar el valor del toast para mostrar operacion exitosa
                changePuntuacionToast(true);
                return;
            }
        }
    }

    public void changePuntuacioError(){
        changePuntuacionToast(false);
    }

    public void changePuntuacionToast(boolean bool) {
        _puntuacionToast.setValue(bool);
        //_puntuacionToast.setValue(_puntuacionToast.getValue());
    }

    public void onDestinationChangeResetPuntuar(int id, int navigation_puntuar) {
        if(id != navigation_puntuar){
            _puntuacionToast = new MutableLiveData<Boolean>();
            _puntuacionLoadingVisibility = new MutableLiveData<Boolean>();
            _puntuacionLoadingVisibility.setValue(false);
        }
    }

    public void changeUserCanDel(boolean can) {
        _infoUserCanDel.setValue(can);
        _infoUserCanDel.setValue(_infoUserCanDel.getValue());
    }

    public void checkUserCanDel() {
        if(_usuario.getValue().getId().equals(_infoPR.getValue().getCreadorID())) {
            changeUserCanDel(true);
        } else if (_usuario.getValue().getRol()) {
            changeUserCanDel(true);
        }
        else {
            changeUserCanDel(false);
        }
    }

    public boolean userCanDel() {
        return  _puntuacionDelVisibility.getValue();
    }

    public void changePuntuarDelVisibility() {
        _puntuacionDelVisibility.setValue(true);
    }

    public void changePuntuarDelVisibilityFalse() {
        _puntuacionDelVisibility.setValue(false);
    }

    public void delPuntuacionButton() {
        repository.delPuntuacion(_infoPR.getValue());
    }

    public void delPR() {
        repository.delPR(_infoPR.getValue());
    }

    public void delPRSucces() {
    }

    public void reportPR() {
        repository.addReport(_infoPR.getValue());
    }

    public void reportarSucces() {
        changeReportarToastVisibility(true);
    }

    public void changeReportarToastVisibility(boolean visible) {
        _infoReportToast.setValue(true);
        _infoReportToast = new MutableLiveData<Boolean>();
    }

    public void modSelectedTrue() {
        changeModSelected(true);
    }
    public void modSelectedFalse() {
        changeModSelected(false);
    }

    public void changeModSelected(boolean bool) {
        this._modSelected = bool;
    }

    public void changeModOutline() {
        if(_infoPR.getValue().isEco()) {
            _nuevoBOutline2.setValue(true);
            _nuevoBOutline1.setValue(false);
        } else {
            _nuevoBOutline2.setValue(false);
            _nuevoBOutline1.setValue(true);
        }
    }

    public void modPR(String nombre, String lat, String lon, String descripcion) {
        if(checkInvCoords(Float.parseFloat(lon), Float.parseFloat(lat))) {
            return;
        }

        nuevoNewLoadVisibility(true);
        if(nombre.equals("") || lat.equals("") || lon.equals("")) {
            _nuevoToastFillFields.setValue(true);
            nuevoNewLoadVisibility(false);
            return;
        }
        if(_nuevoBOutline1.getValue()) {
            repository.modAllPRFields(nombre, lat, lon, descripcion, false, _infoPR.getValue());
        } else if(_nuevoBOutline2.getValue()) {
            repository.modAllPRFields(nombre, lat, lon, descripcion, true, _infoPR.getValue());
        } else {
            _nuevoToastFillFields.setValue(true);
            nuevoNewLoadVisibility(false);
        }
    }
    public void nuevoModSuccess() {
        PRAlreadyExists(false);
    }

    public void getPRListForRecyclerError() {
        changeErrorToastValue(true);
    }

    public void checkNewUserError() {
        changeErrorToastValue(true);
    }

    public void addNewPRError() {
        changeErrorToastValue(true);
    }

    public void modPRPuntuacionesError() {
        changeErrorToastValue(true);
    }

    public void addReportError() {
        changeErrorToastValue(true);
    }

    public void modAllPRFieldsError() {
        changeErrorToastValue(true);
    }

    public void changeErrorToastValue(boolean error) {
        _errorToast.setValue(error);
    }

    public boolean checkInvCoords (float longitud, float latitud){
        if(repository.checkInvCoords(longitud,latitud)) { //Si las coordenadas son invalidas
            _coordError.setValue(true);
            return true;
        }
        return false;
    }

    public boolean checkAut(String tempStringEmailPass) {
        if(_usuario.getValue().getAutonomia().equals(tempStringEmailPass)){
            return true;
        } else {
            return false;
        }
    }

    public void onDestinationChangeSearch(int id, int navigation_pr_list_search) {
        if(id == navigation_pr_list_search) {
            _searchPR.setValue(true);
        }
    }

    public void onDestinationChangeList(int id, int navigation_pr_list) {
        if(id == navigation_pr_list) {
            _searchPR.setValue(false);
        }
    }

    public ArrayList<PuntoRecarga> changePRCompleteList(String searchBy) {
        ArrayList<PuntoRecarga> newList = new ArrayList<PuntoRecarga>();
        for (PuntoRecarga p: _recyclerListCompleteData.getValue()) {
            if(p.getNombre().toLowerCase().contains(searchBy.toLowerCase())){
                newList.add(new PuntoRecarga(p));
            }
        }
        _recyclerlistFilteredData.setValue(newList);
        return _recyclerlistFilteredData.getValue();
    }

    public boolean isAdmin() {
        return _usuario.getValue().getRol();
    }

    public void createOfficialPRs(String jsonText) throws JSONException {
        repository.PRfromJson(jsonText);
    }
}
