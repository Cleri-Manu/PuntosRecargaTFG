package es.usal.tfg1.ViewC.MAPA.usuario;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.MAPA.dialog.CustomDialog;
import es.usal.tfg1.ViewC.MainActivityLogin;
import es.usal.tfg1.databinding.FragmentUsuarioBinding;
import es.usal.tfg1.model.Usuario;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link usuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class usuario extends Fragment {
    private FragmentUsuarioBinding binding;
    private VM myVM;
    private CustomDialog myDialog;
    private int changeField = -1;
    private String tempStringEmailPass;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public usuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment usuario.
     */
    // TODO: Rename and change types and number of parameters
    public static usuario newInstance(String param1, String param2) {
        usuario fragment = new usuario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false);
        myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        binding.setMyVM(myVM);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
    @Override
    public void  onStart() {
        super.onStart();
        getView().findViewById(R.id.textEmailUser).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                myVM.textFocusUser(v, hasFocus);
            }

        });
        getView().findViewById(R.id.buttonEmailUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText temp = (EditText)getView().findViewById(R.id.textEmailUser);
                tempStringEmailPass = temp.getText().toString();
                if(tempStringEmailPass.equals("")){
                    showEmptyToast();
                    return;
                }

                changeField = 1; //1 Se corresponde con cambiar Email , 2 con cambiar contraseña
                myDialog = new CustomDialog();
                myDialog.show(getActivity().getSupportFragmentManager(), "myDialog");
                //myVM.openDialog(getActivity().getSupportFragmentManager());
            }
        });
        final Observer<Boolean> toastBoolObs = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean myToastBool) {
                if(myDialog == null) return;                //Si el cuadro de dialogo no esta creado, o no se ha seleccionado un tipo de cambio correcto, no hacer nada
                if(changeField == 1) {                      //Cambio de correo
                    if(myToastBool) {                       //Si el login ha sucedido con exito
                        changeField = 0;                    //Indicamos que el siguiente acceso al metodo es para comrpobar que se ha podido realizar el cambio en el usuario
                        changeEmail();
                    } else {                                //Si no
                        changeField = -1;                   //devolvemos el valor por defecto a la variable
                        showToast(false);
                    }

                } else if(changeField == 2) {               //Cambio de contraseña
                    if(myToastBool) {                       //Si el login ha sucedido con exito
                        changeField = 0;                    //Indicamos que el siguiente acceso al metodo es para comrpobar que se ha podido realizar el cambio en el usuario
                    } else {                                //Si no
                        showToast(false);
                        changeField = -1;                   //devolvemos el valor por defecto a la variable
                    }
                } else if (changeField == 0) {              //toast indicando si se han realizadolos cambios correctamente
                    if(myToastBool) {                       //Si el cambio se ha realizado
                        showToast(true);
                    } else {                                //Si no
                        showToast(false);
                    }
                    tempStringEmailPass = "";
                    changeField = -1;
                }
                myDialog.dismiss();

            }
        };
        myVM.gettoastVisibility().observe(this, toastBoolObs);
    }

    public void logOutUser(View view) {
        final Intent myIntent = new Intent(getActivity(), MainActivityLogin.class);
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(myIntent);
                    }
                });
    }

    public void changeEmail() {
        myVM.changeEmail(tempStringEmailPass);
    }

    public void showToast(Boolean myToastBool) {
        //Mostrar toast segun el valor del bool
        if(myToastBool) { //Mostrar toast exito
            Toast.makeText(getContext(), R.string.toast_succes_login, Toast.LENGTH_SHORT).show();
        } else { //Mostrar toast fallo
            Toast.makeText(getContext(), R.string.toast_error_login, Toast.LENGTH_SHORT).show();
        }
    }

    public void showEmptyToast() {
        Toast.makeText(getContext(), R.string.toast_empty, Toast.LENGTH_SHORT).show();
    }

}
