package com.imjaspreet.mvvmpattern.data;

import com.imjaspreet.mvvmpattern.data.model.RedditNewsResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jaspreet on 06/06/17.
 */

public interface InterfaceApi {

    public final static String BASE_URL = "https://www.reddit.com";

    @GET("/top.json")
    Observable<RedditNewsResponse> getTop(@Query("limit") String limit);

}
