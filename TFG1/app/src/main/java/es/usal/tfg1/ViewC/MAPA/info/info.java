package es.usal.tfg1.ViewC.MAPA.info;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;


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
    private OnPRDelListener onPRDelListener;
    private  OnPRModListener onPRModListener;
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

        myVM.checkUserCanDel();
        getView().findViewById(R.id.button_Del_PR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if(myVM.getInfoUserCanDel().getValue()) {
                    //Toast.makeText(getContext(), "BOrrando", Toast.LENGTH_SHORT).show();
                    builder1.setMessage(R.string.d_del_PR);
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myVM.delPR();
                                    dialog.dismiss();
                                    onPRDelListener.onPRDelSelected();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                } else {
                    //Toast.makeText(getContext(), "REportando", Toast.LENGTH_SHORT).show();
                    builder1.setMessage(R.string.d_del_PR_Report);
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myVM.reportPR();
                                    dialog.dismiss();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                }
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        myVM.getInfoReportToast().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Toast.makeText(getContext(), R.string.toast_PR_report, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.toast_PR_report_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        getView().findViewById(R.id.info_mod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPRModListener.onPRModSelected();
            }
        });
    }

    public interface OnPuntuarSelectedListener {
        public void onPuntuarSelected();
    }

    public interface OnPRDelListener {
        public void onPRDelSelected();
    }

    public interface OnPRModListener {
        public void onPRModSelected();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnPuntuarSelectedListener){
            puntuarSelectedListener = (OnPuntuarSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement listener of selected for info");
        }
        if(context instanceof OnPRDelListener){
            onPRDelListener = (OnPRDelListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement listener of del for info");
        }
        if(context instanceof OnPRModListener){
            onPRModListener = (OnPRModListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement listener of del for info");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        puntuarSelectedListener = null;
    }

}
