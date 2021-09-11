package com.example.myapplication.other;

import com.example.myapplication.model.Accounts;
import com.example.myapplication.model.BXH;
import com.example.myapplication.model.Info;
import com.example.myapplication.model.Message;
import com.example.myapplication.model.MessageAccount;
import com.example.myapplication.model.MessageLogin;
import com.example.myapplication.model._Trochoi;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_Service="https://hoccungminh.dinhnt.com/";

    @GET("api/token")
    Observable<Token> GetToken();

    @POST("api/register")
    Observable<Message> Register(@Header("Authorization") String token, @Body Info info);

    @POST("api/login")
    Observable<MessageLogin> Login(@Header("Authorization") String token, @Body Info info);

    @GET("api/ranking")
    Observable<ArrayList<BXH>> GetBXH(@Header("Authorization") String token);

    @GET("api/details")
    Observable<MessageAccount> GetUser(@Header("Authorization") String token, @Query("id") String id);

    @POST("api/change-info")
    Observable<Message> Changeinfo(@Header("Authorization") String token, @Body Info info);

    @GET("api/quiz")
    Observable<_Trochoi> GetQuiz(@Header("Authorization") String token);


    @POST("api/update-score")
    Observable<Message> UpdateBXH(@Header("Authorization") String token, @Body Accounts acc);

}
