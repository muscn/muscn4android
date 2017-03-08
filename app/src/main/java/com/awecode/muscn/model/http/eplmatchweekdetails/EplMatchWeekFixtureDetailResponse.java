
package com.awecode.muscn.model.http.eplmatchweekdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EplMatchWeekFixtureDetailResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Integer mufcScore;
    @SerializedName("opponent_score")
    @Expose
    private Integer opponentScore;
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

    /**
     * No args constructor for use in serialization
     * 
     */
    public EplMatchWeekFixtureDetailResponse() {
    }

    /**
     * 
     * @param broadcastOn
     * @param competitionYear
     * @param data
     * @param isHomeGame
     * @param round
     * @param remarks
     * @param isToday
     * @param venue
     * @param opponentScore
     * @param id
     * @param opponent
     * @param mufcScore
     * @param datetime
     */
    public EplMatchWeekFixtureDetailResponse(Integer id, Boolean isToday, Boolean isHomeGame, String datetime, String round, String venue, String broadcastOn, Integer mufcScore, Integer opponentScore, String remarks, String data, Opponent opponent, CompetitionYear competitionYear) {
        super();
        this.id = id;
        this.isToday = isToday;
        this.isHomeGame = isHomeGame;
        this.datetime = datetime;
        this.round = round;
        this.venue = venue;
        this.broadcastOn = broadcastOn;
        this.mufcScore = mufcScore;
        this.opponentScore = opponentScore;
        this.remarks = remarks;
        this.data = data;
        this.opponent = opponent;
        this.competitionYear = competitionYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsToday() {
        return isToday;
    }

    public void setIsToday(Boolean isToday) {
        this.isToday = isToday;
    }

    public Boolean getIsHomeGame() {
        return isHomeGame;
    }

    public void setIsHomeGame(Boolean isHomeGame) {
        this.isHomeGame = isHomeGame;
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

    public Integer getMufcScore() {
        return mufcScore;
    }

    public void setMufcScore(Integer mufcScore) {
        this.mufcScore = mufcScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(Integer opponentScore) {
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
