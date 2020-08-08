package es.usal.tfg1.ViewC.pr_activity_fragmentos.listaPR;

import android.content.Context;
import android.location.Location;
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
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.databinding.FragmentPrListBinding;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.vm.VM;

public class prLista extends Fragment implements PRListaAdapter.OnPRListener {
    private FragmentPrListBinding binding;
    private VM myVM;
    private OnPRSelectedListener prSelectedListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public prLista() { }

    public static prLista newInstance(String param1, String param2) {
        prLista fragment = new prLista();
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
        binding = FragmentPrListBinding.inflate(inflater, container, false);
        myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        binding.setMyVM(myVM);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void  onStart() {
        super.onStart();
    }

    @Override
    /**
     * Cuando la activiad se crea se añaden observers para los botones y toast de menseajes de exito/fallo
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_p_cercanos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final PRListaAdapter adapter = new PRListaAdapter(this);
        recyclerView.setAdapter(adapter);
        FusedLocationProviderClient myClient = LocationServices.getFusedLocationProviderClient(getContext());
        if(myVM.getSearchPR().getValue()) {
            myClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    myVM.loadRecyclerListComplete(task.getResult());
                }
            });
            myVM.getRecyclerListCompleteData().observe(getViewLifecycleOwner(), new Observer<ArrayList<PuntoRecarga>>() {
                @Override
                public void onChanged(ArrayList<PuntoRecarga> puntosRecarga) {
                    adapter.setPRList(puntosRecarga);
                }
            });
        } else if(!myVM.getSearchPR().getValue()) {
            myClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    myVM.loadRecyclerList(task.getResult());
                }
            });
            myVM.getRecyclerListData().observe(getViewLifecycleOwner(), new Observer<ArrayList<PuntoRecarga>>() {
                @Override
                public void onChanged(ArrayList<PuntoRecarga> puntosRecarga) {
                    adapter.setPRList(puntosRecarga);
                }
            });
        }
        getView().findViewById(R.id.img_b_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myVM.getSearchPR().getValue()) {
                    EditText text = (EditText)getView().findViewById(R.id.text_search);
                    if(text.getText().toString().equals("") || text.getText().equals(" ")) {
                        myClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                myVM.loadRecyclerListComplete(task.getResult());
                            }
                        });
                    } else {
                        adapter.setPRList(myVM.changePRCompleteList(text.getText().toString()));
                    }
                }
            }
        });
    }

    @Override
    public void onPRClick(int pos) {
        prSelectedListener.onPRSelected(pos);
    }

    @Override
    /**
     * Inicializa los listener para comunicarse con PRActivity
     */
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnPRSelectedListener){
            prSelectedListener = (OnPRSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement listener for prLista");
        }
    }

    @Override
    /**
     * Borra los listener para comunicarse con PRActivity
     */
    public void onDetach() {
        super.onDetach();
        prSelectedListener = null;
    }

    public interface OnPRSelectedListener {
        public void onPRSelected(int position);
    }

}


