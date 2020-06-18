package es.usal.tfg1.ViewC.MAPA.p_cercanos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.databinding.FragmentPCrecanosBinding;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link p_cercanos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class p_cercanos extends Fragment implements PCercanoAdapter.OnPRListener {
    private FragmentPCrecanosBinding binding;
    private VM myVM;
    OnPRSelectedListener callback;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public p_cercanos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment p_crecanos.
     */
    // TODO: Rename and change types and number of parameters
    public static p_cercanos newInstance(String param1, String param2) {
        p_cercanos fragment = new p_cercanos();
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
        binding = FragmentPCrecanosBinding.inflate(inflater, container, false);
        myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        binding.setMyVM(myVM);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void  onStart() {
        super.onStart();
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_p_cercanos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final PCercanoAdapter adapter = new PCercanoAdapter(this);
        recyclerView.setAdapter(adapter);
        myVM.getRecyclerListData().observe(this, new Observer<ArrayList<PuntoRecarga>>() {
            @Override
            public void onChanged(ArrayList<PuntoRecarga> puntosRecarga) {
                adapter.setPRList(puntosRecarga);
            }
        });
        myVM.loadRecyclerList();
    }

    @Override
    public void onPRClick(int pos) {
        //Navegar al nuevo fragmento, pero primero dar valores a los campos en el VM
        //Toast de prueba
        Toast.makeText(getContext(), myVM.getRecyclerListData().getValue().get(pos).getId(), Toast.LENGTH_SHORT).show();
        callback.onPRSelected(pos);
    }

    public void setOnHeadlineSelectedListener(OnPRSelectedListener callback) {
        this.callback = callback;
    }

    public interface OnPRSelectedListener {
        public void onPRSelected(int position);
    }

}


