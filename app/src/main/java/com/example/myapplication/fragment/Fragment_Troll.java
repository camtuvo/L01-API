package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MathAdapter;
import com.example.myapplication.adapter.TrollAdapter;
import com.example.myapplication.model.Accounts;
import com.example.myapplication.model.Message;
import com.example.myapplication.model._Math;
import com.example.myapplication.model._Q1;
import com.example.myapplication.model._Q2;
import com.example.myapplication.model._Q3;
import com.example.myapplication.model._Trochoi;
import com.example.myapplication.model._Trolls;
import com.example.myapplication.other.DataToken;
import com.example.myapplication.other.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Troll extends Fragment {
    private RecyclerView rclTrollList;
    private DataToken dataToken;
    public Button btnOKTroll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__troll, container, false);
        rclTrollList = view.findViewById(R.id.rclTrollList);
        btnOKTroll = view.findViewById(R.id.btnOKTroll);
        btnOKTroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d= readPre();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Kết quả");
                dialog.setMessage("Bạn đúng dc "+d+" (cập nhật là "+updateScore(d)+")");

                dialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences pr = getActivity().getSharedPreferences("dangnhap", MODE_PRIVATE);
                        Accounts acc = new Accounts(pr.getString("mssv",""),updateScore(0));
                        getScore(acc);
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = dialog.create();
                al.show();
            }
        });
        dataToken = new DataToken(getContext());
        showProgressDialog(getContext(), "Đang tải dữ liệu... ");
        getTroll();
        reset();
        return view;
    }
    private void getScore(Accounts acc) {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.UpdateBXH(dataToken.getToken(), acc) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponses, this::handleError)
        );
    }
    private void handleResponses(Message message) {
        Toast.makeText(getContext(), message.getNotification(), Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
    }
    private void getTroll() {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.GetQuiz(dataToken.getToken()) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(_Trochoi trochoi) {
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rclTrollList.setLayoutManager(linearLayoutManager);
            TrollAdapter adapter = new TrollAdapter(getContext(), trochoi);
            rclTrollList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissProgressDialog();
    }

    private void handleError(Throwable error) {
        dismissProgressDialog();
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
    }
    private int readPre(){
        SharedPreferences pr = getActivity().getSharedPreferences("Trolls", MODE_PRIVATE);
        int tong = 0;
        for (int i=1; i<=Integer.parseInt(pr.getString("Tong",""));i++){
            tong = tong+ Integer.parseInt(pr.getString(i+"",""));
        }
        return tong;
    }
    private int updateScore(int diem){
        SharedPreferences readScore = getActivity().getSharedPreferences("dangnhap", MODE_PRIVATE);
        int s = Integer.parseInt(readScore.getString("score",""))+ diem;

        SharedPreferences pr = getActivity().getSharedPreferences("dangnhap", MODE_PRIVATE);
        SharedPreferences.Editor editor = pr.edit();
        editor.putString("score",s+""); //update score
        editor.commit();
        return s;
    }

    private void reset(){
        SharedPreferences pr = getActivity().getSharedPreferences("Trolls", MODE_PRIVATE);
        SharedPreferences.Editor editor = pr.edit();
        for (int i =1; i<=4;i++){
            editor.putString(i+"",0+"");
        }
        editor.putString("Tong",0+"");
        editor.commit();
    }
}