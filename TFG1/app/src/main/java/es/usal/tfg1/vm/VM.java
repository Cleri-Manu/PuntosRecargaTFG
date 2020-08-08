package es.usal.tfg1.vm;

import android.location.Location;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.Repository;
import es.usal.tfg1.model.Parada;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.model.Puntuacion;
import es.usal.tfg1.model.Usuario;

public class VM extends ViewModel {
    /**
     * Tipo repositorio utilizado para las comunicaciones con Firebase y modelos
     */
    private Repository repository = new Repository(this);
    /**
     * Variable que indica si se esta en modo modificar
     * Con esto se indica a los fragmentos si necestian cargar los datos de un punto de rearga o una vista por defecto
     */
    private boolean _modSelected = false;
    public boolean modSelected;

    /**
     * Contiene un objeto de la clase usuario con el usuario actual
     */
    private MutableLiveData<Usuario> _usuario;
    public LiveData<Usuario> usuario;

    /**
     * Indica la visibilidad del menu inferior
     */
    private MutableLiveData<Integer> _visibility;
    public LiveData<Integer> visibility;

    /**
     * Indica la visibilidad del boton de modificar email en la pantalla de usuario
     */
    private MutableLiveData<Integer> _userEmBVisibility;
    public LiveData<Integer> userEmBVisibility;

    /**
     * Indica la visibilidad del boton de modificar contraseña en la pantalla de usuario
     */
    private MutableLiveData<Integer> _userPassBVisibility;
    public LiveData<Integer> userPassBVisibility;

    /**
     * Indica valores para mostrar exito o error al verificar un usuario
     */
    private MutableLiveData<Boolean> _toastVisibility;
    public  LiveData<Boolean> toastVisibility;

    /**
     * Inidica la visibilidad de el icono de carga en la pantalla de usuario
     */
    private MutableLiveData<Boolean> _loadUserVisibility;
    public  LiveData<Boolean> loadUserVisibility;

    /**
     * Inidica la visibilidad de el icono de carga en la pantalla de puntos de recarga
     */
    private MutableLiveData<Boolean> _loadRecyclerVisibility;
    public  LiveData<Boolean> loadRecyclerVisibility;

    /**
     * Contiene un array con todos los puntos de recarga cercanos
     */
    private MutableLiveData<ArrayList<PuntoRecarga>> _recyclerListData;
    public  LiveData<ArrayList<PuntoRecarga>> recyclerListData;

    /**
     * Constiene un array con todos los puntos de recarga
     */
    private MutableLiveData<ArrayList<PuntoRecarga>> _recyclerListCompleteData;
    public  LiveData<ArrayList<PuntoRecarga>> recyclerListCompleteData;

    /**
     * Constiene un array con los puntos de recagra filtrados por el parametro indicado por el usuario
     */
    private MutableLiveData<ArrayList<PuntoRecarga>> _recyclerlistFilteredData;
    public  LiveData<ArrayList<PuntoRecarga>> recyclerlistFilteredData;

    /**
     * Constiene un array con todos los puntos de recarga
     */
    private MutableLiveData<ArrayList<PuntoRecarga>> _PRCompleteList;
    public  LiveData<ArrayList<PuntoRecarga>> PRCompleteList;

    /**
     * Indica la visibilidad de un marco entorno al boton de punto de recarga normal en la pantalla de crear punto de recarga
     */
    private MutableLiveData<Boolean> _nuevoBOutline1;
    public  LiveData<Boolean> nuevoBOutline1;

    /**
     * Indica la visibilidad de un marco entorno al boton de punto de recarga eco en la pantalla de crear punto de recarga
     */
    private MutableLiveData<Boolean> _nuevoBOutline2;
    public  LiveData<Boolean> nuevoBOutline2;

    /**
     * Indica valores para mostrar un toast indicando que hay que rellenar los campos
     */
    private MutableLiveData<Boolean> _nuevoToastFillFields;
    public  LiveData<Boolean> nuevoToastFillFields;

    /**
     * Indica valroes para mosrtar un toast si un punto de recarga ya existe o no
     */
    private MutableLiveData<Boolean> _nuevoToastPRAlreadyExists;
    public  LiveData<Boolean> nuevoToastPRAlreadyExists;

    /**
     * Indica la visibilidad de la interfaz de busqueda en las vistas de los puntos de recarga
     */
    private MutableLiveData<Boolean> _searchPR;
    public  LiveData<Boolean> searchPR;

    /**
     * Inidica la visibilidad de el icono de carga en la pantalla de nuevo punto de recarga
     */
    private MutableLiveData<Boolean> _nuevoLoadingVisibility;
    public  LiveData<Boolean> nuevoLoadingVisibility;

    /**
     * Inidica la visibilidad de el icono de carga en la pantalla de la informacion de un punto de recarga
     */
    private MutableLiveData<Boolean> _infoLoadingVisibility;
    public  LiveData<Boolean> infoLoadingVisibility;

    /**
     * Contiene un array con el valor del recycler de puntuaciones de un punto de rearga para la pantalla de info de ese punto
     */
    private MutableLiveData<ArrayList<Puntuacion>> _infoRecyclerListData;
    public  LiveData<ArrayList<Puntuacion>> infoRecyclerListData;

    /**
     * Contiene el punto de rearga seleccionado en una lsita de puntos de recarga
     */
    private MutableLiveData<PuntoRecarga> _currentPR;
    public  LiveData<PuntoRecarga> currentPR;

    /**
     * Inidica la visibilidad de el icono de carga en la pantalla de la puntuacion de un punto de recarga
     */
    private MutableLiveData<Boolean> _puntuacionLoadingVisibility;
    public  LiveData<Boolean> puntuacionLoadingVisibility;

    /**
     * Indica valores para mostrar un toast indicando el resultado de la modificacion o publicacion de una puntuacion
     */
    private MutableLiveData<Boolean> _puntuacionToast;
    public  LiveData<Boolean> puntuacionToast;

    /**
     * Indica si el usuario puede ver un boton para borrar la puntuacion o no
     */
    private MutableLiveData<Boolean> _puntuacionDelVisibility;
    public  LiveData<Boolean> puntuacionDelVisibility;

    /**
     * Indica si el suario actual puede borrar un punto de recarga en concreto
     */
    private MutableLiveData<Boolean> _infoUserCanDel;
    public  LiveData<Boolean> infoUserCanDel;

    /**
     * Indica valores para mostrar un toast indicando que se ha reportado el punto de recarga
     */
    private MutableLiveData<Boolean> _infoReportToast;
    public  LiveData<Boolean> infoReportToast;

    /**
     * Indica valores para mostrar un toast indicando que se ha producido un error
     */
    private MutableLiveData<Boolean> _errorToast;
    public  LiveData<Boolean> errorToast;

    /**
     * Indica valores para mostrar un toast indicando que hay un error con la latitud y/o longitud
     */
    private MutableLiveData<Boolean> _coordError;
    public  LiveData<Boolean> coordError;

    /**
     * Usuario actual
     */
    private Usuario myUser;
    /**
     * Lista de puntos de recarga
     */
    private ArrayList<PuntoRecarga> PRList;

    /**
     * Devuelve el valor de _toastVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> gettoastVisibility() {
        return (LiveData<Boolean>) _toastVisibility;
    }

    /**
     * Devuelve el valor de _usuario como un LiveData
     * @return
     */
    public LiveData<Usuario> getUsuario() {
        return (LiveData<Usuario>) _usuario;
    }

    /**
     * Devuelve el valor de _visibility como un LiveData
     * @return
     */
    public LiveData<Integer> getVisibility() {
        return (LiveData<Integer>) _visibility;
    }

    /**
     * Devuelve el valor de _loadUserVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> getLoadUserVisibility() {
        return (LiveData<Boolean>) _loadUserVisibility;
    }

    /**
     * Devuelve el valor de _loadRecyclerVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> getLoadRecyclerVisibility() {
        return (LiveData<Boolean>) _loadRecyclerVisibility;
    }

    /**
     * Devuelve el valor de _recyclerListData como un LiveData
     * @return
     */
    public LiveData<ArrayList<PuntoRecarga>> getRecyclerListData() {
        return (LiveData<ArrayList<PuntoRecarga>>) _recyclerListData;
    }

    /**
     * Devuelve el valor de _recyclerListCompleteData como un LiveData
     * @return
     */
    public LiveData<ArrayList<PuntoRecarga>> getRecyclerListCompleteData() {
        return (LiveData<ArrayList<PuntoRecarga>>) _recyclerListCompleteData;
    }

    /**
     * Devuelve el valor de _userEmBVisibility como un LiveData
     * @return
     */
    public LiveData<Integer> getUserEmBVisibility() {
        return (LiveData<Integer>) _userEmBVisibility;
    }

    /**
     * Devuelve el valor de _userPassBVisibility como un LiveData
     * @return
     */
    public LiveData<Integer> getUserPassBVisibility() {
        return (LiveData<Integer>) _userPassBVisibility;
    }

    /**
     * Devuelve el valor de _nuevoOutline1 como un LiveData
     * @return
     */
    public LiveData<Boolean> getNuevoBOutline1() {
        return (LiveData<Boolean>) _nuevoBOutline1;
    }

    /**
     * Devuelve el valor de _nuevoOutline2 como un LiveData
     * @return
     */
    public LiveData<Boolean> getNuevoBOutline2() {
        return (LiveData<Boolean>)  _nuevoBOutline2;
    }

    /**
     * Devuelve el valor de _nuevoToastFillFields como un LiveData
     * @return
     */
    public LiveData<Boolean> getNuevoToastFillFields() {
        return (LiveData<Boolean>)  _nuevoToastFillFields;
    }

    /**
     * Devuelve el valor de _PRCompleteList como un LiveData
     * @return
     */
    public LiveData<ArrayList<PuntoRecarga>> getPRCompleteList() {
        return (LiveData<ArrayList<PuntoRecarga>>)  _PRCompleteList;
    }

    /**
     * Devuelve el valor de _nuevoToastPRALreadyExists como un LiveData
     * @return
     */
    public LiveData<Boolean> getNuevoToastPRAlreadyExists() {
        return (LiveData<Boolean>)  _nuevoToastPRAlreadyExists;
    }

    /**
     * Devuelve el valor de _searchPR como un LiveData
     * @return
     */
    public LiveData<Boolean> getSearchPR() {
        return (LiveData<Boolean>)  _searchPR;
    }

    /**
     * Devuelve el valor de _nuevoLoadingVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> getNuevoLoadingVisibility() {
        return (LiveData<Boolean>)  _nuevoLoadingVisibility;
    }

    /**
     * Devuelve el valor de _infoLoadingVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> getInfoLoadingVisibility() {
        return (LiveData<Boolean>)  _infoLoadingVisibility;
    }

    /**
     * Devuelve el valor de _infoRecyclerListData como un LiveData
     * @return
     */
    public LiveData<ArrayList<Puntuacion>> getInfoRecyclerListData() {
        return (LiveData<ArrayList<Puntuacion>>) _infoRecyclerListData;
    }

    /**
     * Devuelve el valor de _puntuacionLoadingVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> getPuntuacionLoadingVisibility() {
        return (LiveData<Boolean>)  _puntuacionLoadingVisibility;
    }

    /**
     * Devuelve el valor de _currentPR como un LiveData
     * @return
     */
    public LiveData<PuntoRecarga> getCurrentPR() {
        return (LiveData<PuntoRecarga>) _currentPR;
    }

    /**
     * Devuelve el valor de _puntuacionToast como un LiveData
     * @return
     */
    public LiveData<Boolean> getPuntuacionToast() {
        return (LiveData<Boolean>)  _puntuacionToast;
    }

    /**
     * Devuelve el valor de _puntuacionDelVisibility como un LiveData
     * @return
     */
    public LiveData<Boolean> getPuntuacionDelVisibility() {
        return (LiveData<Boolean>)  _puntuacionDelVisibility;
    }

    /**
     * Devuelve el valor de _infoUserCanDel como un LiveData
     * @return
     */
    public LiveData<Boolean> getInfoUserCanDel() {
        return (LiveData<Boolean>)  _infoUserCanDel;
    }

    /**
     * Devuelve el valor de _inforReportToast como un LiveData
     * @return
     */
    public LiveData<Boolean> getInfoReportToast () {
        return (LiveData<Boolean>) _infoReportToast;
    }

    /**
     * Devuelve el valor de _modSelected como un LiveData
     * @return
     */
    public boolean getModSelected() {
        boolean temp = _modSelected;
        _modSelected = false;
        return temp;
    }

    /**
     * Devuelve el valor de _errorToast como un LiveData
     * @return
     */
    public LiveData<Boolean> getErrorToast () {
        return (LiveData<Boolean>) _errorToast;
    }

    /**
     * Devuelve el valor de _coordError como un LiveData
     * @return
     */
    public LiveData<Boolean> getCoordError () {
        return (LiveData<Boolean>) _coordError;
    }

    /**
     * Inicializa los valores de los MutableLiveData apra aseguar que no sean nulos
     */
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
        if(_currentPR == null) {
            _currentPR = new MutableLiveData<PuntoRecarga>();
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

    /**
     * Compreuba si se navega a la pantalla de usuario para ocultar o no el menu inferior
     * @param idDest
     * @param idNavUsuario
     */
    public void onDestinationChangeUsuario(int idDest, int idNavUsuario) {
        if(idDest == idNavUsuario) {
            _visibility.setValue(View.INVISIBLE);
        } else {
            _visibility.setValue(View.VISIBLE);
        }
    }

    /**
     * Recupera o crea un usuario de Firebase
     * @param currentUser
     * @param own
     */
    public void checkNewUser(FirebaseUser currentUser, LifecycleOwner own) {
        repository.checkNewUser(currentUser, _usuario);
  /*      final Observer<Usuario> userObs = new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario user) {
                //Inicializar el usuario porprimera vez
                addUser();
            }
        };
        this.getUsuario().observe(own, userObs);
*/
    }

    /*
    public void addUser() {
        if(myUser == null)
            myUser = repository.getMyUser();
    }*/

    /**
     * Cambia el valor del email del usuario para vaciar el campo cuando se selecciona
     * @param id
     * @param hasFocus
     */
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

    /**
     * Intenta hacer login de nuevo al usuario actual
     * @param pass
     */
    public  void reLogUserEmail(String pass) {
        userLoadVisibility(true);
        repository.relLogUser(repository.getMyUser(), pass);
    }

    /**
     * Error al intentar hacer login de nuevo al usuario actual
     * Se cambia el valor del toast para que se muestre el mensaje de fallo
     */
    public void reLogError() {
        this._toastVisibility.setValue(false);
        userLoadVisibility(false);
    }

    /**
     * Exito al intentar hacer login de nuevo al usuario actual
     * Se cambia el valor del toast para que se muestre el mensaje de exito
     */
    public void reLogSucces() {
        userLoadVisibility(false);
        this._toastVisibility.setValue(true);
    }

    /**
     * Error al intentar hacer un cambio al usuario actual
     * Se cambia el valor del toast para que se muestre el mensaje de fallo
     */
    public void changeError(){
        userLoadVisibility(false);
        this._toastVisibility.setValue(false);
    }

    /**
     * Exito al intentar hacer un cambio al usuario actual
     * Se cambia el valor del toast para que se muestre el mensaje de exito
     * Se actualizan los valores
     */
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

    /**
     * Exito al intentar hacer un cambio al usuario actual
     * Se cambia el valor del toast para que se muestre el mensaje de exito
     */
    public  void changeSucces() {
        this._toastVisibility.setValue(true);
    }

    /**
     * Cambia el valor _loadUserVisivility de manera que se vea reflejado en la vista al momento
     * @param visible
     */
    private void userLoadVisibility(boolean visible) {
        _loadUserVisibility.setValue(visible);
        _loadUserVisibility.setValue(_loadUserVisibility.getValue());
    }

    /**
     * Envia email de recuperacion de contraseña
     */
    public void recovery() {
        repository.recovery();
    }

    /**
     * Cambia la contraseña del usuario actual
     * @param pass
     */
    public void changePass(String pass) {
        repository.modUserpassAuth(pass);
    }

    /**
     * Cambia la autonomia del usuario actual
     * @param aut
     */
    public void changeAut(String aut) {
        _usuario.getValue().setAutonomia(aut);
        repository.moduserAut();
    }

    /**
     * Cambia el email del usuario actual
     * @param email
     */
    public void changeEmail(String email) {
        repository.modUserEmailAuth(email);
    }

    /**
     * Borra el usuario actual
     */
    public void DelUser() {
        repository.delUser();
    }

    /**
     * Controla el estado del otuline en los botones asociados a  _nuevoBOutline1 y _nuevoBOutline2
     * Si hay un outline activo en un boton y se selecciona el otro, sedesactiva en el primero para activar en el segundo
     */
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

    /**
     * Controla el estado del otuline en los botones asociados a  _nuevoBOutline1 y _nuevoBOutline2
     * Si hay un outline activo en un boton y se selecciona el otro, sedesactiva en el primero para activar en el segundo
     */
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

    /**
     * Si se cambia de destino a la pantalla de nuevo punto de recarga, resetea los valores asociados
     * @param id
     * @param navigation_nuevo
     */
    public void onDestinationChangeResetNuevo(int id, int navigation_nuevo) {
        if(id != navigation_nuevo){
            _nuevoBOutline1.setValue(false);
            _nuevoBOutline2.setValue(false);
            _nuevoToastPRAlreadyExists = new MutableLiveData<Boolean>();
            _nuevoToastFillFields = new MutableLiveData<Boolean>();
            _coordError = new MutableLiveData<Boolean>();
        }
    }

    /**
     * Añade un nuevo punto de recarga a Firebase si el campo de nobmre no esta vacio y si las coordenadas son validas
     * @param nombre
     * @param lat
     * @param lon
     * @param descripcion
     */
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

    /**
     * Cambia el valor del toast asociado, y quita la visibilidad del icono de carga
     */
    public void PRAlreadyExists(boolean exists) {
        nuevoNewLoadVisibility(false);
        _nuevoToastPRAlreadyExists.setValue(exists);
    }

    /**
     * Cambia el valor de la lista de puntos de recarga al completo
     * Tambien reinicia el valor de la lista de puntos de recarga filtrados
     * @param prCompleteList
     */
    public void changePRCompleteList(ArrayList<PuntoRecarga> prCompleteList) {
        _PRCompleteList.setValue(prCompleteList);
        _PRCompleteList.setValue(_PRCompleteList.getValue());
        ArrayList<PuntoRecarga> temp = new ArrayList<PuntoRecarga>();
        for (PuntoRecarga p: prCompleteList) {
            temp.add(new PuntoRecarga(p));
        }
        _recyclerlistFilteredData.setValue(temp);
    }

    /**
     * Cambia la lsita de puntos de recarga y oculta el icono de carga del recycler asociado
     * @param prList
     */
    public void changePRList(ArrayList<PuntoRecarga> prList) {
        _recyclerListData.setValue(prList);
        _loadRecyclerVisibility.setValue(false);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
    }

    /**
     * Cambia el valor de la lista de puntos de recarga al completo
     * Tambien reinicia el valor de la lista de puntos de recarga filtrados
     * @param prCompleteList
     */
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

    /**
     * Carga el recycler con los puntos de recarga cercanos dandole un valor a la distancia con el usuario actual
     * Tambien hace visible la patnalla de carga
     * @param result
     */
    public void loadRecyclerList(Location result) {
        _loadRecyclerVisibility.setValue(true);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        repository.getPRList(new Parada(result.getLongitude(),result.getLatitude()));
    }

    /**
     * Carga el recycler con los puntos de recarga dandole un valor a la distancia con el usuario actual
     * Tambien hace visible la patnalla de carga
     * @param result
     */
    public void loadRecyclerListComplete(Location result) {
        _loadRecyclerVisibility.setValue(true);
        _loadRecyclerVisibility.setValue(_loadRecyclerVisibility.getValue());
        repository.getPRListComplete(new Parada(result.getLongitude(),result.getLatitude()));
    }

    /**
     * Cambia el valor de la visibilidad de la carga en la pantalla de nuevo punto de recarga para que se vea reflejada al momento en la vista
     * @param value
     */
    public void nuevoNewLoadVisibility(boolean value) {
        _nuevoLoadingVisibility.setValue(value);
        _nuevoLoadingVisibility.setValue(_nuevoLoadingVisibility.getValue());
    }

    /**
     * Cambia el valor del punto de recrga seleccionado
     * @param position
     */
    public void setSelectedPR(int position) {
        if(_searchPR.getValue()) {
            setSelectedPR(_recyclerlistFilteredData.getValue().get(position));
        } else {
            setSelectedPR(_recyclerListData.getValue().get(position));
        }
    }

    /**
     * Cambia el valor del punto de recarga seleccionado
     * @param p
     */
    public void setSelectedPR(PuntoRecarga p) {
        _currentPR = new MutableLiveData<PuntoRecarga>();
        _currentPR.setValue(p);
        _currentPR.setValue(_currentPR.getValue());
        _infoRecyclerListData.setValue(p.getPuntuaciones());
        _infoRecyclerListData.setValue(_infoRecyclerListData.getValue());
    }

    /**
     * Comprueba si el usuario actual tiene un puntuacion para el punto de recarga actual y la devuelve
     * @return
     */
    public Puntuacion checkUserPunt(){
        if(_currentPR.getValue().getPuntuaciones() != null) {
            for (Puntuacion p: _currentPR.getValue().getPuntuaciones()) {
                if(p.getId().equals(_usuario.getValue().getId())) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Crea una nueva puntuacion para el punto de recarga y el usuario en Firebase con los datos dados
     * @param op
     * @param rating
     */
    public void newPuntuacion(String op, float rating) {
        changePuntuacionLoadingVisibility(true);
        repository.NewPuntuacion(op, rating, _currentPR.getValue());
    }

    /**
     * Cambia la visivilidad de la pantalla de carga para que se vea reflejado al momento en la vista
     * @param visibility
     */
    public void changePuntuacionLoadingVisibility(boolean visibility){
        _puntuacionLoadingVisibility.setValue(visibility);
        _puntuacionLoadingVisibility.setValue(_puntuacionLoadingVisibility.getValue());
    }

    /**
     * Cambia el punto de recarga actual por uno actualizado de la lista completa
     */
    public void changePuntuacion() {
        for (PuntoRecarga puntoRecarga: _recyclerListData.getValue()) {
            if(puntoRecarga.getId().equals(_currentPR.getValue().getId())) {
                setSelectedPR(puntoRecarga);
                changePuntuacionLoadingVisibility(false);
                //Cambiar el valor del toast para mostrar operacion exitosa
                changePuntuacionToast(true);
                return;
            }
        }
        for (PuntoRecarga puntoRecarga: _recyclerListCompleteData.getValue()) {
            if(puntoRecarga.getId().equals(_currentPR.getValue().getId())) {
                setSelectedPR(puntoRecarga);
                changePuntuacionLoadingVisibility(false);
                //Cambiar el valor del toast para mostrar operacion exitosa
                changePuntuacionToast(true);
                return;
            }
        }
    }

    /**
     * cambia el valor del toast de puntuacion para mostrar mensaje de error
     */
    public void changePuntuacioError(){
        changePuntuacionToast(false);
    }

    /**
     * cambia el valor del toast de puntuacion para mostrar mensaje de error o exito
     */
    public void changePuntuacionToast(boolean bool) {
        _puntuacionToast.setValue(bool);
    }

    /**
     * Si se cambia de destino a la pantalla de puntuar punto de recarga, resetea los valores asociados
     * @param id
     * @param navigation_puntuar
     */
    public void onDestinationChangeResetPuntuar(int id, int navigation_puntuar) {
        if(id != navigation_puntuar){
            _puntuacionToast = new MutableLiveData<Boolean>();
            _puntuacionLoadingVisibility = new MutableLiveData<Boolean>();
            _puntuacionLoadingVisibility.setValue(false);
        }
    }

    /**
     * Cambia el valor asociado a _infoUserCanDel para que se vea reflejado en la vista al momento
     * @param can
     */
    public void changeUserCanDel(boolean can) {
        _infoUserCanDel.setValue(can);
        _infoUserCanDel.setValue(_infoUserCanDel.getValue());
    }

    /**
     * Comprueba si el usuario actual puede borrar el punto de recarga seleccionado y cambia el valor asociado
     */
    public void checkUserCanDel() {
        if(_usuario.getValue().getId().equals(_currentPR.getValue().getCreadorID())) {
            changeUserCanDel(true);
        } else if (_usuario.getValue().getRol()) {
            changeUserCanDel(true);
        }
        else {
            changeUserCanDel(false);
        }
    }

    /**
     * Devuelve el valor asociado que indica si el suario puede borrar o no
     * @return
     */
    public boolean userCanDel() {
        return  _puntuacionDelVisibility.getValue();
    }

    /**
     * Cambia el valor asociado que indica si el suario puede ver le boton de borrar o no
     * @return
     */
    public void changePuntuarDelVisibility() {
        _puntuacionDelVisibility.setValue(true);
    }

    /**
     * Cambia el valor asociado que indica si el suario puede ver le boton de borrar o no
     * @return
     */
    public void changePuntuarDelVisibilityFalse() {
        _puntuacionDelVisibility.setValue(false);
    }

    /**
     * Borra la putuacion del usuario actual del punto de recarga seleccionado
     */
    public void delPuntuacionButton() {
        repository.delPuntuacion(_currentPR.getValue());
    }

    /**
     * Borra el punto de recarga seleccionado
     */
    public void delPR() {
        repository.delPR(_currentPR.getValue());
    }

    /**
     * Reporta el punto de recarga seleccionado
     */
    public void reportPR() {
        repository.addReport(_currentPR.getValue());
    }

    /**
     * Cambia el valor para mostrar un toast indica el exito de reportar
     */
    public void reportarSucces() {
        changeReportarToastVisibility(true);
    }

    /**
     * Cambia el valor para mostrar un toast indica el exito o fallo de reportar
     */
    public void changeReportarToastVisibility(boolean visible) {
        _infoReportToast.setValue(true);
        _infoReportToast = new MutableLiveData<Boolean>();
    }

    /**
     * Indica que se ha seleccionado modificar
     */
    public void modSelectedTrue() {
        changeModSelected(true);
    }

    /**
     * Asigna un valor a _modSelected
     * @param bool
     */
    public void changeModSelected(boolean bool) {
        this._modSelected = bool;
    }

    /**
     * Cambia el outline de los botones en la pantalla modificar punto de recarga
     */
    public void changeModOutline() {
        if(_currentPR.getValue().isEco()) {
            _nuevoBOutline2.setValue(true);
            _nuevoBOutline1.setValue(false);
        } else {
            _nuevoBOutline2.setValue(false);
            _nuevoBOutline1.setValue(true);
        }
    }

    /**
     * Modifica los valores de un punto de recarga
     * @param nombre
     * @param lat
     * @param lon
     * @param descripcion
     */
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
            repository.modAllPRFields(nombre, lat, lon, descripcion, false, _currentPR.getValue());
        } else if(_nuevoBOutline2.getValue()) {
            repository.modAllPRFields(nombre, lat, lon, descripcion, true, _currentPR.getValue());
        } else {
            _nuevoToastFillFields.setValue(true);
            nuevoNewLoadVisibility(false);
        }
    }

    /**
     * Indica exito al modifica un punto de recarga
     */
    public void nuevoModSuccess() {
        PRAlreadyExists(false);
    }

    /**
     * Indica un error
     */
    public void getPRListForRecyclerError() {
        changeErrorToastValue(true);
    }

    /**
     * Indica un error
     */
    public void checkNewUserError() {
        changeErrorToastValue(true);
    }

    /**
     * Indica un error
     */
    public void addNewPRError() {
        changeErrorToastValue(true);
    }

    /**
     * Indica un error
     */
    public void modPRPuntuacionesError() {
        changeErrorToastValue(true);
    }

    /**
     * Indica un error
     */
    public void addReportError() {
        changeErrorToastValue(true);
    }

    /**
     * Indica un error
     */
    public void modAllPRFieldsError() {
        changeErrorToastValue(true);
    }
    /**
     * Indica un error
     */
    public void changeErrorToastValue(boolean error) {
        _errorToast.setValue(error);
    }

    /**
     * Comprueba si las coordenadas son validas
     * @param longitud
     * @param latitud
     * @return
     */
    public boolean checkInvCoords (float longitud, float latitud){
        if(repository.checkInvCoords(longitud,latitud)) { //Si las coordenadas son invalidas
            _coordError.setValue(true);
            return true;
        }
        return false;
    }

    /**
     * COmprueba si la nueva autonomia indicada es la misma que l aya existente
     * @param tempStringEmailPass
     * @return
     */
    public boolean checkAut(String tempStringEmailPass) {
        if(_usuario.getValue().getAutonomia().equals(tempStringEmailPass)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cambia la visibilidad de la parte de busqueda si se va a la pantalla de busqueda de puntos de recarga
     * @param id
     * @param navigation_pr_list_search
     */
    public void onDestinationChangeSearch(int id, int navigation_pr_list_search) {
        if(id == navigation_pr_list_search) {
            _searchPR.setValue(true);
        }
    }

    /**
     * Cambia la visibilidad de la parte de busqueda si se va a la pantalla de puntos de recarga cercanos
     * @param id
     * @param navigation_pr_list
     */
    public void onDestinationChangeList(int id, int navigation_pr_list) {
        if(id == navigation_pr_list) {
            _searchPR.setValue(false);
        }
    }

    /**
     * Cambia la lista completa de puntos de recarga filtrados
     * @param searchBy
     * @return
     */
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

    /**
     * Compreuba si un usuario es adminsitrador
     * @return
     */
    public boolean isAdmin() {
        return _usuario.getValue().getRol();
    }

    /**
     * Crea puntos de recarga oficiales a partir delJSON oficial de la Junta de Castilla y Leon
     * @param jsonText
     * @throws JSONException
     */
    public void createOfficialPRs(String jsonText) throws JSONException {
        repository.PRfromJson(jsonText);
    }
}
