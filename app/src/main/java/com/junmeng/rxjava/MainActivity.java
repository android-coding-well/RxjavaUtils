package com.junmeng.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.junmeng.rxutil.NetProgressSubscriber;
import com.junmeng.rxutil.NetSubscriber;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    GithubService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         service = retrofit.create(GithubService.class);

    }

    public void onClickNetProgressSubscriber(View view) {
        service.listRepos("huweijian5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetProgressSubscriber<List<Repo>>(this) {

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.i(TAG,"NetProgressSubscriber--onBefore");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.i(TAG,"NetProgressSubscriber--onAfter");
                    }


                    @Override
                    public void onSuccess(List<Repo> repos) {
                        Log.i(TAG,"NetProgressSubscriber--onSuccess:"+repos.size());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        Log.i(TAG,"NetProgressSubscriber--onFailed");
                    }
                });
    }

    public void onClickNetSubscriber(View view) {
        service.listRepos("huweijian5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<List<Repo>>() {

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.i(TAG,"NetSubscriber--onBefore");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.i(TAG,"NetSubscriber--onAfter");
                    }


                    @Override
                    public void onSuccess(List<Repo> repos) {
                        Log.i(TAG,"NetSubscriber--onSuccess:"+repos.size());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        Log.i(TAG,"NetSubscriber--onFailed");
                    }
                });
    }
}
