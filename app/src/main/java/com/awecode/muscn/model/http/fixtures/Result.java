
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result extends RealmObject {

    @SerializedName("resultId")
    @Expose
    private Integer resultId;
    @SerializedName("is_today")
    @Expose
    private Boolean isToday;
    @SerializedName("is_home_game")
    @Expose
    private Boolean isHomeGame;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("round")
    @Expose
    private String round;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("broadcast_on")
    @Expose
    private String broadcastOn;
    @SerializedName("mufc_score")
    @Expose
    private String mufcScore;
    @SerializedName("opponent_score")
    @Expose
    private String opponentScore;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("opponent")
    @Expose
    private Opponent opponent;
    @SerializedName("competition_year")
    @Expose
    private CompetitionYear competitionYear;


    public Result() {
    }

}
