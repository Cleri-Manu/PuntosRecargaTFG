package es.usal.tfg1.ViewC.MAPA;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.MainActivityLogin;
import es.usal.tfg1.databinding.ActivityMainMapaBinding;
import es.usal.tfg1.model.Usuario;
import es.usal.tfg1.vm.VM;

public class MainActivityMAPA extends AppCompatActivity {
    private Toolbar myToolbar;
    private FirebaseUser currentUser;
    private FirebaseFirestore firestore;
    public VM myVM;
    private ActivityMainMapaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate view and obtain an instance of the binding class.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_mapa);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());



        final BottomNavigationView navView = findViewById(R.id.nav_view);
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        myVM = new ViewModelProvider(this).get(VM.class);
        myVM.initializeValues();
        binding.setMyVM(myVM);

        //Gestion de datos del usuario, si no existe sera necesario crear uno nuevo en Firebase
        myVM.checkNewUser(FirebaseAuth.getInstance().getCurrentUser(), this);
        /*final Observer<Usuario> userObs = new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario user) {
                //Inicializar el usuario porprimera vez
                myVM.addUser();
            }
        };
        myVM.getUsuario().observe(this, userObs);*/

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



        //Control de la interfaz en cambios de fragmentos
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                myVM.onDestinationChangeUsuario(destination.getId(), R.id.navigation_usuario);
            }
        });

        //Control de cambios de interfaz en userFragment
        //(EditText)findViewById(R.id.textEmailUser).addText








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

    public void changeEmail(View view) {
        myVM.changeEmail(view, (EditText)findViewById(R.id.textEmailUser));
    }




}
