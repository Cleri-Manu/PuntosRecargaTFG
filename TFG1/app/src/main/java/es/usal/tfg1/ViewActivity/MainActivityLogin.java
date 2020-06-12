package es.usal.tfg1.ViewActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewActivity.MAPA.MainActivityMAPA;
import es.usal.tfg1.vm.FactoriaVM;
import es.usal.tfg1.vm.VM;

public class MainActivityLogin extends AppCompatActivity {
    private FirebaseAuth myAuth;
    private int RC_SIGN_IN = 1;
    Intent myIntent;
    @Override //Inicializar valores importantes
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAuth = FirebaseAuth.getInstance();
    }

    @Override //Al comenzar comprobar el estado de autentificacion de usuario
    public void onStart() {
        super.onStart();
        // Comprobar si el usuario tiene iniciada sesión
            //Sesion iniciada: saltar a actividad principal MAPA
            //Sesion no iniciada: saltar a actividad LOGIN
        FirebaseUser currentUser = myAuth.getCurrentUser();
        if(currentUser != null) { //Ya hay usuario logeado, pasar a menu principal
            myIntent = new Intent(this, MainActivityMAPA.class);
            startActivity(myIntent);
            //this.finish();
        } else if (currentUser == null) { //Si el usuario no ha iniciado sesion, llevar a la pantalla LOGIN
            List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().setRequireName(false).build(), new AuthUI.IdpConfig.GoogleBuilder().build());
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().
                    setAvailableProviders(providers)
                    .setLogo(R.drawable.logo_main)
                    .build(), RC_SIGN_IN);
        }
    }

    @Override //Al volver de la actividad de LOGIN se comprueba el resultado
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) { //Login correcto, avanzar a pantalla principal
                myIntent = new Intent(this, MainActivityMAPA.class);
                startActivity(myIntent);
                //this.finish();
            } else { //Login fallido, comprobar motivo y actuar
                if(response == null) { //El usuario ha cancelado el login pulsando el botón atras
                    //Mostrar mensaje cancelado?
                    return;
                } else if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //Mostrar mensaje NO_NETWORK
                } else if (response.getError().getErrorCode() == ErrorCodes.EMAIL_MISMATCH_ERROR) {
                    //Mostrar mensaje email incorrecto
                } else if(response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //Mostrar mensaje error, intentar login de nuevo en unos segundos
                }
            }
        }
    }
    @Override
    public void onBackPressed() { }
}
