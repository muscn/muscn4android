package com.awecode.muscn.util.retrofit.feed;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by munnadroid on 6/2/17.
 */

public class FeedClient {


    private static final String BASE_URL = "http://talksport.com/";
    private static OkHttpClient client;
    private static Retrofit retrofit;

    public static <S> S createService(Class<S> serviceClass) {
        if (client == null
                || retrofit == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }
        return retrofit.create(serviceClass);
    }


}
