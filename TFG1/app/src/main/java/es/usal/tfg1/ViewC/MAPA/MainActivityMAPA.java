package es.usal.tfg1.ViewC.MAPA;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.MainActivityLogin;

public class MainActivityMAPA extends AppCompatActivity {
    private Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mapa);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        //Indicar que la toolbar reemplaza la actionbar en esta actividad
        myToolbar = findViewById(R.id.main_toolbar); //Obtener referencia
        setSupportActionBar(myToolbar);              //Asiganarla
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navController.getCurrentDestination().getId() == R.id.navigation_usuario) {
                    return;
                } else {
                    navController.navigate(R.id.navigation_usuario);
                }
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_usuario) {
                    navView.setVisibility(View.INVISIBLE);
                    /*
                    navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
                    navView.getMenu().getItem(0).setVisible(false);
                    navView.getMenu().getItem(1).setVisible(false);
                    navView.getMenu().getItem(2).setVisible(false);*/
                } else {
                    navView.setVisibility(View.VISIBLE);
                    /*
                    navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
                    navView.getMenu().getItem(0).setVisible(true);
                    navView.getMenu().getItem(1).setVisible(true);
                    navView.getMenu().getItem(2).setVisible(true);*/

                }
            }
        });


    }

    public void logOutUser(View view) {
        final Intent myIntent = new Intent(this, MainActivityLogin.class);
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(myIntent);
                    }
                });
    }




}
