package es.usal.tfg1;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Comparator;

import es.usal.tfg1.model.Parada;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.model.Puntuacion;
import es.usal.tfg1.model.Reporte;
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
                    myVM.checkNewUserError();
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

    public void moduserAut() {
        DocumentReference usuarioAct = firestore.collection("Usuarios").document(this.currentUser.getUid());
        usuarioAct.update("autonomia", _usuario.getValue().getAutonomia(), "autonomiaF", _usuario.getValue().getAutonomiaF()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
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

    public void getPRList(final Parada currentLoc) {
        this.currentLoc = new Parada(currentLoc);
        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PRList = new ArrayList<PuntoRecarga>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        float res[] = new float[2];
                        Location.distanceBetween(currentLoc.getLatitud(),currentLoc.getLongitud(), document.toObject(PuntoRecarga.class).getParada().getLatitud(), document.toObject(PuntoRecarga.class).getParada().getLongitud(), res);
                        PuntoRecarga newPR = new PuntoRecarga(document.toObject(PuntoRecarga.class));
                        newPR.setDistanciaF(res[0]);
                        if(newPR.getDistaciaF() <= _usuario.getValue().getAutonomiaF()) {
                            PRList.add(new PuntoRecarga(newPR));
                        }
                    }
                    PRList.sort(Comparator.comparing(PuntoRecarga::getDistaciaF));
                    myVM.changePRList(PRList);
                } else {
                    myVM.getPRListForRecyclerError();
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void getPRListComplete(Parada parada) {
        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PRCompleteList = new ArrayList<PuntoRecarga>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        float res[] = new float[2];
                        Location.distanceBetween(currentLoc.getLatitud(),currentLoc.getLongitud(), document.toObject(PuntoRecarga.class).getParada().getLatitud(), document.toObject(PuntoRecarga.class).getParada().getLongitud(), res);
                        PuntoRecarga newPR = new PuntoRecarga(document.toObject(PuntoRecarga.class));
                        newPR.setDistanciaF(res[0]);
                        PRCompleteList.add(new PuntoRecarga(newPR));
                    }
                    myVM.changePRListComplete(new ArrayList<PuntoRecarga>(PRCompleteList));
                } else {
                    myVM.getPRListForRecyclerError();
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void getPRListAndUpdatePRInfo() {
        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PRList = new ArrayList<PuntoRecarga>();
                    PRCompleteList = new ArrayList<PuntoRecarga>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        float res[] = new float[2];
                        Location.distanceBetween(currentLoc.getLatitud(),currentLoc.getLongitud(), document.toObject(PuntoRecarga.class).getParada().getLatitud(), document.toObject(PuntoRecarga.class).getParada().getLongitud(), res);
                        PuntoRecarga newPR = new PuntoRecarga(document.toObject(PuntoRecarga.class));
                        newPR.setDistanciaF(res[0]);
                        if(newPR.getDistaciaF() <= _usuario.getValue().getAutonomiaF()) {
                            PRList.add(new PuntoRecarga(newPR));
                        }
                        PRCompleteList.add(new PuntoRecarga(newPR));
                    }
                    PRList.sort(Comparator.comparing(PuntoRecarga::getDistaciaF));
                    myVM.changePRList(PRList);
                    myVM.changePRCompleteList(PRCompleteList);
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
        firestore.collection("PuntosRecarga").add(p).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()) {
                    task.getResult().update("id",task.getResult().getId());
                    PuntoRecarga nuevo = new PuntoRecarga(p);
                    nuevo.setId(task.getResult().getId());
                    PRCompleteList.add(nuevo);
                    myVM.changePRCompleteList(PRCompleteList);
                    myVM.PRAlreadyExists(false);
                } else {
                    myVM.addNewPRError();
                }
            }
        });
    }

    public Parada getParada(double longitud, double latitud) {
        return new Parada(longitud, latitud);
    }

    public Parada getCurrentLoc() {
        return currentLoc;
    }

    public void newPR(String nombre, String lat, String lon, String descripcion, boolean eco) {

        tempPR = new PuntoRecarga(new Parada(Double.parseDouble(lon), Double.parseDouble(lat)), "temp", nombre, myUser.getId(), false, descripcion, eco);
        if(checkIfTooNear(tempPR)) {
            myVM.PRAlreadyExists(true);
            return;
        }
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
                    modPRPuntuaciones(PR);
                    return;
                }
            }
            //Si no se ha encontrado una puntuacion de este usuario añadirla
            PR.getPuntuaciones().add(p);
            PR.updatePuntuacion();
            //Una vez añadida la puntuacion actualizar el PR correspondiente en firebase
            modPRPuntuaciones(PR);
        } else {
            ArrayList<Puntuacion> newP = new ArrayList<Puntuacion>();
            newP.add(p);
            PR.setPuntuaciones(newP);
            //Una vez añadida la puntuacion actualizar el PR correspondiente en firebase
            modPRPuntuaciones(PR);
        }
    }

    public void modPRPuntuaciones(PuntoRecarga PR) {
        DocumentReference puntoRecarga = firestore.collection("PuntosRecarga").document(PR.getId());
        puntoRecarga.update("puntuaciones", PR.getPuntuaciones()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    //Si se modifica avisar de esto al vm y actualizar las listas de PR
                    getPRListAndUpdatePRInfo();
                    //Luego avisar a vm para que modifique el boolean del toast de exito

                } else {
                    myVM.modPRPuntuacionesError();
                }
            }
        });
    }

    public void delPuntuacion(PuntoRecarga PR) {
        int i = 0;
        boolean del = false;
        for (Puntuacion p: PR.getPuntuaciones()) {
            if(p.getId().equals(currentUser.getUid())) {
                del = true;
                break;
            }
            i++;
        }
        if(del) {
            PR.getPuntuaciones().remove(i);
            modPRPuntuaciones(PR);
        }
    }

    public void delPR(PuntoRecarga PR) {
        firestore.collection("PuntosRecarga").document(PR.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    myVM.modPRPuntuacionesError();
                }
            }
        });
    }

    public void addReport(PuntoRecarga PR) {
        firestore.collection("reportes").add(new Reporte(PR.getId(), currentUser.getUid())).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()) {
                    myVM.reportarSucces();
                } else {
                    myVM.addReportError();
                }
            }
        });
    }

    public void modAllPRFields(String nombre, String lat, String lon, String descripcion, boolean eco, PuntoRecarga PR) {
        DocumentReference puntoRecarga = firestore.collection("PuntosRecarga").document(PR.getId());
        Parada p = new Parada(Double.parseDouble(lon), Double.parseDouble(lat));
        if(checkIfTooNear(p, PR)) {
            myVM.PRAlreadyExists(true);
            return;
        }
        puntoRecarga.update("nombre", nombre, "parada", p, "descripcion", descripcion, "eco", eco).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    getPRListAndUpdatePRInfo();
                    myVM.nuevoModSuccess();
                } else {
                    myVM.modAllPRFieldsError();
                }
            }
        });
    }

    public boolean checkIfTooNear(PuntoRecarga p) {
        for (PuntoRecarga puntoRecarga: PRCompleteList) {
            float res[] = new float[2];
            Location.distanceBetween(p.getParada().getLatitud(),p.getParada().getLongitud(), puntoRecarga.getParada().getLatitud(), puntoRecarga.getParada().getLongitud(), res);
            if(res[0] < 100) {
                return true;
            }
        }
        return  false;
    }

    public boolean checkIfTooNear(Parada p, PuntoRecarga PR) {
        for (PuntoRecarga puntoRecarga: PRCompleteList) {
            float res[] = new float[2];
            Location.distanceBetween(p.getLatitud(),p.getLongitud(), puntoRecarga.getParada().getLatitud(), puntoRecarga.getParada().getLongitud(), res);
            if(res[0] < 100) {
                if(PR.getParada().getLongitud() != p.getLongitud() || PR.getParada().getLatitud() != p.getLatitud()) {
                    return true;
                }
            }
        }
        return  false;
    }

    public boolean checkInvCoords(float longitud, float latitud) {
        Parada temp = new Parada(1,1);
        return temp.checkInvCoords(longitud, latitud);
    }

    public void PRfromJson(String jsonText) throws JSONException {
        PuntoRecarga temp = new PuntoRecarga();
        ArrayList<PuntoRecarga> puntosOficiales = temp.createPRFromJson(jsonText);

        firestore.collection("PuntosRecarga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PRCompleteList = new ArrayList<PuntoRecarga>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PRCompleteList.add(document.toObject(PuntoRecarga.class));
                    }
                    //Revisamos todos los puntos de recarga, comprobanodo si ya existen
                    //Si es así se modifican los existentes y se borran de la lista
                    //Luego los que no existen se crean y añaden
                    ArrayList<String> toDelete = new ArrayList<String>();
                    for (int i = 0; i < PRCompleteList.size(); i++) {
                        for (PuntoRecarga punto: puntosOficiales) {
                            if(punto.getNombre().toLowerCase().equals(PRCompleteList.get(i).getNombre().toLowerCase()) && PRCompleteList.get(i).isVerificado()) {   //Si tienen el mismo nombre y estan verificados son el mismo
                                modAllPRFields(punto.getNombre(), Double.toString(punto.getParada().getLatitud()), Double.toString(punto.getParada().getLongitud()), punto.getDescripcion(), PRCompleteList.get(i));
                                toDelete.add(punto.getNombre());
                            }
                        }
                    }
                    for (String nombre : toDelete) {
                        puntosOficiales.removeIf(punt -> punt.getNombre().toLowerCase().equals(nombre));
                    }
                    for(PuntoRecarga puntoRecarga : puntosOficiales) {
                        addPuntoRecargaToFirestore(puntoRecarga);
                    }
                } else {
                    myVM.getPRListForRecyclerError();
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void modAllPRFields(String nombre, String lat, String lon, String descripcion, PuntoRecarga PR) {
        DocumentReference puntoRecarga = firestore.collection("PuntosRecarga").document(PR.getId());
        Parada p = new Parada(Double.parseDouble(lon), Double.parseDouble(lat));
        puntoRecarga.update("nombre", nombre, "parada", p, "descripcion", descripcion);
    }

}
