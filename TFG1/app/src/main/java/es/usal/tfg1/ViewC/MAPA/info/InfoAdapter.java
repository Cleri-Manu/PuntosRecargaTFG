package es.usal.tfg1.ViewC.MAPA.info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.model.Puntuacion;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {
    private ArrayList<Puntuacion> puntuaciones =  new ArrayList<Puntuacion>();

    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_puntuacion, parent, false);
        return new InfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder holder, int position) {
        Puntuacion p = puntuaciones.get(position);
        holder.punt_text.setText(p.getComentario());
        holder.punt_rating_bar.setRating((float)p.getPuntuacion());

    }

    @Override
    public int getItemCount() {
        return puntuaciones.size();
    }

    public void setPuntuciones(ArrayList<Puntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
        notifyDataSetChanged();
    }

    class InfoHolder extends RecyclerView.ViewHolder {
        private TextView punt_text;
        private AppCompatRatingBar punt_rating_bar;

        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            punt_text = itemView.findViewById(R.id.punt_text);
            punt_rating_bar = itemView.findViewById(R.id.punt_rating_bar);
        }
    }
}
