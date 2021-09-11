package com.example.myapplication;

import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.Info;
import com.example.myapplication.model.Message;
import com.example.myapplication.other.DataToken;
import com.example.myapplication.other.ServiceAPI;
import com.example.myapplication.other.Token;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_dangky extends AppCompatActivity {
    private EditText edtMSSV, edtName, edtEmail,edtSDT;
    private Button btnDangnhap, btnDangky;

    private DataToken dataToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        edtMSSV = findViewById(R.id.edtMSSV);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtSDT = findViewById(R.id.edtSDT);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        btnDangky = findViewById(R.id.btnDangky);

        dataToken = new DataToken(Activity_dangky.this);
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog(Activity_dangky.this, "Đang đăng ký...");
                if(!dataToken.getToken().equals("")){
                    Info info = new Info(edtMSSV.getText().toString(), edtName.getText().toString(), edtEmail.getText().toString(), edtSDT.getText().toString());
                    RegisterAPI(info);
                }else {
                    getTokenAPI();
                }
            }
        });
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_dangky.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
        Info info = new Info(edtMSSV.getText().toString(), edtName.getText().toString(), edtEmail.getText().toString(), edtSDT.getText().toString());
        RegisterAPI(info);
    }
    private void RegisterAPI(Info info) {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.Register(dataToken.getToken(), info) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Message message) {
        try {
            Toast.makeText(getApplicationContext(), message.getNotification(), Toast.LENGTH_LONG).show();
            if (message.getStatus() == 1) {
                startActivity(new Intent(Activity_dangky.this, MainActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissProgressDialog();
    }

    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(Activity_dangky.this, "Lỗi", Toast.LENGTH_SHORT).show();
    }
}