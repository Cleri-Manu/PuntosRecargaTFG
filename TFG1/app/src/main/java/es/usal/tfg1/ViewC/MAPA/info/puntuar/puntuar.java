package es.usal.tfg1.ViewC.MAPA.info.puntuar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import es.usal.tfg1.R;
import es.usal.tfg1.databinding.FragmentPuntuarBinding;
import es.usal.tfg1.model.Puntuacion;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link puntuar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class puntuar extends Fragment {
    private FragmentPuntuarBinding binding;
    private VM myVM;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public puntuar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment puntuar.
     */
    // TODO: Rename and change types and number of parameters
    public static puntuar newInstance(String param1, String param2) {
        puntuar fragment = new puntuar();
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
        binding = FragmentPuntuarBinding.inflate(inflater, container, false);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Puntuacion puntuacion = myVM.checkUserPunt();
        if(puntuacion != null) {
            AppCompatRatingBar ratingBar =  (AppCompatRatingBar)getView().findViewById(R.id.PR_puntuar_rating_bar);
            ratingBar.setRating(puntuacion.getPuntuacion());
            EditText editText = (EditText)getView().findViewById(R.id.puntuacion_opinion_editText);
            editText.setText(puntuacion.getComentario());
        }
        getView().findViewById(R.id.puntuacion_b_crear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)getView().findViewById(R.id.puntuacion_opinion_editText);
                AppCompatRatingBar ratingBar =  (AppCompatRatingBar)getView().findViewById(R.id.PR_puntuar_rating_bar);
                myVM.newPuntuacion(editText.getText().toString(), ratingBar.getRating());
            }
        });
        myVM.getPuntuacionToast().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) { //exito
                    Toast.makeText(getContext(), R.string.toast_punt_publicada, Toast.LENGTH_SHORT).show();
                } else { //fallo
                    Toast.makeText(getContext(), R.string.toast_punt_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
