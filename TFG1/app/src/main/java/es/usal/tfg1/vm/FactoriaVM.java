package es.usal.tfg1.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.usal.tfg1.model.DAO;

public class FactoriaVM extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DAO myDAO = new DAO();
        return (T) new VM(myDAO);
    }
}
