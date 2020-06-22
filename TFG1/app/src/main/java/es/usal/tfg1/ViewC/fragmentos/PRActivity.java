package es.usal.tfg1.ViewC.fragmentos;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.transform.Result;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.fragmentos.info.info;
import es.usal.tfg1.ViewC.fragmentos.listaPR.prLista;
import es.usal.tfg1.ViewC.StartActivity;
import es.usal.tfg1.databinding.ActivityPrBinding;
import es.usal.tfg1.vm.VM;

public class PRActivity extends AppCompatActivity implements prLista.OnPRSelectedListener, info.OnPuntuarSelectedListener, info.OnPRDelListener, info.OnPRModListener, info.OnPRGoListener {
    private Toolbar myToolbar;
    private FirebaseUser currentUser;
    private FirebaseFirestore firestore;
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

    private void parseJSON(String jsonText) throws JSONException {
        myVM.createOfficialPRs(jsonText);
    }

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
    public void onPRSelected(int position) {
        if (navController.getCurrentDestination().getId() == R.id.navigation_info) {
            return;
        } else {
            myVM.setSelectedPR(position);
            navController.navigate(R.id.navigation_info);
        }
    }

    @Override
    public void onPuntuarSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_puntuar) {
            return;
        } else {
            navController.navigate(R.id.navigation_puntuar);
        }
    }

    @Override
    public void onPRDelSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_pr_list) {
            return;
        } else {
            navController.navigate(R.id.navigation_pr_list);
            Toast.makeText(this, R.string.toast_PR_eliminado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPRModSelected() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_nuevo) {
            return;
        } else {
            myVM.modSelectedTrue();
            navController.navigate(R.id.navigation_nuevo);
        }
    }

    @Override
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

