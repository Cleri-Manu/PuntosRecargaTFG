package es.usal.tfg1.ViewC.MAPA.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.MainActivityLogin;
import es.usal.tfg1.databinding.FragmentUsuarioBinding;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link usuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class usuario extends Fragment {
    private FragmentUsuarioBinding binding;
    private VM myVM;
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
        getActivity().findViewById(R.id.textEmailUser).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                myVM.textFocusUser(v, hasFocus);
            }

        });
       /* //COmprobar si se esta editando el texto de email o contraseña,en ese caso mostrar el boton para poder guardar los cambios
        EditText textEmB = (EditText)getActivity().findViewById(R.id.textEmailUser);
        textEmB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myVM.onTextChange(R.id.textEmailUser);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText textPassB = (EditText)getActivity().findViewById(R.id.textPassUser);
        textPassB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myVM.onTextChange(R.id.textPassUser);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
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

    public void changeEmail(View view) {
        myVM.changeEmail(view, (EditText) getActivity().findViewById(R.id.textEmailUser));
    }

}
