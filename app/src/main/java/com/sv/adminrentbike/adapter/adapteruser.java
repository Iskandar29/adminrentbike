package com.sv.adminrentbike.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sv.adminrentbike.R;
import com.sv.adminrentbike.model.User;

import java.util.ArrayList;

public class adapteruser extends RecyclerView.Adapter<adapteruser.CustomerViewHolder> {
    private ArrayList<User> listUser;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public adapteruser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerViewHolder holder, int position) {
        User field = listUser.get(position);

        holder.tvName.setText(field.getName());
        holder.tvPhone.setText(field.getPhone());
        holder.tvNotkp.setText(field.getNoktp());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listUser.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvNotkp;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvNotkp = itemView.findViewById(R.id.tv_noktp);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(User data);
    }
}
