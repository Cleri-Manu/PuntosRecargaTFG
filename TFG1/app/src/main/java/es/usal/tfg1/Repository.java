package es.usal.tfg1;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.usal.tfg1.model.Usuario;

public class Repository {
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private Usuario myUser;
    private MutableLiveData<Usuario> _usuario;
    private static final String TAG = "DocSnippets";

    public Repository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void checkNewUser(final FirebaseUser currentUser, MutableLiveData<Usuario> _usuario) {
        this.currentUser = currentUser;
        this._usuario = _usuario;
        DocumentReference usuarioAct = firestore.collection("Usuarios").document(this.currentUser.getUid());

        //Intentamos conseguir el documento del usuario especificado
        usuarioAct.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {      //Cuando se acaba la tarea se comrpueba su exito
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {   //Si el documento no existe crear al usuario
                        Repository.this.myUser = new Usuario(Repository.this.currentUser.getUid(), Repository.this.currentUser.getEmail());
                        Repository.this._usuario.setValue(new Usuario(Repository.this.myUser));
                        addUser(Repository.this.myUser);
                    } else {                    //Si no, solo rellenar los datos de este en la clase local usuario
                        Repository.this.myUser = document.toObject(Usuario.class);
                        Repository.this._usuario.setValue(new Usuario(Repository.this.myUser));
                    }
                } else {                        //En caso de fallo indicarlo
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public void addUser(Usuario usuario) {
        firestore.collection("Usuarios").document(usuario.getId()).set(usuario);
    }

    public void modUser() {
        DocumentReference usuarioAct = firestore.collection("Usuarios").document(this.currentUser.getUid());
        usuarioAct.update("email", _usuario.getValue().getEmail());
    }



}
