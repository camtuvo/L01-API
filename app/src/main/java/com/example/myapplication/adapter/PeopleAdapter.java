package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.BXH;
import com.example.myapplication.other.ItemClickListener;

import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private ArrayList<BXH> alBXH;
    private Context context;

    public PeopleAdapter(ArrayList<BXH> alBXH, Context context) {
        this.alBXH = alBXH;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bxh, parent, false);
        return new ViewHolder(view);
    }

    int f=0;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(alBXH.get(position).getFullName());
        holder.tvHang.setText(String.valueOf(alBXH.get(position).getScore()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Toast.makeText(context, "Đang chọn bạn "+ alBXH.get(pos).getFullName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alBXH.size(); //cai nay ne!
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvHang, tvName;

        public ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHang = itemView.findViewById(R.id.tvHang);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this); //cai nay ne!
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getAdapterPosition());
        }
        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
}
