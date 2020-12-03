package com.sv.adminrentbike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sv.adminrentbike.model.Bike;

import java.util.ArrayList;

public class adapterbike extends RecyclerView.Adapter<adapterbike.BikeViewHolder> {
    private ArrayList<Bike> listBike;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public adapterbike(ArrayList<Bike> listUser) {
        this.listBike = listUser;
    }

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_item, parent, false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BikeViewHolder holder, int position) {
        Bike field = listBike.get(position);

        holder.tvKode.setText(field.getKode());
        holder.tvMerk.setText(field.getMerk());
        holder.tvJenis.setText(field.getJenis());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listBike.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBike.size();
    }

    public class BikeViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode, tvMerk, tvJenis;

        public BikeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.tv_kode);
            tvMerk = itemView.findViewById(R.id.tv_merk);
            tvJenis = itemView.findViewById(R.id.tv_jenis);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Bike data);
    }
}
