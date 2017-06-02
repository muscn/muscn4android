package com.awecode.muscn.util.retrofit.feed;

import com.awecode.muscn.model.simplexml.Rss;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by munnadroid on 6/2/17.
 */

public interface FeedApiInterface {
    @GET("rss/sportonline_uk_edition/football/teams/m/man_utd/rss.xml")
    Observable<Rss> getManutdFeed();
}
