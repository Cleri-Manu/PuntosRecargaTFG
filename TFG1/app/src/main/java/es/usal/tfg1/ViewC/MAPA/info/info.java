package es.usal.tfg1.ViewC.MAPA.info;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.databinding.FragmentInfoBinding;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.model.Puntuacion;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class info extends Fragment {
    FragmentInfoBinding binding;
    private VM myVM;
    private OnPuntuarSelectedListener puntuarSelectedListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment info.
     */
    // TODO: Rename and change types and number of parameters
    public static info newInstance(String param1, String param2) {
        info fragment = new info();
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
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        binding.setMyVM(myVM);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_p_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final InfoAdapter adapter = new InfoAdapter();
        recyclerView.setAdapter(adapter);
        myVM.getInfoRecyclerListData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Puntuacion>>() {
            @Override
            public void onChanged(ArrayList<Puntuacion> puntuaciones) {
                adapter.setPuntuciones(puntuaciones);
            }
        });
        myVM.loadRecyclerList();
        getView().findViewById(R.id.button_info_punt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puntuarSelectedListener.onPuntuarSelected();
            }
        });
    }

    public interface OnPuntuarSelectedListener {
        public void onPuntuarSelected();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnPuntuarSelectedListener){
            puntuarSelectedListener = (OnPuntuarSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement listener for info");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        puntuarSelectedListener = null;
    }

}
