package es.usal.tfg1;

import android.util.Log;

import androidx.annotation.NonNull;

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
    private static final String TAG = "DocSnippets";

    public Repository() {
        firestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public Usuario checkNewUser() {
        DocumentReference usuarioAct = firestore.collection("Usuarios").document(currentUser.getUid());

        //Intentamos conseguir el documento del usuario especificado
        usuarioAct.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {      //Cuando se acaba la tarea se comrpueba su exito
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {   //Si el documento no existe crear al usuario
                        myUser = new Usuario(currentUser.getUid(), currentUser.getEmail());
                        addUser(myUser);
                    } else {                    //Si no, solo rellenar los datos de este en la clase local usuario
                        myUser = document.toObject(Usuario.class);
                    }
                } else {                        //En caso de fallo indicarlo
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return myUser;
    }

    public void addUser(Usuario usuario) {
        firestore.collection("Usuarios").document(usuario.getId()).set(usuario);
    }


}
