
package com.awecode.muscn.model.http.eplmatchweek;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;


@Getter
public class EplMatchweekResponse extends RealmObject {

    @SerializedName("home_team")
    @Expose
    private String homeTeam;
    @SerializedName("away_team")
    @Expose
    private String awayTeam;
    @SerializedName("kickoff")
    @Expose
    private String kickoff;
    @SerializedName("live")
    @Expose
    private String live;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("eid")
    @Expose
    private String eid;
    @SerializedName("minute")
    @Expose
    private String minute;

    @SerializedName("fixture_id")
    @Expose
    private String fixtureId;

}
