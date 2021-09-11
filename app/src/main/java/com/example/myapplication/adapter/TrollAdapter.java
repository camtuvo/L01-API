package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model._Math;
import com.example.myapplication.model._Q1;
import com.example.myapplication.model._Q2;
import com.example.myapplication.model._Q3;
import com.example.myapplication.model._Q4;
import com.example.myapplication.model._Trochoi;
import com.example.myapplication.model._Trolls;
import com.example.myapplication.other.ItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrollAdapter extends RecyclerView.Adapter<TrollAdapter.ViewHolder> {

    private _Trochoi trochoi;
    private Context context;
    private int s=0;
    List<_Math> arrayList = new ArrayList<>();
    public TrollAdapter(Context context,_Trochoi trochoi) {
        this.trochoi = trochoi;
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
        addList();
        holder.tvCauhoi.setText(arrayList.get(position).question);
        holder.A.setText(arrayList.get(position).options.get(0));
        holder.B.setText(arrayList.get(position).options.get(1));
        holder.C.setText(arrayList.get(position).options.get(2));
        holder.D.setText(arrayList.get(position).options.get(3));
        int k = position;
        holder.A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =arrayList.get(k).answer;
                if (holder.A.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        holder.B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =arrayList.get(k).answer;
                if (holder.B.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        holder.C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =arrayList.get(k).answer;
                if (holder.C.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
        holder.D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans =arrayList.get(k).answer;
                if (holder.D.getText().toString().equals(ans))
                    s=1; else s=0;
                savePrefs(k+1+"",s);
            }
        });
//        arrayList.size()
        savePrefs("Tong",4);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //CODE HERE
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
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
    private void addList(){
        List<String> list = new ArrayList<>();
        list.add(trochoi.quiz.trolls.q1.options.get(0));
        list.add(trochoi.quiz.trolls.q1.options.get(1));
        list.add(trochoi.quiz.trolls.q1.options.get(2));
        list.add(trochoi.quiz.trolls.q1.options.get(3));
        _Math data = new _Math(trochoi.quiz.trolls.q1.question,list,trochoi.quiz.trolls.q1.answer);
        arrayList.add(data);
        List<String> list1 = new ArrayList<>();
        list1.add(trochoi.quiz.trolls.q2.options.get(0));
        list1.add(trochoi.quiz.trolls.q2.options.get(1));
        list1.add(trochoi.quiz.trolls.q2.options.get(2));
        list1.add(trochoi.quiz.trolls.q2.options.get(3));
        _Math data1 = new _Math(trochoi.quiz.trolls.q2.question,list1,trochoi.quiz.trolls.q2.answer);
        arrayList.add(data1);
        List<String> list3 = new ArrayList<>();
        list3.add(trochoi.quiz.trolls.q3.options.get(0));
        list3.add(trochoi.quiz.trolls.q3.options.get(1));
        list3.add(trochoi.quiz.trolls.q3.options.get(2));
        list3.add(trochoi.quiz.trolls.q3.options.get(3));
        _Math data3 = new _Math(trochoi.quiz.trolls.q3.question,list3,trochoi.quiz.trolls.q3.answer);
        arrayList.add(data3);
        List<String> list4 = new ArrayList<>();
        list4.add(trochoi.quiz.trolls.q4.options.get(0));
        list4.add(trochoi.quiz.trolls.q4.options.get(1));
        list4.add(trochoi.quiz.trolls.q4.options.get(2));
        list4.add(trochoi.quiz.trolls.q4.options.get(3));
        _Math data4 = new _Math(trochoi.quiz.trolls.q4.question,list4,trochoi.quiz.trolls.q4.answer);
        arrayList.add(data4);
    }
    private void savePrefs(String cauhoi, int diem){
        SharedPreferences pr = context.getSharedPreferences("Trolls", MODE_PRIVATE);
        SharedPreferences.Editor editor = pr.edit();
        editor.putString(cauhoi,diem+"");
        editor.commit();
    }

}
