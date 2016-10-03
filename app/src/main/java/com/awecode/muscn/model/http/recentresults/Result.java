
package com.awecode.muscn.model.http.recentresults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

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

    /**
     * 
     * @return
     *     The isHomeGame
     */
    public Boolean getIsHomeGame() {
        return isHomeGame;
    }

    /**
     * 
     * @param isHomeGame
     *     The is_home_game
     */
    public void setIsHomeGame(Boolean isHomeGame) {
        this.isHomeGame = isHomeGame;
    }

    /**
     * 
     * @return
     *     The opponentName
     */
    public String getOpponentName() {
        return opponentName;
    }

    /**
     * 
     * @param opponentName
     *     The opponent_name
     */
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    /**
     * 
     * @return
     *     The mufcScore
     */
    public Integer getMufcScore() {
        return mufcScore;
    }

    /**
     * 
     * @param mufcScore
     *     The mufc_score
     */
    public void setMufcScore(Integer mufcScore) {
        this.mufcScore = mufcScore;
    }

    /**
     * 
     * @return
     *     The venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * 
     * @param venue
     *     The venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * 
     * @return
     *     The opponentScore
     */
    public Integer getOpponentScore() {
        return opponentScore;
    }

    /**
     * 
     * @param opponentScore
     *     The opponent_score
     */
    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

    /**
     * 
     * @return
     *     The opponentCrest
     */
    public String getOpponentCrest() {
        return opponentCrest;
    }

    /**
     * 
     * @param opponentCrest
     *     The opponent_crest
     */
    public void setOpponentCrest(String opponentCrest) {
        this.opponentCrest = opponentCrest;
    }

    /**
     * 
     * @return
     *     The opponentShortName
     */
    public String getOpponentShortName() {
        return opponentShortName;
    }

    /**
     * 
     * @param opponentShortName
     *     The opponent_short_name
     */
    public void setOpponentShortName(String opponentShortName) {
        this.opponentShortName = opponentShortName;
    }

    /**
     * 
     * @return
     *     The competitionName
     */
    public String getCompetitionName() {
        return competitionName;
    }

    /**
     * 
     * @param competitionName
     *     The competition_name
     */
    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    /**
     * 
     * @return
     *     The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * 
     * @param datetime
     *     The datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
