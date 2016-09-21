
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

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
    private Object round;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("broadcast_on")
    @Expose
    private Object broadcastOn;
    @SerializedName("mufc_score")
    @Expose
    private Object mufcScore;
    @SerializedName("opponent_score")
    @Expose
    private Object opponentScore;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("opponent")
    @Expose
    private Opponent opponent;
    @SerializedName("competition_year")
    @Expose
    private CompetitionYear competitionYear;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The isToday
     */
    public Boolean getIsToday() {
        return isToday;
    }

    /**
     * @param isToday The is_today
     */
    public void setIsToday(Boolean isToday) {
        this.isToday = isToday;
    }

    /**
     * @return The isHomeGame
     */
    public Boolean getIsHomeGame() {
        return isHomeGame;
    }

    /**
     * @param isHomeGame The is_home_game
     */
    public void setIsHomeGame(Boolean isHomeGame) {
        this.isHomeGame = isHomeGame;
    }

    /**
     * @return The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @param datetime The datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     * @return The round
     */
    public Object getRound() {
        return round;
    }

    /**
     * @param round The round
     */
    public void setRound(Object round) {
        this.round = round;
    }

    /**
     * @return The venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * @param venue The venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * @return The broadcastOn
     */
    public Object getBroadcastOn() {
        return broadcastOn;
    }

    /**
     * @param broadcastOn The broadcast_on
     */
    public void setBroadcastOn(Object broadcastOn) {
        this.broadcastOn = broadcastOn;
    }

    /**
     * @return The mufcScore
     */
    public Object getMufcScore() {
        return mufcScore;
    }

    /**
     * @param mufcScore The mufc_score
     */
    public void setMufcScore(Object mufcScore) {
        this.mufcScore = mufcScore;
    }

    /**
     * @return The opponentScore
     */
    public Object getOpponentScore() {
        return opponentScore;
    }

    /**
     * @param opponentScore The opponent_score
     */
    public void setOpponentScore(Object opponentScore) {
        this.opponentScore = opponentScore;
    }

    /**
     * @return The remarks
     */
    public Object getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks
     */
    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    /**
     * @return The data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return The opponent
     */
    public Opponent getOpponent() {
        return opponent;
    }

    /**
     * @param opponent The opponent
     */
    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    /**
     * @return The competitionYear
     */
    public CompetitionYear getCompetitionYear() {
        return competitionYear;
    }

    /**
     * @param competitionYear The competition_year
     */
    public void setCompetitionYear(CompetitionYear competitionYear) {
        this.competitionYear = competitionYear;
    }

}
