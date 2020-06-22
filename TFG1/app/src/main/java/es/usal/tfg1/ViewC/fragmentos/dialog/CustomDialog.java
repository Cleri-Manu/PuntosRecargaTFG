package es.usal.tfg1.ViewC.fragmentos.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import es.usal.tfg1.R;
import es.usal.tfg1.vm.VM;

public class CustomDialog extends AppCompatDialogFragment {
    private EditText pass;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater myInflater = getActivity().getLayoutInflater();
        View myView = myInflater.inflate(R.layout.custom_dialog_pass, null);

        myBuilder.setView(myView);

        pass = myView.findViewById(R.id.textPassDialog);
        AppCompatButton bCancel = myView.findViewById(R.id.button_dialog_Cancel);
        AppCompatButton bContinue = myView.findViewById(R.id.button_dialog_Continue);
        final VM myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equals("")) {
                    //Si no se ha puesto ninguna contraseña se muestra un toast avisando de esto
                    Toast.makeText(getContext(), R.string.toast_no_pass, Toast.LENGTH_SHORT).show();
                } else {
                    //Lamada a VM para hacer re-log del usuario en Repository con firebase
                    myVM.reLogUserEmail(pass.getText().toString());
                    //Despues de esa llamada se cierra cuadro de dialogo
                    dismiss();
                }
            }
        });
        return myBuilder.create();
    }
}
