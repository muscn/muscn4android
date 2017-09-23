package com.awecode.muscn.util.retrofit.feed;

import com.awecode.muscn.model.simplexml.Rss;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by munnadroid on 6/2/17.
 */

public interface FeedApiInterface {
    @GET("rss/football/manchester-united/feed")
    Observable<Rss> getManutdFeed();
}
