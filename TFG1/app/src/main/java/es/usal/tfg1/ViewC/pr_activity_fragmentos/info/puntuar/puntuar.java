package es.usal.tfg1.ViewC.pr_activity_fragmentos.info.puntuar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    /**
     * Binding de la vista
     */
    private FragmentPuntuarBinding binding;
    /**
     * ViewModel
     */
    private VM myVM;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public puntuar() { }

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
    /**
     * Cuando la activiad se crea se añaden observers para los botones y toast de menseajes de exito/fallo
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Puntuacion puntuacion = myVM.checkUserPunt();
        if(puntuacion != null) {
            AppCompatRatingBar ratingBar =  (AppCompatRatingBar)getView().findViewById(R.id.PR_puntuar_rating_bar);
            ratingBar.setRating(puntuacion.getPuntuacion());
            EditText editText = (EditText)getView().findViewById(R.id.puntuacion_opinion_editText);
            editText.setText(puntuacion.getComentario());
            myVM.changePuntuarDelVisibility();
        } else {
            myVM.changePuntuarDelVisibilityFalse();
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
        getView().findViewById(R.id.buttonDelPuntuacion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(R.string.d_del_punt);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myVM.delPuntuacionButton();
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

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
}
