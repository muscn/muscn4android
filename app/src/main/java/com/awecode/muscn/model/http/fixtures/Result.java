
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

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


    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Boolean getToday() {
        return isToday;
    }

    public void setToday(Boolean today) {
        isToday = today;
    }

    public Boolean getHomeGame() {
        return isHomeGame;
    }

    public void setHomeGame(Boolean homeGame) {
        isHomeGame = homeGame;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getBroadcastOn() {
        return broadcastOn;
    }

    public void setBroadcastOn(String broadcastOn) {
        this.broadcastOn = broadcastOn;
    }

    public String getMufcScore() {
        return mufcScore;
    }

    public void setMufcScore(String mufcScore) {
        this.mufcScore = mufcScore;
    }

    public String getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(String opponentScore) {
        this.opponentScore = opponentScore;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public CompetitionYear getCompetitionYear() {
        return competitionYear;
    }

    public void setCompetitionYear(CompetitionYear competitionYear) {
        this.competitionYear = competitionYear;
    }
}
