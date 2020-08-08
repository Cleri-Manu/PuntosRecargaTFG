package es.usal.tfg1.ViewC.pr_activity_fragmentos;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;

import java.io.InputStream;
import java.util.Scanner;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.pr_activity_fragmentos.info.info;
import es.usal.tfg1.ViewC.pr_activity_fragmentos.listaPR.prLista;
import es.usal.tfg1.ViewC.StartActivity;
import es.usal.tfg1.databinding.ActivityPrBinding;
import es.usal.tfg1.vm.VM;

public class PRActivity extends AppCompatActivity implements prLista.OnPRSelectedListener, info.OnPuntuarSelectedListener, info.OnPRDelListener, info.OnPRModListener, info.OnPRGoListener {
    private Toolbar myToolbar;
    public VM myVM;
    private ActivityPrBinding binding;
    private NavController navController;
    final private int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pr);
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
                if (navController.getCurrentDestination().getId() == R.id.navigation_nuevo) {
                    return;
                } else {
                    navController.navigate(R.id.navigation_nuevo);
                }
            }
        });

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController.getCurrentDestination().getId() == R.id.navigation_usuario) {
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
                myVM.onDestinationChangeUsuario(destination.getId(), R.id.navigation_usuario);
                myVM.onDestinationChangeResetNuevo(destination.getId(), R.id.navigation_nuevo);
                myVM.onDestinationChangeResetPuntuar(destination.getId(), R.id.navigation_puntuar);
                myVM.onDestinationChangeList(destination.getId(), R.id.navigation_pr_list);
                myVM.onDestinationChangeSearch(destination.getId(), R.id.navigation_pr_list_search);
            }
        });

        myVM.getErrorToast().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(PRActivity.this, R.string.toast_error_gen, Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.b_load_pr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myVM.isAdmin()) {
                    try {
                        readJSON();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Lee el jsno adjunto con los puntos de recarga
     * @throws JSONException
     */
    private void readJSON() throws JSONException {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.pr);
        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();
        while(scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String s = builder.toString();
        parseJSON(s);
    }

    /**
     * Hace una llmada a la funcion para añadir los puntos de recarfa deljson
     * @param jsonText
     * @throws JSONException
     */
    private void parseJSON(String jsonText) throws JSONException {
        myVM.createOfficialPRs(jsonText);
    }

    /**
     * Cierra la sesion del usuario
     * @param view
     */
    public void logOutUser(View view) {
        final Intent myIntent = new Intent(this, StartActivity.class);
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
    /**
     * Si se selcciona un punto de recarga navegar a la pantalla de info indicando que se cargue la de este
     */
    public void onPRSelected(int position) {
        if (navController.getCurrentDestination().getId() == R.id.navigation_info) {
            return;
        } else {
            myVM.setSelectedPR(position);
            navController.navigate(R.id.navigation_info);
        }
    }

    @Override
    /**
     * Si se selecciona puntuacr navegar a la pantalla de puntuar
     */
    public void onPuntuarSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_puntuar) {
            return;
        } else {
            navController.navigate(R.id.navigation_puntuar);
        }
    }

    @Override
    /**
     * Si se selcciona borrar punto de recarga navegar a la lista de puntos de recarga cercanos
     */
    public void onPRDelSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_pr_list) {
            return;
        } else {
            navController.navigate(R.id.navigation_pr_list);
            Toast.makeText(this, R.string.toast_PR_eliminado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    /**
     * Si se selecciona modificar un punto de recarga navegar a la pantalla de nuevo pero con los datos de ese punto de recarga
     */
    public void onPRModSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_nuevo) {
            return;
        } else {
            myVM.modSelectedTrue();
            navController.navigate(R.id.navigation_nuevo);
        }
    }

    @Override
    /**
     * Si se seleeciona elboton de ir a un punto de recarga navegar a la pantalla de mapa e indicar esto
     */
    public void onPRGoSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_mapa) {
            return;
        } else {
            myVM.modSelectedTrue();
            navController.navigate(R.id.navigation_mapa);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                }
                break;
        }
    }



}

