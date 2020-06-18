package es.usal.tfg1.ViewC.MAPA.nuevo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import es.usal.tfg1.R;
import es.usal.tfg1.databinding.FragmentNuevoBinding;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nuevo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevo extends Fragment {
    private FragmentNuevoBinding binding;
    private VM myVM;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nuevo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nuevo.
     */
    // TODO: Rename and change types and number of parameters
    public static nuevo newInstance(String param1, String param2) {
        nuevo fragment = new nuevo();
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
        binding = FragmentNuevoBinding.inflate(inflater, container, false);
        myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        binding.setMyVM(myVM);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void  onStart() {
        super.onStart();
        getView().findViewById(R.id.nuevo_frameLayout_n).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVM.nuevoChargerNClick();
            }
        });
        getView().findViewById(R.id.nuevo_frameLayout_g).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVM.nuevoChargerGClick();
            }
        });
        //Añadir nuevo punto de recarga
        getView().findViewById(R.id.nuevo_b_crear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nombre = (EditText) getView().findViewById(R.id.nuevo_nombe_editText);
                EditText lat = (EditText) getView().findViewById(R.id.nuevo_lat_editText);
                EditText lon = (EditText) getView().findViewById(R.id.nuevo_lon_editText);
                EditText desc = (EditText) getView().findViewById(R.id.nuevo_descripcion_editText);
                myVM.addNewPR(nombre.getText().toString(), lat.getText().toString(), lon.getText().toString(), desc.getText().toString());
            }
        });

        myVM.getNuevoToastFillFields().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) { //Si ha fallado porque los campos estan vacios mostrar toast avisando de esto
                    Toast.makeText(getContext(), R.string.toast_nuevo_fill_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });
        myVM.getNuevoToastPRAlreadyExists().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) { //Si ha fallado porque los campos estan vacios mostrar toast avisando de esto
                    Toast.makeText(getContext(), R.string.toast_nuevo_already_exists, Toast.LENGTH_SHORT).show();
                } else if(!aBoolean) { //Si funciona mostrar toast avisando
                    Toast.makeText(getContext(), R.string.toast_nuevo_added, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
