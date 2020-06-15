package es.usal.tfg1;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.usal.tfg1.model.Usuario;
import es.usal.tfg1.vm.VM;

public class Repository {
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private AuthCredential credential;

    private Usuario myUser;
    private VM myVM;
    private MutableLiveData<Usuario> _usuario;
    private static final String TAG = "DocSnippets";

    public Repository(VM myVM) {
        this.myVM = myVM;
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

    public void modUserEmailFirestore() {
        DocumentReference usuarioAct = firestore.collection("Usuarios").document(this.currentUser.getUid());
        usuarioAct.update("email", _usuario.getValue().getEmail());
    }
    public void modUserEmailAuth(final String email) {
        currentUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            myVM.changeSucces(currentUser.getEmail());
                        } else {
                            myVM.changeError();
                        }
                    }
                });
    }

    public void relLogUser(Usuario user, String pass) {
        credential = EmailAuthProvider.getCredential(user.getEmail(), pass);
        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    //Llama a myVM para indicarle el resultado
                    myVM.reLog();
                } else {
                    //Llamar a myVM para que muestre un toast diciendo error o añadir texto de error invisile que cambia a visible con esta llamada
                    myVM.reLogError();
                }
            }
        });

    }



}
