package com.awecode.muscn.util.retrofit;

import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.injuries.InjuriesResponse;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;
import com.awecode.muscn.model.http.topscorers.TopScorersResponse;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by munnadroid on 1/27/16.
 */
public interface MuscnApiInterface {

    @GET("api/v1/fixtures")
    Observable<FixturesResponse> getFixtures();

    @GET("api/v1/league_table")
    Observable<List<LeagueTableResponse>> getLeague();

    @GET("api/v1/top_scorers")
    Observable<List<TopScorersResponse>> getTopScorers();

    @GET("api/v1/injuries")
    Observable<InjuriesResponse> getInjuredPlayers();

}
