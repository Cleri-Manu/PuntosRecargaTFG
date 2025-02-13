package es.usal.tfg1.ViewC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.pr_activity_fragmentos.PRActivity;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;
    private FirebaseUser currentUser;
    private int RC_SIGN_IN = 1;
    final private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 2;
    final private int MY_PERMISSIONS_INTERNET = 3;
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
        currentUser = myAuth.getCurrentUser();
        checkLocationPerm();
    }

    /**
     * Comprobar el estado de login del usuario y mostrar la pantalla de autentficacion o continuar a PRACtivity
     */
    public void continueStart() {
        if(currentUser != null) { //Ya hay usuario logeado, pasar a menu principal
            myIntent = new Intent(this, PRActivity.class);
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
    /**
     * Al volver de la actividad de LOGIN se comprueba el resultado
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) { //Login correcto, avanzar a pantalla principal
                myIntent = new Intent(this, PRActivity.class);
                startActivity(myIntent);
            }
        }
    }
    @Override
    public void onBackPressed() { }

    @Override
    /**
     * Pedir permisos
     */
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkInternetPerm();
                } else {
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_INTERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    continueStart();
                } else {
                    finish();
                }
                return;
            }

        }
    }

    public void checkLocationPerm() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        } else {
            checkInternetPerm();
        }
    }

    public void checkInternetPerm() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_INTERNET);
        } else {
            continueStart();
        }
    }
}
