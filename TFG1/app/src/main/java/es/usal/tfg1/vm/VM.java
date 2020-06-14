package es.usal.tfg1.vm;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.usal.tfg1.R;
import es.usal.tfg1.model.Usuario;
import io.opencensus.tags.Tag;

public class VM extends ViewModel {
    private FirebaseFirestore firestore;
    private static final String TAG = "DocSnippets";

    private MutableLiveData<Usuario> _usuario;
    public LiveData<Usuario> usuario;

    private MutableLiveData<Integer> _visibility;
    public LiveData<Integer> visibility;

    private MutableLiveData<Integer> _userEmBVisibility;
    public LiveData<Integer> userEmBVisibility;

    private MutableLiveData<Integer> _userPassBVisibility;
    public LiveData<Integer> userPassBVisibility;

    private Usuario myUser;

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

    public void checkNewUser(final FirebaseUser currentUser) {
        if(firestore == null)
            firestore = FirebaseFirestore.getInstance();
        DocumentReference usuarioAct = firestore.collection("Usuarios").document(currentUser.getUid());

        //Intentamos conseguir el documento del usuario especificado
        usuarioAct.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //Cuando se acaba la tarea se comrpueba su exito
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //Si el documento no existe crear al usuario
                    if (!document.exists()) {
                        myUser = new Usuario(currentUser.getUid(), currentUser.getEmail());
                        addUser(myUser);

                        _usuario.setValue(new Usuario(myUser));
                    } else { //Si no, solo rellenar los datos de este en la clase local usuario
                        myUser = document.toObject(Usuario.class);
                        _usuario.setValue(new Usuario(myUser));
                    }
                } else {//EN caso de fallo indicarlo
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public void addUser(Usuario usuario) {
        if(firestore == null)
            firestore = FirebaseFirestore.getInstance();
        firestore.collection("Usuarios").document(usuario.getId()).set(usuario);
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
}
