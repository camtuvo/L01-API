package com.example.myapplication;

import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

public class Activity_BXH extends AppCompatActivity {
    private RecyclerView rclBXH;
    private DataToken dataToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bxh);
        rclBXH = findViewById(R.id.rclBXH);

        showProgressDialog(Activity_BXH.this, "Đang tải dữ liệu... ");

        dataToken = new DataToken(Activity_BXH.this);
        if(!dataToken.getToken().equals("")){
            getInfoBXH();
        }else {
            getTokenAPI();
        }
    }

    public void getTokenAPI() {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponses, this::handleError)
        );
    }
    private void handleResponses(Token token) {
        dataToken.saveToken(token.getToken());
        getInfoBXH();
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rclBXH.setLayoutManager(linearLayoutManager);
            PeopleAdapter courseAdapter = new PeopleAdapter(infoCourses, Activity_BXH.this);
            rclBXH.setAdapter(courseAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissProgressDialog();
    }

    private void handleError(Throwable error) {
        dismissProgressDialog();
        Toast.makeText(Activity_BXH.this, "Lỗi", Toast.LENGTH_SHORT).show();
    }
}