package es.usal.tfg1;

import android.text.Editable;
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
import es.usal.tfg1.model.Puntuacion;
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
    private PuntoRecarga tempPR;

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
        *  De momento se pasan todos los puntos de recarga al recycler
        */

        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PRCompleteList = new ArrayList<PuntoRecarga>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PRCompleteList.add(document.toObject(PuntoRecarga.class));
                        myVM.changePRList(PRCompleteList);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void getPRListAndUpdatePRInfo() {
        //TODO
        /* Rellenar el arraylist con todas las paradas (puntos de recarga) cercanas al usuario
         *  Recuperar todos los puntos de recarga (HECHO), poner todos con marcadores en el mapa y meter los que esten a menos de autonomia*0.65 de distancia para mostrar como puntos cercanos ordenando por distancia
         *  De momento se pasan todos los puntos de recarga al recycler
         */

        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PRCompleteList = new ArrayList<PuntoRecarga>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PRCompleteList.add(document.toObject(PuntoRecarga.class));
                    }
                    myVM.changePRList(PRCompleteList);
                    myVM.changePuntuacion();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    myVM.changePuntuacioError();
                }
            }
        });
    }

    public PuntoRecarga getPuntoRecargaFromFirestore() {
        PuntoRecarga p = new PuntoRecarga();
        return p;
    }

    public void addPuntoRecargaToFirestore(final PuntoRecarga p) {
        //firestore.collection("PuntosRecarga").whereEqualTo( "parada.latitud", p.getParada().getLatitud()).whereEqualTo( "parada.longitud", p.getParada().getLongitud()).get()
        //Primero comprobamos si ya existe o no
        firestore.collection("PuntosRecarga").whereLessThan( "parada.latitud", p.getParada().getLatitud()*1.005).whereGreaterThan( "parada.latitud", p.getParada().getLatitud()*0.995).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().size() != 0){ //Se busca si hay un punto muy cercano al que se queire cear, si lo hay avisar, y acabar, si no continuar y añadir el nuevo punto de recarga
                        for (QueryDocumentSnapshot document : task.getResult()) {
                                if(p.getParada().checkIfLonEqual(p.getParada(), document.toObject(PuntoRecarga.class).getParada())){ //Si la latitud y longitud son muy similares se considera repetido
                                    myVM.PRAlreadyExists(true);
                                    return;
                                }
                        }
                    }
                    //Se añade el punto de recarga, se coge el ID unico que asigna Firebase, se modifica el punto de recarga poniendole ese ID
                    firestore.collection("PuntosRecarga").add(p).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            documentReference.update("id",documentReference.getId());
                            PuntoRecarga nuevo = new PuntoRecarga(p);
                            nuevo.setId(documentReference.getId());
                            PRCompleteList.add(nuevo);
                            myVM.changePRCompelteList(PRCompleteList);
                            myVM.PRAlreadyExists(false);
                        }
                    });

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

/*
    public void getPRFromFirestore(String id) {
        DocumentReference currentPR = firestore.collection("PuntosRecarga").document(id);

        //Intentamos conseguir el documento del usuario especificado
        currentPR.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {      //Cuando se acaba la tarea se comrpueba su exito
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {   //Si el documento no existe crear al usuario
                        myVM.setSelectedPR(document.toObject(PuntoRecarga.class));
                    } else {                    //Si no, solo rellenar los datos de este en la clase local usuario
                    }
                } else {                        //En caso de fallo indicarlo
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }*/

    public Parada getParada(double longitud, double latitud) {
        return new Parada(longitud, latitud);
    }

    public Parada getCurrentLoc() {
        return currentLoc;
    }

    public void newPR(String nombre, String lat, String lon, String descripcion, boolean eco) {
        tempPR = new PuntoRecarga(new Parada(Double.parseDouble(lon), Double.parseDouble(lat)), "temp", nombre, myUser.getId(), false, descripcion, eco);
        addPuntoRecargaToFirestore(new PuntoRecarga(tempPR));
    }

    public void NewPuntuacion(String op, float rating, PuntoRecarga PR) {
        //Cuando se edita un valor hacer que myVM recargue todos los puntos de recarga
        Puntuacion p = new Puntuacion(currentUser.getUid(), op,  rating);
        if(PR.getPuntuaciones() != null) {
            for (Puntuacion puntuacion: PR.getPuntuaciones()) { //Si el usuario ya tiene una puntuacion, modificarla
                if(puntuacion.getId().equals(this.currentUser.getUid())) {
                    puntuacion.setComentario(p.getComentario());
                    puntuacion.setId(p.getId());
                    puntuacion.setPuntuacion(p.getPuntuacion());
                    PR.updatePuntuacion();
                    //Una vez añadida la puntuacion actualizar el PR correspondiente en firebase
                    modPR("puntuaciones", PR);
                    return;
                }
            }
            //Si no se ha encontrado una puntuacion de este usuario añadirla
            PR.getPuntuaciones().add(p);
            PR.updatePuntuacion();
            //Una vez añadida la puntuacion actualizar el PR correspondiente en firebase
            modPR("puntuaciones", PR);
        } else {
            ArrayList<Puntuacion> newP = new ArrayList<Puntuacion>();
            newP.add(p);
            PR.setPuntuaciones(newP);
            //Una vez añadida la puntuacion actualizar el PR correspondiente en firebase
            modPR("puntuaciones", PR);
        }
    }

    public void modPR(String campo, PuntoRecarga PR) {
        DocumentReference puntoRecarga = firestore.collection("PuntosRecarga").document(PR.getId());
        if(campo.equals("puntuaciones")){
            puntoRecarga.update(campo, PR.getPuntuaciones()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        //Si se modifica avisar de esto al vm y actualizar las listas de PR
                        getPRListAndUpdatePRInfo();
                        //Luego avisar a vm para que modifique el boolean del toast de exito

                    } else {
                        //TODO
                        /* Si falla avisar a myVM para que modifique el observer correspondiente a la toast de fallo
                        *
                        */

                    }
                }
            });
        }
    }
}
