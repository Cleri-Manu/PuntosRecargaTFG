package es.usal.tfg1.ViewC.MAPA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.MAPA.p_cercanos.p_cercanos;
import es.usal.tfg1.ViewC.MainActivityLogin;
import es.usal.tfg1.databinding.ActivityMainMapaBinding;
import es.usal.tfg1.vm.VM;

public class MainActivityMAPA extends AppCompatActivity implements p_cercanos.OnPRSelectedListener {
    private Toolbar myToolbar;
    private FirebaseUser currentUser;
    private FirebaseFirestore firestore;
    public VM myVM;
    private ActivityMainMapaBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate view and obtain an instance of the binding class.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_mapa);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());



        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        myVM = new ViewModelProvider(this).get(VM.class);
        myVM.initializeValues();
        binding.setMyVM(myVM);
        myVM.checkNewUser(FirebaseAuth.getInstance().getCurrentUser(), this);
        myToolbar = findViewById(R.id.main_toolbar); //Obtener referencia
        setSupportActionBar(myToolbar);              //Asiganarla
        findViewById(R.id.b_add_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navController.getCurrentDestination().getId() == R.id.navigation_nuevo) {
                    return;
                } else {
                    navController.navigate(R.id.navigation_nuevo);
                }
            }
        });

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


        //Control de la interfaz en cambios de fragmentos
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                myVM.onDestinationChangeUsuario(destination.getId(), R.id.navigation_usuario);
                myVM.onDestinationChangeResetNuevo(destination.getId(), R.id.navigation_nuevo);
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

    public void newPR(View view) {

    }


    @Override
    public void onPRSelected(int position) {
        if(navController.getCurrentDestination().getId() == R.id.navigation_info) {
            return;
        } else {
            myVM.setSelectedPR(position);
            navController.navigate(R.id.navigation_info);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof p_cercanos) {
            p_cercanos p_cercanosFragment = (p_cercanos) fragment;
            p_cercanosFragment.setOnHeadlineSelectedListener(this);
        }
    }

}

