package es.usal.tfg1;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import es.usal.tfg1.model.Parada;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.model.Usuario;
import es.usal.tfg1.vm.VM;

public class Repository {
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private AuthCredential credential;
    private FirebaseAuth auth;
    private Parada currentLoc;
    private Usuario myUser;
    private ArrayList<PuntoRecarga> PRCompleteList;
    private ArrayList<PuntoRecarga> PRList;
    private VM myVM;
    private MutableLiveData<Usuario> _usuario;
    private static final String TAG = "DocSnippets";

    public Usuario getMyUser() {
        return myUser;
    }

    public void setMyUser(Usuario myUser) {
        this.myUser = myUser;
    }

    public Repository(VM myVM) {
        this.myVM = myVM;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        PRList = new ArrayList<PuntoRecarga>();
        PRCompleteList = new ArrayList<PuntoRecarga>();
        currentLoc = new Parada();
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

    public void delUserFirestore() {
        firestore.collection("Usuarios").document(myUser.getId()).delete();
    }


    public void relLogUser(Usuario user, String pass) {
        credential = EmailAuthProvider.getCredential(user.getEmail(), pass);
        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    //Llama a myVM para indicarle el resultado
                    myVM.reLogSucces();
                } else {
                    //Llamar a myVM para indicarle el resultado
                    myVM.reLogError();
                }
            }
        });
    }

    public void relLogUserGoogle(GoogleSignInAccount acct) {
        if (acct != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        myVM.reLogSucces();
                    } else {
                        myVM.reLogError();
                    }
                }
            });
        } else {
            myVM.changeError();
        }
    }

    public void recovery() {
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(myUser.getEmail());
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

    public void modUserpassAuth(String pass) {
        currentUser.updatePassword(pass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            myVM.changeSucces();
                        } else {
                            myVM.changeError();
                        }
                    }
                });
    }

    public void delUser() {
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delUserFirestore();
                    myVM.changeSucces();
                } else {
                    myVM.changeError();
                }
            }
        });

    }

    public void getPRList(Parada currentLoc) {
        this.currentLoc = new Parada(currentLoc);
        //TODO
        /* Rellenar el arraylist con todas las paradas (puntos de recarga) cercanas al usuario
        *  Recuperar todos los puntos de recarga (HECHO), poner todos con marcadores en el mapa y meter los que esten a menos de autonomia*0.65 de distancia para mostrar como puntos cercanos ordenando por distancia
        */

        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PRCompleteList.add(document.toObject(PuntoRecarga.class));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public PuntoRecarga getPuntoRecargaFromFirestore() {
        PuntoRecarga p = new PuntoRecarga();
        return p;
    }

    public void addPuntoRecargaToFirestore(PuntoRecarga p) {
        //Se añade el punto de recarga, se coge el ID unico que asigna Firebase, se modifica el punto de recarga poniendole ese ID
        firestore.collection("PuntosRecarga").add(p).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                documentReference.update("id",documentReference.getId());
            }
        });
    }

    public Parada getParada(double longitud, double latitud) {
        return new Parada(longitud, latitud);
    }

    public Parada getCurrentLoc() {
        return currentLoc;
    }
}
