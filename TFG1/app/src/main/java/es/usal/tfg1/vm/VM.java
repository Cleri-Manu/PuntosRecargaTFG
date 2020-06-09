package es.usal.tfg1.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.usal.tfg1.model.DAO;
import es.usal.tfg1.model.Usuario;

public class VM extends ViewModel {
    private DAO myDAO = new DAO();

    public LiveData<Usuario> getUsuario() {
        return myDAO.getLDUsuario();
    }

    public VM(DAO myDAO) {
        this.myDAO = myDAO;
    }
}
