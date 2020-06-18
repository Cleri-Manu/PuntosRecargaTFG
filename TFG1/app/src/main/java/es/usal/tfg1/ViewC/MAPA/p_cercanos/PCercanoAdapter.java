package es.usal.tfg1.ViewC.MAPA.p_cercanos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.model.PuntoRecarga;

public class PCercanoAdapter extends RecyclerView.Adapter<PCercanoAdapter.PCercanoHolder> {
    private ArrayList<PuntoRecarga> PRList = new ArrayList<PuntoRecarga>();
    private  OnPRListener onPRListener;

    public PCercanoAdapter(OnPRListener onPRListener) {
        this.onPRListener = onPRListener;
    }
    @NonNull
    @Override
    public PCercanoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_cercano, parent, false);
        return new PCercanoHolder(itemView, onPRListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PCercanoHolder holder, int position) {
        PuntoRecarga currentPR = PRList.get(position);
        if(currentPR.isEco()) {
            holder.PR_icon_green.setVisibility((View.VISIBLE));
            holder.PR_icon_n.setVisibility((View.INVISIBLE));
        } else {
            holder.PR_icon_green.setVisibility((View.INVISIBLE));
            holder.PR_icon_n.setVisibility((View.VISIBLE));
        }
        holder.PR_name.setText(currentPR.getNombre());
        holder.PR_dist.setText(currentPR.getDistancia());
        holder.PR_rating_bar.setRating((float)currentPR.getPuntuacion());
        if (currentPR.isVerificado()) {
            holder.PR_ver.setVisibility(View.VISIBLE);
        } else {
            holder.PR_ver.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return PRList.size();
    }

    public void setPRList(ArrayList<PuntoRecarga> PRList) {
        this.PRList = PRList;
        notifyDataSetChanged();
    }

    class PCercanoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ShapeableImageView PR_icon_green;
        private ShapeableImageView PR_icon_n;
        private LinearLayoutCompat separator_PR;
        private TextView PR_name;
        private RatingBar PR_rating_bar;
        private TextView PR_dist;
        private TextView PR_ver;
        OnPRListener onPRListener;

        public PCercanoHolder(@NonNull View itemView, OnPRListener onPRListener) {
            super(itemView);
            PR_icon_green = itemView.findViewById(R.id.PR_icon_green);
            PR_icon_n = itemView.findViewById(R.id.PR_icon_n);
            separator_PR = itemView.findViewById(R.id.separator_PR);
            PR_name = itemView.findViewById(R.id.PR_name);
            PR_rating_bar = itemView.findViewById(R.id.PR_rating_bar);
            PR_dist = itemView.findViewById(R.id.PR_dist);
            PR_ver = itemView.findViewById(R.id.PR_ver);
            this.onPRListener = onPRListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onPRListener.onPRClick(getAdapterPosition());
        }
    }
    public interface OnPRListener {
        void onPRClick(int pos);

    }
}
