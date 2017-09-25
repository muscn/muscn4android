package com.awecode.muscn.util.retrofit;

import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.injuries.InjuriesResponse;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;
import com.awecode.muscn.model.http.partners.PartnersResponse;
import com.awecode.muscn.model.http.recent_results.RecentResultsResponse;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.model.http.signin.SignInData;
import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.model.http.top_scorers.TopScorersResponse;
import com.awecode.muscn.model.membership.MembershipResponse;
import com.awecode.muscn.model.registration.RegistrationPostData;
import com.awecode.muscn.model.registration.RegistrationResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by munnadroid on 1/27/16.
 */
public interface MuscnApiInterface {

    @GET("api/v1/fixtures/")
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
    Observable<RegistrationResponse> postRegistrationData(@Body RegistrationPostData registrationPostData);

    @GET("api/v1/fixture_detail/{result_id}/")
    Observable<ResultDetailsResponse> getResultDetails(@Path("result_id") int resultId);

    @POST("api/v1/users/")
    Observable<SignUpPostData> postSignUpData(@Body SignUpPostData signUpPostData);

    @GET("api/v1/users/membership/")
    Observable<SignInSuccessData> getMembershipDetails();

    @POST("api/v1/obtain_auth_token/")
    Observable<SignInSuccessData> doSignIn(@Body SignInData signInData);

    @Multipart
    @POST("api/v1/users/membership/")
    Observable<SignInSuccessData> postMembershipData(@PartMap Map<String, RequestBody> map,@Part MultipartBody.Part file);

    @GET("api/v1/partners/")
    Observable<PartnersResponse> getPartnersList();

}
