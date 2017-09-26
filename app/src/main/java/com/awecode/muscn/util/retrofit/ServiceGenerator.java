package com.awecode.muscn.util.retrofit;

import android.text.TextUtils;

import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.PrefsHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by munnadroid on 1/28/16.
 */
//demo url http://192.168.0.120:8000
//liveurl https://manutd.org.np/

public class ServiceGenerator {
    private static final String TAG = ServiceGenerator.class.getSimpleName();

    private static final int TIME_OUT = 15;
    public static final String API_BASE_URL = "https://manutd.org.np/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static Retrofit retrofit;
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create());
    private static MyInterceptor interceptor;

    public static <S> S createService(Class<S> serviceClass) {

        if (interceptor == null)
            interceptor = new MyInterceptor();
        httpClient.interceptors().add(interceptor);

//        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        httpClient.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(TIME_OUT, TimeUnit.SECONDS);


        retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit retrofit() {
        return retrofit;

    }

    private static class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = original.newBuilder()
                    .header("key", Constants.DISTRIBUTION_KEY);

            if (!TextUtils.isEmpty(PrefsHelper.getLoginToken())) {
                requestBuilder.header("Authorization", "token " + PrefsHelper.getLoginToken());
            }


            Request request = requestBuilder.build();
            Response response = chain.proceed(request);
            readPaymentStatus(response.headers());
            return response;
        }
    }

    private static void readPaymentStatus(Headers headers) {
        try {
            String status = headers.get("status");
            SignInSuccessData data = PrefsHelper.getLoginResponse();
            if (!TextUtils.isEmpty(status) && data != null) {
                data.setStatus(status);
                PrefsHelper.saveLoginResponse(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
