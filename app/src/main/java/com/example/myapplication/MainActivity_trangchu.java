package com.example.myapplication;

import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.fragment.Fragment_BXH;
import com.example.myapplication.fragment.Fragment_Math;
import com.example.myapplication.fragment.Fragment_Troll;
import com.example.myapplication.model.Accounts;
import com.example.myapplication.model.Info;
import com.example.myapplication.model.Message;
import com.example.myapplication.model.MessageAccount;
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

public class MainActivity_trangchu extends AppCompatActivity {
    private Button btnTroll, btnMath, btnBXH;
    private TextView tvHello;
    private DataToken dataToken;
    Boolean f= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_trangchu);
        tvHello = findViewById(R.id.tvHello);
        btnTroll = findViewById(R.id.btnTroll);
        btnMath = findViewById(R.id.btnMath);
        btnBXH = findViewById(R.id.btnBXH);

        dataToken = new DataToken(MainActivity_trangchu.this);

        SharedPreferences pr = getSharedPreferences("dangnhap",MODE_PRIVATE);
        tvHello.setText("Hi, "+pr.getString("fullname","") +"\nCùng chiến đấu thôi");

        btnBXH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity_trangchu.this, Activity_BXH.class));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (f==false){
                    Fragment_BXH fragmentBxh = new Fragment_BXH();
                    transaction.add(R.id.fragment, fragmentBxh);
                    f=true;
                } else  transaction.replace(R.id.fragment, new Fragment_BXH());
                transaction.commit();
            }
        });
        btnTroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity_trangchu.this, Activity_Troll.class);
//                startActivity(intent);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (f==false){
                    transaction.add(R.id.fragment, new Fragment_Troll());
                    f=true;
                } else  transaction.replace(R.id.fragment, new Fragment_Troll());
                transaction.commit();
            }
        });
        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity_trangchu.this, Activity_Math.class));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (f==false){
                    transaction.add(R.id.fragment, new Fragment_Math());
                    f=true;
                } else  transaction.replace(R.id.fragment, new Fragment_Math());
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemMenu:
                showProgressDialog(MainActivity_trangchu.this, "Đang lấy thông tin...");
                SharedPreferences pr = getSharedPreferences("dangnhap",MODE_PRIVATE);
                GetUserByID(pr.getString("mssv",""));
                break;
        }
        return super.onOptionsItemSelected(item);
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
        SharedPreferences pr = getSharedPreferences("dangnhap",MODE_PRIVATE);
        GetUserByID(pr.getString("mssv",""));
    }
    private void GetUserByID(String id) {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.GetUser(dataToken.getToken(), id) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleResponse(MessageAccount message) {
        dismissProgressDialog();
        try {
            if (message.getStatus()==1){
                Button btnOK, btnCanecl;
                EditText edtMSSV, edtEmail, edtName, edtPhone;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity_trangchu.this);
                LayoutInflater inflater = MainActivity_trangchu.this.getLayoutInflater();
                View dialogview = inflater.inflate(R.layout.layout_thaydoithongtin, null);
                btnOK = dialogview.findViewById(R.id.btnOK);
                btnCanecl = dialogview.findViewById(R.id.btnCanecl);
                edtMSSV = dialogview.findViewById(R.id.edtMSSV);
                edtEmail = dialogview.findViewById(R.id.edtEmail);
                edtName = dialogview.findViewById(R.id.edtName);
                edtPhone = dialogview.findViewById(R.id.edtPhone);

                edtMSSV.setText(message.getAccount().getMssv());
                edtEmail.setText(message.getAccount().getEmail());
                edtName.setText(message.getAccount().getFullName());
                edtPhone.setText(message.getAccount().getPhone());

                dialog.setView(dialogview);
                dialog.setTitle("Thay đổi thông tin");
                AlertDialog bb = dialog.create();
                bb.show();
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtPhone.getText().toString().length()==0 || edtName.getText().toString().length()==0 )
                            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                        else {
                            Info info = new Info(edtMSSV.getText().toString(), edtName.getText().toString(), edtEmail.getText().toString(), edtPhone.getText().toString());
                            Change_info(info);
                            tvHello.setText("Hi, "+ edtName.getText().toString() +"\nCùng chiến đấu thôi");
                            bb.cancel();
                        }
                    }
                });
                btnCanecl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bb.cancel();
                    }
                });
            } else Toast.makeText(this,"Lấy thông tin người chơi thất bại", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        savePrefs(message.getAccount().getScore());
    }
    private void handleError(Throwable throwable) {
        dismissProgressDialog();
    }


    private void Change_info(Info info) {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.Changeinfo(dataToken.getToken(), info) //here!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsechange, this::handleError)
        );
    }
    private void handleResponsechange(Message message) {
        try {
            Toast.makeText(getApplicationContext(), message.getNotification(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void savePrefs(int diem){
        SharedPreferences pr = this.getSharedPreferences("dangnhap", MODE_PRIVATE);
        SharedPreferences.Editor editor = pr.edit();
        editor.putString("score",diem+"");
        editor.commit();
    }
}