package com.example.myapplication;

import static com.example.myapplication.other.ServiceAPI.BASE_Service;
import static com.example.myapplication.other.ShowNotifyUser.dismissProgressDialog;
import static com.example.myapplication.other.ShowNotifyUser.showProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.adapter.MathAdapter;
import com.example.myapplication.model._Trochoi;
import com.example.myapplication.other.DataToken;
import com.example.myapplication.other.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Math extends AppCompatActivity {
    private RecyclerView rclMathList;

    private DataToken dataToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
        rclMathList = findViewById(R.id.rclMathList);

        dataToken = new DataToken(Activity_Math.this);
        showProgressDialog(Activity_Math.this, "Đang tải dữ liệu... ");
        getMath();


//        DBMath db = new DBMath(this);
//        for (int i = 1; i <= 50; i++) {
//            db.insertMath(new MathS("Day la cau hoi","A","B","C","D","dapan"));
//        }
//         ArrayList arrayList = new ArrayList<>(db.getAll());
//        rclMathList.setHasFixedSize(true);  //thiết lập để cuộn mượt hơn
//        MathAdapter mathAdapter = new MathAdapter(arrayList, Activity_Math.this, db);
//        //LINEAR
//        LinearLayoutManager linearLayoutMath= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        //linearLayoutManager.scrollToPosition(10); //Thiết lập phần tử hiển thị mặc định nếu muốn
//        rclMathList.setLayoutManager(linearLayoutMath);
//        rclMathList.setAdapter(mathAdapter);
    }

    private void getMath() {
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rclMathList.setLayoutManager(linearLayoutManager);

            MathAdapter adapter = new MathAdapter(Activity_Math.this, trochoi.getQuiz().maths);
            rclMathList.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        Toast.makeText(this, trochoi.getQuiz().maths.size()+" hallo", Toast.LENGTH_LONG).show();
        dismissProgressDialog();
    }

    private void handleError(Throwable error) {
        dismissProgressDialog();
        Toast.makeText(Activity_Math.this, error.toString(), Toast.LENGTH_LONG).show();
    }
}