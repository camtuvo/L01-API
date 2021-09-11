package com.example.myapplication;

import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.Info;
import com.example.myapplication.model.MessageLogin;
import com.example.myapplication.other.Token;
import com.example.myapplication.other.DataToken;
import com.example.myapplication.other.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText edtMSSV, edtEmail;
    private Button btnDangnhap, btnDangky;

    private DataToken dataToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtMSSV = findViewById(R.id.edtMSSV);
        edtEmail = findViewById(R.id.edtEmail);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        btnDangky = findViewById(R.id.btnDangky);

        dataToken = new DataToken(MainActivity.this);
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog(MainActivity.this, "Đang đăng nhập...");
                if(!dataToken.getToken().equals("")){
                    Info info = new Info(edtMSSV.getText().toString(), edtEmail.getText().toString());
                    LoginAPI(info);
                }else {
                    getTokenAPI();
                }
//                startActivity(new Intent(MainActivity.this, MainActivity_trangchu.class));
            }
        });
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_dangky.class);
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
        Info info = new Info(edtMSSV.getText().toString(), edtEmail.getText().toString());
        LoginAPI(info);
    }

    private void LoginAPI(Info info) {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.Login(dataToken.getToken(), info) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(MessageLogin message) {
        dismissProgressDialog();
        try {
            Toast.makeText(getApplicationContext(), message.getNotification(), Toast.LENGTH_SHORT).show();
            if (message.getStatus() == 1) {
                savePrefs(message.getMssv(), message.getFullName());
                startActivity(new Intent(MainActivity.this, MainActivity_trangchu.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
    }


    private void savePrefs(String userID, String fullname){
        SharedPreferences pr = getSharedPreferences("dangnhap",MODE_PRIVATE);
        SharedPreferences.Editor editor = pr.edit();
        editor.putString("mssv",userID);
        editor.putString("fullname",fullname);
        editor.putString("score", 0+"");
        editor.commit();
    }
}