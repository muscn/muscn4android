package com.awecode.muscn.util.retrofit;

import com.awecode.muscn.util.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by munnadroid on 1/28/16.
 */
//demo url http://192.168.0.120:8000
//liveurl http://manutd.org.np/

public class ServiceGenerator {
    private static final String TAG = ServiceGenerator.class.getSimpleName();

    private static final int TIME_OUT = 15;
    public static final String API_BASE_URL = "http://manutd.org.np/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static Retrofit retrofit;
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {

        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder;
                requestBuilder = original.newBuilder()
                        .header("key", Constants.DISTRIBUTION_KEY);

                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        httpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        httpClient.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(TIME_OUT, TimeUnit.SECONDS);


        retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
