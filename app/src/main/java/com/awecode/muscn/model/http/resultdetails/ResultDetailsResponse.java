
package com.awecode.muscn.model.http.resultdetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultDetailsResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_home_game")
    @Expose
    private Boolean isHomeGame;
    @SerializedName("opponent_name")
    @Expose
    private String opponentName;
    @SerializedName("mufc_score")
    @Expose
    private Integer mufcScore;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("opponent_score")
    @Expose
    private Integer opponentScore;
    @SerializedName("opponent_crest")
    @Expose
    private String opponentCrest;
    @SerializedName("opponent_short_name")
    @Expose
    private String opponentShortName;
    @SerializedName("competition_name")
    @Expose
    private String competitionName;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("broadcast_on")
    @Expose
    private String broadcastOn;
    @SerializedName("goals")
    @Expose
    private List<Goal> goals = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResultDetailsResponse() {
    }

    /**
     * 
     * @param opponentCrest
     * @param broadcastOn
     * @param data
     * @param competitionName
     * @param isHomeGame
     * @param venue
     * @param opponentScore
     * @param id
     * @param goals
     * @param mufcScore
     * @param opponentShortName
     * @param datetime
     * @param opponentName
     */
    public ResultDetailsResponse(Integer id, Boolean isHomeGame, String opponentName, Integer mufcScore, String venue, Integer opponentScore, String opponentCrest, String opponentShortName, String competitionName, String datetime, Data data, String broadcastOn, List<Goal> goals) {
        super();
        this.id = id;
        this.isHomeGame = isHomeGame;
        this.opponentName = opponentName;
        this.mufcScore = mufcScore;
        this.venue = venue;
        this.opponentScore = opponentScore;
        this.opponentCrest = opponentCrest;
        this.opponentShortName = opponentShortName;
        this.competitionName = competitionName;
        this.datetime = datetime;
        this.data = data;
        this.broadcastOn = broadcastOn;
        this.goals = goals;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsHomeGame() {
        return isHomeGame;
    }

    public void setIsHomeGame(Boolean isHomeGame) {
        this.isHomeGame = isHomeGame;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public Integer getMufcScore() {
        return mufcScore;
    }

    public void setMufcScore(Integer mufcScore) {
        this.mufcScore = mufcScore;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

    public String getOpponentCrest() {
        return opponentCrest;
    }

    public void setOpponentCrest(String opponentCrest) {
        this.opponentCrest = opponentCrest;
    }

    public String getOpponentShortName() {
        return opponentShortName;
    }

    public void setOpponentShortName(String opponentShortName) {
        this.opponentShortName = opponentShortName;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getBroadcastOn() {
        return broadcastOn;
    }

    public void setBroadcastOn(String broadcastOn) {
        this.broadcastOn = broadcastOn;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

}
