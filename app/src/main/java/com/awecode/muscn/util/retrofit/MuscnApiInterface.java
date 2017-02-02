package com.awecode.muscn.util.retrofit;

import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.injuries.InjuriesResponse;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;
import com.awecode.muscn.model.http.recentresults.RecentResultsResponse;
import com.awecode.muscn.model.http.topscorers.TopScorersResponse;
import com.awecode.muscn.model.registration.RegistrationPostData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by munnadroid on 1/27/16.
 */
public interface MuscnApiInterface {

    @GET("api/v1/fixtures")
    Observable<FixturesResponse> getFixtures();

    @GET("api/v1/league_table")
    Observable<List<LeagueTableResponse>> getLeague();

    /**
     * fetch manutd recent match results list
     */
    @GET("api/v1/recent_results/")
    Observable<RecentResultsResponse> getResults();

    /**
     * fetch epl matchweek results list
    */
    @GET("api/v1/fixtures/epl_matchweek/")
    Observable<ResponseBody> getEplMatchweekFixtures();

    @GET("api/v1/top_scorers")
    Observable<List<TopScorersResponse>> getTopScorers();

    @GET("api/v1/injuries")
    Observable<InjuriesResponse> getInjuredPlayers();

    @POST("api/v1/user_device/")
    Observable<RegistrationPostData> postRegistrationData(@Body RegistrationPostData registrationPostData);

}
