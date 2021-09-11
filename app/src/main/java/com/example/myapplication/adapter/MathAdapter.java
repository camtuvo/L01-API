package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity_trangchu;
import com.example.myapplication.R;
import com.example.myapplication.model._Math;
import com.example.myapplication.other.ItemClickListener;

import java.util.List;

public class MathAdapter extends RecyclerView.Adapter<MathAdapter.ViewHolder> {

    private List<_Math> cauhoiList;
    private Context context;
    private int s=0;
    public MathAdapter(Context context, List<_Math> cauhoiList) {
        this.cauhoiList = cauhoiList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_math, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText("Câu "+ (position+1) +": ");
        holder.tvCauhoi.setText(cauhoiList.get(position).question);
        holder.A.setText(cauhoiList.get(position).options.get(0));
        holder.B.setText(cauhoiList.get(position).options.get(1));
        holder.C.setText(cauhoiList.get(position).options.get(2));
        holder.D.setText(cauhoiList.get(position).options.get(3));
        int k = position;
//        holder.tvTitle.setText(arr.get(position).getCauHoi());
        holder.A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =cauhoiList.get(k).answer;
                if (holder.A.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        holder.B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =cauhoiList.get(k).answer;
                if (holder.B.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        holder.C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =cauhoiList.get(k).answer;
                if (holder.C.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        holder.D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =cauhoiList.get(k).answer;
                if (holder.D.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        savePrefs("Tong",cauhoiList.size());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //CODE HERE
            }
        });
    }

    @Override
    public int getItemCount() {
        return cauhoiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle, tvCauhoi;
        public RadioButton A, B, C, D;

        public ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCauhoi = itemView.findViewById(R.id.tvCauhoi);
            A = itemView.findViewById(R.id.A);
            B = itemView.findViewById(R.id.B);
            C = itemView.findViewById(R.id.C);
            D = itemView.findViewById(R.id.D);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getAdapterPosition());
        }
        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
    private void savePrefs(String cauhoi, int diem){
        SharedPreferences pr = context.getSharedPreferences("Maths", MODE_PRIVATE);
        SharedPreferences.Editor editor = pr.edit();
        editor.putString(cauhoi,diem+"");
        editor.commit();
    }
}
