package com.imjaspreet.mvvmpattern.data;

import android.util.Log;

import com.imjaspreet.mvvmpattern.App;
import com.imjaspreet.mvvmpattern.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by jaspreet on 06/06/17.
 */

public class Injector {

    private static Retrofit provideRetrofit (String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( provideOkHttpClient() )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }

    private static OkHttpClient provideOkHttpClient () {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideHeaderInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .cache( provideCache() )
                .build();
    }

    private static Cache provideCache () {
        Cache cache = null;
        try {
            cache = new Cache( new File( App.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    private static Interceptor provideOfflineCacheInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request request = chain.request();

                if ( !App.hasNetwork() ) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }
                return chain.proceed( request );
            }
        };
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor () {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger(){
                    @Override
                    public void log (String message) {
                        Log.d("Injector", message);
                    }
                } );
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? BODY : NONE );
        return httpLoggingInterceptor;
    }

    private static Interceptor provideHeaderInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        };
    }

    public static InterfaceApi provideApi () {
        return provideRetrofit( InterfaceApi.BASE_URL ).create( InterfaceApi.class );
    }
}
