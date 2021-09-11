package com.example.myapplication.fragment;

import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Activity_BXH;
import com.example.myapplication.R;
import com.example.myapplication.adapter.PeopleAdapter;
import com.example.myapplication.model.BXH;
import com.example.myapplication.other.DataToken;
import com.example.myapplication.other.ServiceAPI;
import com.example.myapplication.other.Token;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_BXH extends Fragment {
    private RecyclerView rclBXH;
    private DataToken dataToken;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__bxh, container, false);
        rclBXH = view.findViewById(R.id.rclBXH);

        showProgressDialog(getContext(), "Đang tải dữ liệu... ");

        dataToken = new DataToken(getContext());
        getInfoBXH();
        return view;
    }

    private void getInfoBXH() {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.GetBXH(dataToken.getToken()) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<BXH> infoCourses) {
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rclBXH.setLayoutManager(linearLayoutManager);
            PeopleAdapter courseAdapter = new PeopleAdapter(infoCourses, getContext());
            rclBXH.setAdapter(courseAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissProgressDialog();
    }

    private void handleError(Throwable error) {
        dismissProgressDialog();
        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
    }
}